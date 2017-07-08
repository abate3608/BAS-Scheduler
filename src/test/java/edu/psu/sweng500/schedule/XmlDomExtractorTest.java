package edu.psu.sweng500.schedule;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import edu.psu.sweng500.schedule.objects.SimpleErrorHandler;
import edu.psu.sweng500.schedule.objects.XmlDomExtractor;

public class XmlDomExtractorTest 
{
	@Test
	public void test() throws SAXException, IOException, ParserConfigurationException, TransformerException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating( false );
		factory.setNamespaceAware( true );
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setErrorHandler( new SimpleErrorHandler() );
		
		Document document = builder.parse( new File("src/test/resources/MeetingSpaceOutput.xml") );
		Document xmlMap = XmlDomExtractor.extractFromDocument( document );
		ByteArrayOutputStream baos = (ByteArrayOutputStream) 
				XmlDomExtractor.writeDocumentToStream( xmlMap, new ByteArrayOutputStream() );
		
		String results = baos.toString( StandardCharsets.UTF_8.name() );
		String expected = new String( Files.readAllBytes(
				Paths.get("src/test/resources/XmlDomExtractorExpected.xml")
				) );
		
		assertTrue( results.equals( expected ) );
	}
}
