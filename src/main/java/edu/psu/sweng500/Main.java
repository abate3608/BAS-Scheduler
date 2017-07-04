package edu.psu.sweng500;

import edu.psu.sweng500.api.MultiThreadedAPIServer;
import edu.psu.sweng500.api.OpenWeatherMapAPI;
import edu.psu.sweng500.bacnetserver.server.BacnetServer;
import edu.psu.sweng500.database.Database;
//import edu.psu.sweng500.database.MysqlAccess;
import edu.psu.sweng500.database.MysqlConnection;
import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.*;
import edu.psu.sweng500.userinterface.CalenderScreen;

//import edu.psu.sweng500.userinterface.LogScreen;

/**
 * This is the application main class. When the built jar file is run, this
 * class's main method will be run.
 * 
 */
public class Main {
	
	// Event listeners
	private final static EventHandler eventHandler = EventHandler.getInstance();
		
	private static DBWeatherTable weather = new DBWeatherTable();
	private static DBSiteTable site = new DBSiteTable();
	
	private static int status = 0;
	
	
	
	public static void main(String args[]) {
		System.out.println("Main > System starting ...");
		status = 1; //running
		try {
			
			// setup event
			System.out.println("Main > Add Main.java to event queue.");
			eventHandler.addListener(new EventQueueListener());

			System.out.println("Main > Test database connection.");
			MysqlConnection dao = new MysqlConnection();
			// test database
			dao.readDB();
			new Database(dao.getConnection()); // start the database
				
			System.out.println("Main > Get site information from DB.");
			eventHandler.fireSiteInfoRequest();
			
			//if no siteid and status is urnning
			System.out.println("Main > Waiting for site information from DB.");
			while (site.getId() == 0 && status == 1) {
				//wait for site ID to boot up the system.
				//Note for database: system must have at least one site. Create default site when create DB table.
				Thread.sleep(5000);  //5 second
			}
			
			//event information set site information
			System.out.println("Main > System site ID: " + site.getId() + ".");
			
			
			System.out.println("Main > Get weather data from DB for Site: " + site.getId() + ".");
			eventHandler.fireWeatherInfoRequest(site.getId());
			
			System.out.println("Main > Get weather data from Open Weather Map API for Site Zip Code: " + site.getZipCode() + ".");
			OpenWeatherMapAPI owm = new OpenWeatherMapAPI();
			owm.getWeatherFromZip(site.getZipCode()); //currently for US only
			
			System.out.println("Main > Start BACnet Server.");			
			new BacnetServer(); // start bacner server
			
			
			System.out.println("Main > Open UI calendar Screen.");
			new CalenderScreen(); // UI StartScreen
			//new StartScreen();
			
			//new MultiThreadedAPIServer();//Start the API Server


			System.out.println("Main > System is running!");
			while (status == 1) {
				
				//get weather
				owm.setZipcode(site.getZipCode());
				System.out.println("Main > Get current weather for Zipcode: " + owm.getZipcode());
				owm.updateWeather();
				System.out.println("Main > Current weather for Zipcode: " + owm.getZipcode() + " Temperature: " + owm.getTemperature() + " Humidity:" + owm.getHumidity() + " Dew Point: " + owm.getDewpoint());
				
				//update database
				DBWeatherTable w = new DBWeatherTable(0, site.getId(), owm.getTemperature(), owm.getHumidity(), owm.getDewpoint(), 0, null);
				eventHandler.fireWeatherInfoUpdateDB(w);
				
				eventHandler.fireRoomInfoRequest();
				
				
				Thread.sleep(10000);  //5 minutes
				System.out.println("Main > System update. Status: " + status + " SiteID: " + site.getId());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public EventHandler getEventHandler() {
		return eventHandler;
	}

	static class EventQueueListener extends EventAdapter {
		// listen to event queue

		@Override
		public void weatherInfoUpdateDBRespond(DBWeatherTable w, int err) {
			System.out.println("Main > Received weather data update DB confirmation for SiteID: " + w.getSiteId() + " Error Code: " + err);

		}
		
		@Override
		public void siteInfoUpdate(DBSiteTable s) {
			System.out.println("Main > Received site data from DB for SiteID: " + s.getId());
			site = s; //write data from db to local variable
			
		}
		
		@Override
		public void weatherInfoUpdate(DBWeatherTable w) {
			System.out.println("Main > Received weather data from DB for SiteID: " + w.getSiteId() + " Temperature: " + w.getTemperature());
			weather = w; //write data from db to local variable
			
		}
		
	}

}
