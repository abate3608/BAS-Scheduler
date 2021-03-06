/*
 * ============================================================================
 * GNU General Public License
 * ============================================================================
 *
 * Copyright (C) 2006-2011 Serotonin Software Technologies Inc. http://serotoninsoftware.com
 * @author Matthew Lohbihler
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * When signing a commercial license with Serotonin Software Technologies Inc.,
 * the following extension to GPL is made. A special exception to the GPL is 
 * included to allow you to distribute a combined work that includes BAcnet4J 
 * without being obliged to provide the source code for any proprietary components.
 */
package edu.psu.sweng500.bacnetserver.bacnet4j2.service.confirmed;

import edu.psu.sweng500.bacnetserver.bacnet4j2.LocalDevice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.exception.BACnetErrorException;
import edu.psu.sweng500.bacnetserver.bacnet4j2.exception.BACnetException;
import edu.psu.sweng500.bacnetserver.bacnet4j2.exception.BACnetServiceException;
import edu.psu.sweng500.bacnetserver.bacnet4j2.obj.BACnetObject;
import edu.psu.sweng500.bacnetserver.bacnet4j2.service.acknowledgement.AcknowledgementService;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.Encodable;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.constructed.Address;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.constructed.PropertyValue;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.ErrorClass;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.ErrorCode;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.PropertyIdentifier;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.ObjectIdentifier;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.OctetString;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.UnsignedInteger;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.BacnetObject;
import edu.psu.sweng500.type.DBSiteRmTempTable;

import java.util.Random;
import java.util.concurrent.Executors;

import com.serotonin.util.queue.ByteQueue;

public class WritePropertyRequest extends ConfirmedRequestService {
	private static final long serialVersionUID = -6047767959151824679L;

	public static final byte TYPE_ID = 15;

	private final ObjectIdentifier objectIdentifier;
	private final PropertyIdentifier propertyIdentifier;
	private final UnsignedInteger propertyArrayIndex;
	private final Encodable propertyValue;
	private final UnsignedInteger priority;

	private final EventHandler eventHandler = EventHandler.getInstance();
	String RoomNumber;

	public WritePropertyRequest(ObjectIdentifier objectIdentifier, PropertyIdentifier propertyIdentifier,
			UnsignedInteger propertyArrayIndex, Encodable propertyValue, UnsignedInteger priority) {
		this.objectIdentifier = objectIdentifier;
		this.propertyIdentifier = propertyIdentifier;
		this.propertyArrayIndex = propertyArrayIndex;
		this.propertyValue = propertyValue;
		this.priority = priority;
	}

	@Override
	public byte getChoiceId() {
		return TYPE_ID;
	}

	@Override
	public void write(ByteQueue queue) {
		write(queue, objectIdentifier, 0);
		write(queue, propertyIdentifier, 1);
		writeOptional(queue, propertyArrayIndex, 2);
		writeEncodable(queue, propertyValue, 3);
		writeOptional(queue, priority, 4);
	}

	WritePropertyRequest(ByteQueue queue) throws BACnetException {
		objectIdentifier = read(queue, ObjectIdentifier.class, 0);
		propertyIdentifier = read(queue, PropertyIdentifier.class, 1);
		propertyArrayIndex = readOptional(queue, UnsignedInteger.class, 2);
		propertyValue = readEncodable(queue, objectIdentifier.getObjectType(), propertyIdentifier, propertyArrayIndex,
				3);
		priority = readOptional(queue, UnsignedInteger.class, 4);
	}

	@Override
	public AcknowledgementService handle(LocalDevice localDevice, Address from, OctetString linkService)
			throws BACnetErrorException {
		BACnetObject obj = localDevice.getObject(objectIdentifier);
		if (obj == null)
			throw new BACnetErrorException(getChoiceId(), ErrorClass.object, ErrorCode.unknownObject);

		PropertyValue pv = new PropertyValue(propertyIdentifier, propertyArrayIndex, propertyValue, priority);
		try {
			if (localDevice.getEventHandler().checkAllowPropertyWrite(obj, pv)) {
				obj.setProperty(pv);
				String Str = obj.getObjectName();
				String retval[] = Str.split("_");
				RoomNumber = "";
				for (int i = 0; i < retval.length-1; i++) {
					if (RoomNumber == "") {
						RoomNumber += retval[i];
					} else {
						RoomNumber += " " + retval[i];
					}
				}

				if (retval[retval.length-1].equals("Status")) {
					
					Executors.newCachedThreadPool().execute(new Runnable() {
						@Override
						public void run() {
							collectHistory(localDevice, RoomNumber);
						}
					});
				} else if (retval[retval.length-1].equals("SpaceTemp")) {
					Encodable value = obj.getProperty(PropertyIdentifier.presentValue);
					eventHandler.fireUpdateSpaceTemp(RoomNumber, Float.valueOf(value.toString()));
				} else if (retval[retval.length-1].equals("UnoccSp")) {
					Encodable value = obj.getProperty(PropertyIdentifier.presentValue);
					eventHandler.fireUpdateUnoccTempSetpoint(RoomNumber, Float.valueOf(value.toString()));
				}
				//eventHandler.fireSaveRoomHistoryData(obj);
				//localDevice.getEventHandler().propertyWritten(obj, pv);
			} else
				throw new BACnetServiceException(ErrorClass.property, ErrorCode.writeAccessDenied);
		} catch (BACnetServiceException e) {
			throw new BACnetErrorException(getChoiceId(), e);
		}

		return null;
	}


