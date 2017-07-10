package edu.psu.sweng500.schedule.objects;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

/**
 * 
 * @author awb
 */
public class XmlDomMap extends Properties
{
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reads a properties file into this {@link XmlDomMap}. 
	 * @param path to properties file
	 * @throws IOException
	 */
	public void readMapFromFile( Path path ) throws IOException
	{
		try ( InputStream in = Files.newInputStream( path ) )
		{
			this.load( in );
		}
	}

	/**
	 * Writes this {@link XmlDomMap} to a properties file.
	 * @param path to write to
	 * @throws IOException
	 */
	public void writeMapToFile( Path path ) throws IOException
	{
		try ( OutputStream out = new BufferedOutputStream(
				Files.newOutputStream( path, StandardOpenOption.CREATE, StandardOpenOption.WRITE ) ) )
		{
			this.store( out, null );
		}
	}

	public String setScheduleRoot( String xpath )
	{
		return (String) this.setProperty( "scheduleRoot", xpath);
	}

	public String setEventIDElement( String xpath )
	{
		return (String) this.setProperty( "eventID", xpath );
	}
	
	public String setEventRoomNameElement( String xpath )
	{
		return (String) this.setProperty( "roomName", xpath );
	}

	public String setEventNameElement( String xpath )
	{
		return (String) this.setProperty( "eventName", xpath );
	}

	public String setEventDescriptionElement( String xpath )
	{
		return (String) this.setProperty( "eventDescription", xpath);
	}

	public String setEventStartElement( String xpath )
	{
		return (String) this.setProperty( "eventStart", xpath );
	}

	public String setEventStopElement( String xpath )
	{
		return (String) this.setProperty( "eventStop", xpath );
	}

	public String setTemperatureElement( String xpath )
	{
		return (String) this.setProperty( "temperatureSetpoint", xpath );
	}

	public String setLighingElement( String xpath )
	{
		return (String) this.setProperty( "lightIntensity", xpath );
	}

}
