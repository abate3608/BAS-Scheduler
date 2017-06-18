package edu.psu.sweng500.eventqueue.event;

import edu.psu.sweng500.type.*;

public interface EventListener {
	/**
     * Notification of an exception while calling a listener method.
     */
    void listenerException(Throwable e);
    
    public void authenticateUserRequest(String userName, String password);
    public void authenticateUserRepond(User u);
    public void getBacnetDeviceRequest(String ObjectIdentifier);
    public void getBacnetDeviceRespond(BacnetDevice d);
    public void getBacnetObject(BacnetObject o);
    public void setBacnetObject(BacnetObject o);
    
}
