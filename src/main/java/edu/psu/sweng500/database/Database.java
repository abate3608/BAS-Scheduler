package edu.psu.sweng500.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.lang.ClassCastException;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.time.DateUtils;
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
	// public static final String email = null;
	// public static final String userName = null;
	// public static final String passWord = null;
	// public static final String firstName= null;
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
		public void getBacnetDevice() {
			try {
				System.out.println("Database > Bacnet Device Request");

				statement = connect.createStatement();

				String query = "select * from psuteam7.bacnetdevices";

				rt = statement.executeQuery(query);

				while ((rt.next())) {
					DBBacnetDevicesTable d = new DBBacnetDevicesTable();
					d.setObject_Identifier(rt.getString("Object_Identifier"));
					d.setDevice_Address_Binding(rt.getString("Device_Address_Binding"));
					d.setPort(rt.getString("Port"));

					eventHandler.fireBacnetDeviceUpdate(d);
					break;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void setBacnetDevice(DBBacnetDevicesTable d) {
			// try {
			/*
			 * //display debug message System.out.
			 * println("Database > Set BACnet Device request received. Device: "
			 * + u.); int err = 0;
			 * 
			 * try {
			 * 
			 * String query =
			 * " insert into psuteam7.User_Profile (firstName, lastName, email, userName, passWord)"
			 * + " values (?, ?, ?, ?, ?)";
			 * 
			 * // create the mysql insert preparedstatement PreparedStatement
			 * preparedStmt = connect.prepareStatement(query);
			 * preparedStmt.setString (1, u.getFirstName());
			 * preparedStmt.setString (2, u.getLastName());
			 * preparedStmt.setString (3, u.getEmail()); preparedStmt.setString
			 * (4, u.getUserName()); preparedStmt.setString (5,
			 * u.getPassword());
			 * 
			 * // execute the preparedstatement preparedStmt.execute(); err = 0;
			 * //good eventHandler.fireCreteUserRespond(u, err);
			 * 
			 * 
			 * 
			 * 
			 * } catch (Exception e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
		}

		@Override
		public void getEvents(Date startDateTime, Date endDateTime) {
			try {
				System.out.println("Database > Get Events received for date range: " + startDateTime.toString() + " - "
						+ endDateTime.toString());

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				statement = connect.createStatement();

				String query = "select * from psuteam7.schedule where StartDateTime >= '" + df.format(startDateTime)
						+ "' and EndDateTime <= '" + df.format(endDateTime) + "'";

				rt = statement.executeQuery(query);

				ArrayList<DBScheduleTable> sList = new ArrayList<DBScheduleTable>();

				while ((rt.next())) {

					DBScheduleTable s = new DBScheduleTable();
					s.setRowGuid(rt.getString("RowGuid"));
					s.setScheduleId(rt.getInt("ScheduleId"));
					s.setName(rt.getString("Name"));
					s.setDescription(rt.getString("Description"));
					s.setNotes(rt.getString("Notes"));
					s.setControlToState(rt.getInt("ControlToState"));
					s.setStartDateTime(rt.getDate("StartDateTime"));
					s.setEndDateTime(rt.getDate("EndDateTime"));
					s.setMarkedForDelete(rt.getBoolean("MarkedForDelete"));
					s.setRoomName(rt.getString("RoomName"));
					s.setTemperatureSetpoint(rt.getFloat("TemperatureSetPoint"));
					s.setLightIntensity(rt.getInt("LightIntensity"));

					sList.add(s);
				}
				// Send list of events to event queue
				eventHandler.fireEventUpdate(sList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void createEvent(DBScheduleTable s) {

			System.out.println("Database > Create schedule event received: Name - " + s.getName());
			int err = 0; // failed
			try {

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				statement = connect.createStatement();
				Date d = DateUtils.addHours(s.getStartDateTime(), -2);
				String query = "select * from psuteam7.schedule where (StartDateTime <= '" + df.format(d) + "'"
						+ " and EndDateTime > '" + df.format(d) + "') and RoomName = '" + s.getRoomName() + "'";

				rt = statement.executeQuery(query);
				// check for existing schedule
				while (rt.next()) {
					String rowGuid = rt.getString("RowGuid");
					if (rowGuid.equalsIgnoreCase(s.getRowGuid())) {
						err = 2; // guid exist. schedule exist.
						// System.out.println("Database > Schedule event existed
						// in DB: Name - " + s.getName());
					}
					String datetime = null;
					Timestamp timestamp = rt.getTimestamp("StartDateTime");
					if (timestamp != null) {
						datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
					}

					Date startDateTime = df.parse(datetime);

					timestamp = rt.getTimestamp("EndDateTime");
					if (timestamp != null) {
						datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
					}
					Date endDateTime = df.parse(datetime);

					if (startDateTime.compareTo(s.getStartDateTime()) <= 0
							&& endDateTime.compareTo(s.getStartDateTime()) > 0) {
						err = 3; // conflict time. if Null Time entered
						eventHandler.fireCreateEventRespond(s, err);
						// System.out.println("Database > Event [" + s.getName()
						// + "] cannot be added for room [" + s.getRoomName() +
						// "]. Another event already rescheduled at this time -
						// Name: " + rt.getString("Name"));
					}
				}

				if (err < 1) { // no error
					String query2 = "INSERT INTO psuteam7.schedule (RowGuid, ScheduleSiteId, Name, "
							+ "Description, Notes, ControlToState, StartDateTime, EndDateTime, MarkedForDelete, "
							+ "RoomName, TemperatureSetpoint, LightIntensity) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					PreparedStatement ps = connect.prepareStatement(query2);

					UUID uuid = UUID.randomUUID();
					String rowGuid = s.getRowGuid();
					s.setRowGuid(uuid.toString());
					ps.setString(1, uuid.toString()); // RowGuid

					if (s.getScheduleSiteId() < 1) { // if no schedule id set
						ps.setInt(2, 1); // ScheduleSiteId
					} else {
						ps.setInt(2, s.getScheduleSiteId()); // ScheduleSiteId
					}

					// ps.setInt(1, event.getEventID());
					ps.setString(3, s.getName()); // Name
					ps.setString(4, s.getDescription());// Description
					ps.setString(5, s.getNotes());// Notes

					ps.setInt(6, s.getControlToState());// ControlToState

					java.sql.Timestamp sqlStartDateTime = new java.sql.Timestamp(s.getStartDateTime().getTime());
					java.sql.Timestamp sqlEndDateTime = new java.sql.Timestamp(s.getEndDateTime().getTime());

					ps.setObject(7, sqlStartDateTime);// start time
					ps.setObject(8, sqlEndDateTime);// end time
					ps.setBoolean(9, s.getMarkedForDelete()); // MarkedForDelete
					ps.setString(10, s.getRoomName());
					s.setTemperatureSetpoint(temperatureCheck(s.getTemperatureSetpoint()));
					ps.setFloat(11, s.getTemperatureSetpoint());
					ps.setInt(12, s.getLightIntensity());
					ps.addBatch();

					ps.executeBatch();
					err = 0; // 0 = good

				}

				eventHandler.fireCreateEventRespond(s, err);
			} catch (Exception e) {
				e.printStackTrace();
				 err = 1;
				 eventHandler.fireCreateEventRespond(s, err);
			}

			System.out.println(
					"Database > Checking if Start Date (" + s.getStartDateTime() + ") selected has passed...");

			try {

				Date currentDate = new Date();
				// check to validate if User selected date  is not today's date or future date
				if (s.getStartDateTime().before(currentDate)) {
					err = 1;
					eventHandler.fireCreateEventRespond(s, err);
				}
			} catch (Exception b) {
				b.printStackTrace();
			}
						
			try {
				// check to make sure end date cannot be before start date
				if(s.getEndDateTime().before(s.getStartDateTime())) {
					err=5;
					eventHandler.fireCreateEventRespond(s, err);
				}
			} catch(Exception z) {
				z.printStackTrace();
			}

		}

		@Override
		public void readEvent(DBScheduleTable s) {
			int err = 0; // success
			try {
				System.out.println("Database > Get Event with UUID: " + s.getRowGuid());

				statement = connect.createStatement();

				String query = "select * from psuteam7.schedule where RowGuid = '" + s.getRowGuid() + "'";

				rt = statement.executeQuery(query);

				if ((rt.next())) {
					s.setRowGuid(rt.getString("RowGuid"));
					s.setScheduleId(rt.getInt("ScheduleId"));
					s.setName(rt.getString("Name"));
					s.setDescription(rt.getString("Description"));
					s.setNotes(rt.getString("Notes"));
					s.setControlToState(rt.getInt("ControlToState"));
					s.setStartDateTime(rt.getDate("StartDateTime"));
					s.setEndDateTime(rt.getDate("EndDateTime"));
					s.setMarkedForDelete(rt.getBoolean("MarkedForDelete"));
					s.setRoomName(rt.getString("RoomName"));
					s.setTemperatureSetpoint(rt.getFloat("TemperatureSetPoint"));
					s.setLightIntensity(rt.getInt("LightIntensity"));

				} else {
					err = 2;
				}
				// Send list of events to event queue
				eventHandler.fireReadEventRespond(s, err);
			} catch (Exception e) {
				e.printStackTrace();
				err = 1;
				eventHandler.fireReadEventRespond(s, err);
			}

		}

		@Override
		public void updateEvent(DBScheduleTable s) {

			System.out.println("Database > Update schedule event received: Name - " + s.getName());
			int err = 0; // failed
			try {

				statement = connect.createStatement();
				String query = "select * from psuteam7.schedule where RowGuid = '" + s.getRowGuid() + "'";

				rt = statement.executeQuery(query);

				if (rt.next()) {
					if (err < 1) { // no error
						String query2 = "UPDATE psuteam7.schedule SET ScheduleSiteId=?, Name=?, Description=?, Notes=?,"
								+ "ControlToState=?, StartDateTime=?, EndDateTime=?, MarkedForDelete=?, RoomName=?,"
								+ "TemperatureSetpoint=?, LightIntensity=? WHERE RowGuid=?";
						PreparedStatement ps = connect.prepareStatement(query2);

						ps.setInt(1, s.getScheduleSiteId()); // ScheduleSiteId
						ps.setString(2, s.getName()); // Name
						ps.setString(3, s.getDescription());// Description
						ps.setString(4, s.getNotes());// Notes
						ps.setInt(5, s.getControlToState());// ControlToState

						java.sql.Timestamp sqlStartDateTime = new java.sql.Timestamp(s.getStartDateTime().getTime());
						java.sql.Timestamp sqlEndDateTime = new java.sql.Timestamp(s.getEndDateTime().getTime());

						ps.setObject(6, sqlStartDateTime);// start time
						ps.setObject(7, sqlEndDateTime);// end time
						ps.setBoolean(8, s.getMarkedForDelete()); // MarkedForDelete
						ps.setString(9, s.getRoomName());
						s.setTemperatureSetpoint(temperatureCheck(s.getTemperatureSetpoint()));
						ps.setFloat(10, s.getTemperatureSetpoint());
						ps.setInt(11, s.getLightIntensity());

						ps.setString(12, s.getRowGuid()); // RowGuid
						ps.addBatch();

						ps.executeBatch();
						err = 0; // 0 = good

					}
				} else {
					err = 2;
				}
				eventHandler.fireUpdateEventRespond(s, err);
			} catch (Exception e) {
				e.printStackTrace();
				//err = 1;
				//eventHandler.fireUpdateEventRespond(s, err);
			}
			try {

				Date currentDate = new Date();
				// check to validate if User selected date  is not today's date or future date
				if (s.getStartDateTime().before(currentDate)) {
					err = 4;
					eventHandler.fireCreateEventRespond(s, err);
				}
			} catch (Exception b) {
				b.printStackTrace();
			}
			try {
				// check to make sure end date cannot be before start date
				if(s.getEndDateTime().before(s.getStartDateTime())) {
					err=5;
					eventHandler.fireCreateEventRespond(s, err);
				}
			} catch(Exception z) {
				z.printStackTrace();
			}

		}

		@Override
		public void deleteEvent(DBScheduleTable s) {

			System.out.println("Database > Delete schedule event received: Name - " + s.getName());
			int err = 0;

			try {

				statement = connect.createStatement();
				String query = "select * from psuteam7.schedule where RowGuid = '" + s.getRowGuid() + "'";

				rt = statement.executeQuery(query);

				if (rt.next()) {
					Calendar calendar = Calendar.getInstance();
					Date currentTime = calendar.getTime();
					Date startTime = rt.getTimestamp("StartDateTime");
					Date endTime = rt.getTimestamp("EndDateTime");
					if (currentTime.after(startTime) && currentTime.before(endTime)) {
						String query2 = "UPDATE psuteam7.schedule SET MarkedForDelete=? WHERE RowGuid=?";
						PreparedStatement ps = connect.prepareStatement(query2);

						ps.setBoolean(1, s.getMarkedForDelete()); // MarkedForDelete
						ps.setString(2, s.getRowGuid()); // RowGuid
						ps.addBatch();

						ps.executeBatch();
						err = 3; // Delete event in progress
					} else {
						if (err < 1) { // no error
							System.out.println("Database > Deleting Event");
							String query3 = "DELETE FROM psuteam7.schedule WHERE RowGuid=?";
							PreparedStatement ps = connect.prepareStatement(query3);

							ps.setString(1, s.getRowGuid()); // RowGuid
							ps.addBatch();

							ps.executeBatch();
							err = 0; // 0 = good
						}
					}
				} else {
					err = 2;
					// Row does not exist
					eventHandler.fireDeleteEventRespond(s, err);
				}
				eventHandler.fireDeleteEventRespond(s, err);
			} catch (Exception e) {
				e.printStackTrace();
				err = 1; // Event failed
				eventHandler.fireDeleteEventRespond(s, err);
			}

		}

		@Override
		public void authenticateUserRequest(String userName, String passWord) {
			// display debug message
			System.out
					.println("Database > Authentication request received. User: " + userName + " Password:" + passWord);
			try {

				// sql statement
				String userquery = "Select * from psuteam7.User_Profile where userName = '" + userName
						+ "' and passWord = '" + passWord + "' ";
				statement = connect.createStatement();
				rt = statement.executeQuery(userquery);

				// create new user variable
				User u = new User(userName, false);
				while (rt.next()) {
					String IDName = rt.getString(5);
					String DBpass = rt.getString(6);

					if (userName.equals(IDName) && (passWord.equals(DBpass))) {
						// found matching credential

						// JOptionPane.showMessageDialog(null, "Login
						// Successful");
						u.setAuthenticated(true);
						// exit while loop
						break;
					} else {
						u.setAuthenticated(false);
					}
				}

				eventHandler.fireAuthenticateUserUpdate(u);

			} catch (SQLException e) {
				System.out.println(e);
			}
		}

		@Override
		public void createUser(User u) {
			// display debug message
			System.out.println("Database > Create user request received. UserInfo: " + u.getUserName());
			int err = 0;

			try {

				String query = " insert into psuteam7.User_Profile (firstName, lastName, email, userName, passWord)"
						+ " values (?, ?, ?, ?, ?)";

				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = connect.prepareStatement(query);
				preparedStmt.setString(1, u.getFirstName());
				preparedStmt.setString(2, u.getLastName());
				preparedStmt.setString(3, u.getEmail());
				preparedStmt.setString(4, u.getUserName());
				preparedStmt.setString(5, u.getPassword());

				// execute the preparedstatement
				preparedStmt.execute();
				err = 0; // good
				eventHandler.fireCreteUserRespond(u, err);
				// JOptionPane.showConfirmDialog(null, "Successful
				// Registration",
				// "Result",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE);

			} catch (Exception e) {

				// eventHandler.fireCreteUserRespond(u, err);
				// }
				System.out.println("Database > Create UserEmail request received. Email: " + u.getEmail());
				try {

					String emailquery = "Select * from psuteam7.User_Profile where Email = ? ";
					PreparedStatement statement = connect.prepareStatement(emailquery);
					statement.setString(1, u.getEmail());
					rt = statement.executeQuery();

					if (rt.next()) {

						err = 1;// already exists
						eventHandler.fireCreteUserRespond(u, err);
						// System.out.println(1);
					}
				} catch (Exception f) {
					f.printStackTrace();
					// System.out.println(f);
					// eventHandler.fireCreteUserRespond(u, err);
				}
				System.out.println("Database > Create UseruserName request Received. UserName: " + u.getUserName());
				try {

					String userquery = "Select * from psuteam7.User_Profile where userName = ?";
					PreparedStatement statement = connect.prepareStatement(userquery);
					statement.setString(1, u.getUserName());
					rt = statement.executeQuery();

					if (rt.next()) {
						err = 2;// already exists
						eventHandler.fireCreteUserRespond(u, err);
					}
				} catch (Exception g) {
					g.printStackTrace();
					// System.out.println(g);
					// eventHandler.fireCreteUserRespond(u, err);
				}
			}
			// }
		}

		@Override
		public void siteInfoRequest() {
			// display debug message
			System.out.println("Database > Site information request received.");
			try {

				// sql statement
				String userquery = "Select * from psuteam7.site";
				statement = connect.createStatement();
				rt = statement.executeQuery(userquery);

				while (rt.next()) {
					if (rt.getInt(1) > 0) {
						site = new DBSiteTable(rt.getInt(1), rt.getString(2), rt.getString(3), rt.getString(4),
								rt.getString(5), rt.getString(6), rt.getString(7), rt.getString(8), rt.getString(9));

						break; // the system currently only support one site.
					}

				}

				// set update info to event queue
				eventHandler.fireSiteInfoUpdate(site);

			} catch (SQLException e) {
				System.out.println(e);
			}

		}

		@Override
		public void roomInfoRequest() {
			// display debug message
			System.out.println("Database > Rooms information request received.");
			try {

				// sql statement
				String userquery = "Select * from psuteam7.room";
				statement = connect.createStatement();
				rt = statement.executeQuery(userquery);

				while (rt.next()) {
					if (rt.getInt(1) > 0) {
						DBRoomTable r = new DBRoomTable(rt.getInt(1), rt.getString(2), rt.getString(3), rt.getString(4),
								rt.getInt(5), rt.getInt(6));
						// set update info to event queue
						eventHandler.fireRoomInfoUpdate(r);
					}

				}

			} catch (SQLException e) {
				System.out.println(e);
			}

		}

		@Override
		public void roomInfoUpdateDB(DBRoomTable r) {
			// display debug message
			System.out.println("Database > Room infomation update DB request received. Room name: " + r.getRoomName());
			int err = 0; // good
			String query = null;

			try {

				query = "select * from psuteam7.room where RoomNumber = '" + r.getRoomNumber() + "'";
				statement = connect.createStatement();
				rt = statement.executeQuery(query);
				// check for existing schedule
				if (rt.next()) {

					// System.out.println("Database > Room exist. Update room
					// info.");
					err = 2;

				}

				if (err == 2) {
					query = " Update psuteam7.room set RoomName = ?, RoomType = ?" + " where RoomNumber = ?";
					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = connect.prepareStatement(query);
					preparedStmt.setString(1, r.getRoomName());
					preparedStmt.setString(2, r.getRoomTye());
					preparedStmt.setString(3, r.getRoomNumber());
					// execute the preparedstatement
					preparedStmt.execute();

				} else if (err == 0) {
					query = " insert into psuteam7.room (RoomNumber, RoomName, RoomType)" + " values (?, ?, ?)";

					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = connect.prepareStatement(query);
					preparedStmt.setString(1, r.getRoomNumber());
					preparedStmt.setString(2, r.getRoomName());
					preparedStmt.setString(3, r.getRoomTye());
					// execute the preparedstatement
					preparedStmt.execute();
				}

				err = 0; // good
				// eventHandler.fireRoomInfoUpdateDBRespond(r, err);
				// JOptionPane.showConfirmDialog(null, "Successful
				// Registration",
				// "Result",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE);

			} catch (Exception e) {
				System.out.println(e);
				err = 1; // bad
				// eventHandler.fireRoomInfoUpdateDBRespond(r, err);

			}

		}

		@Override
		public void siteInfoUpdateDB(DBSiteTable s) {
			// display debug message
			System.out.println("Database > Site infomation update request received. Site: " + s.getName());
			int err = 1; // bad
			try {
				// the mysql insert statement
				String query = " Update psuteam7.site set Name = ?, Description = ?, Address = ?, Address2 = ?, City = ?, State = ?, Zipcode = ?, CountryCode = ?"
						+ " where ID = " + s.getId();

				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = connect.prepareStatement(query);
				preparedStmt.setString(1, s.getName());
				preparedStmt.setString(2, s.getDescription());
				preparedStmt.setString(3, s.getAddress());
				preparedStmt.setString(4, s.getAddress2());
				preparedStmt.setString(5, s.getCity());
				preparedStmt.setString(6, s.getState());
				preparedStmt.setString(7, s.getZipCode());
				preparedStmt.setString(8, s.getCountryCode());
				preparedStmt.setInt(9, s.getId());
				// execute the preparedstatement
				preparedStmt.execute();

				err = 0; // good
				eventHandler.fireSiteInfoUpdateDBRespond(s, err);
				// JOptionPane.showConfirmDialog(null, "Successful
				// Registration",
				// "Result",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE);

			} catch (Exception e) {
				System.out.println(e);
				err = 1; // bad
				eventHandler.fireSiteInfoUpdateDBRespond(s, err);

			}

		}

		@Override
		public void weatherInfoRequest(int siteId) {
			// display debug message
			System.out.println("Database > Weather information request received for site Id: " + siteId);
			try {

				// sql statement
				String userquery = "Select * from psuteam7.weather where SiteID = " + siteId + " ORDER BY ID DESC";
				statement = connect.createStatement();
				rt = statement.executeQuery(userquery);

				while (rt.next()) {
					if (rt.getInt(1) > 0) {
						weather = new DBWeatherTable(rt.getInt(1), rt.getInt(2), rt.getDouble(3), rt.getDouble(4),
								rt.getDouble(5), rt.getInt(6), rt.getDate(7));

						break; // get only current (latest) information
					}

				}

				// set update info to event queue
				eventHandler.fireWeatherInfoUpdate(weather);

			} catch (SQLException e) {
				System.out.println(e);
			}
		}

		@Override
		public void weatherInfoUpdateDB(DBWeatherTable w) {
			// display debug message
			System.out.println("Database > Weather infomation update DB request received. Site: " + w.getSiteId());
			int err = 1; // bad
			try {
				// add to database if change
				if ((int) w.getTemperature() != (int) weather.getTemperature()
						|| (int) w.getHumidity() != (int) weather.getHumidity()
						|| w.getConditionId() != weather.getConditionId()) {
					// the mysql insert statement
					String query = " insert into psuteam7.weather (SiteID, Temperature, Humidity, DewPoint, ConditionID)"
							+ " values (?, ?, ?, ?, ?)";

					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = connect.prepareStatement(query);
					preparedStmt.setInt(1, w.getSiteId());
					preparedStmt.setDouble(2, w.getTemperature());
					preparedStmt.setDouble(3, w.getHumidity());
					preparedStmt.setDouble(4, w.getDewpoint());
					preparedStmt.setInt(5, w.getConditionId());

					// execute the preparedstatement
					preparedStmt.execute();

					err = 0; // good

				} else {
					err = 999; // no change
				}
				// JOptionPane.showConfirmDialog(null, "Successful
				// Registration",
				// "Result",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE);
				eventHandler.fireWeatherInfoUpdateDBRespond(w, err);

			} catch (Exception e) {
				System.out.println(e);
				eventHandler.fireWeatherInfoUpdateDBRespond(w, err);
			}
		}

		@Override
		public void updateBaseline(String roomNumber) {
			//display debug message
			System.out.println("Database > Update baseline request received. Room: " + roomNumber);
			
			try {
				

			} catch(Exception e) {
				System.out.println(e);
				
			}
		}

		@Override
		public void updateOccStatus() {
			//display debug message
			System.out.println("Database > Update Occupancy status request received.");
			
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 

				statement = connect.createStatement();
				Date dateTime = new Date();
				
				java.sql.Timestamp ts = new java.sql.Timestamp(dateTime.getTime());
				
				
				String query = "select * from psuteam7.schedule where StartDateTime <= '" + df.format(dateTime) + "' and EndDateTime >= '" + df.format(dateTime) + "'";

				rt = statement.executeQuery(query); 
				
				ArrayList<DBScheduleTable> sList = new ArrayList<DBScheduleTable>();

				while ((rt.next())) { 

					DBScheduleTable s = new DBScheduleTable();
					s.setRowGuid(rt.getString("RowGuid"));
					s.setScheduleId(rt.getInt("ScheduleId"));
					s.setName(rt.getString("Name"));
					s.setDescription(rt.getString("Description"));
					s.setNotes(rt.getString("Notes"));
					s.setControlToState(rt.getInt("ControlToState"));
					s.setStartDateTime(rt.getDate("StartDateTime"));
					s.setEndDateTime(rt.getDate("EndDateTime")); 
					s.setMarkedForDelete(rt.getBoolean("MarkedForDelete"));
					s.setRoomName(rt.getString("RoomName"));
					s.setTemperatureSetpoint(rt.getFloat("TemperatureSetPoint"));
					s.setLightIntensity(rt.getInt("LightIntensity"));
					
					sList.add(s);
					
					
					query = "Update psuteam7.room set OccState = ? where RoomNumber = '" + s.getRoomName() + "'";

					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = connect.prepareStatement(query);
					preparedStmt.setInt(1, 1);
					// execute the preparedstatement
					preparedStmt.execute();

					
					
				}
				
				query = "Update psuteam7.room set OccState = ? where LastUpdate < '" + ts + "'";

				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = connect.prepareStatement(query);
				preparedStmt.setInt(1, 0);
				// execute the preparedstatement
				preparedStmt.execute();

			} catch(Exception e) {
				System.out.println(e);
				
			}
		}
		private int calendarId;

		public int getCalendarId() {
			return calendarId;
		}

		public void setCalendarId(int calendarId) {
			this.calendarId = calendarId;
		}

		private float temperatureCheck(float temp) {
			float newTemp;
			if (temp < 60.0 || temp > 80.0) {
				newTemp = 68.0f;
			} else {
				newTemp = temp;
			}
			return newTemp;
		}
	}
}
