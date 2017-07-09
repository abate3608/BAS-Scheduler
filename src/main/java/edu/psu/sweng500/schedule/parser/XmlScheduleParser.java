package edu.psu.sweng500.schedule.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.psu.sweng500.schedule.objects.XmlDomMap;
import edu.psu.sweng500.type.ScheduleEvent;

/**
 * XML schedule parsing class.
 * @author awb
 */
public class XmlScheduleParser
{	
	private static final XPath XPATH = XPathFactory.newInstance().newXPath();

	/**
	 * Parses an XML {@link Document} into {@link ScheduleEvent}s 
	 * according to an {@link XmlDomMap}  
	 * @param document the {@link Document} to parse
	 * @param map the {@link XmlDomMap} of parsing rules
	 * @return an {@link ArrayList} of {@link ScheduleEvent}s
	 * @throws XPathExpressionException
	 */
	public ArrayList<ScheduleEvent> parse( Document document, XmlDomMap map ) throws XPathExpressionException
	{
		NodeList nodes = (NodeList) XPATH
				.compile( map.getScheduleRoot() )
				.evaluate( document, XPathConstants.NODESET );

		ArrayList<ScheduleEvent> scheduleEvents = new ArrayList<ScheduleEvent>();
		for( int i = 0; i < nodes.getLength(); i++ )
		{
			Node root = nodes.item( i );
			ScheduleEvent event = new ScheduleEvent();
			try{
				fillEventField( event, root, map.getMap() );
				scheduleEvents.add( event );
			} catch (ParseException e) {
				System.err.println("Failed to parse datetime from event. Schedule event discarded...");
			}
		}
		return scheduleEvents;
	}

	/**
	 * Populates a {@link ScheduleEvent} from an XML subtree. 
	 * @param event the {@link ScheduleEvent} to populate
	 * @param root the root {@link Node} of the XML subtree of 
	 *   the schedule event data
	 * @param map the {@link Map} of XPath expressions
	 * @return a populated {@link ScheduleEvent}
	 * @throws XPathExpressionException
	 * @throws ParseException if a datetime value could not be parsed
	 */
	private ScheduleEvent fillEventField( ScheduleEvent event, Node root, Map<String, String> map ) 
			throws XPathExpressionException, ParseException
	{
		for( String field : map.keySet() )
		{
			XPathExpression xpath = XPATH.compile( map.get( field ) );
			switch( field )
			{
			case "eventID":
				event.setEventID(
						((Double) xpath.evaluate(root, XPathConstants.NUMBER )).intValue()
						);
				break;
			case "eventName":
				event.setEventName(
						(String) xpath.evaluate( root, XPathConstants.STRING )
						);
				break;
			case "eventDescription":
				event.setEventDescription(
						(String) xpath.evaluate( root, XPathConstants.STRING )
						);
				break;
			case "eventStart":
				event.setEventStart( parseDate(
						(String) xpath.evaluate( root, XPathConstants.STRING )
						) );
				break;
			case "eventStop":
				event.setEventStop( parseDate(
						(String) xpath.evaluate( root, XPathConstants.STRING )
						) );
				break;
			case "temperatureSetpoint":
				event.setTemperatureSetpoint(
						((Double) xpath.evaluate( root, XPathConstants.NUMBER )).floatValue() 
						);
				break;
			case "lightIntensity":
				event.setLightIntensity(
						((Double) xpath.evaluate( root, XPathConstants.NUMBER )).floatValue()
						);
				break;
			}
		}
		return event;
	}

	/**
	 * Helper method for parsing datetime strings.
	 * @param datestring
	 * @return a {@link Date} parsed from the given datetime string
	 * @throws ParseException if the given datestring could not be parsed
	 */
	private static Date parseDate( String datestring ) throws ParseException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM'T'hh:mm:ss");
		return df.parse( datestring );
	}
}
