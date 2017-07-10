package edu.psu.sweng500.util;

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import edu.psu.sweng500.schedule.objects.SimpleErrorHandler;

/**
 * Utility class for loading XML documents.
 * @author awb
 */
public enum DocumentLoader 
{
	INSTANCE;
	
	private static final DocumentBuilderFactory FACTORY = getFactory();
	
	private static DocumentBuilderFactory getFactory()
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating( false );
		factory.setNamespaceAware( false );
		return factory;
	}
	
	/**
	 * Creates a {@link Document} from an XML file. 
	 * @param path to XML file
	 * @return {@link Document}
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document loadDocument( String path ) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilder builder = FACTORY.newDocumentBuilder();
		builder.setErrorHandler( new SimpleErrorHandler() );
		return builder.parse( path );
	}
	
	/**
	 * Creates a {@link Document} from an XML file. 
	 * @param path to XML file
	 * @return {@link Document}
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document loadDocument( Path path ) throws ParserConfigurationException, SAXException, IOException
	{
		return loadDocument( path.toString() );
	}

}
