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
import edu.psu.sweng500.testUtils.DocumentLoader;
import edu.psu.sweng500.type.ScheduleEvent;

public class XmlScheduleParserTest 
{
	@Test
	public void testParse() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException
	{
		Document document = DocumentLoader.loadDocument("src/test/resources/ParsingTestInput.xml");
		
		// setup an XmlDomMap for the test document
		XmlDomMap map = new XmlDomMap();
		map.setScheduleRoot("//Event");
		map.setEventIDElement(".//@eventKey");
		map.setEventNameElement(".//@eventName");
		map.setEventDescriptionElement(".//@eventPostAs");
		map.setEventStartElement(".//MeetingSpace/@startDateTime");
		map.setEventStopElement(".//MeetingSpace/@endDateTime");
		map.setTemperatureElement(".//Temperature");
		map.setLighingElement(".//Light");
		
		XmlScheduleParser parser = new XmlScheduleParser();
		ArrayList<ScheduleEvent> events = parser.parse(document, map);
		
		boolean pass = true;
		for( ScheduleEvent event : events )
		{
			pass &= ( event.getEventID() == 65733 );
			pass &= ( ("Group").equals(event.getEventName()) );
			pass &= ( ("Audit Year 3@Deloitte: Go the Distance").equals(event.getEventDescription()) );
			pass &= ( ("Fri Apr 06 08:00:00 EDT 2018").equals(event.getEventStart().toString()) );
			//System.out.println( event.getEventStart() );
			pass &= ( ("Fri Apr 06 14:15:00 EDT 2018").equals(event.getEventStop().toString()) );
			//System.out.println( event.getEventStop() );
			pass &= ( event.getTemperatureSetpoint() == 20.0 );
			pass &= ( event.getLightIntensity() == 70.0 );
		}
		
		assertTrue( pass );
	}

}
