package edu.psu.sweng500.bacnetserver.server;

import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

import edu.psu.sweng500.bacnetserver.bacnet4j2.LocalDevice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.RemoteDevice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.event.DeviceEventAdapter;
import edu.psu.sweng500.bacnetserver.bacnet4j2.exception.BACnetServiceException;
import edu.psu.sweng500.bacnetserver.bacnet4j2.npdu.ip.IpNetwork;
import edu.psu.sweng500.bacnetserver.bacnet4j2.obj.BACnetObject;
import edu.psu.sweng500.bacnetserver.bacnet4j2.service.unconfirmed.WhoIsRequest;
import edu.psu.sweng500.bacnetserver.bacnet4j2.transport.Transport;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.Encodable;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.constructed.StatusFlags;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.BinaryPV;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.EngineeringUnits;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.EventState;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.ObjectType;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.PropertyIdentifier;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.CharacterString;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.ObjectIdentifier;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.Real;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.Unsigned16;
import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.*;

public class BacnetServer {
	static LocalDevice localBacnetDevice;
	private static boolean initialized = false;
	// Event listeners
	private final EventHandler eventHandler = EventHandler.getInstance();

	static ArrayList<DBSiteRmTempTable> siteRmTempTable = new ArrayList<DBSiteRmTempTable>();
	static DBWeatherTable weather;
	public BacnetServer() {
		eventHandler.addListener(new EventQueueListener());
		eventHandler.fireGetBacnetDeviceRequest();
		

	}

	public static void start(DBBacnetDevicesTable d) throws Exception {
		try {
			IpNetwork network = new IpNetwork(d.getDevice_Address_Binding(), d.getIntPort());
			Transport transport = new Transport(network);
			// transport.setTimeout(15000);
			// transport.setSegTimeout(15000);
			if (isNumeric(d.getObject_Identifier())) {
				localBacnetDevice = new LocalDevice(Integer.parseInt(d.getObject_Identifier()), transport);
				// create local device
				localBacnetDevice.initialize();
				// notify network of new device on network
				localBacnetDevice.sendGlobalBroadcast(new WhoIsRequest());

				// listen to bacnet events
				localBacnetDevice.getEventHandler().addListener(new Bacnet4j2Listener());
				initialized = localBacnetDevice.isInitialized();
				System.out.println("BACnet Server > Started on with Object ID [" + d.getObject_Identifier() + "] interface [" + d.getDevice_Address_Binding() + ":" + d.getPort() + "]");
			}

		} catch (Exception e) {
			localBacnetDevice.terminate();
			initialized = false;
			System.out.println("BACnet Server > Failed to start BACnet Server.");
		}
	}

	
	public EventHandler getEventHandler() {
		return eventHandler;
	}

	static class Bacnet4j2Listener extends DeviceEventAdapter {
		@Override
		public void iAmReceived(RemoteDevice d) {
			System.out.println("Bacnet Server >> IAm received from " + d);

		}
	}

