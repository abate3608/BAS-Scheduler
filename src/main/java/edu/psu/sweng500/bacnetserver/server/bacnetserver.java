package server;

import bacnet4j2.LocalDevice;
import bacnet4j2.RemoteDevice;
import bacnet4j2.event.DeviceEventAdapter;
import bacnet4j2.npdu.ip.IpNetwork;
import bacnet4j2.npdu.ip.IpNetworkIdentifier;
import bacnet4j2.service.unconfirmed.WhoIsRequest;
import bacnet4j2.transport.Transport;

public class bacnetserver {
    static LocalDevice localDevice;
    public static final int port = 0xBAC0;
    public static final String ipaddress = "192.168.30.1";
    
    public static void main(String[] args) throws Exception {
    	
        IpNetwork network = new IpNetwork(ipaddress, port);
        Transport transport = new Transport(network);
        //        transport.setTimeout(15000);
        //        transport.setSegTimeout(15000);
        localDevice = new LocalDevice(1234, transport);
        try {
        	//create local device
            localDevice.initialize();
            //notify network of new device on network
            localDevice.sendGlobalBroadcast(new WhoIsRequest());
            
            //listen to bacnet events
            localDevice.getEventHandler().addListener(new Listener());
            
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

    static class Listener extends DeviceEventAdapter {
        @Override
        public void iAmReceived(RemoteDevice d) {
            System.out.println("IAm received from " + d);
			
        }
    }

    
}
