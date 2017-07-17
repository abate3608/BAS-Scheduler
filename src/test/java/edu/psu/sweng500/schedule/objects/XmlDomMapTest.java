package edu.psu.sweng500.schedule.objects;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import org.junit.Test;

public class XmlDomMapTest 
{
	@Test
	public void testReadFromFile() throws IOException
	{
		DefaultXmlDomMap map = new DefaultXmlDomMap();
		//map.readMapFromFile( Paths.get("src/test/resources/defaultXmlDomMap.properties") );
		
		boolean pass = true;
		pass &= ("//MeetingSpace").equals( map.getProperty("scheduleRoot") );
		pass &= (".//@roomName").equals( map.getProperty( "roomName" ) );
		
		assertTrue( pass );
	}

}
