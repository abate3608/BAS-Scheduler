package edu.psu.sweng500.schedule.importer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.schedule.objects.XmlDomMap;
import edu.psu.sweng500.schedule.parser.XmlScheduleParser;
import edu.psu.sweng500.type.DBScheduleTable;
import edu.psu.sweng500.util.DocumentLoader;

/**
 * Importer for XML schedule documents.
 * @author awb
 */
public class XmlScheduleImporter extends ScheduleImporter 
{
	private static final EventHandler EVENT_HANDLER = EventHandler.getInstance();
	private XmlDomMap map = null;
	
	@Override
	/**
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void take( Path path ) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException 
	{
		Document document = DocumentLoader.loadDocument( path );

		XmlScheduleParser parser = new XmlScheduleParser();
		ArrayList<DBScheduleTable> schedules = parser.parse( document, map );
		
		for( DBScheduleTable s : schedules )
		{
			EVENT_HANDLER.fireCreateEvent( s );
		}
	}
	
	/**
	 * Sets the {@link XmlDomMap} configuration file path this 
	 * {@link XmlScheduleImporter} should use when importing XML 
	 * documents.
	 * @param path to the {@link XmlDomMap} properties file 
	 * @throws IOException
	 */
	public void setXmlDomMap( Path path ) throws IOException
	{
		map = new XmlDomMap();
		map.readMapFromFile( path );
	}
	
	/**
	 * Sets the {@link XmlDomMap} configuration this
	 * {@link XmlScheduleImporter} should use when importing XML
	 * documents.
	 * @param map the {@link XmlDomMap}
	 */
	public void setXmlDomMap( XmlDomMap map )
	{
		this.map = map;
	}

}
