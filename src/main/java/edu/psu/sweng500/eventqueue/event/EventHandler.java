package edu.psu.sweng500.eventqueue.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import edu.psu.sweng500.bacnetserver.bacnet4j2.obj.BACnetObject;
import edu.psu.sweng500.type.*;

public class EventHandler {
	final ConcurrentLinkedQueue<EventListener> listeners = new ConcurrentLinkedQueue<EventListener>();

	private static EventHandler instance = null;

	protected EventHandler() {
		// Exists only to defeat instantiation.
	}

	public static EventHandler getInstance() {
		if (instance == null) {
			instance = new EventHandler();
		}
		return instance;
	}

	//
	//
	// Listener management
	//
	public void addListener(EventListener l) {
		listeners.add(l);
	}

	public void removeListener(EventListener l) {
		listeners.remove(l);
	}

	public int getListenerCount() {
		return listeners.size();
	}

	private void handleException(EventListener l, Throwable e) {
		try {
			l.listenerException(e);
		} catch (Throwable e1) {
			// no op
		}
	}

	// UI fire request
	//	public void fireAuthenticateUserRequest(final String userName, final String password) {
	//		for (EventListener l : listeners) {
	//			try {
	//				l.authenticateUserRequest(userName, password);
	//			} catch (Throwable e) {
	//				handleException(l, e);
	//			}
	//		}
	//	}
	//
	//	// UI fire request
	//	public void fireAuthenticateUserUpdate(final User u) {
	//		for (EventListener l : listeners) {
	//			try {
	//				l.authenticateUserUpdate(u);
	//			} catch (Throwable e) {
	//				handleException(l, e);
	//			}
	//		}
	//	}

