package edu.psu.sweng500.userinterface;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class NewEventScreen implements ActionListener{

	private JFrame newEventWin;
	private JPanel newEventPane;
	private JLabel eventName; 
	private JLabel eventStartTime; 
	private JLabel eventEndTime ; 
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

	public void actionPerformed(ActionEvent e) {
		
		
		newEventWin = new JFrame("Global Schedular System New Event"); 
		newEventWin .setExtendedState(JFrame.MAXIMIZED_BOTH);
		newEventWin .setSize(Toolkit.getDefaultToolkit().getScreenSize());
		newEventWin .setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		newEventPane = new JPanel(); 
		newEventWin .add(newEventPane);
		newuserLayout(newEventPane);

		newEventWin .setVisible(true);
	}
	private final void newuserLayout(JPanel newEventPanel) {

		newEventPanel.setLayout(null);
		newEventPanel.setBorder(BorderFactory.createTitledBorder("Schedule a New Event"));  

		eventName = new JLabel("Event Name"); 
		eventName.setBounds(550, 100 , 80, 25);
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
		eventEndTime .setBounds(550, 230, 100, 25);
		newEventPanel.add(eventEndTime );

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
		lightSetting .setBounds(550,425, 160, 25); 
		newEventPanel.add(lightSetting); 

		lightSettingTXT = new JTextField(20);
		lightSettingTXT.setBounds(550, 450, 160, 25); 
		newEventPanel.add(lightSettingTXT);

		temperatureSetting = new JLabel("Temperature Setting");  
		temperatureSetting .setBounds(550, 490, 160, 25); 
		newEventPanel.add(temperatureSetting); 

		temperatureSettingTXT = new JTextField(20);
		temperatureSettingTXT.setBounds(550, 515, 160, 25); 
		newEventPanel.add(temperatureSettingTXT);

		newEventButton = new JButton("Submit Request");
		newEventButton.setBounds(560,575, 140, 25);
		newEventPanel.add(newEventButton);
		newEventButton.addActionListener(new userRegistration());

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(560, 625, 140, 25);
		newEventPanel.add(cancelButton);
		cancelButton.addActionListener(new cancelButtonPress());

	}


	private final class cancelButtonPress implements ActionListener{   

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

	private final class userRegistration implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null,"Submitting New Event Request");

		}
	}
}		



