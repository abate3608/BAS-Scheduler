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
			//50.63.144.233
			connect = DriverManager.getConnection("jdbc:mysql://honswd.com:3306/psuteam7?useSSL=false", "psuteam7",
					"psuteam7123");
			if (connect != null) {
				System.out.println("Database Connection Successful!!");
			}
		} catch (SQLException ex) {
			System.out.println("Error Connecting to the Database");
			ex.printStackTrace();
		}
		statement = connect.createStatement();
		rt = statement.executeQuery("select * from site");
		while ((rt.next())) {
			int siteID = rt.getInt("ID");
			String name = rt.getString("Name");
			System.out.println("Test connection to site ID: " + siteID + " Name: " + name);

		}
	}

	public EventHandler getEventHandler() {
		return eventHandler;
	}
	
	public Connection getConnection() {
		return connect;
	}

}
