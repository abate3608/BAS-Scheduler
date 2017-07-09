package edu.psu.sweng500.schedule.objects;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class XmlDomMap 
{
	private String scheduleRoot;
	private Map<String, String> map = new ConcurrentHashMap<String, String>();

	public void setScheduleRoot( String xpath )
	{
		scheduleRoot = xpath;
	}
	
	public String getScheduleRoot()
	{
		return scheduleRoot;
	}
	
	public Map<String, String> getMap()
	{
		return map;
	}

	public String setEventIDElement( String xpath )
	{
		return map.put( "eventID", xpath );
	}
	
	public String setEventNameElement( String xpath )
	{
		return map.put( "eventName", xpath );
	}
	
	public String setEventDescriptionElement( String xpath )
	{
		return map.put( "eventDescription", xpath);
	}
	
	public String setEventStartElement( String xpath )
	{
		return map.put( "eventStart", xpath );
	}
	
	public String setEventStopElement( String xpath )
	{
		return map.put( "eventStop", xpath );
	}
	
	public String setTemperatureElement( String xpath )
	{
		return map.put( "temperatureSetpoint", xpath );
	}
	
	public String setLighingElement( String xpath )
	{
		return map.put( "lightIntensity", xpath );
	}
	
}
