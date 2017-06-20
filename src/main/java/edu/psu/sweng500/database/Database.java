package edu.psu.sweng500.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;


import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.BacnetDevice;
import edu.psu.sweng500.type.User;

/*
 * Class to get data based on CalendarID. Can be changed as development progresses. 
 */
public class Database {

	 // Event listeners
    private final static EventHandler eventHandler = EventHandler.getInstance();
    private static Statement statement = null;
    private static ResultSet rt = null;
    private final static Connection connect = null;
    
	public Database() {
		eventHandler.addListener(new EventQueueListener());
	}
	
	 static class EventQueueListener extends EventAdapter {
	    	//listen to event queue
	    	@Override
	    	public void getBacnetDevice(String ObjectIdentifier) {
	    		try {
	    			System.out.println("getBacnetDeviceReqeust received for " + ObjectIdentifier);
		            
		            //get information from data and send the data back to Bacnet server
		            
					//get information for the ObjectIdentifier
		            int port = 0xBAC0; //get information from database and replace static data
		            String ipAddress = "192.168.30.1"; //get information from database and replace static data
		            
		            //create new bacnet device
		            BacnetDevice bacnetDevice = new BacnetDevice(ObjectIdentifier, port, ipAddress);
		            
		            //Generate the event
		            eventHandler.fireBacnetDeviceUpdate(bacnetDevice);
	    			
	    			
	    			
	    			
	    		} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    	}
	    	
	    	@Override
	    	public void getEvents(Date Start, Date Stop) {
	    		try {
	    			
	    			
		            //Query for events in date range
	    			
	    			statement = connect.createStatement();
	    			
	    			rt =statement.executeQuery("select ScheduleId, StartTime, EndTime from Schedule where StartTime = str_to_date('06-20-17','%m-%d-%y') and EndTime = str_to_date('06-25-17','%m-%d-%y')");
	    			while((rt.next())){
	    				int ScheduleID = rt.getInt("ScheduleID");
	    			String StartTime = rt.getString("StartTime");
	    			String EndTime = rt.getString("EndTime");
	    			System.out.println(ScheduleID + "\n" + StartTime + "\n" + EndTime + "\n");
	    			}
	    			
	    			//Loop to each event
	    			//Create SheduleEvent object
		            //Send each event to event queue
	    			//eventHandler.fireEventUpdate(o);
	    			
	    		} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    	}
	    	
	    	@Override
	    	public void authenticateUserRequest(String userName, String password) {
	    		try {
	    			
		            //for now, check to see if the password match
	    			//Send out authenticate user update
	    			User u = new User(userName, true);
	    			
	    			eventHandler.fireAuthenticateUserUpdate(u);
	    			
	    		} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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