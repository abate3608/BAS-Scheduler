package edu.psu.sweng500.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.BacnetDevice;
import edu.psu.sweng500.type.ScheduleEvent;
import edu.psu.sweng500.type.User;

/*
 * Class to get data based on CalendarID. Can be changed as development progresses. 
 */
public class Database {

	// Event listeners
	private final static EventHandler eventHandler = EventHandler.getInstance();
	private static Statement statement = null;
	private static ResultSet rt = null;
	private static Connection connect = null;

	public Database(Connection connect) {
		eventHandler.addListener(new EventQueueListener());
		this.connect = connect;
	}

	static class EventQueueListener extends EventAdapter {
		// listen to event queue
		@Override
		public void getBacnetDevice(String ObjectIdentifier) {
			try {
				System.out.println("getBacnetDeviceReqeust received for " + ObjectIdentifier);

				// get information from data and send the data back to Bacnet
				// server

				// get information for the ObjectIdentifier
				int port = 0xBAC0; // get information from database and replace
									// static data
				String ipAddress = "192.168.30.1"; // get information from
													// database and replace
													// static data

				// create new bacnet device
				BacnetDevice bacnetDevice = new BacnetDevice(ObjectIdentifier, port, ipAddress);

				// Generate the event
				eventHandler.fireBacnetDeviceUpdate(bacnetDevice);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void getEvents(Date Start, Date Stop) {
			try {

				  DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 

				  statement = connect.createStatement();
				  
				  String s = "select * from psuteam7.schedule where StartTime >= '" + df.format(Start) + "' and EndTime <= '" + df.format(Stop) + "'";
				  
				  rt = statement.executeQuery(s); 
				  
				  while ((rt.next())) { 
					  
					  // Loop to each event
					  // Create SheduleEvent object
					  ScheduleEvent scheduleEvent = new ScheduleEvent();
					  scheduleEvent.setEventID(rt.getInt("ScheduleId"));
					  scheduleEvent.setEventName(rt.getString("Name"));
					  scheduleEvent.setEventDescription(rt.getString("Description"));
					  scheduleEvent.setEventStart(rt.getDate("StartTime"));
					  scheduleEvent.setEventStop(rt.getDate("EndTime")); 
					  
					  // Send each event to event queue
					  eventHandler.fireEventUpdate(scheduleEvent);
				  }
				

				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		@Override
		public void createEvents(ArrayList<ScheduleEvent> events) {
			System.out.println("Databse: Entering createEvents");
			try{  
				String query = "INSERT INTO psuteam7.schedule (ScheduleId, Name, Description, StartTime, EndTime) "
						+ "VALUES (?, ?, ?, ?, ?)";   
				PreparedStatement ps = connect.prepareStatement(query);
				for (ScheduleEvent event : events) {
					ps.setInt(1, event.getEventID());
					ps.setString(2, event.getEventName());
					ps.setString(3, event.getEventDescription());
					ps.setString(4, event.getEventDescription());
					Calendar cal = Calendar.getInstance();
		            cal.setTime(event.getEventStart());
					ps.setDate(5, new java.sql.Date(cal.DATE));
					cal.setTime(event.getEventStop());
					ps.setDate(6, new java.sql.Date(cal.DATE));
					ps.addBatch();
				}
				ps.executeBatch();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void getEvents() {
			try {
				  statement = connect.createStatement();
				  
				  String s = "select * from psuteam7.schedule";
				  
				  rt = statement.executeQuery(s); 
				  
				  while ((rt.next())) { 
					  
					  // Loop to each event
					  // Create SheduleEvent object
					  ScheduleEvent scheduleEvent = new ScheduleEvent();
					  scheduleEvent.setEventID(rt.getInt("ScheduleId"));
					  scheduleEvent.setEventName(rt.getString("Name"));
					  scheduleEvent.setEventDescription(rt.getString("Description"));
					  scheduleEvent.setEventStart(rt.getDate("StartTime"));
					  scheduleEvent.setEventStop(rt.getDate("EndTime")); 
					  
					  // Send each event to event queue
					  eventHandler.fireEventUpdate(scheduleEvent);
				  }

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void authenticateUserRequest(String userName, String password) {
			try {

				// for now, check to see if the password match
				// Send out authenticate user update
				User u = new User(userName, true);

				eventHandler.fireAuthenticateUserUpdate(u);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void readData(DataEvent b) {
			System.out.println("Data being read from Importor " + b);
		}

		public void updateData(DataEvent b) {
			System.out.println("Data has been successfully received from " + b);
		}

		public void deleteData(DataEvent b) {
			System.out.println("Old data has been deleted from " + b);
		}

		public void validateData(DataEvent b) {
			System.out.println("Datas in the database are valid " + b);
		}

		public void notify(DataEvent b) {
			System.out.println("Data in " + b + "has been changed");
		}
	}

	private int calendarId;

	public int getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(int calendarId) {
		this.calendarId = calendarId;
	}
}