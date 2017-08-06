package edu.psu.sweng500.userinterface;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.*;
import edu.psu.sweng500.userinterface.datepicker.DatePicker;
import edu.psu.sweng500.userinterface.datepicker.TimePicker;

public class NewEventScreen implements ActionListener {

	private static JFrame newEventWin;
	private JPanel newEventPane;
	private JLabel eventName;
	private JLabel eventStartTime;
	private JLabel eventEndTime;
	private JLabel eventDate;
	private JLabel eventRoom;
	private JLabel endDate;
	private JLabel lightSetting;
	private JLabel temperatureSetting;
	private JTextField eventNameTXT;
	private JTextField eventDateTXT;
	private JTextField eventRoomText;
	private JTextField lightSettingTXT;
	private JTextField temperatureSettingTXT;
	private JTextField endEventDateTXT;
	//private JButton startDateBtn;
	private JButton newEventButton;
	private JButton cancelButton;
	private TimePicker timeStart;
	private TimePicker timeEnd;
	
	
	private static final FocusListener HIGHLIGHTER = new FocusHighlighter();

	// Event listeners
	private final static EventHandler eventHandler = EventHandler.getInstance();
	private static EventQueueListener eql = new EventQueueListener();

	public void actionPerformed(ActionEvent e) {

		// setup event
		eventHandler.addListener(eql);


		newEventWin = new JFrame("Global Schedular System New Event");
		newEventWin.setSize(360, 555);
		newEventWin.setLocationRelativeTo(null);
		//newEventWin.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//newEventWin.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		newEventWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		newEventPane = new JPanel();
		newEventWin.add(newEventPane);
		newuserLayout(newEventPane);

		newEventWin.addWindowListener(getWindowAdapter());
		newEventWin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		//newEventWin.setAlwaysOnTop(true);
		newEventWin.setResizable(false);
		newEventWin.setVisible(true);
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
	            	newEventWin.setState(JFrame.NORMAL);
	                
	            }
	        };
	    }
	private final void newuserLayout(JPanel newEventPanel) {

		newEventPanel.setLayout(null);
		newEventPanel.setBorder(BorderFactory.createTitledBorder("Schedule a New Event"));

		eventName = new JLabel("Event Name:");
		eventName.setBounds(20, 25, 90, 25);
		//eventName.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(eventName);

		eventNameTXT = new JTextField(20);
		eventNameTXT.setBounds(20, 50, 300, 25);
		newEventPanel.add(eventNameTXT);
		eventNameTXT.addKeyListener(new EnterButtonPress());
		eventNameTXT.addFocusListener(HIGHLIGHTER);

		eventStartTime = new JLabel("Start Time:");
		eventStartTime.setBounds(20, 75, 120, 25);
		//eventStartTime.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(eventStartTime);

//		eventStartTimeTXT = new JTextField(); // CHANGED 
//		eventStartTimeTXT.setBounds(20, 100, 160, 25);
//		eventStartTimeTXT.setColumns(10);
//		newEventPanel.add(eventStartTimeTXT);
//		eventStartTimeTXT.addKeyListener(new EnterButtonPress());
//		eventStartTimeTXT.addFocusListener(HIGHLIGHTER);
		
		timeStart = new TimePicker();
		timeStart.setBounds(20, 100, 160, 25); //Location
		 newEventPanel.add(timeStart);
		
//		startDateBtn = new JButton("New button");
//		startDateBtn.setBounds(20, 100, 50, 25); // NEDDS CHANGEDE
//		newEventPanel.add(startDateBtn);

		eventEndTime = new JLabel("End Time:");
		eventEndTime.setBounds(20, 125, 120, 25);
		//eventEndTime.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(eventEndTime);

//		eventEndTimeTXT = new JTextField(20);
//		eventEndTimeTXT.setBounds(20, 150, 160, 25);
//		newEventPanel.add(eventEndTimeTXT);
//		eventEndTimeTXT.addKeyListener(new EnterButtonPress());
//		eventEndTimeTXT.addFocusListener(HIGHLIGHTER);

		timeEnd = new TimePicker();
		timeEnd.setBounds(20, 150, 160, 25); //Location
		 newEventPanel.add(timeEnd);
		
		eventDate = new JLabel("Start Date: (yyyy-mm-dd)");
		eventDate.setBounds(20, 175, 160, 25);
		//eventDate.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(eventDate);

		eventDateTXT = new JTextField(20);
		eventDateTXT.setBounds(20, 200, 160, 25);
		newEventPanel.add(eventDateTXT);
		eventDateTXT.addKeyListener(new EnterButtonPress());
		eventDateTXT.addFocusListener(HIGHLIGHTER);

		//create button and there object
		JButton dateButton = new JButton("Start Date");
		dateButton.setBounds(215, 200, 100, 25);
		newEventPanel.add(dateButton);
		//perform action listener
		dateButton.addActionListener(new StartDateButtonPress()) ;

		
		endDate = new JLabel("End Date: (yyyy-mm-dd)");
		endDate.setBounds(20, 225, 160, 25);
		//endDate.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(endDate);
		
		endEventDateTXT = new JTextField(20);
		endEventDateTXT.setBounds(20, 250, 160, 25);
		newEventPanel.add(endEventDateTXT);
		endEventDateTXT.addKeyListener(new EnterButtonPress());
		endEventDateTXT.addFocusListener(HIGHLIGHTER);
		
		//create button and there object
		JButton enddateButton = new JButton("End Date");
		enddateButton.setBounds(215, 250, 100, 25);
		newEventPanel.add(enddateButton);
		//perform action listener
		enddateButton.addActionListener(new EndDateButtonPress()) ;
		

		eventRoom = new JLabel("Event Room");
		eventRoom.setBounds(20, 275, 160, 25);
		//eventRoom.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(eventRoom);

		eventRoomText = new JTextField(20);
		eventRoomText.setBounds(20, 300, 160, 25);
		newEventPanel.add(eventRoomText);
		eventRoomText.addKeyListener(new EnterButtonPress());
		eventRoomText.addFocusListener(HIGHLIGHTER);

		lightSetting = new JLabel("Lighting Intensity:");
		lightSetting.setBounds(20, 325, 160, 25);
		//lightSetting.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(lightSetting);

		lightSettingTXT = new JTextField(20);
		lightSettingTXT.setBounds(20, 350, 160, 25);
		newEventPanel.add(lightSettingTXT);
		lightSettingTXT.addKeyListener(new EnterButtonPress());
		lightSettingTXT.addFocusListener(HIGHLIGHTER);

		temperatureSetting = new JLabel("Temperature Setpoint:");
		temperatureSetting.setBounds(20, 375, 160, 25);
		//temperatureSetting.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(temperatureSetting);

		temperatureSettingTXT = new JTextField(20);
		temperatureSettingTXT.setBounds(20, 400, 160, 25);
		newEventPanel.add(temperatureSettingTXT);
		temperatureSettingTXT.addKeyListener(new EnterButtonPress());
		temperatureSettingTXT.addFocusListener(HIGHLIGHTER);

		newEventButton = new JButton("Submit Request");
		newEventButton.setBounds(30, 430, 140, 25);
		newEventPanel.add(newEventButton);
		newEventButton.addActionListener(new SubmitEvent());

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(30, 460, 140, 25);
		newEventPanel.add(cancelButton);
		cancelButton.addActionListener(new cancelButtonPress());

	}

	//Date Picker
	public final class StartDateButtonPress implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {

			//set text which is collected by date picker i.e. set date 
			eventDateTXT.setText( DatePicker.showDatePickerDialog() );
			
		}

	}
	
	public final class EndDateButtonPress implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {

			//set text which is collected by date picker i.e. set date 
			endEventDateTXT.setText( DatePicker.showDatePickerDialog() );
			
		}

	}

	// Cancel Button
	private final class cancelButtonPress implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			eventNameTXT.setText(null);
			//eventStartTimeTXT.setText(null);
		//	eventEndTimeTXT.setText(null);
			eventDateTXT.setText(null);
			endEventDateTXT.setText(null);
			eventRoomText.setText(null);
			lightSettingTXT.setText(null);
			temperatureSettingTXT.setText(null);
			close();
		}
	}


	public void createEvent(){

		if(verifyFields()) {
			DateFormat clockStart = new SimpleDateFormat("HH:mm:ss");
			DateFormat clockEnd = new SimpleDateFormat("HH:mm:ss");
			
			String eventName = eventNameTXT.getText(); 
			String startTime = clockStart.format((Date)timeStart.getSelectedItem());
			String endTime= clockEnd.format((Date)timeEnd.getSelectedItem());
			String eventDate = eventDateTXT.getText(); 
			String endeventDate = endEventDateTXT.getText();
			String eventRoom = eventRoomText.getText(); 
			String lightIntensity = lightSettingTXT.getText(); 
			String temperatureSetpoint = temperatureSettingTXT.getText(); 


			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); /// TODO REMOVE SECONDS

			try{
				Date startDateTime = df.parse(eventDate + " " + startTime);
				Date endDateTime = df.parse(endeventDate + " " + endTime); /// ADD END DATE NOT IMPLEMENTD



				//if (tempSetting == null) {
				//tempSetting = "72";
				//''}

				//if (lightSetting == null) {
				//lightSetting = "100";
				//}
				DBScheduleTable s = new DBScheduleTable();
				s.setName(eventName);
				s.setDescription(" ");

				//end to match Schedule Event Data type

				s.setStartDateTime(startDateTime);
				s.setEndDateTime(endDateTime);
				s.setRoomName(eventRoom);
				s.setTemperatureSetpoint(Integer.parseInt(temperatureSetpoint));
				s.setLightIntensity(Integer.parseInt(lightIntensity));
				eventHandler.fireCreateEvent(s);
			//	JOptionPane.showMessageDialog(null, "Submitting New Event Request");

			}
			catch(ParseException e){

				JOptionPane.showMessageDialog(null,"Date entered is not the correct format"); 
			}

		}
	}
	
	private boolean verifyFields() {
		if(verifyTextField(eventNameTXT) && verifyDateField(eventDateTXT) &&
				verifyDateField(endEventDateTXT) && verifyTextField(eventRoomText) &&
				verifyIntegerField(lightSettingTXT) && verifyFloatField(temperatureSettingTXT)) {
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
		   JOptionPane.showMessageDialog(null,"Error: Complete form!");
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
		   JOptionPane.showMessageDialog(null,"Error: Complete form!");
	       return false;
	     } else {
	    	 String test = "2017-08-14";
	    	 String format = "yyyy-MM-dd";
	    	 SimpleDateFormat sdf = new SimpleDateFormat(format);
	    	 sdf.setLenient(false);
	    	 try {
	    	     Date date = sdf.parse(test);
	    	     if (!sdf.format(date).equals(test)) {
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
	
	///////////////////////////////////////////////////////////////////////////////////////// ADDED 6/23/2017

	private final class SubmitEvent implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			createEvent();
		}

	}
	//Enter Button Press
	private final class EnterButtonPress extends KeyAdapter {

		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode()== KeyEvent.VK_ENTER) {

				createEvent();
			}
		}
	}


	public EventHandler getEventHandler() {
		return eventHandler;
	}
	
	public static void close() {
		eventHandler.removeListener(eql);
		newEventWin.setVisible(false);
	}

	static class EventQueueListener extends EventAdapter {
		// listen to event queue

		@Override
		public void createEventRespond(DBScheduleTable s, int err) {
			System.out.println("NewEventScreen > Create event respond received. Name: " + s.getName() + " Error Code:" + err);
			switch( err ) {
				case 0:
					JOptionPane.showMessageDialog(null,"Event is Scheduled");
					close();
					break;
				case 1:
					JOptionPane.showMessageDialog(null,"Error: Database Connection Issue!");
					break;
				case 2:
					JOptionPane.showMessageDialog(null,"Error: Event already exists!");
					break;
				case 3:
					//JOptionPane.showMessageDialog(null,"Error: Invalid date/time selected!");
					break;
				case 5:
					JOptionPane.showMessageDialog(null,"Error: Time Conflict with Room.");
					break;
				default:
					newEventWin.setVisible(true);
						break;			
			}
			
		
		}
	}


}


