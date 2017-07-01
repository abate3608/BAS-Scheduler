package edu.psu.sweng500.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.log4j.chainsaw.Main;

import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.*;

/*
 * Class to get data based on CalendarID. Can be changed as development progresses. 
 */
public class Database {

	// Event listeners
	private final static EventHandler eventHandler = EventHandler.getInstance();
	public static final String email = null;
	public static final String userName = null;
	public static final String passWord = null;
	public static final String firstName= null; 
	private static Statement statement = null;
	private static ResultSet rt = null;
	private static Connection connect = null;
	public static String lastName;

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
		public void createEvent(ScheduleEvent event) {
			System.out.println("Database > Create event received: Name - " + event.getEventName());
			int err = 1;
			try{  
				String query = "INSERT INTO psuteam7.schedule (RowGuid, ScheduleId, ScheduleSiteId, Name, "
						+ "Description, Notes, ControlToState, StartTime, EndTime, MarkedForDelete, CalendarId, "
						+ "CalendarSiteId, DownloadStatus, SaveStatus, ActiveOnCalendarDays) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";   
				PreparedStatement ps = connect.prepareStatement(query);
				//for (ScheduleEvent event : events) {
					ps.setString(1, "E45E13D8-CFF0-4FC7-B7C8"); //RowGuid
					ps.setInt(2, 5528 ); // ScheduleId
					ps.setInt(3, 25); // ScheduleSiteId
					//ps.setInt(1, event.getEventID());
					ps.setString(4, event.getEventName()); //Name
					ps.setString(5, event.getEventDescription());//Description
					ps.setString(6," ");//Notes
					ps.setInt(7, 0);//ControlToState
					Calendar cal = Calendar.getInstance();
		            cal.setTime(event.getEventStart());
					ps.setDate(8, new java.sql.Date(cal.DATE));//start time
					cal.setTime(event.getEventStop());
					ps.setDate(9, new java.sql.Date(cal.DATE));//end time
					ps.setBoolean(10, false); //MarkedForDelete
					ps.setInt(11,1); //CalendarId
					ps.setInt(12, 1);//CalendarSiteId
					ps.setInt(13, 0);//download status
					ps.setInt(14, 0);//SaveStatus
					ps.setBoolean(15, false);//ActiveOnCalendarDays
					ps.addBatch();
				//}
				ps.executeBatch();
				err = 0; //0 = good
				eventHandler.fireCreateEventRespond(event, err);
			} catch (Exception e) {
				e.printStackTrace();
				err = 1;
				eventHandler.fireCreateEventRespond(event, err);
			}
			
		}

		@Override
		public void authenticateUserRequest(String userName, String passWord) {
			//display debug message
			System.out.println("Database > Authentication request received. User: " + userName + " Password:" + passWord);
			try {
				
				//sql statement
				String userquery = "Select * from psuteam7.User_Profile where userName = '" + userName + "' and passWord = '" + passWord + "' ";
				statement = connect.createStatement();
				rt=statement.executeQuery(userquery);
				
				//create new user variable
				User u = new User(userName, false);
				while(rt.next()) 
				{
					String IDName = rt.getString(5);
					String DBpass = rt.getString(6);
					 
					if(userName.equals(IDName) && (passWord.equals(DBpass))) {
						//found matching credential
						
						JOptionPane.showMessageDialog(null, "Login Successful");
						u.setAuthenticated(true);
						//exit while loop
						break;
					} 	else
					{
						u.setAuthenticated(false);
					}
				}
				//rt.close();	
				//if user is not authenicated. Display 
				if(!u.isAuthenticated()) 
				{
					JOptionPane.showMessageDialog(null, "Login Fail. Please Enter Correct UserName and Password");
				}
			
				eventHandler.fireAuthenticateUserUpdate(u);

				 
			}catch (SQLException e) {
				System.out.println(e);	
				}
			}
	@Override
		public void createUser(User u) {
			//display debug message
			System.out.println("Database > Create user request received. User: " + u.getUserName());
			int err = 1;
			try {
				
				
				statement= connect.createStatement();
				
				statement.executeQuery("Insert into psuteam7.User_Profile VALUES (null," + u.getFirstName() + ", " + u.getLastName() + ", " + u.getEmail() + ", " + u.getUserName() + ", " + u.getPassword());
				err = 0;
				eventHandler.fireCreteUserRespond(u, err);
				JOptionPane.showConfirmDialog(null, "Successful Registration", "Result",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE);
				
		} catch(Exception e) {
			System.out.println(e);
			eventHandler.fireCreteUserRespond(u, err);
			
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