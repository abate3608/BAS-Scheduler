package edu.psu.sweng500.importer;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.*;

public class XMLImporter {
	private String siteTagName = "Property";
	private String siteIDTagName = "propertyKey";
	private String siteNameTagName = "propertyName";

	private String eventIdTagName = "roomKey";
	private String eventTagName = "MeetingSpace";
	private String eventRoomNumberTagName = "roomName";
	private String eventNameTagName = "meetingName";
	private String eventDescriptionTagName = "meetingPostAs";
	private String eventStartTagName = "startDateTime";
	private String eventStopTagName = "endDateTime";
	private String eventTemperatureSetpointTagName = null;
	private String eventLIghtIntensityTagName = null;

	private String xmlFile;
	// Event listeners
	private final EventHandler eventHandler = EventHandler.getInstance();

	
	public XMLImporter() {
		xmlFile = "C:\\Users\\phong\\git\\BAS-Scheduler\\src\\main\\java\\edu\\psu\\sweng500\\importer\\MeetingSpaceOutput.xml";
	}
	
	public void parseXML() {
		try {


			DBScheduleTable schedule = new DBScheduleTable();


			
			
			//create document builder XML Parser
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			//parse xml file
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();


			System.out.println("XML Importer > " + doc.getDocumentElement().getNodeName());

			//get site information
			NodeList nodes = doc.getElementsByTagName(siteTagName);
			System.out.println("==========================");

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					System.out.println("Site ID: " + element.getAttribute(siteIDTagName));
					System.out.println("Site Name: " + element.getAttribute(siteNameTagName));
				}
			}



			//get event information
			nodes = doc.getElementsByTagName(eventTagName);
			System.out.println("==========================");

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					//get room information
					DBRoomTable room = new DBRoomTable();
					room.setRoomNumber(element.getAttribute(eventRoomNumberTagName));
					room.setRoomName(element.getAttribute(eventRoomNumberTagName));
					
					eventHandler.fireRoomInfoUpdateDB(room);
					
					
					//System.out.println("Room Number: " + room.getRoomNumber());
					//System.out.println("Room Name: " + room.getRoomName());

					//get event information
					schedule.setRoomName(room.getRoomNumber());
					
					schedule.setRowGuid(null);
					
					schedule.setName(element.getAttribute(eventNameTagName));
					//System.out.println("XML Importer > Import Event Name: " + element.getAttribute(eventNameTagName));

					schedule.setDescription(element.getAttribute(eventDescriptionTagName));
					//System.out.println("Event Description: " + element.getAttribute(eventDescriptionTagName));

					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					schedule.setStartDateTime(df.parse(cleanStringDateTime(element.getAttribute(eventStartTagName))));
					//System.out.println("Event Start: " + element.getAttribute(eventStartTagName));

					schedule.setEndDateTime(df.parse(cleanStringDateTime(element.getAttribute(eventStopTagName))));
					//System.out.println("Event Stop: " + element.getAttribute(eventStopTagName));

					schedule.setControlToState(1);

					//System.out.println("Event Start: " + element.getAttribute(eventStartTagName));

					eventHandler.fireCreateEvent(schedule);
				}
			}


		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private String cleanStringDateTime(String strDateTime) {
		strDateTime = strDateTime.replaceAll("[a-zA-Z]", " "); //replace for char with space
		strDateTime = strDateTime.replaceAll("  ", " ");
		strDateTime = strDateTime.trim();
		return strDateTime;
	}
}