	// Bacnet Server calls this function to request for BACnet Device info
	public void fireGetBacnetDeviceRequest() {
		for (EventListener l : listeners) {
			try {
				l.getBacnetDevice();
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}

	public void fireSetBacnetDeviceRequest(DBBacnetDevicesTable d) {
		for (EventListener l : listeners) {
			try {
				l.setBacnetDevice(d);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}
	
	// Database calls this function to respond to Bacnet Server request
	public void fireBacnetDeviceUpdate(DBBacnetDevicesTable d) {
		for (EventListener l : listeners) {
			try {
				l.bacnetDeviceUpdate(d);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}

	public void fireCreateEvent(DBScheduleTable s) {
		for (EventListener l : listeners) {
			try {
				l.createEvent(s);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}

	}
	
	public void fireReadEvent(DBScheduleTable s) {
		for (EventListener l : listeners) {
			try {
				l.readEvent(s);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}

	}
	
	public void fireUpdateEvent(DBScheduleTable s) {
		for (EventListener l : listeners) {
			try {
				l.updateEvent(s);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}

	}
	
	public void fireDeleteEvent(DBScheduleTable s) {
		for (EventListener l : listeners) {
			try {
				l.deleteEvent(s);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}

	}

	public void fireGetEvents(Date startDateTime, Date endDateTime) {
		for (EventListener l : listeners) {
			try {
				l.getEvents(startDateTime, endDateTime);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}

	}
	
	public void fireGetDailyEvents(Date dailyDate) {
		for (EventListener l : listeners) {
			try {
				l.getDailyEvents(dailyDate);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}

	}
	
	public void fireEventUpdate(ArrayList<DBScheduleTable> s) {
		for (EventListener l : listeners) {
			try {
				l.eventUpdate(s);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}
	
	public void fireEventDailyUpdate(ArrayList<DBScheduleTable> s) {
		for (EventListener l : listeners) {
			try {
				l.eventDailyUpdate(s);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}

	public void fireCreteUser(User u) {
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.createUser(u);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}

	public void fireCreteUserRespond(User u, int err) {
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.createUserRespond(u, err);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}


	///////////////////////////////////////////////////////////////////////////////////////////// - USER INTERFACE	
	// UI fire request - Log Screen
	public void fireAuthenticateUserRequest(final String userName, final String password) {
		for (EventListener l : listeners) {
			try {
				l.authenticateUserRequest(userName, password);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}

	// UI fire request - Log Screen
	public void fireAuthenticateUserUpdate(final User u) {
		for (EventListener l : listeners) {
			try {
				l.authenticateUserUpdate(u);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}


	/*	// UI fire request - NEW USER
	public void fireAuthenticateNewUserRequest(final String firstName, final String lastName, final String email, final String userName, final String password) {
		for (EventListener l : listeners) {
			try {
				l.authenticateNewUserRequest(firstName, lastName, email, userName, password); // EVENT hander
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}

	// UI fire request - NEW USER
	public void fireAuthenticateNewUserUpdate(final NewUser u) {
		for (EventListener l : listeners) {
			try {
				l.authenticateNewUserUpdate(u);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}*/

	// NEW EVENT        
	public void fireAuthenticateNewEventRequest(final String  eventName, final String startTime, final String endTime, 
			final String eventDate, final String eventRoom, final String lightSetting, final String tempSetting) {

		for (EventListener l : listeners) {
			try {
				l.authenticateNewEventRequest(eventName, startTime, endTime, eventDate, eventRoom, lightSetting, tempSetting); 
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}

	// NEW EVENT
	public void fireAuthenticateNewEventUpdate(final NewEvent u) {
		for (EventListener l : listeners) {
			try {
				l.authenticateNewEventUpdate(u);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}		

	//EDIT EVENT SUBMIT
	public void fireAuthenticateEditEventRequest(final String  eventName, final String startTime, final String endTime, 
			final String eventDate, final String eventRoom, final String lightSetting, final String tempSetting) {

		for (EventListener l : listeners) {
			try {
				l.authenticateEditEventRequest(eventName, startTime, endTime, eventDate, eventRoom, lightSetting, tempSetting); 
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}

	//EDIT EVENT SUBMIT
	public void fireAuthenticateEditEventUpdate(final EditEvent u) {
		for (EventListener l : listeners) {
			try {
				l.authenticateEditEventUpdate(u);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}		
	///////////////////////////////////////////////////////////////////////////////////////////	

	public void fireCreateEventRespond(DBScheduleTable s, int err) {
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.createEventRespond(s, err);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}
	
	public void fireReadEventRespond(DBScheduleTable s, int err) {
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.readEventRespond(s, err);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}
	
	public void fireUpdateEventRespond(DBScheduleTable s, int err) {
		for (EventListener l : listeners) {
			try {
				l.updateEventRespond(s, err);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}
	
	public void fireDeleteEventRespond(DBScheduleTable s, int err) {
		for (EventListener l : listeners) {
			try {
				l.deleteEventRespond(s, err);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}

	public void fireSiteInfoRequest() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.siteInfoRequest();
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}

	public void fireSiteInfoUpdate(DBSiteTable s) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.siteInfoUpdate(s);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}

	public void fireWeatherInfoRequest(int siteId) {
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.weatherInfoRequest(siteId);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}

	public void fireWeatherInfoUpdate(DBWeatherTable w) {
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.weatherInfoUpdate(w);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}

	public void fireSiteInfoUpdateDB(DBSiteTable s) {
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.siteInfoUpdateDB(s);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}


	}

	public void fireSiteInfoUpdateDBRespond(DBSiteTable s, int err) {
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.siteInfoUpdateDBRespond(s, err);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}


	}

	public void fireWeatherInfoUpdateDB(DBWeatherTable w) {
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.weatherInfoUpdateDB(w);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}


	public void fireWeatherInfoUpdateDBRespond(DBWeatherTable w, int err) {
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.weatherInfoUpdateDBRespond(w, err);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}

	public void fireRoomInfoRequest() {
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.roomInfoRequest();
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}

	public void fireRoomInfoUpdate(DBRoomTable r) {
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.roomInfoUpdate(r);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}
	
	public void fireRoomInfoUpdateDB(DBRoomTable r) {
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.roomInfoUpdateDB(r);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}
	
	public void fireUpdateBaseline(String roomNumber) {
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.updateBaseline(roomNumber);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}
	
	public void fireUpdateOccupancy(String roomNumber) {
		// TODO Auto-generated method stub
		for (EventListener l : listeners) {
			try {
				l.updateOccupancy(roomNumber);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}
	
	public void fireUpdateOccStatus() {
		for (EventListener l : listeners) {
			try {
				l.updateOccStatus();
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}
	
	public void fireSaveRoomHistoryData(BACnetObject obj) {
		for (EventListener l : listeners) {
			try {
				l.saveRoomHistoryData(obj);
			} catch (Throwable e) {
				handleException(l, e);
			}
		}
	}
	
	
}
