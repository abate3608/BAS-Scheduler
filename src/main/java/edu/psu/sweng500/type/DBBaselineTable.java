package edu.psu.sweng500.type;

import java.util.Date;

public class DBBaselineTable {
	private int ID;
	private int SiteID;
	private String RoomNumber;
	private float Yunocc;
	private int OAT;
	private float X4;
	private float Y4;
	private float X5;
	private float Y5;
	private float X6;
	private float Y6;
	private float X7;
	private float Y7;
	private float Xz0;
	private float Yz0;
	private float Xz1;
	private float Yz1;
	private float Xz2;
	private float Yz2;
	private float Xz3;
	private float Yz3;
	private float Yocc;
	private float X0;
	private float Y0;
	private float X1;
	private float Y1;
	private float X2;
	private float Y2;
	private float X3;
	private float Y3;
	private float X;
	private float Y;
	private Date OccTime;
	private Date UnOccTime;
	private float OccOffset;
	private float UnoccOffset;
	private float RoomTemp;

	public DBBaselineTable() {
		this.ID = 0;
	}

	//get
	public int getID() {
		return this.ID;
	}
	public int getSiteID() {
		return this.SiteID;
	}
	public String getRoomNumber() {
		return this.RoomNumber;
	}
	public float getYunocc() {
		return this.Yunocc;
	}
	public int getOAT() {
		return this.OAT;
	}
	public float getX4() {
		return this.X4;
	}
	public float getY4() {
		return this.Y4;
	}
	public float getX5() {
		return this.X5;
	}
	public float getY5() {
		return this.Y5;
	}
	public float getX6() {
		return this.X6;

	}
	public float getY6() {
		return this.Y6;
	}
	public float getX7() {
		return this.X7;
	}
	public float getY7() {
		return this.Y7;
	}
	public float getXz0() {
		return this.Xz0;
	}
	public float getYz0() {
		return this.Yz0;
	}
	public float getXz1() {
		return this.Xz1;
	}
	public float getYz1() {
		return this.Yz1;
	}
	public float getXz2() {
		return this.Xz2;
	}
	public float getYz2() {
		return this.Yz2;
	}
	public float getXz3() {
		return this.Xz3;
	}
	public float getYz3() {
		return this.Yz3;
	}
	public float getYocc() {
		return this.Yocc;
	}
	public float getX0() {
		return this.X0;
	}
	public float getY0() {
		return this.Y0;
	}
	public float getX1() {
		return this.X1;
	}
	public float getY1() {
		return this.Y1;
	}
	public float getX2() {
		return this.X2;
	}
	public float getY2() {
		return this.Y2;
	}
	public float getX3() {
		return this.X3;
	}
	public float getY3() {
		return this.Y3;
	}
	public float getX() {
		return this.X;
	}
	public float getY() {
		return this.Y;
	}
	public Date getOccTime() {
		return this.OccTime;
	}
	public Date getUnOccTime() {
		return this.UnOccTime;
	}


	//set
	public void setID (int ID) {
		this.ID = ID;
	}
	public void setSiteID (int SiteID) {
		this.SiteID = SiteID;
	}
	public void setRoomNumber (String RoomNumber) {
		this.RoomNumber = RoomNumber;
	}
	public void setYunocc (float Yunocc) {
		this.Yunocc = Yunocc;
	}
	public void setOAT (int OAT) {
		this.OAT = OAT;
	}
	public void setX4 (float X4) {
		this.X4 = X4;
	}
	public void setY4 (float Y4) {
		this.Y4 = Y4;
	}
	public void setX5 (float X5) {
		this.X5 = X5;
	}
	public void setY5 (float Y5) {
		this.Y5 = Y5;
	}
	public void setX6 (float X6) {
		this.X6 = X6;
	}
	public void setY6 (float Y6) {
		this.Y6 = Y6;
	}
	public void setX7 (float X7) {
		this.X7 = X7;
	}
	public void setY7 (float Y7) {
		this.Y7 = Y7;
	}
	public void setXz0 (float Xz0) {
		this.Xz0 = Xz0;
	}
	public void setYz0 (float Yz0) {
		this.Yz0 = Yz0;
	}
	public void setXz1 (float Xz1) {
		this.Xz1 = Xz1;
	}
	public void setYz1 (float Yz1) {
		this.Yz1 = Yz1;
	}
	public void setXz2 (float Xz2) {
		this.Xz2 = Xz2;
	}
	public void setYz2 (float Yz2) {
		this.Yz2 = Yz2;
	}
	public void setXz3 (float Xz3) {
		this.Xz3 = Xz3;
	}
	public void setYz3 (float Yz3) {
		this.Yz3 = Yz3;
	}
	public void setYocc (float Yocc) {
		this.Yocc = Yocc;
	}
	public void setX0 (float X0) {
		this.X0 = X0;
	}
	public void setY0 (float Y0) {
		this.Y0 = Y0;
	}
	public void setX1 (float X1) {
		this.X1 = X1;
	}
	public void setY1 (float Y1) {
		this.Y1 = Y1;
	}
	public void setX2 (float X2) {
		this.X2 = X2;
	}
	public void setY2 (float Y2) {
		this.Y2 = Y2;
	}
	public void setX3 (float X3) {
		this.X3 = X3;
	}
	public void setY3 (float Y3) {
		this.Y3 = Y3;
	}
	public void setX (float X) {
		this.X = X;
	}
	public void setY (float Y) {
		this.Y = Y;
	}
	public void setOccTime (Date OccTime) {
		this.OccTime = OccTime;
	}
	public void setUnOccTime (Date UnOccTime) {
		this.UnOccTime = UnOccTime;
	}
	
	public void setOccOffset (float OccOffset) {
		this.OccOffset = OccOffset;
	}
	public void setUnoccOffset (float UnoccOffset) {
		this.UnoccOffset = UnoccOffset;
	}
	
	public float getOccOffset () {
		return this.OccOffset;
	}
	
	public float getUnoccOffset () {
		return this.UnoccOffset;
	}
	
	public void setRoomTemp (float RoomTemp) {
		this.RoomTemp = RoomTemp;
	}
	
	public float getRoomTemp () {
		return this.RoomTemp;
	}
}
