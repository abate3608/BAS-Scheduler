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
import java.util.UUID;
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

	private static DBSiteTable site = new DBSiteTable();
	private static DBWeatherTable weather = new DBWeatherTable();

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
				String query = "INSERT INTO psuteam7.schedule (RowGuid, ScheduleSiteId, Name, "
						+ "Description, Notes, ControlToState, StartTime, EndTime, MarkedForDelete, CalendarId, "
						+ "CalendarSiteId, DownloadStatus, SaveStatus, ActiveOnCalendarDays) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";   
				PreparedStatement ps = connect.prepareStatement(query);
				//for (ScheduleEvent event : events) {
				UUID uuid = UUID.randomUUID();
				ps.setString(1, uuid.toString()); //RowGuid
				ps.setInt(3, 1); // ScheduleSiteId
				//ps.setInt(1, event.getEventID());
				ps.setString(4, event.getEventName()); //Name
				ps.setString(5, event.getEventDescription());//Description
				ps.setString(6," ");//Notes
				ps.setInt(7, 1);//ControlToState
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


				//statement= connect.createStatement();

				//statement.executeQuery("Insert into psuteam7.User_Profile VALUES (null," + u.getFirstName() + ", " + u.getLastName() + ", " + u.getEmail() + ", " + u.getUserName() + ", " + u.getPassword());
				// the mysql insert statement
				String query = " insert into psuteam7.User_Profile (firstName, lastName, email, userName, passWord)"
						+ " values (?, ?, ?, ?, ?)";

				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = connect.prepareStatement(query);
				preparedStmt.setString (1, u.getFirstName());
				preparedStmt.setString (2, u.getLastName());
				preparedStmt.setString (3, u.getEmail());
				preparedStmt.setString (4, u.getUserName());
				preparedStmt.setString (5, u.getPassword());

				// execute the preparedstatement
				preparedStmt.execute();


				err = 0; //good
				eventHandler.fireCreteUserRespond(u, err);
				//JOptionPane.showConfirmDialog(null, "Successful Registration", "Result",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE);

			} catch(Exception e) {
				System.out.println(e);
				eventHandler.fireCreteUserRespond(u, err);

			}
		}

		@Override
		public void siteInfoRequest() {
			//display debug message
			System.out.println("Database > Site information request received.");
			try {

				//sql statement
				String userquery = "Select * from psuteam7.site";
				statement = connect.createStatement();
				rt=statement.executeQuery(userquery);


				while(rt.next()) 
				{
					if (rt.getInt(1) > 0) {
						site = new DBSiteTable(rt.getInt(1), rt.getString(2), rt.getString(3), rt.getString(4), rt.getString(5), rt.getString(6), rt.getString(7), rt.getString(8), rt.getString(9));

						break; //the system currently only support one site. 
					}

				}

				//set update info to event queue
				eventHandler.fireSiteInfoUpdate(site);


			}catch (SQLException e) {
				System.out.println(e);	
			}

		}

		@Override
		public void siteInfoUpdateDB(DBSiteTable s) {
			//display debug message
			System.out.println("Database > Site infomation update request received. Site: " + s.getName());
			int err = 1; //bad
			try {
				// the mysql insert statement
				String query = " Update psuteam7.site set Name = ?, Description = ?, Address = ?, Address2 = ?, City = ?, State = ?, Zipcode = ?, CountryCode = ?"
						+ " where ID = " + s.getId();

				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = connect.prepareStatement(query);
				preparedStmt.setString (1, s.getName());
				preparedStmt.setString (2, s.getDescription());
				preparedStmt.setString (3, s.getAddress());
				preparedStmt.setString (4, s.getAddress2());
				preparedStmt.setString (5, s.getCity());
				preparedStmt.setString (6, s.getState());
				preparedStmt.setString (7, s.getZipCode());
				preparedStmt.setString (8, s.getCountryCode());
				preparedStmt.setInt (9, s.getId());
				// execute the preparedstatement
				preparedStmt.execute();


				err = 0; //good
				eventHandler.fireSiteInfoUpdateDBRespond(s, err);
				//JOptionPane.showConfirmDialog(null, "Successful Registration", "Result",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE);

			} catch(Exception e) {
				System.out.println(e);
				eventHandler.fireSiteInfoUpdateDBRespond(s, err);

			}

		}

		@Override
		public void weatherInfoRequest(int siteId) {


			//display debug message
			System.out.println("Database > Weather information request received for site Id: " + siteId);
			try {

				//sql statement
				String userquery = "Select top 1 * from psuteam7.weather where SiteID = " + siteId + " ORDER BY LastUpdate DESC";
				statement = connect.createStatement();
				rt=statement.executeQuery(userquery);


				while(rt.next()) 
				{
					if (rt.getInt(1) > 0) {
						weather = new DBWeatherTable(rt.getInt(1), rt.getInt(2), rt.getDouble(3), rt.getDouble(4), rt.getDouble(5), rt.getInt(6), rt.getDate(7));

						break; //get only current information
					}

				}

				//set update info to event queue
				eventHandler.fireWeatherInfoUpdate(weather);


			}catch (SQLException e) {
				System.out.println(e);	
			}
		}

		@Override
		public void weatherInfoUpdateDB(DBWeatherTable w) {
			//display debug message
			System.out.println("Database > Weather infomation update DB request received. Site: " + w.getSiteId());
			int err = 1; //bad
			try {
				//add to database if change
				if (w.getTemperature() != weather.getTemperature() || w.getHumidity() != weather.getHumidity() || w.getConditionId() != weather.getConditionId()) {
					// the mysql insert statement
					String query = " insert into psuteam7.weather (SiteID, Temperature, Humidity, DewPoint, ConditionID)"
							+ " values (?, ?, ?, ?, ?)";

					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = connect.prepareStatement(query);
					preparedStmt.setInt (1, w.getSiteId());
					preparedStmt.setDouble (2, w.getTemperature());
					preparedStmt.setDouble (3, w.getHumidity());
					preparedStmt.setDouble (4, w.getDewpoint());
					preparedStmt.setInt (5, w.getConditionId());

					// execute the preparedstatement
					preparedStmt.execute();


					err = 0; //good

				} else {
					err = 999; //no change
				}
				//JOptionPane.showConfirmDialog(null, "Successful Registration", "Result",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE);
				eventHandler.fireWeatherInfoUpdateDBRespond(w, err);

			} catch(Exception e) {
				System.out.println(e);
				eventHandler.fireWeatherInfoUpdateDBRespond(w, err);
			}
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