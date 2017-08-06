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
import java.util.Locale;
import java.lang.ClassCastException;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.chainsaw.Main;

import edu.psu.sweng500.bacnetserver.bacnet4j2.LocalDevice;
import edu.psu.sweng500.bacnetserver.bacnet4j2.obj.BACnetObject;
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
	//private static ResultSet rt = null;
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

				ResultSet rt = statement.executeQuery(query);

				while ((rt.next())) {
					DBBacnetDevicesTable d = new DBBacnetDevicesTable();
					d.setObject_Identifier(rt.getString("Object_Identifier"));
					d.setDevice_Address_Binding(rt.getString("Device_Address_Binding"));
					d.setPort(rt.getString("Port"));

					eventHandler.fireBacnetDeviceUpdate(d);
					break;
				}
				//rt = null;

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

				String query = "SELECT * FROM psuteam7.schedule WHERE ('" + df.format(startDateTime) + 
						"' BETWEEN CAST(StartDateTime AS DATE) AND CAST(EndDateTime AS DATE)) " +
						"OR ('" + endDateTime + "' BETWEEN CAST(StartDateTime AS DATE) AND CAST(EndDateTime AS DATE)) " +
						"OR (StartDateTime BETWEEN '" + df.format(startDateTime) + "' AND '" + df.format(endDateTime) + "') " +
						"OR (EndDateTime BETWEEN '" + df.format(startDateTime) + "' AND '" + df.format(endDateTime) + "')";

				ResultSet rt = statement.executeQuery(query);

				ArrayList<DBScheduleTable> sList = new ArrayList<DBScheduleTable>();

				while ((rt.next())) {

					DBScheduleTable s = new DBScheduleTable();
					s.setRowGuid(rt.getString("RowGuid"));
					s.setScheduleId(rt.getInt("ScheduleId"));
					s.setName(rt.getString("Name"));
					s.setDescription(rt.getString("Description"));
					s.setNotes(rt.getString("Notes"));
					s.setControlToState(rt.getInt("ControlToState"));
					s.setStartDateTime(rt.getTimestamp("StartDateTime"));
					s.setEndDateTime(rt.getTimestamp("EndDateTime"));
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
		public void getDailyEvents(Date dailyDate) {
			try {
				System.out.println("Database >> Get Daily Events received for: " + dailyDate.toString());

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

				statement = connect.createStatement();

				String query = "SELECT * FROM psuteam7.schedule WHERE '" + df.format(dailyDate) + "'"
						+ " BETWEEN CAST(StartDateTime AS DATE) AND CAST(EndDateTime AS DATE);";

				ResultSet rt = statement.executeQuery(query);

				ArrayList<DBScheduleTable> sList = new ArrayList<DBScheduleTable>();

				while ((rt.next())) {

					DBScheduleTable s = new DBScheduleTable();
					s.setRowGuid(rt.getString("RowGuid"));
					s.setScheduleId(rt.getInt("ScheduleId"));
					s.setName(rt.getString("Name"));
					s.setDescription(rt.getString("Description"));
					s.setNotes(rt.getString("Notes"));
					s.setControlToState(rt.getInt("ControlToState"));
					s.setStartDateTime(rt.getTimestamp("StartDateTime"));
					s.setEndDateTime(rt.getTimestamp("EndDateTime"));
					s.setMarkedForDelete(rt.getBoolean("MarkedForDelete"));
					s.setRoomName(rt.getString("RoomName"));
					s.setTemperatureSetpoint(rt.getFloat("TemperatureSetPoint"));
					s.setLightIntensity(rt.getInt("LightIntensity"));

					sList.add(s);
				}
				// Send list of events to event queue
				eventHandler.fireEventDailyUpdate(sList);
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

				String query = "SELECT * FROM psuteam7.schedule WHERE ('" + df.format(s.getStartDateTime()) + 
						"' BETWEEN CAST(StartDateTime AS DATE) AND CAST(EndDateTime AS DATE)) " +
						"OR ('" + s.getEndDateTime() + "' BETWEEN CAST(StartDateTime AS DATE) AND CAST(EndDateTime AS DATE)) " +
						"OR (StartDateTime BETWEEN '" + df.format(s.getStartDateTime()) + "' AND '" + df.format(s.getEndDateTime()) + "') " +
						"OR (EndDateTime BETWEEN '" + df.format(s.getStartDateTime()) + "' AND '" + df.format(s.getEndDateTime()) + "') " +
						"AND RoomName = '" + s.getRoomName() + "'";

				ResultSet rt = statement.executeQuery(query);
				// check for existing schedule
				while (rt.next()) {
					String rowGuid = rt.getString("RowGuid");
					if (rowGuid.equalsIgnoreCase(s.getRowGuid())) {
						err = 2; // guid exist. schedule exist.
						// System.out.println("Database > Schedule event existed
						// in DB: Name - " + s.getName());
					} else {
						//Time conflict with room
						err = 5;
					}
				}

				Date currentDate = new Date();
				// check to validate if User selected start/end date are not before current date
				if (s.getStartDateTime().before(currentDate) || s.getEndDateTime().before(s.getStartDateTime())) {
					err = 3;
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
				System.out.println("Database >> createEvent Error code: " + err);
				eventHandler.fireCreateEventRespond(s, err);
			} catch (Exception e) {
				e.printStackTrace();
				err = 1;
				eventHandler.fireCreateEventRespond(s, err);
			}
		}

		@Override
		public void readEvent(DBScheduleTable s) {
			int err = 0; // success
			try {
				System.out.println("Database > Get Event with UUID: " + s.getRowGuid());

				statement = connect.createStatement();

				String query = "select * from psuteam7.schedule where RowGuid = '" + s.getRowGuid() + "'";

				ResultSet rt = statement.executeQuery(query);

				if ((rt.next())) {
					s.setRowGuid(rt.getString("RowGuid"));
					s.setScheduleId(rt.getInt("ScheduleId"));
					s.setName(rt.getString("Name"));
					s.setDescription(rt.getString("Description"));
					s.setNotes(rt.getString("Notes"));
					s.setControlToState(rt.getInt("ControlToState"));
					s.setStartDateTime(rt.getTimestamp("StartDateTime"));
					s.setEndDateTime(rt.getTimestamp("EndDateTime"));
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

				ResultSet rt = statement.executeQuery(query);

				if (rt.next()) {

					Date currentDate = new Date();
					// check to validate if User selected dates are not before current date
					if (s.getEndDateTime().before(currentDate) || s.getEndDateTime().before(s.getStartDateTime())) {
						err = 4;
					}

					if(err < 1) {
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

						statement = connect.createStatement();

						String queryDup = "SELECT * FROM psuteam7.schedule WHERE ('" + df.format(s.getStartDateTime()) + 
								"' BETWEEN CAST(StartDateTime AS DATE) AND CAST(EndDateTime AS DATE)) " +
								"OR ('" + s.getEndDateTime() + "' BETWEEN CAST(StartDateTime AS DATE) AND CAST(EndDateTime AS DATE)) " +
								"OR (StartDateTime BETWEEN '" + df.format(s.getStartDateTime()) + "' AND '" + df.format(s.getEndDateTime()) + "') " +
								"OR (EndDateTime BETWEEN '" + df.format(s.getStartDateTime()) + "' AND '" + df.format(s.getEndDateTime()) + "') " +
								"AND RoomName = '" + s.getRoomName() + "'";


						rt = statement.executeQuery(queryDup);
						// check for existing schedule
						while (rt.next()) {
							//Time conflict with room
							err = 5;
						}
					}

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
				System.out.println("Database >> updateEvent Error code: " + err);
				eventHandler.fireUpdateEventRespond(s, err);
			} catch (Exception e) {
				e.printStackTrace();
				err = 1;
				eventHandler.fireUpdateEventRespond(s, err);
			}
		}

		@Override
		public void deleteEvent(DBScheduleTable s) {

			System.out.println("Database > Delete schedule event received: Name - " + s.getName());
			int err = 0;

			try {

				statement = connect.createStatement();
				String query = "select * from psuteam7.schedule where RowGuid = '" + s.getRowGuid() + "'";

				ResultSet rt = statement.executeQuery(query);

				if (rt.next()) {
					Calendar calendar = Calendar.getInstance();
					Date currentTime = calendar.getTime();
					Date startTime = rt.getTimestamp("StartDateTime");
					Date endTime = rt.getTimestamp("EndDateTime");
					System.out.println("CurrentTime: " + currentTime + " StartTime: " + startTime + " EndTime: " + endTime);
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
					// Row does not exist
					err = 2;
				}
				System.out.println("Database >> deleteEvent Error code: " + err);
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
				ResultSet rt = statement.executeQuery(userquery);

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
					ResultSet rt = statement.executeQuery();

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
					ResultSet rt = statement.executeQuery();

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
				ResultSet rt = statement.executeQuery(userquery);

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
				ResultSet rt = statement.executeQuery(userquery);

				while (rt.next()) {
					if (rt.getInt(1) > 0) {
						DBRoomTable r = new DBRoomTable(rt.getInt(1), rt.getString(2), rt.getString(3), rt.getString(4),
								rt.getInt(5), rt.getInt(6), rt.getInt(7), rt.getFloat(8), rt.getFloat(9), rt.getFloat(10),
								rt.getInt(13), rt.getInt(14), rt.getFloat(15));
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
				ResultSet rt = statement.executeQuery(query);
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
				//preparedStmt.setInt(9, s.getId());
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
				ResultSet rt = statement.executeQuery(userquery);

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



					//update optimize time
					//get current baseline
					query = "Select * from psuteam7.baseline where OAT = " + Math.round(w.getTemperature());
					statement = connect.createStatement();
					ResultSet rt3 = statement.executeQuery(query);
					DBBaselineTable baseline = new DBBaselineTable();
					while (rt3.next()) {
						baseline.setID(rt3.getInt(1));
						baseline.setSiteID(rt3.getInt(2));
						baseline.setRoomNumber(rt3.getString(3));
						baseline.setYunocc(rt3.getFloat(4));
						baseline.setOAT(rt3.getInt(5));
						baseline.setX4(rt3.getFloat(6));
						baseline.setY4(rt3.getFloat(7));
						baseline.setX5(rt3.getFloat(8));
						baseline.setY5(rt3.getFloat(9));
						baseline.setX6(rt3.getFloat(10));
						baseline.setY6(rt3.getFloat(11));
						baseline.setX7(rt3.getFloat(12));
						baseline.setY7(rt3.getFloat(13));
						baseline.setXz0(rt3.getFloat(14));
						baseline.setYz0(rt3.getFloat(15));
						baseline.setXz1(rt3.getFloat(16));
						baseline.setYz1(rt3.getFloat(17));
						baseline.setXz2(rt3.getFloat(18));
						baseline.setYz2(rt3.getFloat(19));
						baseline.setXz3(rt3.getFloat(20));
						baseline.setYz3(rt3.getFloat(21));
						baseline.setYocc(rt3.getFloat(22));
						baseline.setX0(rt3.getFloat(23));
						baseline.setY0(rt3.getFloat(24));
						baseline.setX1(rt3.getFloat(25));
						baseline.setY1(rt3.getFloat(26));
						baseline.setX2(rt3.getFloat(27));
						baseline.setY2(rt3.getFloat(28));
						baseline.setX3(rt3.getFloat(29));
						baseline.setY3(rt3.getFloat(30));
						baseline.setX(rt3.getFloat(31));
						baseline.setY(rt3.getFloat(32));
						baseline.setOccTime(rt3.getTimestamp(33));
						baseline.setUnOccTime(rt3.getTimestamp(34));

						if (baseline.getOccTime() == null) { 
							baseline.setOccTime(new Date()); 
						}

						if (baseline.getUnOccTime() == null) { 
							baseline.setUnOccTime(new Date()); 
						}


						// sql statement
						query = "Select * from psuteam7.room where RoomNumber = '" + baseline.getRoomNumber() + "'";
						statement = connect.createStatement();
						ResultSet rt2 = statement.executeQuery(query);
						DBRoomTable room = new DBRoomTable();
						while (rt2.next()) {
							room = new DBRoomTable(rt2.getInt(1), rt2.getString(2), rt2.getString(3), rt2.getString(4),
									rt2.getInt(5), rt2.getInt(6), rt2.getInt(7), rt2.getFloat(8), rt2.getFloat(9), rt2.getFloat(10),
									rt2.getInt(13), rt2.getInt(14), rt2.getFloat(15));
							room.setLastUpdated(rt2.getTimestamp(12));
							break;
						}
						if (baseline.getYocc() == 0) { baseline.setYocc(1); }
						if (baseline.getX() ==0) { baseline.setX(1); }
						if (baseline.getY7() == 0) { baseline.setY7(1); }
						if (baseline.getX7() ==0) { baseline.setX7(1); }

						int occOffsetPerDegree = Math.round(baseline.getYocc() / (baseline.getX() / 60));
						int unoccOffsetPerDegree = Math.round(baseline.getY7() / (baseline.getX7() / 60));

						int occDegreeSpan = Math.round(Math.abs(room.getTempSetpoint() - room.getRoomTemp()));
						int unoccDegreeSpan = Math.round(Math.abs(room.getUnoccSetpoint() - room.getRoomTemp()));

						int occOffset = occOffsetPerDegree * occDegreeSpan;
						int unoccOffset = unoccOffsetPerDegree;

						query = "Update psuteam7.room set OccOffset = ?, UnoccOffset = ? where RoomNumber = '" + room.getRoomNumber() + "'";

						// create the mysql insert preparedstatement
						preparedStmt = connect.prepareStatement(query);

						preparedStmt.setFloat(1, occOffset);
						preparedStmt.setFloat(2, unoccOffset);
						// execute the preparedstatement
						preparedStmt.execute();

					}


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
		public void getBaseline(String roomNumber) {
			//display debug message
			System.out.println("Database > Get baseline request received. Room: " + roomNumber);

			try {
				boolean hasRecord = false;
				//get current baseline
				String query = "Select * from psuteam7.baseline where RoomNumber = '" + roomNumber + "' and OAT >= " + weather.getTemperature() + " order by OAT ASC";
				statement = connect.createStatement();
				ResultSet  rt = statement.executeQuery(query);
				DBBaselineTable baseline = new DBBaselineTable();
				while (rt.next()) {
					baseline.setID(rt.getInt(1));
					baseline.setSiteID(rt.getInt(2));
					baseline.setRoomNumber(rt.getString(3));
					baseline.setYunocc(rt.getFloat(4));
					baseline.setOAT(rt.getInt(5));
					baseline.setX4(rt.getFloat(6));
					baseline.setY4(rt.getFloat(7));
					baseline.setX5(rt.getFloat(8));
					baseline.setY5(rt.getFloat(9));
					baseline.setX6(rt.getFloat(10));
					baseline.setY6(rt.getFloat(11));
					baseline.setX7(rt.getFloat(12));
					baseline.setY7(rt.getFloat(13));
					baseline.setXz0(rt.getFloat(14));
					baseline.setYz0(rt.getFloat(15));
					baseline.setXz1(rt.getFloat(16));
					baseline.setYz1(rt.getFloat(17));
					baseline.setXz2(rt.getFloat(18));
					baseline.setYz2(rt.getFloat(19));
					baseline.setXz3(rt.getFloat(20));
					baseline.setYz3(rt.getFloat(21));
					baseline.setYocc(rt.getFloat(22));
					baseline.setX0(rt.getFloat(23));
					baseline.setY0(rt.getFloat(24));
					baseline.setX1(rt.getFloat(25));
					baseline.setY1(rt.getFloat(26));
					baseline.setX2(rt.getFloat(27));
					baseline.setY2(rt.getFloat(28));
					baseline.setX3(rt.getFloat(29));
					baseline.setY3(rt.getFloat(30));
					baseline.setX(rt.getFloat(31));
					baseline.setY(rt.getFloat(32));
					baseline.setOccTime(rt.getTimestamp(33));
					baseline.setUnOccTime(rt.getTimestamp(34));

					if (baseline.getOccTime() == null) { 
						baseline.setOccTime(new Date()); 
					}

					if (baseline.getUnOccTime() == null) { 
						baseline.setUnOccTime(new Date()); 
					}

					hasRecord = true;
					break;
				}
				
				if (!hasRecord) {
					query = "Select * from psuteam7.baseline where RoomNumber = '" + roomNumber + "' order by OAT DESC";
					statement = connect.createStatement();
					ResultSet  rt2 = statement.executeQuery(query);
					//DBBaselineTable baseline = new DBBaselineTable();
					while (rt2.next()) {
						baseline.setID(rt2.getInt(1));
						baseline.setSiteID(rt2.getInt(2));
						baseline.setRoomNumber(rt2.getString(3));
						baseline.setYunocc(rt2.getFloat(4));
						baseline.setOAT(rt2.getInt(5));
						baseline.setX4(rt2.getFloat(6));
						baseline.setY4(rt2.getFloat(7));
						baseline.setX5(rt2.getFloat(8));
						baseline.setY5(rt2.getFloat(9));
						baseline.setX6(rt2.getFloat(10));
						baseline.setY6(rt2.getFloat(11));
						baseline.setX7(rt2.getFloat(12));
						baseline.setY7(rt2.getFloat(13));
						baseline.setXz0(rt2.getFloat(14));
						baseline.setYz0(rt2.getFloat(15));
						baseline.setXz1(rt2.getFloat(16));
						baseline.setYz1(rt2.getFloat(17));
						baseline.setXz2(rt2.getFloat(18));
						baseline.setYz2(rt2.getFloat(19));
						baseline.setXz3(rt2.getFloat(20));
						baseline.setYz3(rt2.getFloat(21));
						baseline.setYocc(rt2.getFloat(22));
						baseline.setX0(rt2.getFloat(23));
						baseline.setY0(rt2.getFloat(24));
						baseline.setX1(rt2.getFloat(25));
						baseline.setY1(rt2.getFloat(26));
						baseline.setX2(rt2.getFloat(27));
						baseline.setY2(rt2.getFloat(28));
						baseline.setX3(rt2.getFloat(29));
						baseline.setY3(rt2.getFloat(30));
						baseline.setX(rt2.getFloat(31));
						baseline.setY(rt2.getFloat(32));
						baseline.setOccTime(rt2.getTimestamp(33));
						baseline.setUnOccTime(rt2.getTimestamp(34));

						if (baseline.getOccTime() == null) { 
							baseline.setOccTime(new Date()); 
						}

						if (baseline.getUnOccTime() == null) { 
							baseline.setUnOccTime(new Date()); 
						}

						
						
						hasRecord = true;
						break;
					}
				}
				
				if (!hasRecord) {
					baseline.setRoomNumber(roomNumber);
				}
				
				query = "Select * from psuteam7.room where RoomNumber = '" + roomNumber + "'";
				statement = connect.createStatement();
				ResultSet rt1 = statement.executeQuery(query);
				DBRoomTable room = new DBRoomTable();
				while (rt1.next()) {
					room = new DBRoomTable(rt1.getInt(1), rt1.getString(2), rt1.getString(3), rt1.getString(4),
							rt1.getInt(5), rt1.getInt(6), rt1.getInt(7), rt1.getFloat(8), rt1.getFloat(9), rt1.getFloat(10),
							rt1.getInt(13), rt1.getInt(14), rt1.getFloat(15));
					room.setLastUpdated(rt1.getTimestamp(12));
					break;
				}
				
				baseline.setOccOffset(room.getOccOffset());
				baseline.setUnoccOffset(room.getUnoccOffset());
				baseline.setRoomTemp(room.getRoomTemp());
				eventHandler.fireGetBaselineRespond(baseline);
			} catch(Exception e) {
				System.out.println(e);

			}
		}

		@Override
		public void updateSpaceTemp (String roomNumber, float roomTemp) {
			System.out.println("Database > Update space temp request received. Room: " 
					+ roomNumber
					+ " temp " + roomTemp);
			try {
				String query = "Update psuteam7.room set RoomTemp = ? where RoomNumber = '" + roomNumber + "'";

				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt;
				preparedStmt = connect.prepareStatement(query);

				preparedStmt.setFloat(1, roomTemp);
				// execute the preparedstatement
				preparedStmt.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void updateUnoccTempSetpoint (String roomNumber, float unoccTempSetpoint) {
			System.out.println("Database > Update space temp request received. Room: " 
					+ roomNumber
					+ " UnocctempSp " + unoccTempSetpoint);
			try {
				String query = "Update psuteam7.room set UnoccSetpoint = ? where RoomNumber = '" + roomNumber + "'";

				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt;
				preparedStmt = connect.prepareStatement(query);

				preparedStmt.setFloat(1, unoccTempSetpoint);
				// execute the preparedstatement
				preparedStmt.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void saveRoomHistoryData (BacnetObject obj) {
			//display debug message
			System.out.println("Database > Update room history data request received. Room: " 
					+ obj.getRoomNumber()
					+ " Status " + obj.getStatus()
					+ " OAT " + obj.getOAT()
					+ " SAT " + obj.getRoomTemp());

			Date CurrentDateTime = new Date();
			float Yocc = 0;
			float n = 0;
			float Y0 = 0;
			float Y1 = 0;
			float Y2 = 0;
			float Y3 = 0;
			float Y = 0;
			//Date OccTime = new Date();
			float X0 = 0;
			float X1 = 0;
			float X2 = 0;
			float X3 = 0;
			float X = 0;
			//update room occ status
			try {
				// sql statement
				String query = "Select * from psuteam7.room where RoomNumber = '" + obj.getRoomNumber() + "'";
				statement = connect.createStatement();
				ResultSet rt1 = statement.executeQuery(query);
				DBRoomTable room = new DBRoomTable();
				while (rt1.next()) {
					room = new DBRoomTable(rt1.getInt(1), rt1.getString(2), rt1.getString(3), rt1.getString(4),
							rt1.getInt(5), rt1.getInt(6), rt1.getInt(7), rt1.getFloat(8), rt1.getFloat(9), rt1.getFloat(10),
							rt1.getInt(13), rt1.getInt(14), rt1.getFloat(15));
					room.setLastUpdated(rt1.getTimestamp(12));
					break;
				}

				//get current baseline
				query = "Select * from psuteam7.baseline where RoomNumber = '" + obj.getRoomNumber() + "' and OAT = " + obj.getOAT();
				statement = connect.createStatement();
				ResultSet  rt = statement.executeQuery(query);
				DBBaselineTable baseline = new DBBaselineTable();
				while (rt.next()) {
					baseline.setID(rt.getInt(1));
					baseline.setSiteID(rt.getInt(2));
					baseline.setRoomNumber(rt.getString(3));
					baseline.setYunocc(rt.getFloat(4));
					baseline.setOAT(rt.getInt(5));
					baseline.setX4(rt.getFloat(6));
					baseline.setY4(rt.getFloat(7));
					baseline.setX5(rt.getFloat(8));
					baseline.setY5(rt.getFloat(9));
					baseline.setX6(rt.getFloat(10));
					baseline.setY6(rt.getFloat(11));
					baseline.setX7(rt.getFloat(12));
					baseline.setY7(rt.getFloat(13));
					baseline.setXz0(rt.getFloat(14));
					baseline.setYz0(rt.getFloat(15));
					baseline.setXz1(rt.getFloat(16));
					baseline.setYz1(rt.getFloat(17));
					baseline.setXz2(rt.getFloat(18));
					baseline.setYz2(rt.getFloat(19));
					baseline.setXz3(rt.getFloat(20));
					baseline.setYz3(rt.getFloat(21));
					baseline.setYocc(rt.getFloat(22));
					baseline.setX0(rt.getFloat(23));
					baseline.setY0(rt.getFloat(24));
					baseline.setX1(rt.getFloat(25));
					baseline.setY1(rt.getFloat(26));
					baseline.setX2(rt.getFloat(27));
					baseline.setY2(rt.getFloat(28));
					baseline.setX3(rt.getFloat(29));
					baseline.setY3(rt.getFloat(30));
					baseline.setX(rt.getFloat(31));
					baseline.setY(rt.getFloat(32));
					baseline.setOccTime(rt.getTimestamp(33));
					baseline.setUnOccTime(rt.getTimestamp(34));

					if (baseline.getOccTime() == null) { 
						baseline.setOccTime(new Date()); 
					}

					if (baseline.getUnOccTime() == null) { 
						baseline.setUnOccTime(new Date()); 
					}

					break;
				}
				//OccTime = room.getLastUpdated();

				PreparedStatement preparedStmt;
				if (room.getStatus() != obj.getStatus()) {
					query = "Update psuteam7.room set Status = ?, LastStatusUpdate = ? where RoomNumber = '" + obj.getRoomNumber() + "'";

					// create the mysql insert preparedstatement

					preparedStmt = connect.prepareStatement(query);
					preparedStmt.setInt(1, obj.getStatus());
					java.sql.Timestamp sqlDateTime = new java.sql.Timestamp(new Date().getTime());
					preparedStmt.setObject(2, sqlDateTime);// start time
					// execute the preparedstatement
					preparedStmt.execute();

					query = "Select * from psuteam7.room where RoomNumber = '" + obj.getRoomNumber() + "'";
					statement = connect.createStatement();
					ResultSet rt2 = statement.executeQuery(query);
					while (rt2.next()) {
						room = new DBRoomTable(rt2.getInt(1), rt2.getString(2), rt2.getString(3), rt2.getString(4),
								rt2.getInt(5), rt2.getInt(6), rt2.getInt(7), rt2.getFloat(8), rt2.getFloat(9), rt2.getFloat(10),
								rt2.getInt(13), rt2.getInt(14), rt2.getFloat(15));
						room.setLastUpdated(rt2.getTimestamp(12));
						break;
					}
					//OccTime = room.getLastUpdated();
					if (baseline.getID() == 0) {
						//doesn't exist
						if (room.getStatus() == 1) {
							Yocc = obj.getRoomTemp() - obj.getOccSetpoint();
							n = Yocc / 5;
							Y0 = Yocc - n;
							Y1 = Y0 - n;
							Y2 = Y1 - n;
							Y3 = Y2 - n;
							Y = 0;

							query = " insert into psuteam7.baseline (SiteID, RoomNumber, OAT, OccTime, Yocc, Y0, Y1, Y2, Y3, Y)"
									+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						} else {
							Yocc = obj.getUnoccSetpoint() - obj.getRoomTemp();
							n = Yocc / 5;
							
							Yocc = 0;
							Y0 = Yocc + n;
							Y1 = Y0 + n;
							Y2 = Y1 + n;
							Y3 = Y2 + n;
							Y = Y3 + n;
							
							query = " insert into psuteam7.baseline (SiteID, RoomNumber, OAT, UnOccTime, Yunocc, Y4, Y5, Y6, Y7, Yz0)"
									+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						}

						// create the mysql insert preparedstatement
						preparedStmt = connect.prepareStatement(query);
						preparedStmt.setInt(1, 1);
						preparedStmt.setString(2, room.getRoomNumber());
						preparedStmt.setFloat(3, obj.getOAT());

						sqlDateTime = new java.sql.Timestamp(room.getLastUpdated().getTime());

						preparedStmt.setObject(4, sqlDateTime);// start time
						preparedStmt.setFloat(5, Yocc);
						preparedStmt.setFloat(6, Y0);
						preparedStmt.setFloat(7, Y1);
						preparedStmt.setFloat(8, Y2);
						preparedStmt.setFloat(9, Y3);
						preparedStmt.setFloat(10, Y);
						// execute the preparedstatement
						preparedStmt.execute();
					}
				} else {


					if (room.getStatus() == 1) {
						if ((room.getLastUpdated().getTime()-baseline.getOccTime().getTime())/1000 != 0) {
							Yocc = obj.getRoomTemp() - obj.getOccSetpoint();
						} else {
							Yocc = baseline.getYocc();
						}
						n = Yocc / 5;
						Y0 = Yocc - n;
						Y1 = Y0 - n;
						Y2 = Y1 - n;
						Y3 = Y2 - n;
						Y = 0;

						if (obj.getRoomTemp() - obj.getOccSetpoint() < 0) {
							X = (CurrentDateTime.getTime()-room.getLastUpdated().getTime())/1000;
							X3 = baseline.getX3();
							X2 = baseline.getX2();
							X1 = baseline.getX1();
							X0 = baseline.getX0();
						} else if (obj.getRoomTemp() - obj.getOccSetpoint() < Y) {
							X3 = (CurrentDateTime.getTime()-room.getLastUpdated().getTime())/1000;
							X = baseline.getX();
							X2 = baseline.getX2();
							X1 = baseline.getX1();
							X0 = baseline.getX0();
						} else if (obj.getRoomTemp() - obj.getOccSetpoint() < Y3) {
							X2 = (CurrentDateTime.getTime()-room.getLastUpdated().getTime())/1000;
							X3 = baseline.getX3();
							X = baseline.getX();
							X1 = baseline.getX1();
							X0 = baseline.getX0();
						} else if (obj.getRoomTemp() - obj.getOccSetpoint() < Y2) {
							X1 = (CurrentDateTime.getTime()-room.getLastUpdated().getTime())/1000;
							X3 = baseline.getX3();
							X2 = baseline.getX2();
							X = baseline.getX();
							X0 = baseline.getX0();
						} else if (obj.getRoomTemp() - obj.getOccSetpoint() < Y1) {
							X0 = (CurrentDateTime.getTime()-room.getLastUpdated().getTime())/1000;
							X3 = baseline.getX3();
							X2 = baseline.getX2();
							X1 = baseline.getX1();
							X = baseline.getX();
						} else {
							X = baseline.getX();
							X3 = baseline.getX3();
							X2 = baseline.getX2();
							X1 = baseline.getX1();
							X0 = baseline.getX0();
						}

						query = "Update psuteam7.baseline set X0 = ?, X1 = ?, X2 = ?, X3 = ?, X = ?, Y0 = ?, Y1 = ?, Y2 = ?, Y3 = ?, Y = ?, OccTime = ?, Yocc = ? where RoomNumber = '" + obj.getRoomNumber() + "' and OAT = " + obj.getOAT();

						// create the mysql insert preparedstatement

						preparedStmt = connect.prepareStatement(query);
						preparedStmt.setFloat(1, X0);
						preparedStmt.setFloat(2, X1);
						preparedStmt.setFloat(3, X2);
						preparedStmt.setFloat(4, X3);
						preparedStmt.setFloat(5, X);
						preparedStmt.setFloat(6, Y0);
						preparedStmt.setFloat(7, Y1);
						preparedStmt.setFloat(8, Y2);
						preparedStmt.setFloat(9, Y3);
						preparedStmt.setFloat(10, Y);
						java.sql.Timestamp sqlDateTime = new java.sql.Timestamp(room.getLastUpdated().getTime());
						preparedStmt.setObject(11, sqlDateTime);// start time

						preparedStmt.setFloat(12, Yocc);
						// execute the preparedstatement
						preparedStmt.execute();
					} else {

						if ((room.getLastUpdated().getTime()-baseline.getUnOccTime().getTime())/1000 != 0) {
							Yocc = obj.getUnoccSetpoint() - obj.getRoomTemp();
						} else {
							Yocc = baseline.getYz0();
						}
						n = Yocc / 5;
						Yocc = 0;
						Y0 = Yocc + n;
						Y1 = Y0 + n;
						Y2 = Y1 + n;
						Y3 = Y2 + n;
						Y = Y3 + n;

						if ((Y - (obj.getUnoccSetpoint() - obj.getRoomTemp())) > Y3) {
							X = (CurrentDateTime.getTime()-room.getLastUpdated().getTime())/1000;
							X3 = baseline.getX7();
							X2 = baseline.getX6();
							X1 = baseline.getX5();
							X0 = baseline.getX4();
						} else if ((Y - (obj.getUnoccSetpoint() - obj.getRoomTemp())) > Y2) {
							X3 = (CurrentDateTime.getTime()-room.getLastUpdated().getTime())/1000;
							X = baseline.getXz0();
							X2 = baseline.getX6();
							X1 = baseline.getX5();
							X0 = baseline.getX4();
						} else if ((Y - (obj.getUnoccSetpoint() - obj.getRoomTemp())) > Y1) {
							X2 = (CurrentDateTime.getTime()-room.getLastUpdated().getTime())/1000;
							X3 = baseline.getX7();
							X = baseline.getXz0();
							X1 = baseline.getX5();
							X0 = baseline.getX4();
						} else if ((Y - (obj.getUnoccSetpoint() - obj.getRoomTemp())) > Y0) {
							X1 = (CurrentDateTime.getTime()-room.getLastUpdated().getTime())/1000;
							X3 = baseline.getX7();
							X2 = baseline.getX6();
							X = baseline.getXz0();
							X0 = baseline.getX4();
						} else if ((Y - (obj.getUnoccSetpoint() - obj.getRoomTemp())) > 0) {
							X0 = (CurrentDateTime.getTime()-room.getLastUpdated().getTime())/1000;
							X3 = baseline.getX7();
							X2 = baseline.getX6();
							X1 = baseline.getX5();
							X = baseline.getXz0();
						} else {
							X = baseline.getXz0();
							X3 = baseline.getX7();
							X2 = baseline.getX6();
							X1 = baseline.getX5();
							X0 = baseline.getX4();
						}

						query = "Update psuteam7.baseline set X4 = ?, X5 = ?, X6 = ?, X7 = ?, Xz0 = ?, Y4 = ?, Y5 = ?, Y6 = ?, Y7 = ?, Yz0 = ?, UnOccTime = ?, Yunocc = ? where RoomNumber = '" + obj.getRoomNumber() + "' and OAT = " + obj.getOAT();

						// create the mysql insert preparedstatement

						preparedStmt = connect.prepareStatement(query);
						preparedStmt.setFloat(1, X0);
						preparedStmt.setFloat(2, X1);
						preparedStmt.setFloat(3, X2);
						preparedStmt.setFloat(4, X3);
						preparedStmt.setFloat(5, X);
						preparedStmt.setFloat(6, Y0);
						preparedStmt.setFloat(7, Y1);
						preparedStmt.setFloat(8, Y2);
						preparedStmt.setFloat(9, Y3);
						preparedStmt.setFloat(10, Y);
						java.sql.Timestamp sqlDateTime = new java.sql.Timestamp(room.getLastUpdated().getTime());
						preparedStmt.setObject(11, sqlDateTime);// start time

						preparedStmt.setFloat(12, Yocc);
						// execute the preparedstatement
						preparedStmt.execute();
					}
				}




			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch(Exception e) {
				System.out.println(e);

			}


			//String str[] = obj.getObjectName().split("_");
			//String type = str[str.length - 1];
			//String object
			//for (int i = 0; i < str.length; i++) 
			try {

				// the mysql insert statement
				//String query = " Insert into psuteam7.site_room_temp set SiteID = ?, Temperature = ?, OccSetpoint = ?, UnOccSetpoint = ?, CoolMode = ?, OAT = ?, OccStatus = ?";
				//	+ " where ID = " + s.getId();
				/*String query = " insert into psuteam7.weather (SiteID, RoomNumber, Temperature, OccSetpoint, UnoccSetpoint, CoolMode, OAT, OccStatus)"
						+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
				// create the mysql insert preparedstatement
				PreparedStatement preparedStmt = connect.prepareStatement(query);
				preparedStmt.setInt (1, r.getSiteID());
				preparedStmt.setString (2, r.getRoomNumber());
				preparedStmt.setFloat (3, r.getTemperature());
				preparedStmt.setFloat (4, r.getOccSetpoint());
				preparedStmt.setFloat (5, r.getUnOccSetpoint());
				preparedStmt.setInt (6, r.getCoolMode());
				preparedStmt.setFloat (7, r.getOAT());
				preparedStmt.setInt (8, r.getOccStatus());
				// execute the preparedstatement
				preparedStmt.execute();*/



			} catch(Exception e) {
				System.out.println(e);

			}
		}

		@Override
		public void updateOccStatus() {
			//display debug message
			System.out.println("Database > Update Occupancy status request received.");

			try {

				/*//Calendar c1 = GregorianCalendar.getInstance(Locale.US);
				 */

				statement = connect.createStatement();
				//ResultSet rt2 = null;


				String query = "Select * from psuteam7.room";
				statement = connect.createStatement();
				ResultSet rt1 = statement.executeQuery(query);
				DBRoomTable room = new DBRoomTable();
				if (rt1.next()) {
					room = new DBRoomTable(rt1.getInt(1), rt1.getString(2), rt1.getString(3), rt1.getString(4),
							rt1.getInt(5), rt1.getInt(6), rt1.getInt(7), rt1.getFloat(8), rt1.getFloat(9), rt1.getFloat(10),
							rt1.getInt(13), rt1.getInt(14), rt1.getFloat(15));
					room.setLastUpdated(rt1.getTimestamp(12));

					if (room.getStatus() == 0) {
						//Calendar c1 = GregorianCalendar.getInstance(Locale.US);
						DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
						//DateFormat df2 = new SimpleDateFormat("HH"); 
						Date currentDate = new Date();
						Calendar cal = Calendar.getInstance();
						// remove next line if you're always using the current time.
						cal.setTime(currentDate);
						cal.add(Calendar.HOUR, -2);
						cal.add(Calendar.MINUTE, -3);
						Date nowTime = cal.getTime();

						cal.add(Calendar.MINUTE, room.getOccOffset());

						Date offsetedTime = cal.getTime();

						query = "SELECT * FROM psuteam7.schedule WHERE RoomName = '" + room.getRoomNumber() + "' and ('" + df2.format(offsetedTime) + 
								"' >= CAST(StartDateTime AS DATE) AND '" + df2.format(nowTime) + "' <= CAST(EndDateTime AS DATE)) ";

						ResultSet rt2 = statement.executeQuery(query); 

						//ArrayList<DBScheduleTable> sList = new ArrayList<DBScheduleTable>();

						if (rt2.next()) { 

							DBScheduleTable s = new DBScheduleTable();
							s.setRowGuid(rt2.getString(1));
							s.setScheduleId(rt2.getInt(2));
							s.setName(rt2.getString(4));
							s.setDescription(rt2.getString(5));
							s.setNotes(rt2.getString(6));
							s.setControlToState(rt2.getInt(7));
							s.setStartDateTime(rt2.getTimestamp(8));
							s.setEndDateTime(rt2.getTimestamp(9));
							s.setMarkedForDelete(rt2.getBoolean(10));
							s.setRoomName(rt2.getString(11));
							s.setTemperatureSetpoint(rt2.getFloat(12));
							s.setLightIntensity(rt2.getInt(13));


							//if (rt3.getInt("Status") != 1)
							query = "Update psuteam7.room set OptOccState = ? where RoomNumber = '" + s.getRoomName() + "'";

							// create the mysql insert preparedstatement
							PreparedStatement preparedStmt = connect.prepareStatement(query);
							preparedStmt.setInt(1, 1);
							// execute the preparedstatement
							preparedStmt.execute();

							//break;

						} else {
							//if (rt3.getInt("Status") != 1)
							query = "Update psuteam7.room set OptOccState = ? where RoomNumber = '" + room.getRoomName() + "'";

							// create the mysql insert preparedstatement
							PreparedStatement preparedStmt = connect.prepareStatement(query);
							preparedStmt.setInt(1, 0);
							// execute the preparedstatement
							preparedStmt.execute();
						}




					} else { //status = 1

						//Calendar c1 = GregorianCalendar.getInstance(Locale.US);
						DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
						//DateFormat df2 = new SimpleDateFormat("HH"); 
						Date currentDate = new Date();
						Calendar cal = Calendar.getInstance();
						// remove next line if you're always using the current time.
						cal.setTime(currentDate);
						cal.add(Calendar.HOUR, -2);
						cal.add(Calendar.MINUTE, -3);
						Date nowTime = cal.getTime();

						cal.add(Calendar.MINUTE, room.getUnoccOffset());

						Date offsetedTime = cal.getTime();

						query = "SELECT * FROM psuteam7.schedule WHERE RoomName = '" + room.getRoomNumber() + "' and ('" + df2.format(nowTime) + 
								"' >= CAST(StartDateTime AS DATE) AND '" + df2.format(offsetedTime) + "' <= CAST(EndDateTime AS DATE)) ";

						ResultSet rt3 = statement.executeQuery(query); 

						//ArrayList<DBScheduleTable> sList = new ArrayList<DBScheduleTable>();

						if (rt3.next()) { //unocc early

							DBScheduleTable s = new DBScheduleTable();
							s.setRowGuid(rt3.getString(1));
							s.setScheduleId(rt3.getInt(2));
							s.setName(rt3.getString(4));
							s.setDescription(rt3.getString(5));
							s.setNotes(rt3.getString(6));
							s.setControlToState(rt3.getInt(7));
							s.setStartDateTime(rt3.getTimestamp(8));
							s.setEndDateTime(rt3.getTimestamp(9));
							s.setMarkedForDelete(rt3.getBoolean(10));
							s.setRoomName(rt3.getString(11));
							s.setTemperatureSetpoint(rt3.getFloat(12));
							s.setLightIntensity(rt3.getInt(13));


							//if (rt3.getInt("Status") != 1)
							query = "Update psuteam7.room set OptOccState = ? where RoomNumber = '" + s.getRoomName() + "'";

							// create the mysql insert preparedstatement
							PreparedStatement preparedStmt = connect.prepareStatement(query);
							preparedStmt.setInt(1, 1);
							// execute the preparedstatement
							preparedStmt.execute();

							
						} else { //still have time
							query = "Update psuteam7.room set OptOccState = ? where RoomNumber = '" + room.getRoomName() + "'";

							// create the mysql insert preparedstatement
							PreparedStatement preparedStmt = connect.prepareStatement(query);
							preparedStmt.setInt(1, 0);
							// execute the preparedstatement
							preparedStmt.execute();
						}

					}

					DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
					//DateFormat df2 = new SimpleDateFormat("HH"); 
					Date currentDate = new Date();
					Calendar cal = Calendar.getInstance();
					// remove next line if you're always using the current time.
					cal.setTime(currentDate);
					cal.add(Calendar.HOUR, -2);
					cal.add(Calendar.MINUTE, -3);
					Date oneHourBack = cal.getTime();

					query = "SELECT * FROM psuteam7.schedule WHERE RoomName = '" + room.getRoomNumber() + "' and ('" + df2.format(oneHourBack) +  
							"' BETWEEN CAST(StartDateTime AS DATE) AND CAST(EndDateTime AS DATE)) ";

					ResultSet rt4 = statement.executeQuery(query); 

					//ArrayList<DBScheduleTable> sList = new ArrayList<DBScheduleTable>();

					if (rt4.next()) { 

						DBScheduleTable s = new DBScheduleTable();
						s.setRowGuid(rt4.getString(1));
						s.setScheduleId(rt4.getInt(2));
						s.setName(rt4.getString(4));
						s.setDescription(rt4.getString(5));
						s.setNotes(rt4.getString(6));
						s.setControlToState(rt4.getInt(7));
						s.setStartDateTime(rt4.getTimestamp(8));
						s.setEndDateTime(rt4.getTimestamp(9));
						s.setMarkedForDelete(rt4.getBoolean(10));
						s.setRoomName(rt4.getString(11));
						s.setTemperatureSetpoint(rt4.getFloat(12));
						s.setLightIntensity(rt4.getInt(13));


						//if (rt3.getInt("Status") != 1)
						query = "Update psuteam7.room set OccState = ? where RoomNumber = '" + s.getRoomName() + "'";

						// create the mysql insert preparedstatement
						PreparedStatement preparedStmt = connect.prepareStatement(query);
						preparedStmt.setInt(1, 1);
						// execute the preparedstatement
						preparedStmt.execute();

						//break;

					} else {
						//if (rt3.getInt("Status") != 1)
						query = "Update psuteam7.room set OccState = ? where RoomNumber = '" + room.getRoomNumber() + "'";

						// create the mysql insert preparedstatement
						PreparedStatement preparedStmt = connect.prepareStatement(query);
						preparedStmt.setInt(1, 0);
						// execute the preparedstatement
						preparedStmt.execute();
					}


				}



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
