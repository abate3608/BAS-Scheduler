package edu.psu.sweng500.eventqueue.event;

import java.util.ArrayList;
import java.util.Date;


import edu.psu.sweng500.type.*;

public interface EventListener {
	/**
	 * Notification of an exception while calling a listener method.
	 */
	void listenerException(Throwable e);

	public void authenticateUserRequest(String userName, String password);

	public void authenticateUserUpdate(User u);

	public void getBacnetDevice(String ObjectIdentifier);

	public void bacnetDeviceUpdate(BacnetDevice d);

	public void getBacnetObject(String ObjectIdentifier);

	public void bacnetObjectUpdate(BacnetObject o);

	public void setBacnetObject(BacnetObject o);
	
	public void createEvents(ArrayList<ScheduleEvent> events);
	
	public void getEvents(Date Start, Date Stop);

	public void eventUpdate(ScheduleEvent o);

	public void createUser(User u);

	public void createUserRespond(User u, int err);

	//public void createUser(ArrayList<UserRegister> events);

}
