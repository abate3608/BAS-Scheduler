package edu.psu.sweng500.schedule.importer;

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import edu.psu.sweng500.schedule.objects.SimpleErrorHandler;
import edu.psu.sweng500.schedule.objects.XmlDomExtractor;

public class XmlScheduleImporter extends ScheduleImporter 
{
	private static final DocumentBuilderFactory FACTORY = getDocumentBuilderFactory();
	
	/**
	 * @return a DocumentBuilderFactory
	 */
	private static DocumentBuilderFactory getDocumentBuilderFactory()
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);
		return factory;
	}
	
	@Override
	/**
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void take(Path path) throws SAXException, IOException, ParserConfigurationException 
	{
		DocumentBuilder builder = FACTORY.newDocumentBuilder();
		builder.setErrorHandler(new SimpleErrorHandler());
		Document document = builder.parse( path.toFile() );
		
		Document structure = XmlDomExtractor.extractFromDocument( document );
		
		//TODO logic for passing the structure to the UI and recieving an XmlDomMap
		//TODO pass document and XmlDomMap to XmlScheduleParser 
	}

}
