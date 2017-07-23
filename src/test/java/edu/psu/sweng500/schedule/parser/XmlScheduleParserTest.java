package edu.psu.sweng500.schedule.parser;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import edu.psu.sweng500.schedule.objects.XmlDomMap;
import edu.psu.sweng500.type.DBScheduleTable;
import edu.psu.sweng500.type.ScheduleFields;
import edu.psu.sweng500.util.DocumentLoader;

public class XmlScheduleParserTest 
{
	@Test
	public void testParse() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException
	{
		Document document = DocumentLoader.loadDocument("src/test/resources/ParsingTestInput.xml");
		
		// setup an XmlDomMap for the test document
		XmlDomMap map = new XmlDomMap();
		map.setProperty(ScheduleFields.SCHEDULE_ROOT.toString(), "//Event");
		map.setProperty(ScheduleFields.EVENT_ID.toString(), ".//@eventKey");
		map.setProperty(ScheduleFields.EVENT_NAME.toString(), ".//@eventName");
		map.setProperty(ScheduleFields.EVENT_DESCRIPTION.toString(), ".//@eventPostAs");
		map.setProperty(ScheduleFields.EVENT_START.toString(), ".//MeetingSpace/@startDateTime");
		map.setProperty(ScheduleFields.EVENT_STOP.toString(), ".//MeetingSpace/@endDateTime");
		map.setProperty(ScheduleFields.TEMPERATURE_SETPOINT.toString(), ".//Temperature");
		map.setProperty(ScheduleFields.LIGHT_INTENSITY.toString(), ".//Light");
		
		XmlScheduleParser parser = new XmlScheduleParser();
		ArrayList<DBScheduleTable> events = parser.parse(document, map);
		
		boolean pass = true;
		for( DBScheduleTable event : events )
		{
			pass &= ( event.getScheduleId() == 65733 );
			pass &= ( ("Group").equals(event.getName()) );
			pass &= ( ("Audit Year 3@Deloitte: Go the Distance").equals(event.getDescription()) );
			pass &= ( ("Fri Jun 16 08:00:00 EDT 2017").equals(event.getStartDateTime().toString()) );
//			System.out.println( event.getStartDateTime() );
			pass &= ( ("Fri Jun 16 14:15:00 EDT 2017").equals(event.getEndDateTime().toString()) );
//			System.out.println( event.getEndDateTime() );
			pass &= ( event.getTemperatureSetpoint() == 20.0 );
			pass &= ( event.getLightIntensity() == 70 );
		}
		
		assertTrue( pass );
	}

}
