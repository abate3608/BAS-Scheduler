package edu.psu.sweng500.schedule.objects;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

import edu.psu.sweng500.type.ScheduleFields;

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
				Files.newOutputStream( path, 
						StandardOpenOption.CREATE, 
						StandardOpenOption.WRITE, 
						StandardOpenOption.TRUNCATE_EXISTING 
						) 
				) )
		{
			this.store( out, null );
		}
	}
	
	public String getProperty( ScheduleFields field )
	{
		return this.getProperty( field.toString() );
	}
	
	public String setProperty( ScheduleFields field, String value )
	{
		return (String)this.setProperty( field.toString(), value );
	}
}
