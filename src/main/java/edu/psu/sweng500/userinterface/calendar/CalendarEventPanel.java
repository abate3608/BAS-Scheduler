package edu.psu.sweng500.userinterface.calendar;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.DBScheduleTable;
import edu.psu.sweng500.userinterface.EditEventScreenNew;
import edu.psu.sweng500.userinterface.Graph;
import edu.psu.sweng500.util.UIThemeColors;

/**
 * Specialized {@link JPanel} for scheduled events. 
 * @author awb
 */
public class CalendarEventPanel extends JPanel 
{
	/** default */
	private static final long serialVersionUID = 1L;
	
	public DBScheduleTable schedule;
	public static DBScheduleTable event;
	// Event listeners
    private static final EventHandler eventHandler = EventHandler.getInstance();
	
	public CalendarEventPanel( DBScheduleTable s )
	{
		
		TitledBorder border = BorderFactory.createTitledBorder("Room: " + s.getRoomName() + " || Meeting Name: " + s.getName() );
		border.setTitleColor( UIThemeColors.CALENDAR_DARK_BLUE );
		this.setBorder( border );
		this.setBackground( UIThemeColors.CALENDAR_BLUE );
		this.setLayout( new BoxLayout(this, BoxLayout.LINE_AXIS) );
		this.schedule = s;
		//this.event = s;
		
		this.add( getEventDetails() );
		this.add( getEditButton() );
		this.add( getDeleteButton() );
		this.add( getOptSchedButton());
	}
	
	/**
	 * Creates a {@link JLabel} containing the event details.
	 * Uses HTML for formatting.
	 * @return the {@link JLabel}
	 */
	private JLabel getEventDetails()
	{
		DateFormat df = new SimpleDateFormat( "dd/MM/yy H:mm" );
		StringBuffer buffer = new StringBuffer( "<html>" );
		buffer.append( df.format(schedule.getStartDateTime()) );
		buffer.append( "&mdash;" );
		buffer.append( df.format(schedule.getEndDateTime()) );
		buffer.append( "<br>" );
		buffer.append( schedule.getDescription() );
		buffer.append( "</hmtl>" );
		
		JLabel details = new JLabel(buffer.toString());
		details.setFont( new Font( "Arial", Font.PLAIN, 14 ) );
		details.setForeground( UIThemeColors.CALENDAR_DARK_BLUE );
		
		return details;
	}
	
	/**
	 * Creates the edit event button.
	 * @return the {@link JButton}
	 */
	private JButton getEditButton()
	{
		JButton edit = new JButton( "Edit" );
		edit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent e ) 
			{
				new EditEventScreenNew( schedule );
			}
		});
		return edit;
	}
	
	/**
	 * Creates the delete event button.
	 * @return the {@link JButton}
	 */
	private JButton getDeleteButton()
	{
		JButton delete = new JButton( "Delete" );
		delete.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent e ) 
			{
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you would like to delete this event!","Warning",dialogButton);
				if(dialogResult == JOptionPane.YES_OPTION){
					eventHandler.fireDeleteEvent(schedule);
				}
			}
		});
		return delete;
	}
	
	/**
	 * Creates the edit event button.
	 * @return the {@link JButton}
	 */
	private JButton getOptSchedButton()
	{
		JButton btn = new JButton( "Opt Sched" );
		btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent e ) 
			{
				Executors.newCachedThreadPool().execute(new Runnable() {
					@Override
					public void run() {
						new Graph( schedule.getRoomName() );
					}
				});
				
			}
		});
		return btn;
	}
	
	public EventHandler getEventHandler() {
		return eventHandler;
	}
	
	
}