	static class EventQueueListener extends EventAdapter {
		// listen to event queue
		@Override
		public void bacnetDeviceUpdate(DBBacnetDevicesTable d) {
			try {
				if (!initialized) {
					start(d);
				} else {

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		@Override
		public void roomInfoUpdate(DBRoomTable r) {
			System.out.println("BACnet Server > Received room informaiton update for room: " + r.getRoomNumber() + " " + r.getRoomName());
			
			int id = ((r.getId()*(5*r.getId()-3)+(2*r.getId()))/r.getId()) + (r.getId()-1);
			
			if (id > 100) return;
			for (BACnetObject obj: localBacnetDevice.getLocalObjects()) {
				if (obj.getInstanceId() == id) {
					System.out.println("BACnet Server > Room found in DB. Room: " + r.getRoomNumber() + " " + r.getRoomName());
					return;
				}
			}
			createBACnetObject(ObjectType.binaryValue, id, r.getOccState(), "Unoccupied", "Occupied", r.getRoomNumber() + "_OccSched", "Occupancy Schedule", EngineeringUnits.noUnits);
			createBACnetObject(ObjectType.binaryValue, id + 1, r.getOccState(), "Unoccupied", "Occupied", r.getRoomNumber() + "_OccOptimized", "Optimized Occupancy Schedule", EngineeringUnits.noUnits);
			createBACnetObject(ObjectType.binaryValue, id + 2, r.getOccState(), "Heat", "Cool", r.getRoomNumber() + "_CoolMode", "0=Heat mode; 1=Cool Mode", EngineeringUnits.noUnits);

			createBACnetObject(ObjectType.analogValue, id, 0, "0", "1", r.getRoomNumber() + "_SpaceTemp", "Space Temperature", EngineeringUnits.degreesFahrenheit);
			createBACnetObject(ObjectType.analogValue, id + 1, r.getTempSetpoint(), "0", "1", r.getRoomNumber() + "_OccSp", "Occupied Setpoint", EngineeringUnits.degreesFahrenheit);
			createBACnetObject(ObjectType.analogValue, id + 2, 80, "0", "1", r.getRoomNumber() + "_UnoccSp", "Unoccupied Setpoint", EngineeringUnits.degreesFahrenheit);
			createBACnetObject(ObjectType.analogValue, id + 3, r.getLightIntensity(), "0", "1", r.getRoomNumber() + "_Light", "Light Intensity", EngineeringUnits.percent);
			
			DBSiteRmTempTable s = new DBSiteRmTempTable();
			s.setSiteID(1);
			s.setRoomNumber(r.getRoomNumber());
			s.setTemperature(0);
			s.setOccSetpoint(r.getTempSetpoint());
			s.setUnOccSetpoint(0);
			s.setOAT((float) weather.getTemperature());
			s.setCoolMode(1);
			s.setOccStatus(r.getOccState());
			
			siteRmTempTable.add(s);
			
			
		}
		
		@Override
		public void weatherInfoUpdate(DBWeatherTable w) {
			System.out.println("BACnet Server > Received weather data update DB confirmation for SiteID: " + w.getSiteId());
			createBACnetObject (ObjectType.analogValue, 1, (float)w.getTemperature(), "0" ,"1", "OAT", "Outside Air Temperature", EngineeringUnits.degreesFahrenheit);
			createBACnetObject (ObjectType.analogValue, 2, (float)w.getHumidity(), "0" ,"1", "OAH", "Outside Air Humidity", EngineeringUnits.percent);
			createBACnetObject (ObjectType.analogValue, 3, (float)w.getDewpoint(), "0" ,"1", "ODP", "Outside Dew Point Temperature", EngineeringUnits.degreesFahrenheit);
			
			weather = w;
		}
		
		@Override
		public void eventUpdate(ArrayList<DBScheduleTable> s) {
			// TEAM 7 TO DO
			// EventObject data type
			//

			
			//String eventDes = "<html>" + o.getEventName() + ": " + o.getEventDescription() + " "+ o.getEventStart() + " - " + o.getEventStop()+"</hmtl>";

		}
	}

	public static boolean createBACnetObject(ObjectType objType, int ID, float value, String activeTxt, String inactiveTxt, String name, String description, EngineeringUnits u) {
		try {
			//set BACnet ID = room ID
        	
			ObjectIdentifier objectId = new ObjectIdentifier(objType, ID);
			
	        BACnetObject object = new BACnetObject(localBacnetDevice, objectId);
			
	        if (objType == ObjectType.analogValue) {
		        object.setProperty(PropertyIdentifier.presentValue, new Real(value));
		        object.setProperty(PropertyIdentifier.units, u);
	        } else if (objType == ObjectType.binaryValue){
	        	boolean b = false;
	        	if (value > 0) {
	        		b = true;
	        	} 
	        	object.setProperty(PropertyIdentifier.inactiveText, new CharacterString(activeTxt));
	        	object.setProperty(PropertyIdentifier.activeText, new CharacterString(inactiveTxt));
	        	object.setProperty(PropertyIdentifier.presentValue, b ? BinaryPV.active : BinaryPV.inactive);
	        	
	        } else {
	        	return false;
	        }
			object.setProperty(PropertyIdentifier.objectName, new CharacterString(cleanString(name)));
			object.setProperty(PropertyIdentifier.description, new CharacterString(description));
			
	        object.setProperty(PropertyIdentifier.statusFlags, new StatusFlags(false, false, false, false));
	        object.setProperty(PropertyIdentifier.eventState, EventState.normal);

	        //check to see if object is already created
	        boolean hasComponent = false;
			for (BACnetObject jc : localBacnetDevice.getLocalObjects()) {
			    if ( jc instanceof BACnetObject ) {
			    	if (jc.getId().equals(object.getId())) {
			    		Encodable d = jc.getProperty(PropertyIdentifier.presentValue);
			    		
			    		jc.setProperty(PropertyIdentifier.presentValue, new Real(value));
			    		System.out.println("BACnet Server > Object eixst. BACnet OBject: [" + jc.getInstanceId() +"] " + jc.getObjectName() +" Update Presenvalue: " + d);
			    		hasComponent = true;
			    	}
			    }
			}object.setProperty(PropertyIdentifier.outOfService, new edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.Boolean(false));
	        
	        if (!hasComponent) {
	        	localBacnetDevice.addObject(object);
	        	System.out.println("BACnet Server > New object created BACNet ID: " + object.getInstanceId() +" Name: " + object.getObjectName() + " Description: " + object.getDescription() + " Value: " + value);
	        	return true;
	        }
	        
	       
		} catch (BACnetServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isNumeric(String s) {
		return s != null && s.matches("[-+]?\\d*\\.?\\d+");
	}
	
	public static String cleanString(String s) {
		s = s.replaceAll(" ", "_");
		return s;
	}

}
