package edu.psu.sweng500.type;

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
	
	public DBRoomTable () {
		id = 0;
		roomNumber = "";
		roomName = "";
		roomType = "";
		status = 0;
		tempSetpoint = 72;
		lightIntensity = 100;
	}
	
	public DBRoomTable (int id, String roomNumber, String proomName, String proomType, int poccState, int optOccState, int pstatus, float tempSetpoint, float lightIntensity) {
		this.id = id;
		this.roomNumber = roomNumber;
		roomName = proomName;
		roomType = proomType;
		occState = poccState;
		this.optOccState = optOccState;
		status = pstatus;
		this.tempSetpoint = tempSetpoint;
		this.lightIntensity = lightIntensity;
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
	
	public float getLightIntensity () {
		return this.lightIntensity;
	}
}
