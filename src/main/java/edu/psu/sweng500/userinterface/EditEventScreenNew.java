package edu.psu.sweng500.userinterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.DBScheduleTable;
//<<<<<<< HEAD
//import edu.psu.sweng500.userinterface.EditEventScreen.EventQueueListener;
//=======
//>>>>>>> branch 'master' of https://github.com/abate3608/BAS-Scheduler.git
import edu.psu.sweng500.userinterface.datepicker.DatePicker;
import edu.psu.sweng500.userinterface.datepicker.TimePicker;
import edu.psu.sweng500.util.FocusHighlightedTextField;

public class EditEventScreenNew 
{
	private static JFrame frame;
	private JTextField nameField;
	private JTextField roomField;
	private JComboBox<Date> startTimeBox;
	private JTextField startDateField;
	private JComboBox<Date> endTimeBox;
	private JTextField endDateField;
	private JTextField lightField;
	private JTextField temperatureField;
	
	private JButton update;
	
	private final static EventHandler eventHandler = EventHandler.getInstance();
	public static DBScheduleTable event;
    private static EventQueueListener eql = new EventQueueListener();
	
	
	public EditEventScreenNew( DBScheduleTable s )
	{
		// setup event
		eventHandler.addListener(eql);
		
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
		
		frame.addWindowListener(getWindowAdapter());
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		//frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setVisible( true );
	}
	
	//listen to frame action and stop frame from minimizing or closing
	private WindowAdapter getWindowAdapter() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {//overrode to show message
                super.windowClosing(we);

                //JOptionPane.showMessageDialog(frame, "Cant Exit");
            }

