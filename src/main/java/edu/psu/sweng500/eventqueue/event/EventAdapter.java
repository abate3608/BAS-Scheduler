package edu.psu.sweng500.eventqueue.event;

import edu.psu.sweng500.type.*;

public class EventAdapter implements EventListener {
	//@Override
    public void listenerException(Throwable e) {
        // Override as required
        e.printStackTrace();
    }
    
  
    //Database attaches to this and listen for BACnet server request
    public void getBacnetDeviceRequest(String ObjectIdentifier) {
    	//System.out.println("getBacnetDeviceRequest");
    }

    //Bacnet server attaches to this and listen for Database respond
    public void getBacnetDeviceRespond(BacnetDevice d) {
    	//System.out.println("getBacnetDeviceRequest");
    }
    
	public void getBacnetObject(BacnetObject o) {
		// TODO Auto-generated method stub
		
	}

	public void setBacnetObject(BacnetObject o) {
		// TODO Auto-generated method stub
		
	}

	public void authenticateUserRequest(String userName, String password) {
		// TODO Auto-generated method stub
		
	}

	public void authenticateUserRepond(User u) {
		// TODO Auto-generated method stub
		
	}
}
