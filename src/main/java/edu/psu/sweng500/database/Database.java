package edu.psu.sweng500.database;

import java.util.ArrayList;

/*
 * Class to get data based on CalendarID. Can be changed as development progresses. 
 */
public class Database {

	private static class DBListener implements DataEventListener {

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

	private ArrayList<DataEventListener> eventListenerList = new ArrayList<DataEventListener>();

	public static void main(String[] args) {

		Database base = new Database();

		ArrayList<DataEventListener> listener = new ArrayList<DataEventListener>();

		base.getDataEventListener(listener);
		base.writeDataEventListener(listener);
	}

	private synchronized void writeDataEventListener(ArrayList<DataEventListener> listener) {
		if (eventListenerList.contains(listener)) {
			eventListenerList.addAll(listener);
		}

	}

	private synchronized void getDataEventListener(ArrayList<DataEventListener> listener) {
		if (eventListenerList.isEmpty()) {
			eventListenerList.addAll(listener);
		}
	}

	public int getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(int calendarId) {
		this.calendarId = calendarId;
	}
}