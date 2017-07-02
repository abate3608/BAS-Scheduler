package edu.psu.sweng500.userinterface;

import javax.swing.*;

import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.*;

import java.awt.*;
import java.awt.event.*;

public class LogScreen {

	private JPanel logPane;
	private JLabel userLabel;
	private JLabel passWord;
	private JTextField userNameText;
	private JPasswordField passwordText;
	private JButton loginButton;
	private JButton newuserButton;

	String userNameGet;
	String passwordTextGet;

	static String userName;
	String password;
	// Event listeners
	private final EventHandler eventHandler = EventHandler.getInstance();

	public LogScreen() {

		// setup event
		eventHandler.addListener(new EventQueueListener());

		JFrame logWin = new JFrame("Global Schedular System Login");
		logWin.setSize(700, 450);
		logWin.setLocationRelativeTo(null);  
		logWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	logWin.setExtendedState(JFrame.MAXIMIZED_BOTH);
	//	logWin.setSize(Toolkit.getDefaultToolkit().getScreenSize());

		logPane = new JPanel();
		logWin.add(logPane);
		panelLayout(logPane);
		logWin.setVisible(true);
	}

	private final void panelLayout(JPanel logPanel) {

		logPanel.setLayout(null);
		logPanel.setBorder(BorderFactory.createTitledBorder("Login"));

		userLabel = new JLabel("Username");
		userLabel.setBounds(275, 105, 80, 25);
		logPanel.add(userLabel);

		userNameText = new JTextField();
		userNameText.setBounds(275, 130, 160, 25);
		logPanel.add(userNameText);
		//userNameText.getText();
		
		
		passWord = new JLabel("Password");
		passWord.setBounds(275, 170, 80, 25);
		logPanel.add(passWord);

		passwordText = new JPasswordField();
		passwordText.setBounds(275, 195, 160, 25);
		logPanel.add(passwordText);

		loginButton = new JButton("Login");
		loginButton.setBounds(312, 235, 80, 25);
		logPanel.add(loginButton);
		loginButton.addActionListener(new loginButtonPress());

		newuserButton = new JButton("Request Access");
		newuserButton.setBounds(282, 275, 140, 25);
		logPanel.add(newuserButton);
		newuserButton.addActionListener(new NewUserScreen());
	}

	// Log in Button
	private final class loginButtonPress implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TEam 7 To Do
			userName = userNameText.getText();
			password = passwordText.getText();

			// fire request event with password
			eventHandler.fireAuthenticateUserRequest(userName, password);		

		}
	}

	public EventHandler getEventHandler() {
		return eventHandler;
	}

	static class EventQueueListener extends EventAdapter {
		// listen to event queue

		@Override
		public void authenticateUserUpdate(User u) {
			System.out.println("LogScreen > Authentication user update received. User: " + userName + " isAuthenicated:" + u.isAuthenticated());
			if (u.getUserName() == userName && u.isAuthenticated())
			{
				new CalenderScreen();
			} else
			{
				//DO SOMETHING : login fail
			}
		}
	}

	// public synchronized void loginListener(loginListener) {
	// if (!loginListenerList.contains(Approved)) {
	// new calenderScreen();
	// }else if (!loginListenerList.contains(Denied)) {
	// JOptionPane.showMessageDialog(null,"User Name and/or Password is
	// Incorrect");
	// new loginScreen();
	//
}
