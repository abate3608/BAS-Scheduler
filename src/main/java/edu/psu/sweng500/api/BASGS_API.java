package edu.psu.sweng500.api;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.ScheduleEvent;

/*
 * Author: Brian Abate
 * This class provides an API for external application to interact
 * with BASGS.
 */
public class BASGS_API {
	
	// Event listeners
	private final static EventHandler eventHandler = EventHandler.getInstance();
	private static ClientServiceThread client;
	private static API_Object apiObj;

	/*
	 * Constructor for BASGS_API
	 */
	public BASGS_API(ClientServiceThread client) {
		// setup event
		eventHandler.addListener(new EventQueueListener());
		this.client = client;
	}

	// Class structure used to store API Object
	static class API_Object {
		String username;
		String password;
		int id;
		int action_id;
		int num_of_obj;
		String message;
		String start_date;
		String stop_date;
		ArrayList<BacnetObj> bacnet = new ArrayList<BacnetObj>();
	}

	// Class structure used to store bacnet object
	static class BacnetObj {
		String eventID;
		String eventName;
		String eventDescription;
		String eventStart;
		String eventStop;
		String temperatureSetpoint;
		String lightIntensity;
//		String object_identifier;
//		String object_name;
//		String object_type;
//		int present_value;
//		String description;
//		String decive_type;
//		String status_flag;
//		String event_state;
//		String reliability;
//		String out_of_service;
//		String update_interval;
//		String units;
//		String min_pres_value;
//		String max_pres_value;
//		String resolution;
//		String cov_increment;
//		String time_delay;
//		String notificaiton_class;
//		String high_limit;
//		String low_limit;
//		String deadband;
//		String limit_enabled;
//		String event_enabled;
//		String acked_transitions;
//		String notify_types;
	}

	/*
	 * This method allows external applications to create BASGS events.
	 * @param [in] api - API_Object that is to be added to the database
	 */
	private void create(API_Object api) throws IOException {
		try {
			ArrayList<ScheduleEvent> events = new ArrayList<ScheduleEvent>();
			for(int i = 0; i < api.num_of_obj; i++) {
				ScheduleEvent event = new ScheduleEvent();
				System.out.println(api.bacnet.get(i).eventID);
				event.setEventID(Integer.parseInt(api.bacnet.get(i).eventID));
				event.setEventName(api.bacnet.get(i).eventName);
				event.setEventDescription(api.bacnet.get(i).eventDescription);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				event.setEventStart(df.parse(api.bacnet.get(i).eventStart));
				event.setEventStop(df.parse(api.bacnet.get(i).eventStop));
				event.setLightIntensity(Float.parseFloat(api.bacnet.get(i).lightIntensity));
				event.setTemperatureSetpoint(Float.parseFloat(api.bacnet.get(i).temperatureSetpoint));
				events.add(event);
			}
			
			for (ScheduleEvent event : events) {
				eventHandler.fireCreateEvent(event);
			}
		} catch(ParseException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method allows external applications to read BASGS events.
	 * @param [in] api - API_Object that specifies what event should
	 * be read from the database
	 */
	private void read(API_Object api) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date startDate = df.parse(api.start_date);
			Date endDate = df.parse(api.stop_date);
			eventHandler.fireGetEvents(startDate, endDate);
		} catch(ParseException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method allows external applications to update BASGS events.
	 * @param [in] api - API_Object that is to be updated in the database
	 */
	private void update(API_Object api) {
		try {
			ArrayList<ScheduleEvent> events = new ArrayList<ScheduleEvent>();
			for(int i = 0; i < api.num_of_obj; i++) {
				ScheduleEvent event = new ScheduleEvent();
				System.out.println(api.bacnet.get(i).eventID);
				event.setEventID(Integer.parseInt(api.bacnet.get(i).eventID));
				event.setEventName(api.bacnet.get(i).eventName);
				event.setEventDescription(api.bacnet.get(i).eventDescription);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				event.setEventStart(df.parse(api.bacnet.get(i).eventStart));
				event.setEventStop(df.parse(api.bacnet.get(i).eventStop));
				event.setLightIntensity(Float.parseFloat(api.bacnet.get(i).lightIntensity));
				event.setTemperatureSetpoint(Float.parseFloat(api.bacnet.get(i).temperatureSetpoint));
				events.add(event);
			}
			for (ScheduleEvent event : events) {
				eventHandler.fireCreateEvent(event);
			}
		} catch(ParseException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method allows external applications to delete BASGS events.
	 * @param [in] api - API_Object that is to be deleted from the database
	 */
	private void delete(API_Object api) {

	}

	/*
	 * This method is called when the Server receives data, parses the data, and
	 * sends the converted api object to the appropriate message.
	 * 
	 * @param [in] api_obj - API_Object that will be parsed to determine what
	 * action will be taken.
	 */
	public void parseMsg(API_Object api) throws IOException {
		this.apiObj = api;
		
		// Case 0 - Create
		// Case 1 - Read events between dates
		// Case 2 - Update Event
		// Case 3 - Delete Event
		switch (this.apiObj.action_id) {
			case 0:
				create(api);
				break;
			case 1:
				read(api);
				break;
			case 2:
				update(api);
				break;
			case 3:
				delete(api);
				break;
			default:
				break;
		}
	}
	
	/*
	 * This method returns the BASGS_API eventHandler
	 * @return the BASGS event handler
	 */
	public EventHandler getEventHandler() {
		return eventHandler;
	}
	
	/*
	 * This class allows the BASGS to listen for event updates from the event queue
	 */
	static class EventQueueListener extends EventAdapter {
		// listen to event queue

		/*
		 * This method is called when an event update takes place
		 * 
		 * @param [in] 0 - ScheduleEvent object that has been returned from the database
		 */
		@Override
		public void eventUpdate(ScheduleEvent o) {
			// TEAM 7 TO DO
			// EventObject data type
			//
			// write code to update API when event arrive
			API_Object apiObjReturn = new API_Object();
			apiObjReturn.num_of_obj = 1;
			BacnetObj bacnetObj = new BacnetObj();
			apiObjReturn.message = "Action Complete";
			bacnetObj.eventID = String.valueOf(o.getEventID());
			bacnetObj.eventDescription = o.getEventDescription();
			bacnetObj.eventName = o.getEventName();
			bacnetObj.eventStart = o.getEventStart().toString();
			bacnetObj.eventStop = o.getEventStop().toString();
			apiObjReturn.bacnet.add(bacnetObj);
			try {
				client.writeJsonStream(apiObjReturn);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * The main method used for running the BASGS_API and starts Camel Spring
	 * Main.
	 * 
	 * @param [in] args - String array of arguements passed in.
	 */
	public static void main(String[] args) throws Exception {

	}

}
