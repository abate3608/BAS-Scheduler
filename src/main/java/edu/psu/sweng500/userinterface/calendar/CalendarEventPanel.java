package edu.psu.sweng500.userinterface.calendar;

import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import edu.psu.sweng500.type.DBScheduleTable;
import edu.psu.sweng500.util.UIThemeColors;

public class CalendarEventPanel extends JPanel 
{
	/** default */
	private static final long serialVersionUID = 1L;
	
	private DBScheduleTable schedule;
	
	public CalendarEventPanel( DBScheduleTable s )
	{
		TitledBorder border = BorderFactory.createTitledBorder( s.getName() );
		border.setTitleColor( UIThemeColors.CALENDAR_DARK_BLUE );
		this.setBorder( border );
		this.setBackground( UIThemeColors.CALENDAR_BLUE );
		this.setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS) ); 
		this.schedule = s;
		
		DateFormat df = new SimpleDateFormat( "dd/MM/yy H:mm" );
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer buffer = new StringBuffer( "<html>" );
		buffer.append( df.format(s.getStartDateTime()) );
		buffer.append( "&mdash;" );
		buffer.append( df.format(s.getEndDateTime()) );
		buffer.append( "<br>" );
		buffer.append( s.getDescription() );
		buffer.append( "</hmtl>" );
		
		JLabel details = new JLabel(buffer.toString());
		details.setFont( new Font( "Arial", Font.PLAIN, 14 ) );
		details.setForeground( UIThemeColors.CALENDAR_DARK_BLUE );
		this.add( details );
	}
	
}
