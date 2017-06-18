package edu.psu.sweng500.eventqueue.event;

import java.util.concurrent.ConcurrentLinkedQueue;

import edu.psu.sweng500.bacnetserver.bacnet4j2.RemoteDevice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.event.DeviceEventListener;
import edu.psu.sweng500.type.*;


public class EventHandler {
    final ConcurrentLinkedQueue<EventListener> listeners = new ConcurrentLinkedQueue<EventListener>();

    //
    //
    // Listener management
    //
    public void addListener(EventListener l) {
        listeners.add(l);
    }

    public void removeListener(EventListener l) {
        listeners.remove(l);
    }

    public int getListenerCount() {
        return listeners.size();
    }
    
    private void handleException(EventListener l, Throwable e) {
        try {
            l.listenerException(e);
        }
        catch (Throwable e1) {
            // no op
        }
    }
    
    //Bacnet Server calls this function to request for BACnet Device info
    public void fireGetBacnetDeviceRequest(final String ObjectIdentifier) {
        for (EventListener l : listeners) {
            try {
                l.getBacnetDeviceRequest(ObjectIdentifier);
            }
            catch (Throwable e) {
                handleException(l, e);
            }
        }
    }
    
    //Database calls this function to respond to Bacnet Server request
    public void fireGetBacnetDeviceRespond(final BacnetDevice d) {
        for (EventListener l : listeners) {
            try {
                l.getBacnetDeviceRespond(d);
            }
            catch (Throwable e) {
                handleException(l, e);
            }
        }
    }
}
