package edu.psu.sweng500.database;

import edu.psu.sweng500.eventqueue.event.*;
import edu.psu.sweng500.type.*;

/*
 * Class to read the database.
 */
public class Main {

	static MysqlConnection dao = new MysqlConnection();

	public static void main(String[] args) throws Exception {

		// register with event queue
		dao.getEventHandler().addListener(new EventQueueListener());

		dao.readDB();
	}

	static class EventQueueListener extends EventAdapter {
		@Override
		public void getBacnetDevice(String ObjectIdentifier) {
			System.out.println("getBacnetDeviceReqeust received for " + ObjectIdentifier);

			// get information from data and send the data back to Bacnet server

			// get information for the ObjectIdentifier
			int port = 0xBAC0; // get information from database and replace
								// static data
			String ipAddress = "192.168.30.1"; // get information from database
												// and replace static data

			// create new bacnet device
			BacnetDevice bacnetDevice = new BacnetDevice(ObjectIdentifier, port, ipAddress);

			// Generate the event
			dao.getEventHandler().fireBacnetDeviceUpdate(bacnetDevice);
		}

		@Override
		public void authenticateUserRequest(String userName, String password) {
			// do something
			// dao.getEventHandler().fireAutheticateUserRespond(u);
		}
	}
}
