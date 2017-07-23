package edu.psu.sweng500.schedule.objects;

import java.io.IOException;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Extracts the DOM structure from an XML document. 
 * @author awb
 */
public class XmlDomExtractor
{
	private static final DocumentBuilderFactory FACTORY = DocumentBuilderFactory.newInstance();
	
	/**
	 * Extract the DOM structure from the given XML document.
	 * @param document {@link Document} representing the external XML document.
	 * @return the DOM structure {@link Document}
	 * @throws ParserConfigurationException
	 */
	public static Document extractFromDocument( Document document ) throws ParserConfigurationException
	{
		Document structure = FACTORY.newDocumentBuilder().newDocument();
		extract( structure, document );
		return structure;
	}
	
	/**
	 * Builds the DOM mapping structure from an external XML document.
	 * @param structure {@link Document} representing the mapping structure
	 * @param document {@link Document} representing the external XML document
	 */
	private static void extract( Document structure, Document document )
	{
		Node n = document.getChildNodes().item( 0 );
		Node m = structure.createElement( n.getNodeName() );
		structure.appendChild( m );
		traverseNodes( structure, m, n.getChildNodes() );
	}
	
	/**
	 * Recursively builds the mapping structure for an external XML document.
	 * @param structure {@link Document} representing the mapping structure
	 * @param current current {@link Node} of the mapping structure
	 * @param nodes {@link NodeList} from the external XML 
	 */
	private static void traverseNodes( Document structure, Node current, NodeList nodes )
	{
		for( int i = 0; i < nodes.getLength(); i++ )
		{
			Node m = current;
			Node n = nodes.item( i ); 
			if( n instanceof Element && structure.getElementsByTagName( n.getNodeName() ).getLength() == 0 )
			{
				m = structure.createElement( n.getNodeName() );
				current.appendChild( m );
				getAttributesAsElements( structure, (Element) m, n.getAttributes() );
			}
			traverseNodes( structure, m, n.getChildNodes() );
		}
	}
	
	/**
	 * Helper method for getting attributes from a {@link Node} in an external XML document
	 * and adding them as child {@link Element}s to the mapping structure. 
	 * @param structure {@link Document} representing the mapping structure
	 * @param parent the parent {@link Element} of the mapping structure
	 * @param attributes the {@link NamedNodeMap} of attributes of the external XML {@link Element}
	 */
	private static void getAttributesAsElements( Document structure, Element parent, NamedNodeMap attributes )
	{
		if( attributes != null )
		{
			for( int a = 0; a < attributes.getLength(); a++ )
			{
				parent.setAttributeNode( structure.createAttribute( attributes.item( a ).getNodeName() ) );
			}
		}
	}
	
	/**
	 * Writes the XML {@link Document} to the given {@link OutputStream}.
	 * @param document the {@link Document} to write
	 * @param osw the {@link OutputStream} to write to
	 * @return the {@link OutputStream}
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static OutputStream writeDocumentToStream( Document document, OutputStream osw ) 
			throws IOException, TransformerException 
	{
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	    transformer.transform(new DOMSource( document ), new StreamResult( osw ));
	    
	    return osw;
	}
	
	/**
	 * Produces the XPath expression for a given {@link Node}
	 * @param node the {@link Node} to find the XPath expression for
	 * @return {@link String} representation of the XPath expression
	 */
	public static String getXPath( Node node ) 
	{
	    Node parent = node.getParentNode();
	    if( node instanceof Attr )
	    {
	    	return getXPath( ((Attr) node).getOwnerElement() ) + "/@" + node.getNodeName();
	    }
	    else if( parent == null || parent instanceof Document ) 
	    {
	        return "/" + node.getNodeName();
	    }
	    return getXPath( parent ) + "/" + node.getNodeName();
	}
	
	/**
	 * Produces the XPath expression for a given {@link Node} 
	 * relative to a given parent or root {@link Node}
	 * @param root the parent or root {@link Node}
	 * @param node the {@link Node} to find the XPath expression for
	 * @return {@link String} representation of the XPath expression
	 */
	public static String getXPath( Node root, Node node )
	{
		if( node instanceof Attr )
		{
			return getXPath( root, ((Attr) node).getOwnerElement() ) + "/@" + node.getNodeName(); 
		}
		if( root.getNodeName().equals( node.getNodeName() ) )
		{
			return "./";
		}
		return getXPath( root, node.getParentNode() ) + "/" + node.getNodeName();
	}
}
