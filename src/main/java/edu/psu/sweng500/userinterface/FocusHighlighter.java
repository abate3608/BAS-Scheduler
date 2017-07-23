package edu.psu.sweng500.userinterface;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.UIManager;

public class FocusHighlighter implements FocusListener 
{
	@Override
	public void focusGained(FocusEvent e) {
		e.getComponent().setBackground( new Color(248, 242, 236) );
	}

	@Override
	public void focusLost(FocusEvent e) {
		e.getComponent().setBackground(UIManager.getColor("TextField.background"));
	}

}
