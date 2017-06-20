package edu.psu.sweng500.bacnetserver.server;

import edu.psu.sweng500.bacnetserver.bacnet4j2.LocalDevice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.RemoteDevice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.event.DeviceEventAdapter;
import edu.psu.sweng500.bacnetserver.bacnet4j2.npdu.ip.IpNetwork;
import edu.psu.sweng500.bacnetserver.bacnet4j2.service.unconfirmed.WhoIsRequest;
import edu.psu.sweng500.bacnetserver.bacnet4j2.transport.Transport;
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
	}

	public static boolean isNumeric(String s) {
		return s != null && s.matches("[-+]?\\d*\\.?\\d+");
	}

}
