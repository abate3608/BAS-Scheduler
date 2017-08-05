package edu.psu.sweng500.api.basgs;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.*;

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
	private static final int CREATE_REQUEST = 0;
	private static final int READ_REQUEST = 1;
	private static final int UPDATE_REQUEST = 2;
	private static final int DELETE_REQUEST = 3;
	private static final int READ_ALL_REQUEST = 4;
	
	private static EventQueueListener eql = new EventQueueListener();

	/*
	 * Constructor for BASGS_API
	 */
	public BASGS_API(ClientServiceThread client) {
		// setup event
		eventHandler.addListener(eql);
		this.client = client;
	}

	// Class structure used to store API Object
	public static class API_Object {
		public String username;
		public String password;
		public int action_id;
		public int num_of_obj;
		public int error;
		public String message;
		public String start_date;
		public String stop_date;
		public ArrayList<BacnetObj> bacnet = new ArrayList<BacnetObj>();
	}

	// Class structure used to store bacnet object
	public static class BacnetObj {
		public String uuid;
		public String eventID;
		public String eventName;
		public String roomName;
		public String eventDescription;
		public String eventStart;
		public String eventStop;
		public String temperatureSetpoint;
		public String lightIntensity;
	}

	/*
	 * This method allows external applications to create BASGS events.
	 * @param [in] api - API_Object that is to be added to the database
	 */
	private static void create(API_Object api) throws IOException {
		System.out.println("BASGS_API: Create Event(s).");
		try {
			ArrayList<DBScheduleTable> schedules = new ArrayList<DBScheduleTable>();
			for(int i = 0; i < api.num_of_obj; i++) {
				DBScheduleTable s = new DBScheduleTable();
				s.setScheduleId(Integer.parseInt(api.bacnet.get(i).eventID));
				s.setName(api.bacnet.get(i).eventName);
				s.setRoomName(api.bacnet.get(i).roomName);
				s.setDescription(api.bacnet.get(i).eventDescription);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				s.setStartDateTime(df.parse(api.bacnet.get(i).eventStart));
				s.setEndDateTime(df.parse(api.bacnet.get(i).eventStop));
				System.out.println(df.parse(api.bacnet.get(i).eventStart) + " " + df.parse(api.bacnet.get(i).eventStop));
				s.setLightIntensity(Integer.parseInt(api.bacnet.get(i).lightIntensity));
				s.setTemperatureSetpoint(Float.parseFloat(api.bacnet.get(i).temperatureSetpoint));
				schedules.add(s);
			}
			
			for (DBScheduleTable s : schedules) {
				eventHandler.fireCreateEvent(s);
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
	private static void read(API_Object api) {
		System.out.println("BASGS_API: Read Event(s).");
		if(api.action_id == READ_REQUEST) {
			try {
				ArrayList<DBScheduleTable> schedules = new ArrayList<DBScheduleTable>();
				for(int i = 0; i < api.num_of_obj; i++) {
					DBScheduleTable s = new DBScheduleTable();
					s.setRowGuid(api.bacnet.get(i).uuid);
					s.setScheduleId(Integer.parseInt(api.bacnet.get(i).eventID));
					s.setName(api.bacnet.get(i).eventName);
					s.setRoomName(api.bacnet.get(i).roomName);
					s.setDescription(api.bacnet.get(i).eventDescription);
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					s.setStartDateTime(df.parse(api.bacnet.get(i).eventStart));
					s.setEndDateTime(df.parse(api.bacnet.get(i).eventStop));
					s.setLightIntensity(Integer.parseInt(api.bacnet.get(i).lightIntensity));
					s.setTemperatureSetpoint(Float.parseFloat(api.bacnet.get(i).temperatureSetpoint));
					schedules.add(s);
				}
				for (DBScheduleTable s : schedules) {
					eventHandler.fireReadEvent(s);
				}
			} catch(ParseException e) {
				e.printStackTrace();
			}
		} else {
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date startDate = df.parse(api.start_date);
				Date endDate = df.parse(api.stop_date);
				eventHandler.fireGetEvents(startDate, endDate);
			} catch(ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * This method allows external applications to update BASGS events.
	 * @param [in] api - API_Object that is to be updated in the database
	 */
	private static void update(API_Object api) {
		System.out.println("BASGS_API: Update Event(s).");
		try {
			ArrayList<DBScheduleTable> schedules = new ArrayList<DBScheduleTable>();
			for(int i = 0; i < api.num_of_obj; i++) {
				DBScheduleTable s = new DBScheduleTable();
				s.setRowGuid(api.bacnet.get(i).uuid);
				s.setScheduleId(Integer.parseInt(api.bacnet.get(i).eventID));
				s.setName(api.bacnet.get(i).eventName);
				s.setRoomName(api.bacnet.get(i).roomName);
				s.setDescription(api.bacnet.get(i).eventDescription);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				s.setStartDateTime(df.parse(api.bacnet.get(i).eventStart));
				s.setEndDateTime(df.parse(api.bacnet.get(i).eventStop));
				s.setLightIntensity(Integer.parseInt(api.bacnet.get(i).lightIntensity));
				s.setTemperatureSetpoint(Float.parseFloat(api.bacnet.get(i).temperatureSetpoint));
				schedules.add(s);
			}
			for (DBScheduleTable s : schedules) {
				eventHandler.fireUpdateEvent(s);
			}
		} catch(ParseException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method allows external applications to delete BASGS events.
	 * @param [in] api - API_Object that is to be deleted from the database
	 */
	private static void delete(API_Object api) {
		System.out.println("BASGS_API: Delete Event(s).");
		try {
			ArrayList<DBScheduleTable> schedules = new ArrayList<DBScheduleTable>();
			for(int i = 0; i < api.num_of_obj; i++) {
				DBScheduleTable s = new DBScheduleTable();
				s.setRowGuid(api.bacnet.get(i).uuid);
				s.setScheduleId(Integer.parseInt(api.bacnet.get(i).eventID));
				s.setName(api.bacnet.get(i).eventName);
				s.setRoomName(api.bacnet.get(i).roomName);
				s.setDescription(api.bacnet.get(i).eventDescription);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				s.setStartDateTime(df.parse(api.bacnet.get(i).eventStart));
				s.setEndDateTime(df.parse(api.bacnet.get(i).eventStop));
				s.setMarkedForDelete(true);
				s.setLightIntensity(Integer.parseInt(api.bacnet.get(i).lightIntensity));
				s.setTemperatureSetpoint(Float.parseFloat(api.bacnet.get(i).temperatureSetpoint));
				schedules.add(s);
			}
			for (DBScheduleTable s : schedules) {
				eventHandler.fireDeleteEvent(s);
			}
		} catch(ParseException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method is called when the Server receives data, parses the data, and
	 * sends the converted api object to the appropriate operation.
	 * 
	 * @param [in] api_obj - API_Object that will be parsed to determine what
	 * action will be taken.
	 */
	public static void parseMsg(API_Object api) throws IOException {
		switch (api.action_id) {
			case CREATE_REQUEST:
				create(api);
				break;
			case READ_REQUEST:
			case READ_ALL_REQUEST:
				read(api);
				break;
			case UPDATE_REQUEST:
				update(api);
				break;
			case DELETE_REQUEST:
				delete(api);
				break;
			default:
				break;
		}
	}
	
	/*
	 * This method is called when the Server receives data to set the global
	 * API object and to authenticate the user.
	 * 
	 * @param [in] api_obj - API_Object that will be parsed to determine what
	 * action will be taken.
	 */
	public void handleApiObject(API_Object api) {
		this.apiObj = api;
		authenticateUser(api);
	}
	
	private static boolean compareEvents(API_Object api1, API_Object api2) {
		boolean isEqual = true;
		if(api1.bacnet.size() == api2.bacnet.size()) {
			for(int i=0;i<api1.bacnet.size();i++) {
				if(api1.bacnet.get(i).eventID != api2.bacnet.get(i).eventID 
						|| api1.bacnet.get(i).eventName != api2.bacnet.get(i).eventName
						|| api1.bacnet.get(i).eventDescription != api2.bacnet.get(i).eventDescription) {
					isEqual = false;
				}
			}
		} else {
			isEqual = false;
		}
		return isEqual;
	}
	
	
	/*
	 * This method is verify the username and password of the client sending the 
	 * event message.
	 * 
	 * @param [in] api_obj - API_Object that will be parsed to determine what
	 * action will be taken.
	 */
	public void authenticateUser(API_Object api) {
		//isAuthenticating = true;
		String userName = api.username;
		String password = api.password;

		// fire request event with password
		eventHandler.fireAuthenticateUserRequest(userName, password);
	}
	
	/*
	 * This method returns the BASGS_API eventHandler
	 * @return the BASGS event handler
	 */
	public EventHandler getEventHandler() {
		return eventHandler;
	}
	
	public void close() {
		eventHandler.removeListener(eql);
	}
	
	/*
	 * This class allows the BASGS to listen for event updates from the event queue
	 */
	static class EventQueueListener extends EventAdapter {
		// listen to event queue
		
		@Override
		public void createEventRespond(DBScheduleTable s, int err) {
			if(s != null) {
				if(apiObj.action_id == CREATE_REQUEST) {
					System.out.println("BASGS_API: Create Event Respond");
					API_Object apiObjReturn = new API_Object();
					apiObjReturn.num_of_obj = 1;
					apiObjReturn.error = err;
					switch(err) {
					case 0:
						apiObjReturn.message = "Success: Create Action Complete";
						break;
					case 1:
						apiObjReturn.message = "Error: Database Connection Issue";
						break;
					case 2:
						apiObjReturn.message = "Error: Event already exists.";
						break;
					case 3:
						apiObjReturn.message = "Error: Invalid date/time selected.";
						break;
					case 5:
						apiObjReturn.message = "Error: Time Conflict with Room.";
						break;
					default:
						apiObjReturn.message = "Error: Unknown";
						break;
					}
					BacnetObj bacnetObj = new BacnetObj();
					bacnetObj.uuid = String.valueOf(s.getRowGuid());
					bacnetObj.eventID = String.valueOf(s.getScheduleId());
					bacnetObj.eventDescription = s.getDescription();
					bacnetObj.roomName = s.getRoomName();
					bacnetObj.eventName = s.getName();
					bacnetObj.eventStart = s.getStartDateTime().toString();
					bacnetObj.eventStop = s.getEndDateTime().toString();
					bacnetObj.temperatureSetpoint = Float.toString(s.getTemperatureSetpoint());
					bacnetObj.lightIntensity = Integer.toString(s.getLightIntensity());
					apiObjReturn.bacnet.add(bacnetObj);
					
					if(!compareEvents(apiObj, apiObjReturn)){
						try {
							client.writeJsonStream(apiObjReturn);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				System.out.println("BASGS_API::createEventRespond >> Null returned object.");
			}
		}
		
		@Override
		public void readEventRespond(DBScheduleTable s, int err) {
			if(s != null) {
				if(apiObj.action_id == READ_REQUEST) {
					System.out.println("BASGS_API: Read Event Respond");
					API_Object apiObjReturn = new API_Object();
					apiObjReturn.num_of_obj = 1;
					apiObjReturn.error = err;
					switch(err) {
					case 0:
						apiObjReturn.message = "Success: Read Action Complete";
						break;
					case 1:
						apiObjReturn.message = "Error: Database Connection Issue";
						break;
					case 2:
						apiObjReturn.message = "Error: Event does not exist.";
						break;
					default:
						apiObjReturn.message = "Error: Unknown";
						break;
					}
					BacnetObj bacnetObj = new BacnetObj();
					bacnetObj.uuid = String.valueOf(s.getRowGuid());
					bacnetObj.eventID = String.valueOf(s.getScheduleId());
					bacnetObj.eventDescription = s.getDescription();
					bacnetObj.roomName = s.getRoomName();
					bacnetObj.eventName = s.getName();
					bacnetObj.eventStart = s.getStartDateTime().toString();
					bacnetObj.eventStop = s.getEndDateTime().toString();
					bacnetObj.temperatureSetpoint = Float.toString(s.getTemperatureSetpoint());
					bacnetObj.lightIntensity = Integer.toString(s.getLightIntensity());
					apiObjReturn.bacnet.add(bacnetObj);
					
					if(!compareEvents(apiObj, apiObjReturn)){
						try {
							client.writeJsonStream(apiObjReturn);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				System.out.println("BASGS_API::readEventRespond >> Null returned object.");
			}
		}
		
		@Override
		public void updateEventRespond(DBScheduleTable s, int err) {
			if(s != null && apiObj != null) {
				if(apiObj.action_id == UPDATE_REQUEST) {
					System.out.println("BASGS_API: Update Event Respond");
					API_Object apiObjReturn = new API_Object();
					apiObjReturn.num_of_obj = 1;
					apiObjReturn.error = err;
					switch(err) {
					case 0:
						apiObjReturn.message = "Success: Update Action Complete";
						break;
					case 1:
						apiObjReturn.message = "Error: Database Connection Issue";
						break;
					case 2:
						apiObjReturn.message = "Error: Event does not exist.";
						break;
					case 3:
					case 4:
						apiObjReturn.message = "Error: Scheduled event time has already passed.";
						break;
					default:
						apiObjReturn.message = "Error: Unknown";
						break;
					}
					BacnetObj bacnetObj = new BacnetObj();
					bacnetObj.uuid = String.valueOf(s.getRowGuid());
					bacnetObj.eventID = String.valueOf(s.getScheduleId());
					bacnetObj.eventDescription = s.getDescription();
					bacnetObj.roomName = s.getRoomName();
					bacnetObj.eventName = s.getName();
					bacnetObj.eventStart = s.getStartDateTime().toString();
					bacnetObj.eventStop = s.getEndDateTime().toString();
					bacnetObj.temperatureSetpoint = Float.toString(s.getTemperatureSetpoint());
					bacnetObj.lightIntensity = Integer.toString(s.getLightIntensity());
					apiObjReturn.bacnet.add(bacnetObj);
					
					if(!compareEvents(apiObj, apiObjReturn)){
						try {
							client.writeJsonStream(apiObjReturn);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				System.out.println("BASGS_API::updateEventRespond >> Null returned object.");
			}
		}
		
		@Override
		public void deleteEventRespond(DBScheduleTable s, int err) {
			if(s != null && apiObj != null) {
				if(apiObj.action_id == DELETE_REQUEST) {
					System.out.println("BASGS_API: Delete Event Respond");
					API_Object apiObjReturn = new API_Object();
					apiObjReturn.num_of_obj = 1;
					apiObjReturn.error = err;
					switch(err) {
						case 0:
							apiObjReturn.message = "Success: Delete Action Complete";
							break;
						case 1:
							apiObjReturn.message = "Error: Database Connection Issue";
							break;
						case 2:
							apiObjReturn.message = "Error: Event does not exist.";
							break;
						case 3:
							apiObjReturn.message = "Error: Event in Progress. Marked for deletion.";
							break;
						default:
							apiObjReturn.message = "Error: Unknown";
							break;
					}
					BacnetObj bacnetObj = new BacnetObj();
					bacnetObj.uuid = String.valueOf(s.getRowGuid());
					bacnetObj.eventID = String.valueOf(s.getScheduleId());
					bacnetObj.eventDescription = s.getDescription();
					bacnetObj.roomName = s.getRoomName();
					bacnetObj.eventName = s.getName();
					bacnetObj.eventStart = s.getStartDateTime().toString();
					bacnetObj.eventStop = s.getEndDateTime().toString();
					bacnetObj.temperatureSetpoint = Float.toString(s.getTemperatureSetpoint());
					bacnetObj.lightIntensity = Integer.toString(s.getLightIntensity());
					apiObjReturn.bacnet.add(bacnetObj);
					
					if(!compareEvents(apiObj, apiObjReturn)){
						try {
							client.writeJsonStream(apiObjReturn);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				System.out.println("BASGS_API::deleteEventRespond >> Null returned object.");
			}
		}
		
		@Override
		public void eventUpdate(ArrayList<DBScheduleTable> s) {
			if(s != null && apiObj != null) {
				if(apiObj.action_id == READ_ALL_REQUEST) {
					System.out.println("BASGS_API: Event Updates");
					// TEAM 7 TO DO
					// EventObject data type
					//
					// write code to update API when event arrive
					API_Object apiObjReturn = new API_Object();
					apiObjReturn.num_of_obj = s.size();
					apiObjReturn.error = 0;
					apiObjReturn.message = "Read Action Complete";
					for(DBScheduleTable sEvent : s) {
						BacnetObj bacnetObj = new BacnetObj();
						bacnetObj.eventID = String.valueOf(sEvent.getScheduleId());
						bacnetObj.eventDescription = sEvent.getDescription();
						bacnetObj.roomName = sEvent.getRoomName();
						bacnetObj.eventName = sEvent.getName();
						bacnetObj.eventStart = sEvent.getStartDateTime().toString();
						bacnetObj.eventStop = sEvent.getEndDateTime().toString();
						bacnetObj.temperatureSetpoint = Float.toString(sEvent.getTemperatureSetpoint());
						bacnetObj.lightIntensity = Integer.toString(sEvent.getLightIntensity());
						apiObjReturn.bacnet.add(bacnetObj);
					}
					
					try {
						client.writeJsonStream(apiObjReturn);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				System.out.println("BASGS_API::eventUpdate >> Null returned object.");
			}
		}
		
		@Override
		public void authenticateUserUpdate(User u) {
			if(u != null) {
				System.out.println("BASGS_API > Authentication user update received. User: " + u.getUserName() + " isAuthenicated:" + u.isAuthenticated());
				if (u.getUserName() == apiObj.username && u.isAuthenticated())
				{
					try {
						parseMsg(apiObj);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				} else
				{
					API_Object apiObjReturn = new API_Object();
					apiObjReturn.num_of_obj = 0;
					apiObjReturn.error = 1;
					apiObjReturn.message = "Login Fail. Please Enter Correct UserName and Password";
					try {
						client.writeJsonStream(apiObjReturn);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				System.out.println("BASGS_API::authenticateUserUpdate >> Null returned object.");
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
