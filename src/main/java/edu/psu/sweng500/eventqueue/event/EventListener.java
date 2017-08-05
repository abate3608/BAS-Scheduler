package edu.psu.sweng500.eventqueue.event;

import java.util.ArrayList;
import java.util.Date;

import edu.psu.sweng500.bacnetserver.bacnet4j2.obj.BACnetObject;
import edu.psu.sweng500.type.*;

public interface EventListener {
	/**
	 * Notification of an exception while calling a listener method.
	 */
	void listenerException(Throwable e);

//	public void authenticateUserRequest(String userName, String password);

//	public void authenticateUserUpdate(User u);

	public void getBacnetDevice();

	public void setBacnetDevice(DBBacnetDevicesTable d);
	
	public void bacnetDeviceUpdate(DBBacnetDevicesTable d);

	public void getBacnetObject(String ObjectIdentifier);

	public void bacnetObjectUpdate(BacnetObject o);

	public void setBacnetObject(BacnetObject o);
	
	public void createEvent(DBScheduleTable s);
	
	public void readEvent(DBScheduleTable s);
	
	public void updateEvent(DBScheduleTable s);
	
	public void deleteEvent(DBScheduleTable s);
	
	public void getEvents(Date startDateTime, Date endDateTime);
	
	public void eventUpdate(ArrayList<DBScheduleTable> s);

	public void createUser(User u);

	public void createUserRespond(User u, int err);

	/////////////////////////////////////////////////////////////////////////////////////////////////////// - USER INTERFACE	
	// Log Screen
	public void authenticateUserRequest(String userName, String password);
	// Log Screen
	public void authenticateUserUpdate(User u);
	
/*	// NEW USER
	public void authenticateNewUserRequest(final String firstName, final String lastName, final String email, 
	final String userName, final String password);
	//NEW USER
	public void authenticateNewUserUpdate(NewUser u);*/
	
	// NEW EVENT
	public void authenticateNewEventRequest(final String  eventName, final String startTime, final String endTime, 
	final String eventDate, final String eventRoom, final String lightSetting, final String tempSetting);
	//NEW EVENT
	public void authenticateNewEventUpdate(final NewEvent u);
	
	//EDIT EVENT - SUBMIT
	public void authenticateEditEventRequest(final String  eventName, final String startTime, final String endTime, 
	final String eventDate, final String eventRoom, final String lightSetting, final String tempSetting);
	//EDIT EVENT - SUBMIT
	public void authenticateEditEventUpdate(final EditEvent u);
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void createEventRespond(DBScheduleTable s, int err);
	
	public void readEventRespond(DBScheduleTable s, int err);
	
	public void updateEventRespond(DBScheduleTable s, int err);
	
	public void deleteEventRespond(DBScheduleTable s, int err);

	public void siteInfoRequest();

	public void siteInfoUpdate(DBSiteTable s);

	public void weatherInfoRequest(int siteId);

	public void weatherInfoUpdate(DBWeatherTable w);

	public void siteInfoUpdateDB(DBSiteTable s);

	public void siteInfoUpdateDBRespond(DBSiteTable s, int err);

	public void weatherInfoUpdateDB(DBWeatherTable w);

	public void weatherInfoUpdateDBRespond(DBWeatherTable w, int err);

	public void roomInfoRequest();

	public void roomInfoUpdate(DBRoomTable r);

	public void roomInfoUpdateDB(DBRoomTable r);

	public void updateBaseline(String roomNumber);

	public void updateOccupancy(String roomNumber);

	public void updateOccStatus();
	
	public void saveRoomHistoryData(BACnetObject obj);

}
