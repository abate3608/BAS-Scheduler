package edu.psu.sweng500.type;

import java.util.Date;

public class DBWeatherTable {
	private int id;
	private int siteId;
	private double temperature;
	private double humidity;
	private double dewpoint;
	private int conditionId;
	private Date lastUpdate;
	
	public DBWeatherTable () {
		id = 0;
		siteId = 0;
		temperature = 0;
		humidity = 0;
		dewpoint = 0;
		conditionId = 0;
		lastUpdate = null;
	}
	
	public DBWeatherTable (int id, int siteId, double temperature, double humidity, double dewpoint, int conditionId, Date lateUpdate) {
		this.id = id;
		this.siteId = siteId;
		this.temperature = temperature;
		this.humidity = humidity;
		this.dewpoint = dewpoint;
		this.conditionId = conditionId;
		this.lastUpdate = lateUpdate;
	}
	
	public int getId() {
		return id;
	}
	
	public void setSiteId (int psiteId) {
		siteId = psiteId;
	}
	
	public int getSiteId () {
		return siteId;
	}
	
	public void setTemperature (double temperature) {
		this.temperature = temperature;
	}
	
	public double getTemperature () {
		return temperature;
	}
	
	public void setHumidity (double humidity) {
		this.humidity = humidity;
	}
	
	public double getHumidity () {
		return humidity;
	}
	
	public void setDewpoint (double dewpoint) {
		this.dewpoint = dewpoint;
	}
	
	public double getDewpoint () {
		return dewpoint;
	}
	
	public void setConditionId (int conditionId) {
		this.conditionId = conditionId;
	}
	
	public int getConditionId () {
		return conditionId;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
}
