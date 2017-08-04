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
import edu.psu.sweng500.type.DBScheduleTable;
import edu.psu.sweng500.type.ScheduleEvent;
import edu.psu.sweng500.type.ScheduleFields;

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
	public ArrayList<DBScheduleTable> parse( Document document, XmlDomMap map ) throws XPathExpressionException
	{
		NodeList nodes = (NodeList) XPATH
				.compile( map.getProperty(ScheduleFields.SCHEDULE_ROOT.toString()) )
				.evaluate( document, XPathConstants.NODESET );

		ArrayList<DBScheduleTable> scheduleEvents = new ArrayList<DBScheduleTable>();
		for( int i = 0; i < nodes.getLength(); i++ )
		{
			Node root = nodes.item( i );
			DBScheduleTable event = new DBScheduleTable();
			try{
				fillEventField( event, root, map );
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
	private DBScheduleTable fillEventField( DBScheduleTable event, Node root, XmlDomMap map ) 
			throws XPathExpressionException, ParseException
	{
		for( Object field : map.keySet() )
		{
			XPathExpression xpath = XPATH.compile( map.getProperty((String) field) );
			if( ScheduleFields.EVENT_ID.toString().equals((String) field) )
			{
				event.setScheduleId(
						((Double) xpath.evaluate(root, XPathConstants.NUMBER )).intValue()
						);
			}
			else if( ScheduleFields.EVENT_NAME.toString().equals((String) field) )
			{
				event.setName(
						(String) xpath.evaluate( root, XPathConstants.STRING )
						);
			}
			else if( ScheduleFields.ROOM_NAME.toString().equals((String) field) )
			{
				event.setRoomName(
						(String) xpath.evaluate( root, XPathConstants.STRING )
						);
			}
			else if( ScheduleFields.EVENT_DESCRIPTION.toString().equals((String) field) )
			{
				event.setDescription(
						(String) xpath.evaluate( root, XPathConstants.STRING )
						);
			}
			else if( ScheduleFields.EVENT_START.toString().equals((String) field) )
			{
				event.setStartDateTime(	parseDate(
						(String) xpath.evaluate( root, XPathConstants.STRING )
						) );
			}
			else if( ScheduleFields.EVENT_STOP.toString().equals((String) field) )
			{
				event.setEndDateTime( parseDate(
						(String) xpath.evaluate( root, XPathConstants.STRING )
						) );
			}
			else if( ScheduleFields.TEMPERATURE_SETPOINT.toString().equals((String) field) )
			{
				event.setTemperatureSetpoint(
						((Double) xpath.evaluate( root, XPathConstants.NUMBER )).floatValue() 
						);
			}
			else if( ScheduleFields.LIGHT_INTENSITY.toString().equals((String) field) )
			{
				event.setLightIntensity(
						((Double) xpath.evaluate( root, XPathConstants.NUMBER )).intValue()
						);
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
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return df.parse( datestring );
	}
}