            //cannot minimize frame
            @Override
            public void windowIconified(WindowEvent we) {
            	frame.setState(JFrame.NORMAL);
                
            }
        };
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
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(event.getStartDateTime());
		int unroundedMinutes = calendar.get(Calendar.MINUTE);
		int mod = unroundedMinutes % 15;
		calendar.set(Calendar.MINUTE, unroundedMinutes + mod);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		startTimeBox.setSelectedIndex(TimePicker.getTimePickerIndex(startTimeBox, calendar.getTime()));
		//TODO set to event time
		c.gridy += 1;
		panel.add( new JLabel( "Start Time: (yyyy-mm-dd)" ), c );
		c.gridy += 1;
		panel.add( startTimeBox, c );
		
		endTimeBox = new TimePicker();
		calendar.setTime(event.getEndDateTime());
		unroundedMinutes = calendar.get(Calendar.MINUTE);
		mod = unroundedMinutes % 15;
		calendar.set(Calendar.MINUTE, unroundedMinutes + mod);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		System.out.println("EndTime Index: " + TimePicker.getTimePickerIndex(endTimeBox, calendar.getTime()));
		endTimeBox.setSelectedIndex(TimePicker.getTimePickerIndex(endTimeBox, calendar.getTime()));
		//TODO set to event time
		c.gridy += 1;
		panel.add( new JLabel( "End Time: (yyyy-mm-dd)" ), c );
		c.gridy += 1;
		panel.add( endTimeBox, c );
		
		startDateField = new FocusHighlightedTextField();
		startDateField.setText( getDateString( event.getStartDateTime() ) );
		c.gridy += 1;
		panel.add( new JLabel( "Start Date:" ), c );
		c.gridy += 1;
		panel.add( startDateField, c );
		c.gridx += 1;
		panel.add( getDatePickerButton( startDateField ), c );
		c.gridx = 0;
		
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
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		
		JButton delete = new JButton( "Delete Event " );
		delete.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				delete();
			}
		});
		panel.add( delete );
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		update = new JButton( "Update Event" );
		update.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try {
					update();
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(frame, "Error: Invalid datetime format.");
				}
			}
		});
		panel.add( update );
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		JButton cancel = new JButton( "      Cancel      " );
		cancel.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				close();
			}
		});
		panel.add( cancel );
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		return panel;
	}
	
	private boolean verifyFields() {
		if(verifyTextField(nameField) && verifyDateField(startDateField) &&
				verifyDateField(endDateField) && verifyTextField(roomField) &&
				verifyIntegerField(lightField) && verifyFloatField(temperatureField)) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean verifyTextField(JTextField field) {
		if (field.getText().equals("")){
		   JOptionPane.showMessageDialog(null,"Error: A required Field is empty, Please complete all fields!");
	       return false;
	     }
	     else {
	       return true;
	     }
	}
		
	private boolean verifyFloatField(JTextField field) {
		if (field.getText().equals("")){
		   JOptionPane.showMessageDialog(null,"Error: A required Field is empty, Please complete all fields!");
	       return false;
	     } else {
	        try {
		        Float.parseFloat(field.getText());
		        return true;
		     } catch (NumberFormatException e) {
		    	 JOptionPane.showMessageDialog(null,"Error: Must enter a number!");
		    	 return false;
			 }
	       
	    }
	}
	
	private boolean verifyIntegerField(JTextField field) {
		if (field.getText().equals("")){
		   JOptionPane.showMessageDialog(null,"Error: A required Field is empty, Please complete all fields!");
	       return false;
	     } else {
	        try {
		        Integer.parseInt(field.getText());
		        return true;
		     } catch (NumberFormatException e) {
		    	 JOptionPane.showMessageDialog(null,"Error: Must enter a number!");
		    	 return false;
			 }
	       
	    }
	}
		
	private boolean verifyDateField(JTextField field) {
		if (field.getText().equals("")){
		   JOptionPane.showMessageDialog(null,"Error: A required Field is empty, Please complete all fields!");
	       return false;
	     } else {
	    	 String test = "2017-08-14";
	    	 String format = "yyyy-MM-dd";
	    	 SimpleDateFormat sdf = new SimpleDateFormat(format);
	    	 sdf.setLenient(false);
	    	 try {
	    	     Date date = sdf.parse(test);
	    	     if (!sdf.format(date).equals(test)) {
	    	    	 System.out.println(test + " is not a valid format for " + format);
	    	         throw new ParseException(test + " is not a valid format for " + format, 0);
	    	     } else {
	    	    	 return true;
	    	     }
	    	 } catch (ParseException ex) {
	    		 JOptionPane.showMessageDialog(null,"Error: Must enter proper date format (yyyy-mm-dd)!");
	    		 return false;
	    	 }
	       
	    }
	}
	
	private String getDateString( Date datetime )
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format( datetime );
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
	
	public static void close() {
		eventHandler.removeListener(eql);
		frame.dispose();
		frame.setVisible(false);
	}
	
	private void update() throws ParseException
	{
		if(verifyFields()) {
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
		}
	}
	
	private void delete() {
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you would like to delete this event!","Warning",dialogButton);
		if(dialogResult == JOptionPane.YES_OPTION){
			eventHandler.fireDeleteEvent(event);
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
	
	static class EventQueueListener extends EventAdapter {
		// listen to event queue
		
		@Override
		public void updateEventRespond(DBScheduleTable s, int err) {
			if(s != null) {
				if(s.getRowGuid().equals(event.getRowGuid())){
					switch(err){
						case 0:
							close();
							break;
						case 1:
							JOptionPane.showMessageDialog(null,"Error: Database Issue!");
							break;
						case 2:
							JOptionPane.showMessageDialog(null,"Error: Event to update not found!");
							break;
						case 4:
							//JOptionPane.showMessageDialog(null,"Error: Invalid date/time selected!");
							break;
						case 5:
							//JOptionPane.showMessageDialog(null,"Error: Time Conflict with Room.");
							break;
						default:
							System.out.println("Unknown Error!");
							break;
					}
				}
			}
		}
		
		@Override
		public void deleteEventRespond(DBScheduleTable s, int err) {
			if(s != null) {
				if(s.getRowGuid().equals(event.getRowGuid())){
					switch(err){
						case 0:
							close();
							break;
						default:
							break;
					}
				}
			}
		}
	}

}
