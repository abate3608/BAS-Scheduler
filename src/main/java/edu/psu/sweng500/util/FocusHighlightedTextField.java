package edu.psu.sweng500.util;

import java.awt.event.FocusListener;

import javax.swing.JTextField;

import edu.psu.sweng500.userinterface.FocusHighlighter;

/**
 * Convenience class
 * @author awb
 */
public class FocusHighlightedTextField extends JTextField 
{
	/** default */
	private static final long serialVersionUID = 1L;
	private static final FocusListener HIGHLIGHTER = new FocusHighlighter();
	
	public FocusHighlightedTextField()
	{
		this.addFocusListener( HIGHLIGHTER );
	}

}
