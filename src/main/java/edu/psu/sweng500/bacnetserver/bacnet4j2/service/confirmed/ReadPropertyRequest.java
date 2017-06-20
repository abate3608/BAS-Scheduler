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
import edu.psu.sweng500.bacnetserver.bacnet4j2.service.acknowledgement.ReadPropertyAck;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.Encodable;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.constructed.Address;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.PropertyIdentifier;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.ObjectIdentifier;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.OctetString;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.UnsignedInteger;
import com.serotonin.util.queue.ByteQueue;

public class ReadPropertyRequest extends ConfirmedRequestService {
	private static final long serialVersionUID = 4866163964355156811L;

	public static final byte TYPE_ID = 12;

	private final ObjectIdentifier objectIdentifier;
	private final PropertyIdentifier propertyIdentifier;
	private UnsignedInteger propertyArrayIndex;

	public ReadPropertyRequest(ObjectIdentifier objectIdentifier, PropertyIdentifier propertyIdentifier) {
		this.objectIdentifier = objectIdentifier;
		this.propertyIdentifier = propertyIdentifier;
	}

	public ReadPropertyRequest(ObjectIdentifier objectIdentifier, PropertyIdentifier propertyIdentifier,
			UnsignedInteger propertyArrayIndex) {
		this.objectIdentifier = objectIdentifier;
		this.propertyIdentifier = propertyIdentifier;
		this.propertyArrayIndex = propertyArrayIndex;
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
	}

	ReadPropertyRequest(ByteQueue queue) throws BACnetException {
		objectIdentifier = read(queue, ObjectIdentifier.class, 0);
		propertyIdentifier = read(queue, PropertyIdentifier.class, 1);
		propertyArrayIndex = readOptional(queue, UnsignedInteger.class, 2);
	}

	@Override
	public AcknowledgementService handle(LocalDevice localDevice, Address from, OctetString linkService)
			throws BACnetException {
		Encodable prop;
		try {
			BACnetObject obj = localDevice.getObjectRequired(objectIdentifier);
			prop = obj.getPropertyRequired(propertyIdentifier, propertyArrayIndex);
		} catch (BACnetServiceException e) {
			throw new BACnetErrorException(getChoiceId(), e);
		}
		return new ReadPropertyAck(objectIdentifier, propertyIdentifier, propertyArrayIndex, prop);
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((objectIdentifier == null) ? 0 : objectIdentifier.hashCode());
		result = PRIME * result + ((propertyArrayIndex == null) ? 0 : propertyArrayIndex.hashCode());
		result = PRIME * result + ((propertyIdentifier == null) ? 0 : propertyIdentifier.hashCode());
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
		final ReadPropertyRequest other = (ReadPropertyRequest) obj;
		if (objectIdentifier == null) {
			if (other.objectIdentifier != null)
				return false;
		} else if (!objectIdentifier.equals(other.objectIdentifier))
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
		return true;
	}
}
