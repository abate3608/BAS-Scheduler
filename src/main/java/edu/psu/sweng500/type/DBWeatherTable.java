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
	
	public DBWeatherTable (int pid, int psiteId, double ptemperature, double phumidity, double pdewpoint, int pconditionId, Date plateUpdate) {
		id = pid;
		siteId = psiteId;
		temperature = ptemperature;
		humidity = phumidity;
		dewpoint = pdewpoint;
		conditionId = pconditionId;
		lastUpdate = plateUpdate;
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
	
	public void setTemperature (double ptemperature) {
		temperature = ptemperature;
	}
	
	public double getTemperature () {
		return temperature;
	}
	
	public void setHumidity (double phumidity) {
		humidity = phumidity;
	}
	
	public double getHumidity () {
		return humidity;
	}
	
	public void setDewpoint (double pdewpoint) {
		dewpoint = pdewpoint;
	}
	
	public double getDewpoint () {
		return dewpoint;
	}
	
	public void setConditionId (int pconditionId) {
		conditionId = pconditionId;
	}
	
	public int getConditionId () {
		return conditionId;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
}
