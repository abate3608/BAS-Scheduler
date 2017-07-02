package edu.psu.sweng500;

import edu.psu.sweng500.api.MultiThreadedAPIServer;
import edu.psu.sweng500.bacnetserver.server.BacnetServer;
import edu.psu.sweng500.database.Database;
//import edu.psu.sweng500.database.MysqlAccess;
import edu.psu.sweng500.database.MysqlConnection;
import edu.psu.sweng500.userinterface.CalenderScreen;

//import edu.psu.sweng500.userinterface.LogScreen;

/**
 * This is the application main class. When the built jar file is run, this
 * class's main method will be run.
 * 
 */
public class Main {
	public static void main(String args[]) {
		System.out.println("The main class has been run!");

		try {

			MysqlConnection dao = new MysqlConnection();

			// test database
			dao.readDB();
			new Database(dao.getConnection()); // start the database
			//new MultiThreadedAPIServer();//Start the API Server

			new CalenderScreen(); // UI StartScreen
			

			new BacnetServer(); // start bacner server
			while (true) {
				Thread.sleep(2000);
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
