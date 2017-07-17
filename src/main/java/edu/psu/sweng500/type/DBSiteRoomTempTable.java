package edu.psu.sweng500.type;

public class DBSiteRoomTempTable {
	private int siteID;
	private String roomNumber;
	private float temperature;
	
	public DBSiteRoomTempTable () {
		this.siteID = 1;
		this.roomNumber = "";
		this.temperature = 0;
	}
	
	public DBSiteRoomTempTable(int siteID, String roomNumber, float temperature) {
		this.siteID = siteID;
		this.roomNumber = roomNumber;
		this.temperature = temperature;
	}
	
	public void setSiteID (int siteID) {
		this.siteID = siteID;
	}
	
	public int getSiteID () {
		return this.siteID;
	}
	
	public void setRoomNumber (String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public String getRoomNumber () {
		return this.roomNumber;
	}
	
	public void setTemperature (float temperature) {
		this.temperature = temperature;
	}
	
	public float getTemperature () {
		return this.temperature;
	}
}
