package edu.psu.sweng500.userinterface.scheduling;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	
	private static Map<ScheduleFields, Node> selectionMap;
	
	/**
	 * JLabel subclass for convenience.
	 */
	private static class NodeLabel extends JLabel
	{
		/** default */
		private static final long serialVersionUID = 1L;
		
		protected NodeLabel( Node node, int depth )
		{
			this.setBorder( new EmptyBorder( 0, 20*depth, 0, 0 ) );
			this.setText( node.getNodeName() );
		}
	}
	
	/**
	 * JComboBox subclass for convenience.
	 */
	private static class NodeComboBox extends JComboBox<ScheduleFields>
	{
		/** default */
		private static final long serialVersionUID = 1L;
		
		private Node node;
		
		protected NodeComboBox( Node node )
		{
			super( ScheduleFields.values() );
			this.node = node;
			this.setSelectedIndex( 0 );
			this.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					ScheduleFields field = (ScheduleFields)NodeComboBox.this.getSelectedItem();
					if( !field.equals(ScheduleFields.NONE) )
						selectionMap.put( field, NodeComboBox.this.getNode() );
				}
			} );
		}
		
		protected Node getNode()
		{
			return this.node;
		}
	}
	
	/**
	 * Construct new {@link NodeSelectionPanel}.
	 */
	public NodeSelectionPanel()
	{
		super( new GridLayout( 0, 2 ) );
		selectionMap = new HashMap<ScheduleFields, Node>();
		this.setBorder( BorderFactory.createTitledBorder("Configure XML Map") );
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
	 * Creates a {@link NodeLabel} and {@link NodeComboBox} for the 
	 *   given {@link Node} and adds them to this {@link NodeSelectionPanel} 
	 * @param node for which to add a 
	 *   {@link NodeLabel} and {@link NodeComboBox}
	 * @param depth of n in the DOM tree
	 */
	private void putNode( Node node, int depth )
	{
		NodeLabel label = new NodeLabel( node, depth );
		NodeComboBox box = new NodeComboBox( node );
		
		this.add( label );
		this.add( box );
	}
	
	/**
	 * Generates an {@link XmlDomMap} from the selection entered in
	 *   the {@link NodeSelectionPanel}.
	 * @return An {@link XmlDomMap} generated from selection map
	 */
	public XmlDomMap buildXmlDomMap() throws NullPointerException
	{
		XmlDomMap dommap = new XmlDomMap();
		Node root = selectionMap.remove( ScheduleFields.SCHEDULE_ROOT );
		dommap.setProperty( ScheduleFields.SCHEDULE_ROOT, "//"+root.getNodeName() );

		for( ScheduleFields field : selectionMap.keySet() )
		{
			dommap.setProperty( field, XmlDomExtractor.getXPath( 
					root, selectionMap.get(field) 
					) );
		}
		
		selectionMap.put( ScheduleFields.SCHEDULE_ROOT, root );
		
		return dommap;
	}

}
