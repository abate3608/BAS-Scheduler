package edu.psu.sweng500.userinterface;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.NewEvent;
import edu.psu.sweng500.type.ScheduleEvent;
import edu.psu.sweng500.type.User;
import edu.psu.sweng500.userinterface.LogScreen.EventQueueListener;

public class NewEventScreen implements ActionListener {

	private JFrame newEventWin;
	private JPanel newEventPane;
	private JLabel eventName;
	private JLabel eventStartTime;
	private JLabel eventEndTime;
	private JLabel eventDate;
	private JLabel eventRoom;
	private JLabel lightSetting;
	private JLabel temperatureSetting;
	private JTextField eventNameTXT;
	private JTextField eventStartTimeTXT;
	private JTextField eventEndTimeTXT;
	private JTextField eventDateTXT;
	private JTextField eventRoomText;
	private JTextField lightSettingTXT;
	private JTextField temperatureSettingTXT;

	private JButton newEventButton;
	private JButton cancelButton;

	// Event listeners
		private final EventHandler eventHandler = EventHandler.getInstance();
		
	public void actionPerformed(ActionEvent e) {

		// setup event
		eventHandler.addListener(new EventQueueListener());

		newEventWin = new JFrame("Global Schedular System New Event");
		newEventWin.setExtendedState(JFrame.MAXIMIZED_BOTH);
		newEventWin.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		newEventWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		newEventPane = new JPanel();
		newEventWin.add(newEventPane);
		newuserLayout(newEventPane);

		newEventWin.setVisible(true);
	}

	private final void newuserLayout(JPanel newEventPanel) {

		newEventPanel.setLayout(null);
		newEventPanel.setBorder(BorderFactory.createTitledBorder("Schedule a New Event"));

		eventName = new JLabel("Event Name");
		eventName.setBounds(550, 100, 80, 25);
		newEventPanel.add(eventName);

		eventNameTXT = new JTextField(20);
		eventNameTXT.setBounds(550, 125, 300, 25);
		newEventPanel.add(eventNameTXT);

		eventStartTime = new JLabel("Event Start Time");
		eventStartTime.setBounds(550, 165, 100, 25);
		newEventPanel.add(eventStartTime);

		eventStartTimeTXT = new JTextField(20);
		eventStartTimeTXT.setBounds(550, 190, 160, 25);
		newEventPanel.add(eventStartTimeTXT);

		eventEndTime = new JLabel("Event End Time");
		eventEndTime.setBounds(550, 230, 100, 25);
		newEventPanel.add(eventEndTime);

		eventEndTimeTXT = new JTextField(20);
		eventEndTimeTXT.setBounds(550, 255, 160, 25);
		newEventPanel.add(eventEndTimeTXT);

		eventDate = new JLabel("Date of Event");
		eventDate.setBounds(550, 295, 100, 25);
		newEventPanel.add(eventDate);

		eventDateTXT = new JTextField(20);
		eventDateTXT.setBounds(550, 320, 160, 25);
		newEventPanel.add(eventDateTXT);

		eventRoom = new JLabel("Event Room");
		eventRoom.setBounds(550, 360, 160, 25);
		newEventPanel.add(eventRoom);

		eventRoomText = new JTextField(20);
		eventRoomText.setBounds(550, 385, 160, 25);
		newEventPanel.add(eventRoomText);

		lightSetting = new JLabel("Light Setting");
		lightSetting.setBounds(550, 425, 160, 25);
		newEventPanel.add(lightSetting);

		lightSettingTXT = new JTextField(20);
		lightSettingTXT.setBounds(550, 450, 160, 25);
		newEventPanel.add(lightSettingTXT);

		temperatureSetting = new JLabel("Temperature Setting");
		temperatureSetting.setBounds(550, 490, 160, 25);
		newEventPanel.add(temperatureSetting);

		temperatureSettingTXT = new JTextField(20);
		temperatureSettingTXT.setBounds(550, 515, 160, 25);
		newEventPanel.add(temperatureSettingTXT);

		newEventButton = new JButton("Submit Request");
		newEventButton.setBounds(560, 575, 140, 25);
		newEventPanel.add(newEventButton);
		newEventButton.addActionListener(new userRegistration());

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(560, 625, 140, 25);
		newEventPanel.add(cancelButton);
		cancelButton.addActionListener(new cancelButtonPress());

	}

	private final class cancelButtonPress implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			eventNameTXT.setText(null);
			eventStartTimeTXT.setText(null);
			eventEndTimeTXT.setText(null);
			eventDateTXT.setText(null);
			eventRoomText.setText(null);
			lightSettingTXT.setText(null);
			temperatureSettingTXT.setText(null);
			newEventWin.dispose();
		}
	}

///////////////////////////////////////////////////////////////////////////////////////// ADDED 6/23/2017

	private final class userRegistration implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "Submitting New Event Request");
			String eventName = eventNameTXT.getText(); 
			String startTime = eventStartTimeTXT.getText(); 
			String endTime= eventEndTimeTXT.getText(); 
			String eventDate = eventDateTXT.getText(); 
			String eventRoom = eventRoomText.getText(); 
			String lightSetting = lightSettingTXT.getText(); 
			String tempSetting = temperatureSettingTXT.getText(); 
			
			Date startDateTime = null;
			Date endDateTime = null;
			
			/*Calendar cal = Calendar.getInstance();
			if (startTime == "") {
				startTime = cal.getTime().toString();
			}
			if (endTime == "")
			{
				endTime = cal.getTime().toString();
			}
			
			// request events
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
			Date startDateTime = null;
			Date endDateTime = null;
			try {
				startDateTime = df.parse(eventDate + " " + startTime);
				endDateTime= df.parse(eventDate + " " + endTime);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			
			//if (tempSetting == null) {
				tempSetting = "72";
			//''}
			
			//if (lightSetting == null) {
				lightSetting = "100";
			//}
			ScheduleEvent se = new ScheduleEvent();
			se.setEventName(eventName);
			se.setEventDescription("Description");
			
			//end to match Schedule Event Data type
			
			se.setEventStart(startDateTime);
			se.setEventStop(endDateTime);
			se.setTemperatureSetpoint(Float.parseFloat(tempSetting));
			se.setLightIntensity(Float.parseFloat(lightSetting));
							
			// fire request event with password
			//eventHandler.fireAuthenticateNewEventRequest(eventName, startTime, endTime, eventDate, eventRoom, lightSetting, tempSetting);
			
			eventHandler.fireCreateEvent(se);
			
		}
	}

	public EventHandler getEventHandler() {
		return eventHandler;
	}

	static class EventQueueListener extends EventAdapter {
		// listen to event queue
	
		@Override
		public void createEventRespond(ScheduleEvent se, int err) {
			System.out.println("NewEventScreen > Create event respond received. Name: " + se.getEventName() + " Error Code:" + err);
			if (err == 0) //good
			{
				new CalenderScreen();
			} else
			{
				//need error code for create event
				//DO SOMETHING : login fail
				JOptionPane.showMessageDialog(null, "Error - Please Re-enter Data");
				new NewEventScreen();
			}
		}
	}

}

