package edu.psu.sweng500.eventqueue.event;

import java.util.ArrayList;
import java.util.Date;


import edu.psu.sweng500.type.*;

public class EventAdapter implements EventListener {
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

//	public void authenticateUserUpdate(User u) {
		// TODO Auto-generated method stub
		//System.out.println("Authenticate User Update Fired");

//	}

	public void getEvents(Date Start, Date Stop) {
		// TODO Auto-generated method stub
	}

	public void eventUpdate(ScheduleEvent o) {
		// TODO Auto-generated method stub

	}
	


//	public void authenticateUserRequest(String userName, String password) {
		 //TODO Auto-generated method stub
		//System.out.println("Authentication request Fired");
		
//	}

	public void createUser(User u) {
		//System.out.println("Registration Fired");
		
	}
	
	public void createUserRespond(User u, int err) {
		
	}

	public void createEvent(ScheduleEvent event) {
		// TODO Auto-generated method stub
		
	}

	////////////////////////////////////////////////////////////////////////////////- USER INTERFACE
	
	// LOG SCREEN
	public void authenticateUserRequest(String userName, String password) {
	// TODO Auto-generated method stub
	
	}
	// LOG SCREEN
	public void authenticateUserUpdate(User u) {
	// TODO Auto-generated method stub
	
	}
	
	// NEW USER
//	public void authenticateNewUserRequest(String firstName, String lastName, String email, String userName, String password) {
	// TODO Auto-generated method stub
//	}
	//NEW USER
//	public void authenticateNewUserUpdate(NewUser u){
	
//	}
	
	// NEW EVENT
	public void authenticateNewEventRequest(String  eventName, String startTime, String endTime, 
	String eventDate, String eventRoom, String lightSetting, String tempSetting){
	}
	//NEW EVENT
	public void authenticateNewEventUpdate(final NewEvent u){	
	}
	
	// EDIT EVENT - SUBMIT
	public void authenticateEditEventRequest(String  eventName, String startTime, String endTime, 
	String eventDate, String eventRoom, String lightSetting, String tempSetting){
	}
	// EDIT EVENT - SUBMIT
	public void authenticateEditEventUpdate(final EditEvent u){	
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	public void createEventRespond(ScheduleEvent event, int err) {
		
	}
	
}
