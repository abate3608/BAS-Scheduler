package edu.psu.sweng500.database;

import java.sql.*;
import edu.psu.sweng500.eventqueue.event.EventHandler;

public class MysqlConnection {

	private Statement statement = null;
	private Connection connect = null;
	private ResultSet rt = null;

	// Event listeners
	private final EventHandler eventHandler = EventHandler.getInstance();

	public void readDB() throws Exception {
		try {
			/*
			 * Database name: psuteam7 user: psuteam7 password:psuteam7123 The
			 * connection to the database and its credentials.
			 */

			connect = DriverManager.getConnection("jdbc:mysql://50.63.144.233:3306/psuteam7?useSSL=false", "psuteam7",
					"psuteam7123");
			if (connect != null) {
				System.out.println("Database Connection Successful!!");
			}
		} catch (SQLException ex) {
			System.out.println("Error Connecting to the Database");
			ex.printStackTrace();
		}
		statement = connect.createStatement();
		rt = statement.executeQuery(
				"select ScheduleId, StartTime, EndTime from Schedule where StartTime = str_to_date('06-20-17','%m-%d-%y') and EndTime = str_to_date('06-25-17','%m-%d-%y')");

		while ((rt.next())) {
			int ScheduleID = rt.getInt("ScheduleID");
			String StartTime = rt.getString("StartTime");
			String EndTime = rt.getString("EndTime");
			System.out.println("ScheduleID is " + ScheduleID + "\n" + "StartTime is " + StartTime + "\n" + "EndTime is"
					+ EndTime + "\n");

		}
	}

	public EventHandler getEventHandler() {
		return eventHandler;
	}

}
