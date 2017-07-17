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
		public void getBacnetDevice() {
			
		}

		@Override
		public void authenticateUserRequest(String userName, String passWord) {

		}
	}
}
