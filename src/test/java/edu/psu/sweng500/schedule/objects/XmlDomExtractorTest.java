package edu.psu.sweng500.schedule.objects;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import edu.psu.sweng500.util.DocumentLoader;

public class XmlDomExtractorTest 
{
	private Document document;

	@Before
	public void beforeTest() throws SAXException, IOException, ParserConfigurationException
	{
		document = DocumentLoader.loadDocument("src/test/resources/MeetingSpaceOutput.xml");
	}

	@Test
	public void testExtractFromDocument() throws SAXException, IOException, ParserConfigurationException, TransformerException
	{
		Document domStructure = XmlDomExtractor.extractFromDocument( document );

		ByteArrayOutputStream baos = (ByteArrayOutputStream) 
				XmlDomExtractor.writeDocumentToStream( domStructure, new ByteArrayOutputStream() );

		String results = baos.toString( StandardCharsets.UTF_8.name() );
		String expected = new String( Files.readAllBytes(
				Paths.get("src/test/resources/XmlDomExtractorExpected.xml")
				) );

		assertTrue( results.equals( expected ) );
	}

	@Test
	public void testGetXPath() throws XPathExpressionException
	{
		String result = XmlDomExtractor.getXPath( document.getChildNodes().item(0) );
		assertTrue( result.equals( "/MeetingSpaceResponse" ) );
	}

}
