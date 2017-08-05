package edu.psu.sweng500.type;

public class BacnetObject {
	private String RoomNumber;
	private float RoomTemp;
	private float OccSetpoint;
	private float UnoccSetpoint;
	private float Light;
	
	private int OccState;
	private int OptOccState;
	private int Status;
	private int CoolMode;
	
	public BacnetObject () {
		
	}
	
	public void setRoomNumber(String RoomNumber) {
		this.RoomNumber = RoomNumber;
	}
	
	public String getRoomNumber () {
		return this.RoomNumber;
	}
	
	public void setRoomTemp (float RoomTemp) {
		this.RoomTemp = RoomTemp;
	}
	
	public float getRoomTemp () {
		return this.RoomTemp;
	}
	
	public void setOccSetpoint (float OccSetpoint) {
		this.OccSetpoint = OccSetpoint;
	}
	
	public float getOccSetpoint() {
		return this.OccSetpoint;
	}

	public void setUnoccSetpoint (float UnoccSetpoint) {
		this.UnoccSetpoint = UnoccSetpoint;
	}
	
	public float getUnoccSetpoint () {
		return this.UnoccSetpoint;
	}
	
	public void setLight (float Light) {
		this.Light = Light;
	}
	
	public float getLight () {
		return this.Light;
	}
	
	public void setOccState (int OccState) {
		this.OccState = OccState;
	}
	
	public int getOccState () {
		return this.OccState;
	}
	
	public void setOptOccState (int OptOccState) {
		this.OptOccState = OptOccState;
	}
	
	public int getOptOccState() {
		return this.OptOccState;
	}
	
	public void setStatus (int Status) {
		this.Status = Status;
	}
	
	public int getStatus () {
		return this.Status;
	}
	
	public void setCoolMode (int CoolMode) {
		this.CoolMode = CoolMode;
	}
	
	public int getCoolMode () {
		return this.CoolMode;
	}
}
