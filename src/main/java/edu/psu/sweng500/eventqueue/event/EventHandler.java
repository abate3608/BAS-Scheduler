package edu.psu.sweng500.eventqueue.event;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import edu.psu.sweng500.type.*;


public class EventHandler {
    final ConcurrentLinkedQueue<EventListener> listeners = new ConcurrentLinkedQueue<EventListener>();

    private static EventHandler instance = null;
    
    protected EventHandler() {
       // Exists only to defeat instantiation.
    }
    
    public static EventHandler getInstance() {
       if(instance == null) {
          instance = new EventHandler();
       }
       return instance;
    }
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
    
    //UI fire request
    public void fireAuthenticateUserRequest(final String userName, final String password) {
    	for (EventListener l : listeners) {
            try {
            	l.authenticateUserRequest(userName, password);
            }
            catch (Throwable e) {
                handleException(l, e);
            }
        }
    }
    
  //UI fire request
    public void fireAuthenticateUserUpdate(final User u) {
    	for (EventListener l : listeners) {
            try {
            	l.authenticateUserUpdate(u);
            }
            catch (Throwable e) {
                handleException(l, e);
            }
        }
    }
    
    
    //Bacnet Server calls this function to request for BACnet Device info
    public void fireGetBacnetDeviceRequest(final String ObjectIdentifier) {
        for (EventListener l : listeners) {
            try {
                l.getBacnetDevice(ObjectIdentifier);
            }
            catch (Throwable e) {
                handleException(l, e);
            }
        }
    }
    
    //Database calls this function to respond to Bacnet Server request
    public void fireBacnetDeviceUpdate(final BacnetDevice d) {
        for (EventListener l : listeners) {
            try {
                l.bacnetDeviceUpdate(d);
            }
            catch (Throwable e) {
                handleException(l, e);
            }
        }
    }
    
    public void fireGetEvents(Date start, Date stop) {
    	for (EventListener l : listeners) {
            try {
                l.getEvents(start, stop);
            }
            catch (Throwable e) {
                handleException(l, e);
            }
        }
    	
    }
    
    public void fireEventUpdate(ScheduleEvent o) {
    	for (EventListener l : listeners) {
            try {
                l.eventUpdate(o);
            }
            catch (Throwable e) {
                handleException(l, e);
            }
        }
    }
}
