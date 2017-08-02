package edu.psu.sweng500.userinterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.DBScheduleTable;
import edu.psu.sweng500.userinterface.EditEventScreen.EventQueueListener;
import edu.psu.sweng500.userinterface.datepicker.DatePicker;
import edu.psu.sweng500.userinterface.datepicker.TimePicker;
import edu.psu.sweng500.util.FocusHighlightedTextField;

public class EditEventScreenNew 
{
	private JFrame frame;
	private JTextField nameField;
	private JTextField roomField;
	private JComboBox<Date> startTimeBox;
	private JTextField startDateField;
	private JComboBox<Date> endTimeBox;
	private JTextField endDateField;
	private JTextField lightField;
	private JTextField temperatureField;
	
	private final EventHandler eventHandler = EventHandler.getInstance();
	private DBScheduleTable event;
	
	public EditEventScreenNew( DBScheduleTable s )
	{
		// setup event
		eventHandler.addListener(new EventQueueListener());
		
		this.event = s;
		frame = new JFrame();
		frame.setTitle( "Global Schedular System Edit Event" );
		frame.setSize( new Dimension( 300, 400 ) );
		frame.setLocationRelativeTo( null );
		frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		frame.setLayout( new BorderLayout() );
		
		frame.getContentPane().add( getMainPanel(), BorderLayout.CENTER );
		frame.getContentPane().add( getButtonPanel(), BorderLayout.PAGE_END );
		frame.pack();
		frame.setVisible( true );
	}
	
	private JPanel getMainPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets( 0, 10, 0, 10 );
		c.weightx = 0.5;
		
		nameField = new FocusHighlightedTextField();
		nameField.setText( event.getName() );
		c.gridx = 0; c.gridy = 0;
		panel.add( new JLabel( "Event Name:" ), c );
		c.gridy += 1;
		panel.add( nameField, c );
		
		startTimeBox = new TimePicker();
		//TODO set to event time
		c.gridy += 1;
		panel.add( new JLabel( "Start Time:" ), c );
		c.gridy += 1;
		panel.add( startTimeBox, c );
		
		startDateField = new FocusHighlightedTextField();
		startDateField.setText( getDateString( event.getStartDateTime() ) );
		c.gridy += 1;
		panel.add( new JLabel( "Start Date:" ), c );
		c.gridy += 1;
		panel.add( startDateField, c );
		c.gridx += 1;
		panel.add( getDatePickerButton( startDateField ), c );
		c.gridx = 0;
		
		endTimeBox = new TimePicker();
		//TODO set to event time
		c.gridy += 1;
		panel.add( new JLabel( "End Time:" ), c );
		c.gridy += 1;
		panel.add( endTimeBox, c );
		
		endDateField = new FocusHighlightedTextField();
		endDateField.setText( getDateString( event.getEndDateTime() ) );
		c.gridy += 1;
		panel.add( new JLabel( "End Date:" ), c );
		c.gridy += 1;
		panel.add( endDateField, c );
		c.gridx += 1;
		panel.add( getDatePickerButton( endDateField ), c );
		c.gridx = 0;
		
		roomField = new FocusHighlightedTextField();
		roomField.setText( event.getRoomName() );
		c.gridy += 1;
		panel.add( new JLabel( "Room Name:" ), c );
		c.gridy += 1;
		panel.add( roomField, c );
		
		lightField = new FocusHighlightedTextField();
		lightField.setText( ""+event.getLightIntensity() );
		c.gridy += 1;
		panel.add( new JLabel( "Lighting Intensity:" ), c );
		c.gridy += 1;
		panel.add( lightField, c );
		
		temperatureField = new FocusHighlightedTextField();
		temperatureField.setText( ""+event.getTemperatureSetpoint() );
		c.gridy += 1;
		panel.add( new JLabel( "Temperature Setpoint:" ), c );
		c.gridy += 1;
		panel.add( temperatureField, c );
		
		return panel;
	}
	
	private JPanel getButtonPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout( new GridLayout( 3, 1 ) );
		
		JButton delete = new JButton( "Delete Event" );
		delete.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				delete();
			}
		});
		panel.add( delete );
		
		JButton update = new JButton( "Update Event" );
		update.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try {
					update();
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(frame, "Error: Invalid datetime format. Could not parse datetime format.");
				}
			}
		});
		panel.add( update );
		
		JButton cancel = new JButton( "Cancel" );
		cancel.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				frame.dispose();
			}
		});
		panel.add( cancel );
		
		return panel;
	}
	
	private String getDateString( Date datetime )
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format( datetime );
	}
	
	private String getHourString( Date datetime )
	{
		DateFormat hf = new SimpleDateFormat( "HH:mm:ss" );
		return hf.format( datetime );
	}
	
	private JButton getDatePickerButton( JTextField field )
	{
		JButton button = new JButton("Choose date");
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				field.setText( DatePicker.showDatePickerDialog() );
			}
		});
		return button;
	}
	
	private void update() throws ParseException
	{
		DateFormat hr = new SimpleDateFormat( "HH:mm:ss" );
		DateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		Date startDateTime = df.parse(startDateField.getText() + " " + hr.format(startTimeBox.getSelectedItem()));
		Date endDateTime = df.parse(endDateField.getText() + " " + hr.format(endTimeBox.getSelectedItem()));
		
		event.setName( nameField.getText() );
		event.setStartDateTime( startDateTime );
		event.setEndDateTime( endDateTime );
		event.setRoomName( roomField.getText() );
		event.setLightIntensity( Integer.parseInt( lightField.getText() ) );
		event.setTemperatureSetpoint( Float.parseFloat( temperatureField.getText() ) );
		
		eventHandler.fireUpdateEvent(event);
		frame.dispose();
		
		//TODO send event
	}
	
	private void delete() {
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you would like to delete this event!","Warning",dialogButton);
		if(dialogResult == JOptionPane.YES_OPTION){
			eventHandler.fireDeleteEvent(event);
			frame.dispose();
		}
	}
	
	/**
	 * Main method intended for functional testing only.
	 * @param args
	 * @throws ParseException
	 */
	public static void main( String[] args ) throws ParseException
	{
		DBScheduleTable s = new DBScheduleTable();
		s.setName( "Mock event" );
		DateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		s.setStartDateTime( df.parse( "2017-07-31 08:00:00" ) );
		s.setEndDateTime( df.parse( "2017-07-31 09:30:00" ) );
		s.setRoomName( "Any" );
		s.setLightIntensity( 100 );
		s.setTemperatureSetpoint( 72.0F );
		
		new EditEventScreenNew( s );
	}

}
