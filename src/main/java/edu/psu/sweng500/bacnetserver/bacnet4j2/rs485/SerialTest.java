package edu.psu.sweng500.bacnetserver.bacnet4j2.rs485;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.psu.sweng500.bacnetserver.bacnet4j2.LocalDevice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.RemoteDevice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.event.DeviceEventAdapter;
import edu.psu.sweng500.bacnetserver.bacnet4j2.exception.BACnetException;
import edu.psu.sweng500.bacnetserver.bacnet4j2.npdu.mstp.MasterNode;
import edu.psu.sweng500.bacnetserver.bacnet4j2.npdu.mstp.MstpNetwork;
import edu.psu.sweng500.bacnetserver.bacnet4j2.service.unconfirmed.WhoIsRequest;
import edu.psu.sweng500.bacnetserver.bacnet4j2.transport.Transport;
import edu.psu.sweng500.bacnetserver.bacnet4j2.type.enumerated.PropertyIdentifier;
import edu.psu.sweng500.bacnetserver.bacnet4j2.util.RequestUtils;
import com.serotonin.io.serial.SerialParameters;

/**
 * This class tests the MS/TP code using an RS-485 network accessed via COM4.
 * 
 * @author Matthew
 * 
 */
public class SerialTest {
    static LocalDevice localDevice;

    public static void main(String[] args) throws Exception {
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.FINEST);

        SerialParameters params = new SerialParameters();
        params.setCommPortId("COM4");
        params.setBaudRate(9600);
        params.setPortOwnerName("Testing");

        MasterNode master = new MasterNode(params, (byte) 0x4, 2);
        MstpNetwork network = new MstpNetwork(master);
        Transport transport = new Transport(network);
        localDevice = new LocalDevice(1234, transport);
        localDevice.getEventHandler().addListener(new Listener());

        localDevice.initialize();

        localDevice.sendGlobalBroadcast(new WhoIsRequest());

        network.sendTestRequest((byte) 8);
    }

    static class Listener extends DeviceEventAdapter {
        @Override
        public void iAmReceived(RemoteDevice d) {
            System.out.println("Received IAm from " + d);

            try {
                System.out.println(RequestUtils.sendReadPropertyAllowNull(localDevice, d, d.getObjectIdentifier(),
                        PropertyIdentifier.objectList));
            }
            catch (BACnetException e) {
                e.printStackTrace();
            }
        }
    }
}
