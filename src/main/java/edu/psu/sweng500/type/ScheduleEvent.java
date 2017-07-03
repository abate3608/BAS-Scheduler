package edu.psu.sweng500.type;

import java.util.Date;

public class ScheduleEvent {
	private String roomName;
	private int eventID;
	private String eventName;
	private String eventDescription;
	private Date eventStart;
	private Date eventStop;
	private float temperatureSetpoint;
	private float lightIntensity;


	public void EventOBject(int eventID, String eventName, String eventDescription, Date eventStart, Date eventStop,
			float temperatureSetpoint, float lightIntensity) {
		this.eventID = eventID;
		this.eventName = eventName;
		this.eventDescription = eventDescription;
		this.eventStart = eventStart;
		this.eventStop = eventStop;
		this.temperatureSetpoint = temperatureSetpoint;
		this.lightIntensity = lightIntensity;
	}

	public void setEventID(int id) {
		eventID = id;
	}
	
	public void setEventName(String str) {
		eventName = str;
	}

	public void setEventDescription(String str) {
		eventDescription = str;
	}

	public void setEventStart(Date date) {
		eventStart = date;
	}

	public void setEventStop(Date date) {
		eventStop = date;
	}

	public void setTemperatureSetpoint(float temp) {
		temperatureSetpoint = temp;
	}

	public void setLightIntensity(float light) {
		lightIntensity = light;
	}
	
	public int getEventID() {
		return eventID;
	}
	public String getEventName() {
		return eventName;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public Date getEventStart() {
		return eventStart;
	}

	public Date getEventStop() {
		return eventStop;
	}

	public float getTemperatureSetpoint() {
		return temperatureSetpoint;
	}

	public float getLightIntensity() {
		return lightIntensity;
	}
}