	private void collectHistory(LocalDevice localDevice, String RoomNumber) {


		boolean run = true;
		int count = 0;
		BacnetObject obj = new BacnetObject();
		obj.setRoomNumber(RoomNumber);
		BACnetObject b = null;



		b = localDevice.getObject(RoomNumber.replace(" ", "_") + "_OccSched");
		if ( b instanceof BACnetObject ) {
			try {
				Encodable value = b.getProperty(PropertyIdentifier.presentValue);
				obj.setOccState(Integer.valueOf(value.toString()));
			} catch (BACnetServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		b = localDevice.getObject(RoomNumber.replace(" ", "_") + "_OccOptimized");
		if ( b instanceof BACnetObject ) {
			try {
				Encodable value = b.getProperty(PropertyIdentifier.presentValue);
				obj.setOptOccState(Integer.valueOf(value.toString()));
			} catch (BACnetServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		b = localDevice.getObject(RoomNumber.replace(" ", "_") + "_CoolMode");
		if ( b instanceof BACnetObject ) {
			try {
				Encodable value = b.getProperty(PropertyIdentifier.presentValue);
				obj.setCoolMode(Integer.valueOf(value.toString()));
			} catch (BACnetServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		b = localDevice.getObject(RoomNumber.replace(" ", "_") + "_Status");
		if ( b instanceof BACnetObject ) {
			try {
				Encodable value = b.getProperty(PropertyIdentifier.presentValue);
				obj.setStatus(Integer.valueOf(value.toString()));
			} catch (BACnetServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		b = localDevice.getObject(RoomNumber.replace(" ", "_") + "_SpaceTemp");
		if ( b instanceof BACnetObject ) {
			try {
				Encodable value = b.getProperty(PropertyIdentifier.presentValue);
				//obj.setRoomTemp(Float.valueOf(value.toString()));
			} catch (BACnetServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		b = localDevice.getObject(RoomNumber.replace(" ", "_") + "_OccSp");
		if ( b instanceof BACnetObject ) {
			try {
				Encodable value = b.getProperty(PropertyIdentifier.presentValue);
				obj.setOccSetpoint(Float.valueOf(value.toString()));
			} catch (BACnetServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		b = localDevice.getObject(RoomNumber.replace(" ", "_") + "_UnoccSp");
		if ( b instanceof BACnetObject ) {
			try {
				Encodable value = b.getProperty(PropertyIdentifier.presentValue);
				obj.setUnoccSetpoint(Float.valueOf(value.toString()));
			} catch (BACnetServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		b = localDevice.getObject(RoomNumber.replace(" ", "_") + "_Light");
		if ( b instanceof BACnetObject ) {
			try {
				Encodable value = b.getProperty(PropertyIdentifier.presentValue);
				obj.setLight(Float.valueOf(value.toString()));
			} catch (BACnetServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		b = localDevice.getObject("OAT");
		if ( b instanceof BACnetObject ) {
			try {
				Encodable value = b.getProperty(PropertyIdentifier.presentValue);
				obj.setOAT(Math.round(Float.valueOf(value.toString())));
			} catch (BACnetServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		Random rand = new Random();
		float n = 0;
		int min = 0;
		int max = 0;
		while (run) {
			try {
				if (obj.getRoomTemp() == 0) {
					
					if (obj.getStatus() == 1) {
						
						max = Math.round(obj.getUnoccSetpoint());
						min = Math.round(obj.getOccSetpoint()) + 5;
						n =  rand.nextInt((max - min) + 1) + min;
					} else {
						max = Math.round(obj.getOccSetpoint());
						min = Math.round(obj.getOccSetpoint()) - 5;
						n =  rand.nextInt((max - min) + 1) + min;
					}
				} else {
					if (obj.getStatus() == 1) {
						n -= 0.5;  
						if (obj.getRoomTemp() < obj.getOccSetpoint()) {run = false; }
					} else {
						n += 0.5;
						if (obj.getRoomTemp() > obj.getUnoccSetpoint()) {run = false; }
					}
				}

				obj.setRoomTemp(n);

				eventHandler.fireSaveRoomHistoryData(obj);

				long max1 = 30000; //30 seconds
				long min1 = 10000; //10 seconds
				long x =  rand.nextInt((int) ((max1 - min1) + 1)) + min1;
				
				Thread.sleep(x); 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //one minute
		}
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((objectIdentifier == null) ? 0 : objectIdentifier.hashCode());
		result = PRIME * result + ((priority == null) ? 0 : priority.hashCode());
		result = PRIME * result + ((propertyArrayIndex == null) ? 0 : propertyArrayIndex.hashCode());
		result = PRIME * result + ((propertyIdentifier == null) ? 0 : propertyIdentifier.hashCode());
		result = PRIME * result + ((propertyValue == null) ? 0 : propertyValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final WritePropertyRequest other = (WritePropertyRequest) obj;
		if (objectIdentifier == null) {
			if (other.objectIdentifier != null)
				return false;
		} else if (!objectIdentifier.equals(other.objectIdentifier))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (propertyArrayIndex == null) {
			if (other.propertyArrayIndex != null)
				return false;
		} else if (!propertyArrayIndex.equals(other.propertyArrayIndex))
			return false;
		if (propertyIdentifier == null) {
			if (other.propertyIdentifier != null)
				return false;
		} else if (!propertyIdentifier.equals(other.propertyIdentifier))
			return false;
		if (propertyValue == null) {
			if (other.propertyValue != null)
				return false;
		} else if (!propertyValue.equals(other.propertyValue))
			return false;
		return true;
	}
}
