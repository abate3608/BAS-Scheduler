package edu.psu.sweng500.type;

import java.util.Date;

public class DBScheduleTable {

	private String rowGuid;
	private int scheduleId;
	private int scheduleSiteId;
	private String name;
	private String description;
	private String notes;
	private int controlToState;
	private Date startDateTime;
	private Date endDateTime;
	private boolean markedForDelete;
	private String roomName;
	private float temperatureSetpoint;
	private int lightIntensity;
	private Date lastUpdate;

	public DBScheduleTable() {
		rowGuid = null;
		scheduleId = 0;
		scheduleSiteId = 0;
		name = null;
		description = null;
		notes = null;
		controlToState = 1;
		startDateTime = null;
		endDateTime = null;
		markedForDelete = false;
		roomName = null;
		temperatureSetpoint = 72;
		lightIntensity = 100;
		lastUpdate = null;
	}
	
	public DBScheduleTable(String rowGuid, int scheduleId, int scheduleSiteId, String name, String description, String notes, int controlToState, Date startDateTime, Date endDateTime,
			boolean markedForDelete, String roomName, int temperatureSetpoint, int lightIntensity, Date lastUpdate) {
		this.rowGuid = rowGuid;
		this.scheduleId = scheduleId;
		this.scheduleSiteId = scheduleSiteId;
		this.name = name;
		this.description = description;
		this.notes = notes;
		this.controlToState = controlToState;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.markedForDelete = markedForDelete;
		this.roomName = roomName;
		this.temperatureSetpoint = temperatureSetpoint;
		this.lightIntensity = lightIntensity;
		this.lastUpdate = lastUpdate;
	}

	public void setRowGuid (String rowGuid) {
		this.rowGuid = rowGuid;
	}
	
	public String getRowGuid () {
		return rowGuid;
	}
	
	public void setScheduleId (int scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	public int getScheduleId () {
		return scheduleId;
	}
	
	public void setScheduleSiteId (int scheduleSiteId) {
		this.scheduleSiteId = scheduleSiteId;
	}
	
	public int getScheduleSiteId () {
		return scheduleSiteId;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public String getName () {
		return name;
	}
	
	public void setDescription (String description) {
		this.description = description;
	}
	
	public String getDescription () {
		return description;
	}
	
	public void setNotes (String notes) {
		this.notes = notes;
	}
	
	public String getNotes () {
		return notes;
	}
	
	public void setControlToState (int controlToState) {
		this.controlToState = controlToState;
	}
	
	public int getControlToState () {
		return controlToState;
	}
	
	public void setStartDateTime (Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	public Date getStartDateTime () {
		return startDateTime;
	}
	
	public void setEndDateTime (Date endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	public Date getEndDateTime () {
		return endDateTime;
	}
	
	public void setMarkedForDelete (boolean markedForDelete) {
		this.markedForDelete = markedForDelete;
	}
	
	public boolean getMarkedForDelete () {
		return markedForDelete;
	}
	
	public void setRoomName (String roomName) {
		this.roomName = roomName;
	}
	
	public String getRoomName () {
		return roomName;
	}
	
	public void setTemperatureSetpoint (float temperatureSetpoint) {
		this.temperatureSetpoint = temperatureSetpoint;
	}
	
	public float getTemperatureSetpoint() {
		return temperatureSetpoint;
	}
	
	public void setLightIntensity (int lightIntensity) {
		this.lightIntensity = lightIntensity;
	}
	
	public int getLightIntensity () {
		return lightIntensity;
	}
	
	public Date getLastUpdate () {
		return lastUpdate;
	}
}
