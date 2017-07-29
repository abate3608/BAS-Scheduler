package edu.psu.sweng500.userinterface;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
	private final EventHandler eventHandler = EventHandler.getInstance();

	public void actionPerformed(ActionEvent e) {

		// setup event
		eventHandler.addListener(new EventQueueListener());


		newEventWin = new JFrame("Global Schedular System New Event");
		newEventWin.setSize(360, 555);
		newEventWin.setLocationRelativeTo(null);
		//newEventWin.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//newEventWin.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		newEventWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		newEventPane = new JPanel();
		newEventWin.add(newEventPane);
		newuserLayout(newEventPane);

		newEventWin.setVisible(true);
	}

	private final void newuserLayout(JPanel newEventPanel) {

		newEventPanel.setLayout(null);
		newEventPanel.setBorder(BorderFactory.createTitledBorder("Schedule a New Event"));

		eventName = new JLabel("Event Name");
		eventName.setBounds(20, 25, 90, 25);
		eventName.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(eventName);

		eventNameTXT = new JTextField(20);
		eventNameTXT.setBounds(20, 50, 300, 25);
		newEventPanel.add(eventNameTXT);
		eventNameTXT.addKeyListener(new EnterButtonPress());
		eventNameTXT.addFocusListener(HIGHLIGHTER);

		eventStartTime = new JLabel("Event Start Time");
		eventStartTime.setBounds(20, 75, 120, 25);
		eventStartTime.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
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

		eventEndTime = new JLabel("Event End Time");
		eventEndTime.setBounds(20, 125, 120, 25);
		eventEndTime.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(eventEndTime);

//		eventEndTimeTXT = new JTextField(20);
//		eventEndTimeTXT.setBounds(20, 150, 160, 25);
//		newEventPanel.add(eventEndTimeTXT);
//		eventEndTimeTXT.addKeyListener(new EnterButtonPress());
//		eventEndTimeTXT.addFocusListener(HIGHLIGHTER);

		timeEnd = new TimePicker();
		timeEnd.setBounds(20, 150, 160, 25); //Location
		 newEventPanel.add(timeEnd);
		
		eventDate = new JLabel("Start Date of Event");
		eventDate.setBounds(20, 175, 160, 25);
		eventDate.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
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

		
		endDate = new JLabel("End Date of Event");
		endDate.setBounds(20, 225, 160, 25);
		endDate.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
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
		eventRoom.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(eventRoom);

		eventRoomText = new JTextField(20);
		eventRoomText.setBounds(20, 300, 160, 25);
		newEventPanel.add(eventRoomText);
		eventRoomText.addKeyListener(new EnterButtonPress());
		eventRoomText.addFocusListener(HIGHLIGHTER);

		lightSetting = new JLabel("Light Setting");
		lightSetting.setBounds(20, 325, 160, 25);
		lightSetting.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(lightSetting);

		lightSettingTXT = new JTextField(20);
		lightSettingTXT.setBounds(20, 350, 160, 25);
		newEventPanel.add(lightSettingTXT);
		lightSettingTXT.addKeyListener(new EnterButtonPress());
		lightSettingTXT.addFocusListener(HIGHLIGHTER);

		temperatureSetting = new JLabel("Temperature Setting");
		temperatureSetting.setBounds(20, 375, 160, 25);
		temperatureSetting.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
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
			newEventWin.dispose();
		}
	}


	public void createEvent(){

		if(eventNameTXT.getText().isEmpty() || eventDateTXT.getText().isEmpty() || eventRoomText.getText().isEmpty() || lightSettingTXT.getText().isEmpty() 
				|| temperatureSettingTXT.getText().isEmpty()){
			JOptionPane.showMessageDialog(null,"A required Field is empty, Please complete all fields"); 

			newEventWin.setVisible(true); 

		}else{

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

	static class EventQueueListener extends EventAdapter {
		// listen to event queue

		@Override
		public void createEventRespond(DBScheduleTable s, int err) {
			System.out.println("NewEventScreen > Create event respond received. Name: " + s.getName() + " Error Code:" + err);
			if (err == 0) //good
			{
				//new CalenderScreen();
				JOptionPane.showMessageDialog(null,"Event Scheduled");
				newEventWin.dispose();

			}	
			/*		
			 else if (err == 1){

			//DO SOMETHING: user user created
			JOptionPane.showMessageDialog(null,"Start Time Conflict, Please Provide a different Start Time");
			}	

			else if (err == 2) {

					//DO SOMETHING: user user created
			JOptionPane.showMessageDialog(null,"End Time Conflict, Please Provide a different End Time");

			} 

			else if (err == 3){

			//DO SOMETHING: user user created
			JOptionPane.showMessageDialog(null,"Room Conflict, The room that is selected is occupied during this time. Please select a different room");
			}	

			else if (err == 4){

				//DO SOMETHING: user user created
				JOptionPane.showMessageDialog(null,"Date Conflict, Please select a different date");
				}	*/


			else			
			{
				//need error code for create event
				//DO SOMETHING : login fail
				//JOptionPane.showMessageDialog(null, "Error - Please Re-enter Data");
				//new NewEventScreen();
				newEventWin.setVisible(true);
			}
		}
	}


}


