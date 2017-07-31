package edu.psu.sweng500.userinterface;


import javax.swing.*;
import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import edu.psu.sweng500.type.*;
import edu.psu.sweng500.userinterface.LogScreen;
import edu.psu.sweng500.userinterface.calendar.CalendarEventPanel;
import edu.psu.sweng500.userinterface.calendar.BasgsMenuBar;
import edu.psu.sweng500.userinterface.datepicker.DatePicker;
import edu.psu.sweng500.util.UIThemeColors;

public class CalenderScreen 
{
//	private static final Font CALENDAR_FONT = new Font("Arial",Font.ITALIC,12);
	
	private static JLabel temperature;
	private static JLabel humidity;
	private static JLabel loginStatus;
	private static String currentDate;

	private static JFrame frame;
	private static JPanel eventPanel;

	private final static EventHandler eventHandler = EventHandler.getInstance();
	private final static LogScreen loginScreen = new LogScreen();
	private static boolean isAuthenticated;

	public CalenderScreen() 
	{
		eventHandler.addListener(new EventQueueListener());

		frame = new JFrame("Global Schedular System");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setMinimumSize( new Dimension(500, 500) );
		frame.setExtendedState( JFrame.MAXIMIZED_BOTH );
		frame.setSize( Toolkit.getDefaultToolkit().getScreenSize() );
		frame.setResizable( true );
		frame.setVisible( true );

		frame.getContentPane().setLayout( new BorderLayout() );
		frame.getContentPane().add( new BasgsMenuBar(), BorderLayout.NORTH );
		frame.getContentPane().add( createControlPanel(), BorderLayout.LINE_START );
		frame.getContentPane().add( createEventPanel(), BorderLayout.CENTER );
		
//		// edit event Button
//		editEventBTN = new JButton("Edit Event");
//		editEventBTN.addActionListener(editeventscreen = new EditEventScreen());

		loginScreen.create();

		frame.revalidate();
		frame.pack();
	}
	
	private JPanel createControlPanel()
	{
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 0, 10);
		
		addUserControls( leftPanel, c );
		addWeatherInfo( leftPanel, c );
		addDatePicker( leftPanel, c );
		
		// add new event button
		JButton newEvent = new JButton("New Event");
		newEvent.addActionListener( new NewEventScreen() );
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridy = 4;
		leftPanel.add( newEvent, c );
		
		// add empty vertical filler panel
		c.weighty = 1;
		c.gridy = 5;
		leftPanel.add( new JPanel(), c );
		
		return leftPanel;
	}
	
	private void addUserControls( JPanel panel, GridBagConstraints c )
	{
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weighty = 0;
		
		loginStatus = new JLabel ("User: ");
		c.gridx = 0;
		c.gridy = 0;
		panel.add( loginStatus, c );
		
		JButton logout = new JButton("Sign Out");
		logout.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JOptionPane.showMessageDialog(null, "User Signed Out");
				System.exit(0);
			}
		});
		c.weightx = 0.5;
		c.gridx = 1;
		panel.add( logout, c );
	}
	
	private void addWeatherInfo( JPanel panel, GridBagConstraints c )
	{
		c.anchor = GridBagConstraints.LINE_START;
		c.weighty = 0;
		
		JLabel tempLabel = new JLabel("Outside Temperature: ");
		c.gridx = 0;
		c.gridy = 1;
		panel.add( tempLabel, c );
		
		JLabel humidLabel = new JLabel("Humidity: ");
		c.gridy = 2;
		panel.add( humidLabel, c );
		
		temperature = new JLabel("...");
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 1;
		panel.add( temperature, c );
		
		humidity = new JLabel ("...");
		c.gridy = 2;
		panel.add( humidity, c );
	}
		
	private void addDatePicker( JPanel panel, GridBagConstraints c )
	{
		c.anchor = GridBagConstraints.BASELINE;
		c.weighty = 0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 3;
		
		DatePicker picker = new DatePicker();
		picker.addPropertyChangeListener(DatePicker.DATE_SELECTION_EVENT, new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent dateChange) 
			{
				updateEvents( (String)dateChange.getNewValue() );
			}
		});
		currentDate = picker.getDateSelection();
		panel.add( picker, c );
	}
	
	private JPanel createEventPanel()
	{
		eventPanel = new JPanel();
		eventPanel.setBorder( BorderFactory.createTitledBorder("Events") );
		eventPanel.setBackground( UIThemeColors.BG_TAN );
		eventPanel.setLayout( new BoxLayout(eventPanel, BoxLayout.PAGE_AXIS) );
		
		return eventPanel;
	}

	private static void updateEvents( String date ) 
	{
		// clear previous selection's events
		eventPanel.removeAll();
		eventPanel.revalidate();
		eventPanel.repaint();   // This is required in some cases

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = df.parse( date + " 00:00:00" );
			Date stop = df.parse( date + " 23:59:59" );
			
			System.out.println( "Start: " + start.toString() + "\t Stop: " + stop.toString() );
			
			eventHandler.fireGetEvents(start, stop);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * EventQueueListener for CalendarScreen
	 * * Updates event panel
	 * * Updates weather info
	 * * Handles user authentication
	 */
	private static class EventQueueListener extends EventAdapter 
	{
		@Override
		public void eventUpdate( ArrayList<DBScheduleTable> sList ) 
		{
			for(DBScheduleTable s : sList)
			{
				System.out.println("CalendarScreen > Schedule event update received. Schedule Name: " + s.getName());

				if (!isAuthenticated) {
					System.out.println("CalendarScreen > User is not authenticated. Exist update calendar screen.");
					return;
				}
				eventPanel.add( new CalendarEventPanel(s) );
			}
		}

		@Override
		public void weatherInfoUpdate(DBWeatherTable w) 
		{
			System.out.println("CalendarScreen > Received weather data from DB for SiteID: " + w.getSiteId() + " Temperature: " + w.getTemperature());
			temperature.setText(w.getTemperature() + " F");
			humidity.setText(w.getHumidity() + " %");
		}

		@Override
		public void authenticateUserUpdate(User u) 
		{
			System.out.println("CalendarScreen > Authentication user update received. User: " 
					+ u.getUserName() + " isAuthenicated: " + u.isAuthenticated());
			isAuthenticated = false;
			if (u.getUserName() == loginScreen.getUserName())
			{
				isAuthenticated = u.isAuthenticated();
				if (isAuthenticated) 
				{
					updateEvents( currentDate );
					loginStatus.setText("User: " + u.getUserName());
				}
			} 
		}
	}
	
}
