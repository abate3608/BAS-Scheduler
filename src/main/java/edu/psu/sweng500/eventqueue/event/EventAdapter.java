package edu.psu.sweng500.eventqueue.event;

import java.util.ArrayList;
import java.util.Date;

import edu.psu.sweng500.bacnetserver.bacnet4j2.LocalDevice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.obj.BACnetObject;
import edu.psu.sweng500.type.*;

public class EventAdapter implements EventListener {
	// @Override
	public void listenerException(Throwable e) {
		// Override as required
		e.printStackTrace();
	}

	// Database attaches to this and listen for BACnet server request
		public void getBacnetDevice() {
			//System.out.println("getBacnetDeviceRequest event fired!");
		}

		public void setBacnetDevice(DBBacnetDevicesTable d) {
			
		}
		
		// Bacnet server attaches to this and listen for Database respond
		public void bacnetDeviceUpdate(DBBacnetDevicesTable d) {
			//System.out.println("getBacnetDeviceRespond fired!");
		}

		public void getBacnetObject(String ObjectIdentifier) {
			// TODO Auto-generated method stub

		}

		public void bacnetObjectUpdate(BacnetObject o) {

		}

		public void setBacnetObject(BacnetObject o) {
			// TODO Auto-generated method stub

		}

//		public void authenticateUserUpdate(User u) {
			// TODO Auto-generated method stub
			//System.out.println("Authenticate User Update Fired");

//		}

		public void getEvents(Date startDateTime, Date endDateTime) {
			// TODO Auto-generated method stub
		}
		
		public void getDailyEvents(Date dailyDate) {
			// TODO Auto-generated method stub
		}
		
		public void eventUpdate(ArrayList<DBScheduleTable> s) {
			// TODO Auto-generated method stub

		}
		
		public void eventDailyUpdate(ArrayList<DBScheduleTable> s) {
			// TODO Auto-generated method stub

		}
		
		public void eventDelete(ArrayList<DBScheduleTable> s) {
			// TODO Auto-generated method stub

		}
		


//		public void authenticateUserRequest(String userName, String password) {
			 //TODO Auto-generated method stub
			//System.out.println("Authentication request Fired");
			
//		}

		public void createUser(User u) {
			//System.out.println("Registration Fired");
			
		}
		
		public void createUserRespond(User u, int err) {
			
		}

		public void createEvent(DBScheduleTable s) {
			// TODO Auto-generated method stub
		}
		
		public void readEvent(DBScheduleTable s) {
			// TODO Auto-generated method stub
		}
		
		public void updateEvent(DBScheduleTable s) {
			// TODO Auto-generated method stub
		}
		
		public void deleteEvent(DBScheduleTable s) {
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
//		public void authenticateNewUserRequest(String firstName, String lastName, String email, String userName, String password) {
		// TODO Auto-generated method stub
//		}
		//NEW USER
//		public void authenticateNewUserUpdate(NewUser u){
		
//		}
		
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

		public void createEventRespond(DBScheduleTable s, int err) {
			
		}
		
		public void readEventRespond(DBScheduleTable s, int err) {
			
		}
		
		public void updateEventRespond(DBScheduleTable s, int err) {
			
		}
		
		public void deleteEventRespond(DBScheduleTable s, int err) {
			
		}


		public void siteInfoRequest() {
			// TODO Auto-generated method stub
			
		}
		
		public void siteInfoUpdate(DBSiteTable s) {
			// TODO Auto-generated method stub
			
		}
		
		public void weatherInfoRequest(int siteId) {
			
		}
		
		public void weatherInfoUpdate(DBWeatherTable w) {
			
		}

		public void siteInfoUpdateDB(DBSiteTable s) {
			// TODO Auto-generated method stub
			
		}
		
		public void siteInfoUpdateDBRespond(DBSiteTable s, int err) {
			// TODO Auto-generated method stub
			
		}

		public void weatherInfoUpdateDB(DBWeatherTable w) {
			// TODO Auto-generated method stub
			
		}
		
		public void weatherInfoUpdateDBRespond(DBWeatherTable w, int err) {
			// TODO Auto-generated method stub
			
		}

		public void roomInfoRequest() {
			// TODO Auto-generated method stub
			
		}
		
		public void roomInfoUpdate(DBRoomTable r) {
			
		}

		public void roomInfoUpdateDB(DBRoomTable r) {
			// TODO Auto-generated method stub
			
		}

		public void updateBaseline(String roomNumber) {
			// TODO Auto-generated method stub
			
		}
		
		public void updateOccupancy(String roomNumber) {
			// TODO Auto-generated method stub
			
		}
		
		public void updateOccStatus() {
			
		}

		public void saveRoomHistoryData(BacnetObject obj) {
			// TODO Auto-generated method stub
			
		}
		
}
