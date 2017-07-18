package edu.psu.sweng500.userinterface.scheduling;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.psu.sweng500.schedule.objects.XmlDomExtractor;
import edu.psu.sweng500.util.DocumentLoader;

public class XmlImportComboBox
{
	public void dosomething( Path path )
	{
		try {
			Document structure = XmlDomExtractor.extractFromDocument( DocumentLoader.loadDocument( path ) );
			ArrayList<JComboBox<String>> comboboxes = new ArrayList<JComboBox<String>>(); 
			buildComboBoxes( structure.getChildNodes(), comboboxes );
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void buildComboBoxes( NodeList nodes, ArrayList<JComboBox<String>> comboboxes )
	{
		for( int i = 0; i < nodes.getLength(); i++ )
		{
			Node n = nodes.item(i);
			
			if( n.hasAttributes() )
			{
				buildAttrComboBoxes( n.getAttributes() );
			}
			buildComboBoxes( n.getChildNodes(), comboboxes );
		}
	}
	
	private void buildAttrComboBoxes( NamedNodeMap attributes )
	{
		
	}
}
