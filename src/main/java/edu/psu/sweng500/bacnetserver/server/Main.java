package edu.psu.sweng500.bacnetserver.server;

import edu.psu.sweng500.bacnetserver.bacnet4j2.LocalDevice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.RemoteDevice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.event.DeviceEventAdapter;
import edu.psu.sweng500.bacnetserver.bacnet4j2.npdu.ip.IpNetwork;
import edu.psu.sweng500.bacnetserver.bacnet4j2.service.unconfirmed.WhoIsRequest;
import edu.psu.sweng500.bacnetserver.bacnet4j2.transport.Transport;
import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.type.*;

public class Main {
	static LocalDevice localDevice;
    public static final int port = 0xBAC0;
    public static final String ipaddress = "192.168.30.1";
    
    public static void main(String[] args) throws Exception {
    	
    	
        
        try {
        	BacnetServer bacnetServer = new BacnetServer();
        	bacnetServer.getEventHandler().addListener(new EventQueueListener());
        	bacnetServer.getEventHandler().fireGetBacnetDeviceRequest("BAS-Scheduler");
        	
        	IpNetwork network = new IpNetwork(ipaddress, port);
            Transport transport = new Transport(network);
            //        transport.setTimeout(15000);
            //        transport.setSegTimeout(15000);
            localDevice = new LocalDevice(1234, transport);
        	//create local device
            localDevice.initialize();
            //notify network of new device on network
            localDevice.sendGlobalBroadcast(new WhoIsRequest());
            
            //listen to bacnet events
            localDevice.getEventHandler().addListener(new Bacnet4j2Listener());
            
            while (localDevice.isInitialized())
            {
            	//todo local device maintenance
            	Thread.sleep(2000);
            }
        }
        finally {
            localDevice.terminate();
        }
    }

    static class Bacnet4j2Listener extends DeviceEventAdapter {
        @Override
        public void iAmReceived(RemoteDevice d) {
            System.out.println("IAm received from " + d);
			
        }
    }
    
    static class EventQueueListener extends EventAdapter {
    	//listen to event queue
    }
    

}
