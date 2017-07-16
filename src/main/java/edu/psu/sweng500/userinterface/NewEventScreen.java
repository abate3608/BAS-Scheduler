package edu.psu.sweng500.userinterface;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import edu.psu.sweng500.type.*;
import edu.psu.sweng500.type.User;
import edu.psu.sweng500.userinterface.LogScreen.EventQueueListener;
import edu.psu.sweng500.userinterface.NewUserScreen.FirstMouseClicked;

public class NewEventScreen implements ActionListener {

	private static JFrame newEventWin;
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
		newEventWin.setSize(360, 505);
		newEventWin.setLocationRelativeTo(null);
		//newEventWin.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//newEventWin.setSize(Toolkit.getDefaultToolkit().getScreenSize());
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
		eventName.setBounds(20, 25, 80, 25);
		newEventPanel.add(eventName);

		eventNameTXT = new JTextField(20);
		eventNameTXT.setBounds(20, 50, 300, 25);
		newEventPanel.add(eventNameTXT);
		eventNameTXT.addKeyListener(new EnterButtonPress());
		eventNameTXT.addMouseListener(new EventMouseClicked());

		eventStartTime = new JLabel("Event Start Time");
		eventStartTime.setBounds(20, 75, 100, 25);
		newEventPanel.add(eventStartTime);

		eventStartTimeTXT = new JTextField(20);
		eventStartTimeTXT.setBounds(20, 100, 160, 25);
		newEventPanel.add(eventStartTimeTXT);
		eventStartTimeTXT.addKeyListener(new EnterButtonPress());
		eventStartTimeTXT.addMouseListener(new StartMouseClicked());


		eventEndTime = new JLabel("Event End Time");
		eventEndTime.setBounds(20, 125, 100, 25);
		newEventPanel.add(eventEndTime);

		eventEndTimeTXT = new JTextField(20);
		eventEndTimeTXT.setBounds(20, 150, 160, 25);
		newEventPanel.add(eventEndTimeTXT);
		eventEndTimeTXT.addKeyListener(new EnterButtonPress());
		eventEndTimeTXT.addMouseListener(new EndMouseClicked());

		eventDate = new JLabel("Date of Event");
		eventDate.setBounds(20, 175, 100, 25);
		newEventPanel.add(eventDate);

		eventDateTXT = new JTextField(20);
		eventDateTXT.setBounds(20, 200, 160, 25);
		newEventPanel.add(eventDateTXT);
		eventDateTXT.addKeyListener(new EnterButtonPress());
		eventDateTXT.addMouseListener(new DateMouseClicked());

		eventRoom = new JLabel("Event Room");
		eventRoom.setBounds(20, 225, 160, 25);
		newEventPanel.add(eventRoom);

		eventRoomText = new JTextField(20);
		eventRoomText.setBounds(20, 250, 160, 25);
		newEventPanel.add(eventRoomText);
		eventRoomText.addKeyListener(new EnterButtonPress());
		eventRoomText.addMouseListener(new RoomMouseClicked());

		lightSetting = new JLabel("Light Setting");
		lightSetting.setBounds(20, 275, 160, 25);
		newEventPanel.add(lightSetting);

		lightSettingTXT = new JTextField(20);
		lightSettingTXT.setBounds(20, 300, 160, 25);
		newEventPanel.add(lightSettingTXT);
		lightSettingTXT.addKeyListener(new EnterButtonPress());
		lightSettingTXT.addMouseListener(new LightMouseClicked());

		temperatureSetting = new JLabel("Temperature Setting");
		temperatureSetting.setBounds(20, 325, 160, 25);
		newEventPanel.add(temperatureSetting);

		temperatureSettingTXT = new JTextField(20);
		temperatureSettingTXT.setBounds(20, 350, 160, 25);
		newEventPanel.add(temperatureSettingTXT);
		temperatureSettingTXT.addKeyListener(new EnterButtonPress());
		temperatureSettingTXT.addMouseListener(new TempMouseClicked());

