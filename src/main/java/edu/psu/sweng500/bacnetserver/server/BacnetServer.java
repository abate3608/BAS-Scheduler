package edu.psu.sweng500.bacnetserver.server;

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
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.EngineeringUnits;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.EventState;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.ObjectType;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.PropertyIdentifier;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.CharacterString;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.ObjectIdentifier;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.primitive.Real;
import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.*;

public class BacnetServer {
	static LocalDevice localBacnetDevice;
	private static boolean initialized = false;
	// Event listeners
	private final EventHandler eventHandler = EventHandler.getInstance();

	public BacnetServer() {
		String ObjectIdentifier = "12345";
		eventHandler.addListener(new EventQueueListener());
		eventHandler.fireGetBacnetDeviceRequest(ObjectIdentifier);
		System.out.println("BACnet Server name: " + ObjectIdentifier);

	}

	public static void start(BacnetDevice d) throws Exception {
		try {
			IpNetwork network = new IpNetwork(d.getIpAddress(), d.getPort());
			Transport transport = new Transport(network);
			// transport.setTimeout(15000);
			// transport.setSegTimeout(15000);
			if (isNumeric(d.getObjectIdentifier())) {
				localBacnetDevice = new LocalDevice(Integer.parseInt(d.getObjectIdentifier()), transport);
				// create local device
				localBacnetDevice.initialize();
				// notify network of new device on network
				localBacnetDevice.sendGlobalBroadcast(new WhoIsRequest());

				// listen to bacnet events
				localBacnetDevice.getEventHandler().addListener(new Bacnet4j2Listener());
				initialized = localBacnetDevice.isInitialized();
			}

		} catch (Exception e) {
			localBacnetDevice.terminate();
			initialized = false;
		}
	}

	public EventHandler getEventHandler() {
		return eventHandler;
	}

	static class Bacnet4j2Listener extends DeviceEventAdapter {
		@Override
		public void iAmReceived(RemoteDevice d) {
			System.out.println("IAm received from " + d);

		}
	}

	static class EventQueueListener extends EventAdapter {
		// listen to event queue
		@Override
		public void bacnetDeviceUpdate(BacnetDevice d) {
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
			createBACnetObjectAV(r.getId() + 3, r.getOccState(), r.getRoomNumber(), r.getRoomName(), EngineeringUnits.noUnits);
			
		}
		
		@Override
		public void weatherInfoUpdate(DBWeatherTable w) {
			System.out.println("BACnet Server > Received weather data update DB confirmation for SiteID: " + w.getSiteId());
			createBACnetObjectAV (1, (float)w.getTemperature(), "OAT", "Outside Air Temperature", EngineeringUnits.degreesFahrenheit);
			createBACnetObjectAV (2, (float)w.getHumidity(), "OAH", "Outside Air Humidity", EngineeringUnits.percent);
			createBACnetObjectAV (3, (float)w.getDewpoint(), "ODP", "Outside Dew Point Temperature", EngineeringUnits.degreesFahrenheit);
		}
		
		
		@Override
		public void eventUpdate(DBScheduleTable s) {
			// TEAM 7 TO DO
			// EventObject data type
			//

			
			//String eventDes = "<html>" + o.getEventName() + ": " + o.getEventDescription() + " "+ o.getEventStart() + " - " + o.getEventStop()+"</hmtl>";

		}
		
		
		
	}

	public static void createBACnetObjectAV(int ID, float value, String name, String description, EngineeringUnits u) {
		try {
			//set BACnet ID = room ID
        	ObjectIdentifier objectId = new ObjectIdentifier(ObjectType.analogValue, ID);

	        BACnetObject object = new BACnetObject(localBacnetDevice, objectId);
			object.setProperty(PropertyIdentifier.presentValue, new Real(value));
			object.setProperty(PropertyIdentifier.objectName, new CharacterString(cleanString(name)));
			object.setProperty(PropertyIdentifier.description, new CharacterString(description));
	        object.setProperty(PropertyIdentifier.units, u);
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
	        }
	       
		} catch (BACnetServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean isNumeric(String s) {
		return s != null && s.matches("[-+]?\\d*\\.?\\d+");
	}
	
	public static String cleanString(String s) {
		s = s.replaceAll(" ", "_");
		return s;
	}

}
