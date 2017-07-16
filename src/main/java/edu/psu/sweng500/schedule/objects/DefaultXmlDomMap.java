package edu.psu.sweng500.schedule.objects;

import java.io.IOException;

/**
 * By default, {@link DefaultXmlDomMap} loads the defaultXmlDomMap.properties file
 * from the classpath upon construction. 
 * <br><br>
 * To load a different XmlDomMap properties file configuration, use the {@link XmlDomMap} 
 * superclass instead.
 * @author awb
 */
public class DefaultXmlDomMap extends XmlDomMap 
{
	/** default */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new instance of {@link DefaultXmlDomMap}.
	 * @throws IOException if defaultXmlDomMap.properties is
	 * absent from the classpath
	 */
	public DefaultXmlDomMap() throws IOException
	{
		this.load( this.getClass().getResourceAsStream( 
				"/defaultXmlDomMap.properties" 
				) );
	}
}
