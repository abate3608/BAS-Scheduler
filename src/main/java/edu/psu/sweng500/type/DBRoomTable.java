package edu.psu.sweng500.type;


import java.util.Date;

public class DBRoomTable {
	private int id;
	private String roomNumber;
	private String roomName;
	private String roomType;
	private int occState;
	private int optOccState;
	private int status;
	private float tempSetpoint;
	private float lightIntensity;
	private float RoomTemp;
	private Date LastUpdated;
	private int OccOffset;
	private int UnoccOffset;
	private float UnoccSetpoint;
	
	
	public DBRoomTable () {
		id = 0;
		roomNumber = "";
		roomName = "";
		roomType = "";
		status = 0;
		tempSetpoint = 72;
		lightIntensity = 100;
	}
	
	public DBRoomTable (int id, String roomNumber, String proomName, String proomType, 
			int poccState, int optOccState, int pstatus, float tempSetpoint, float lightIntensity, 
			float RoomTemp, int OccOffset, int UnoccOffset, float UnoccSetpoint) {
		this.id = id;
		this.roomNumber = roomNumber;
		roomName = proomName;
		roomType = proomType;
		occState = poccState;
		this.optOccState = optOccState;
		status = pstatus;
		this.tempSetpoint = tempSetpoint;
		this.lightIntensity = lightIntensity;
		this.RoomTemp = RoomTemp;
		this.OccOffset = OccOffset;
		this.UnoccOffset = UnoccOffset;
		this.UnoccSetpoint = UnoccSetpoint;
	}
	
	public int getId() {
		return id;
	}
	
	public void setRoomNumber (String proomNumber) {
		roomNumber = proomNumber;
	}
	
	public String getRoomNumber () {
		return roomNumber;
	}
	
	public void setRoomName (String proomName) {
		roomName = proomName;
	}
	
	public String getRoomName () {
		return roomName;
	}
	
	public void setRoomType (String proomType) {
		roomType = proomType;
	}
	
	public String getRoomTye () {
		return roomType;
	}
	
	public void setStatus (int pstatus) {
		status = pstatus;
	}
	
	public int getStatus () {
		return status;
	}
	
	public void setOccState (int poccState) {
		occState = poccState;
	}
	
	public int getOccState () {
		return occState;
	}
	
	public void setOptOccState (int optOccState) {
		this.optOccState = optOccState;
	}
	
	public int getOptOccState () {
		return optOccState;
	}
	
	public void setTempSetpoint(float tempSetpoint) {
		this.tempSetpoint = tempSetpoint;
	}
	
	public float getTempSetpoint () {
		return this.tempSetpoint;
	}
	
	public void setLightIntensity(float lightIntensity) {
		this.lightIntensity = lightIntensity;
	}
	
	public float getRoomTemp () {
		return this.RoomTemp;
	}
	public void setRoomTemp(float RoomTemp) {
		this.RoomTemp = RoomTemp;
	}
	
	public float getLightIntensity () {
		return this.lightIntensity;
	}
	public void setLastUpdated (Date time) {
		this.LastUpdated = time;
	}
	
	public Date getLastUpdated () {
		return this.LastUpdated;
	}
	
	public void setOccOffset (int OccOffset) {
		this.OccOffset = OccOffset;
	}
	
	public int getOccOffset () {
		return this.OccOffset;
	}
	
	public void setUnoccOffset (int UnoccOffset) {
		this.UnoccOffset = UnoccOffset;
	}
	
	public int getUnoccOffset () {
		return this.UnoccOffset;
	}
	
	public void setUnoccSetpoint (float UnoccSetpoint) {
		this.UnoccSetpoint = UnoccSetpoint;
	}
	
	public float getUnoccSetpoint () {
		return this.UnoccSetpoint;
	}
}
