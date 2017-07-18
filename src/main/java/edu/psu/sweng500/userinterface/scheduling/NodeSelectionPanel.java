package edu.psu.sweng500.userinterface.scheduling;

import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.psu.sweng500.schedule.objects.XmlDomExtractor;
import edu.psu.sweng500.schedule.objects.XmlDomMap;
import edu.psu.sweng500.type.ScheduleFields;
import edu.psu.sweng500.util.DocumentLoader;

/**
 * A {@link JPanel} containing an XML DOM node selection map.
 * @author awb
 */
public class NodeSelectionPanel extends JPanel
{
	/** default */
	private static final long serialVersionUID = 1L;
	
	private Map<NodeLabel, MyComboBox> map;
	
	/**
	 * JLabel that stores a {@link Node} reference 
	 */
	private static class NodeLabel extends JLabel
	{
		/** default */
		private static final long serialVersionUID = 1L;
		
		private Node node;
		
		protected NodeLabel( Node node )
		{
			this.node = node;
		}
		
		protected Node getNode()
		{
			return node;
		}
	}
	
	/**
	 * JComboBox subclass for convenience.
	 */
	private static class MyComboBox extends JComboBox<ScheduleFields>
	{
		/** default */
		private static final long serialVersionUID = 1L;
		
		protected MyComboBox()
		{
			super( ScheduleFields.values() );
			this.setSelectedIndex( 0 );
		}
	}
	
	/**
	 * Construct new {@link NodeSelectionPanel}.
	 */
	public NodeSelectionPanel()
	{
		super( new GridLayout( 0, 2 ) );
		this.setBorder( BorderFactory.createTitledBorder("Configure XML Map") );
		this.map = new HashMap<NodeLabel, MyComboBox>();
		this.setVisible( false );
	}
	
	/**
	 * Sets the components of the {@link NodeSelectionPanel} based on the XML 
	 *   document at the given {@link Path}.
	 * @param path to the XML document
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void setPanel( Path path ) throws ParserConfigurationException, SAXException, IOException
	{
		Document document = XmlDomExtractor.extractFromDocument( 
				DocumentLoader.loadDocument( path ) );
		buildComponents( document.getChildNodes(), 0 );
		this.setVisible( true );
	}
	
	/**
	 * Recurses over the DOM tree to build the node selection map.
	 * @param nodes 
	 * @param depth
	 */
	private void buildComponents( NodeList nodes, int depth )
	{
		for( int i = 0; i < nodes.getLength(); i++ )
		{
			Node n = nodes.item( i );
			if( n instanceof Element)
			{
				putNode( n, depth );
				buildComponentsAttrs( n, depth+1 );
			}
			buildComponents( n.getChildNodes(), depth+1 );
		}
	}

	/**
	 * Adds an {@link Element}'s attributes (if any) to the node 
	 *   selection map.
	 * @param n
	 * @param depth
	 */
	private void buildComponentsAttrs( Node n, int depth )
	{
		if( n.hasAttributes() )
		{
			NamedNodeMap attrs = n.getAttributes();
			for( int i = 0; i < attrs.getLength(); i++ )
			{
				putNode( attrs.item( i ), depth );
			}
		}
	}
	
	/**
	 * Creates a {@link NodeLabel} and {@link MyComboBox} for the 
	 *   given {@link Node} and adds them to this {@link NodeSelectionPanel} 
	 * @param n the {@link Node} for which to add a 
	 *   {@link NodeLabel} and {@link MyComboBox}
	 * @param depth of n in the DOM tree
	 */
	private void putNode( Node n, int depth )
	{
		NodeLabel label = new NodeLabel( n );
		label.setBorder( new EmptyBorder( 0, 20*depth, 0, 0 ) );
		label.setText( n.getNodeName() );
		MyComboBox box = new MyComboBox();
		map.put( label, box );
		
		this.add( label );
		this.add( box );
	}
	
	public XmlDomMap buildXmlDomMap()
	{
		//TODO
		return null;
	}
}
