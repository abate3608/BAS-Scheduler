package bacnet4j2.discovery;

import bacnet4j2.LocalDevice;
import bacnet4j2.RemoteDevice;
import bacnet4j2.npdu.ip.IpNetwork;
import bacnet4j2.transport.Transport;
import bacnet4j2.type.constructed.Address;
import bacnet4j2.type.primitive.OctetString;

public class WhoIs {
    public static void main(String[] args) throws Exception {
        IpNetwork network = new IpNetwork();
        Transport transport = new Transport(network);
        LocalDevice localDevice = new LocalDevice(45677, transport);

        try {
            localDevice.initialize();

            // CBM
            //            RemoteDevice r = localDevice.findRemoteDevice(new Address(36, (byte) 1), new OctetString("89.101.141.54"),
            //                    121);

            // CBT
            RemoteDevice r = localDevice.findRemoteDevice(new Address(36, (byte) 2), new OctetString("89.101.141.54"),
                    122);

            // CBR
            //            RemoteDevice r = localDevice.findRemoteDevice(new Address("89.101.141.54", IpNetwork.DEFAULT_PORT), null,
            //                    123);

            System.out.println(r);
        }
        finally {
            localDevice.terminate();
        }
    }
}
