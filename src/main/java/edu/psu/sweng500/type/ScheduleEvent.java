package edu.psu.sweng500.type;

import java.util.Date;

public class ScheduleEvent {
	private String eventName;
	private String eventDescription;
	private Date eventStart;
	private Date eventStop;
	private float temperatureSetpoint;
	private float lightIntensity;
	
	public void EventOBject(String eventName, String eventDescription, Date eventStart, Date eventStop, float temperatureSetpoint, float lightIntensity) {
		this.eventName = eventName;
		this.eventDescription = eventDescription;
		this.eventStart = eventStart;
		this.eventStop = eventStop;
		this.temperatureSetpoint = temperatureSetpoint;
		this.lightIntensity = lightIntensity;
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
