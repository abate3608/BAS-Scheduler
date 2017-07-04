package edu.psu.sweng500.type;

public class DBRoomTable {
	private int id;
	private String roomNumber;
	private String roomName;
	private String roomType;
	private int occState;
	private int status;
	
	public DBRoomTable () {
		id = 0;
		roomNumber = "";
		roomName = "";
		roomType = "";
		status = 0;
	}
	
	public DBRoomTable (int pid, String proomNumber, String proomName, String proomType, int poccState, int pstatus) {
		id = pid;
		roomNumber = proomNumber;
		roomName = proomName;
		roomType = proomType;
		occState = poccState;
		status = pstatus;
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
}
