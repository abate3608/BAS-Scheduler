package edu.psu.sweng500.eventqueue.event;

import java.util.ArrayList;
import java.util.Date;


import edu.psu.sweng500.type.*;

public class EventAdapter<ScheduleUserEvents> implements EventListener {
	// @Override
	public void listenerException(Throwable e) {
		// Override as required
		e.printStackTrace();
	}

	// Database attaches to this and listen for BACnet server request
	public void getBacnetDevice(String ObjectIdentifier) {
		System.out.println("getBacnetDeviceRequest event fired!");
	}

	// Bacnet server attaches to this and listen for Database respond
	public void bacnetDeviceUpdate(BacnetDevice d) {
		System.out.println("getBacnetDeviceRespond fired!");
	}

	public void getBacnetObject(String ObjectIdentifier) {
		// TODO Auto-generated method stub

	}

	public void bacnetObjectUpdate(BacnetObject o) {

	}

	public void setBacnetObject(BacnetObject o) {
		// TODO Auto-generated method stub

	}

	public void authenticateUserRequest(String userName, boolean password) {
		// TODO Auto-generated method stub

	}

	public void authenticateUserUpdate(User u) {
		// TODO Auto-generated method stub

	}

	public void getEvents(Date Start, Date Stop) {
		// TODO Auto-generated method stub
	}

	public void eventUpdate(ScheduleEvent o) {
		// TODO Auto-generated method stub

	}
	
	public void createEvents(ArrayList<ScheduleEvent> events) {
		System.out.println("createEvents fired!");
	}

	public void authenticateUserRequest(String userName, String password) {
		 //TODO Auto-generated method stub
		
	}

	public void CreateUser() {
		System.out.println("Registration Fired");
		
	}

	//public void createUser(ArrayList<UserRegister> events) {
		// TODO Auto-generated method stub
		
	//}


}
