package edu.psu.sweng500.type;

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
}
