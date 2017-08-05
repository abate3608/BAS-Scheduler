package edu.psu.sweng500.type;

import java.util.ArrayList;

public class DBSiteRmTempTable {
	private int ID;
	private int SiteID;
	private String RoomNumber;
	private float Temperature;
	private float OccSetpoint;
	private float UnOccSetpoint;
	private int CoolMode;
	private float OAT;
	private int OccStatus;
	
	//final static ArrayList<DBSiteRmTempTable> siteRoomTempTable = new ArrayList<DBSiteRmTempTable>();
	
	public DBSiteRmTempTable () {
		SiteID = 1;
		RoomNumber = null;
		Temperature = 0;
		OccSetpoint = 0;
		UnOccSetpoint = 0;
		CoolMode = 1;
		OAT = 0;
		OccStatus = 0;
	}
	
	public DBSiteRmTempTable (int SiteID, String RoomNumber, float Temperature, float OccSetpoint, float UnOccSetpoint, int CoolMode, float OAT, int OccStatus) {
		this.SiteID = SiteID;
		this.RoomNumber = RoomNumber;
		this.Temperature = Temperature;
		this.OccSetpoint = OccSetpoint;
		this.UnOccSetpoint = UnOccSetpoint;
		this.CoolMode = CoolMode;
		this.OAT = OAT;
		this.OccStatus = OccStatus;
	}
	
	/*public void addItem (self) {
		for (DBSiteRmTempTable r: siteRoomTempTable) {
			
			if (r.getRoomNumber().equals(s.getRoomNumber())) {
				return;
			}
		}
		siteRoomTempTable.add(s);
	}
	
	public void removeItem (DBSiteRmTempTable s) {
		int i = 0;
		for (DBSiteRmTempTable r: siteRoomTempTable) {
			
			if (r.getRoomNumber().equals(s.getRoomNumber())) {
				siteRoomTempTable.remove(i);
				return;
			}
			i = i + 1;
		}*/
		
	//}
	
	public int getSiteID () {
		return this.SiteID;
	}
	
	public String getRoomNumber () {
		return this.RoomNumber;
		
	}
	
	public float getTemperature () {
		return this.Temperature;
	}
	
	public float getOccSetpoint () {
		return this.OccSetpoint;
	}
	
	public float getUnOccSetpoint () {
		return this.UnOccSetpoint;
	}
	
	public int getCoolMode() {
		return this.CoolMode;
	}
	
	public float getOAT () {
		return this.OAT;
	}
	
	public int getOccStatus () {
		return this.OccStatus;
	}
	
	public void setSiteID (int SiteID) {
		this.SiteID = SiteID;
	}
	
	public void setRoomNumber (String RoomNumber) {
		this.RoomNumber = RoomNumber;
		
	}
	
	public void setTemperature (float Temperature) {
		this.Temperature = Temperature;
	}
	
	public void setOccSetpoint (float OccSetpoint) {
		this.OccSetpoint = OccSetpoint;
	}
	
	public void setUnOccSetpoint (float UnOccSetpoint) {
		this.UnOccSetpoint = UnOccSetpoint;
	}
	
	public void setCoolMode(int CoolMode) {
		this.CoolMode = CoolMode;
	}
	
	public void setOAT (float OAT) {
		this.OAT = OAT;
	}
	
	public void setOccStatus (int OccStatus) {
		this.OccStatus = OccStatus;
	}
}