		newEventButton = new JButton("Submit Request");
		newEventButton.setBounds(30, 380, 140, 25);
		newEventPanel.add(newEventButton);
		newEventButton.addActionListener(new userRegistration());

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(30, 410, 140, 25);
		newEventPanel.add(cancelButton);
		cancelButton.addActionListener(new cancelButtonPress());
		
	}

	//Event Name Highlight
	public final class EventMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {		
		}

		public void mouseEntered(MouseEvent arg0) {
			eventNameTXT.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			eventNameTXT.setBackground(Color.white );			
		}

		public void mousePressed(MouseEvent arg0) {
			eventNameTXT.setBackground(Color.white );		
		}

		public void mouseReleased(MouseEvent arg0) {
			eventNameTXT.setBackground(Color.white );
		}                                         
	}    

	//Event Start Time Highlight
	public final class StartMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {		
		}

		public void mouseEntered(MouseEvent arg0) {
			eventStartTimeTXT.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			eventStartTimeTXT.setBackground(Color.white );			
		}

		public void mousePressed(MouseEvent arg0) {
			eventStartTimeTXT.setBackground(Color.white );		
		}

		public void mouseReleased(MouseEvent arg0) {
			eventStartTimeTXT.setBackground(Color.white );
		}                                         
	}               


	//Event End Time Highlight
	public final class EndMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {		
		}

		public void mouseEntered(MouseEvent arg0) {
			eventEndTimeTXT.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			eventEndTimeTXT.setBackground(Color.white );			
		}

		public void mousePressed(MouseEvent arg0) {
			eventEndTimeTXT.setBackground(Color.white );		
		}

		public void mouseReleased(MouseEvent arg0) {
			eventEndTimeTXT.setBackground(Color.white );
		}                                         
	}               


	//Event Date Highlight
	public final class DateMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {		
		}

		public void mouseEntered(MouseEvent arg0) {
			eventDateTXT.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			eventDateTXT.setBackground(Color.white );			
		}

		public void mousePressed(MouseEvent arg0) {
			eventDateTXT.setBackground(Color.white );		
		}

		public void mouseReleased(MouseEvent arg0) {
			eventDateTXT.setBackground(Color.white );
		}                                         
	}               

	//Room Highlight
	public final class RoomMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {	
		}

		public void mouseEntered(MouseEvent arg0) {
			eventRoomText.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			eventRoomText.setBackground(Color.white );
		}

		public void mousePressed(MouseEvent arg0) {
			eventRoomText.setBackground(Color.white );
		}

		public void mouseReleased(MouseEvent arg0) {
			eventRoomText.setBackground(Color.white );
		}                                         
	} 

	// Light Highlighted
	public final class LightMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {	
		}

		public void mouseEntered(MouseEvent arg0) {
			lightSettingTXT.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			lightSettingTXT.setBackground(Color.white );
		}

		public void mousePressed(MouseEvent arg0) {
			lightSettingTXT.setBackground(Color.white );
		}

		public void mouseReleased(MouseEvent arg0) {
			lightSettingTXT.setBackground(Color.white );
		}                                         
	}     	

	//Temp Highlighted
	public final class TempMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {	
		}

		public void mouseEntered(MouseEvent arg0) {
			temperatureSettingTXT.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			temperatureSettingTXT.setBackground(Color.white );
		}

		public void mousePressed(MouseEvent arg0) {
			temperatureSettingTXT.setBackground(Color.white );
		}

		public void mouseReleased(MouseEvent arg0) {
			temperatureSettingTXT.setBackground(Color.white );
		}                                         
	}     	

	// Cancel Button
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


		}
	}

	//Enter Button Press
	private final class EnterButtonPress extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			try {
				if (e.getKeyCode()== KeyEvent.VK_ENTER) {
					JOptionPane.showMessageDialog(null, "Submitting New Event Request");
					String eventName = eventNameTXT.getText(); 
					String startTime = eventStartTimeTXT.getText(); 
					String endTime= eventEndTimeTXT.getText(); 
					String eventDate = eventDateTXT.getText(); 
					String eventRoom = eventRoomText.getText(); 
					String lightIntensity = lightSettingTXT.getText(); 
					String temperatureSetpoint = temperatureSettingTXT.getText(); 

					Date startDateTime = null;
					Date endDateTime = null;

					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); /// REMOVE SECONDS


					startDateTime = df.parse(eventDate + " " + startTime);
					endDateTime = df.parse(eventDate + " " + endTime); /// ADD END DATE NOT IMPLEMENTD



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
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
				newEventWin.dispose();
			} else
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

