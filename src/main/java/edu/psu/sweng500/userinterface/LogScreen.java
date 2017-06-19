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
	private  JTextField userNameText;
	private JPasswordField passwordText;
	private JButton loginButton;
	private JButton newuserButton;

	String userNameGet;
	String passwordTextGet;

	 // Event listeners
    private final EventHandler eventHandler = EventHandler.getInstance();
    
	public LogScreen() {
		
		//setup event
		eventHandler.addListener(new EventQueueListener());
				
		JFrame logWin = new JFrame("Global Schedular System Login");
		//logWin.setSize(1800, 750);
		logWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logWin.setExtendedState(JFrame.MAXIMIZED_BOTH);
		logWin.setSize(Toolkit.getDefaultToolkit().getScreenSize());

		logPane = new JPanel();
		logWin.add(logPane);
		panelLayout(logPane);
		logWin.setVisible(true);
	}
	private final void panelLayout(JPanel logPanel) {

		logPanel.setLayout(null);
		logPanel.setBorder(BorderFactory.createTitledBorder("Login"));  

		userLabel = new JLabel("Username");
		userLabel.setBounds(550, 225, 80, 25);
		logPanel.add(userLabel);

		userNameText = new JTextField();
		userNameText.setBounds(550, 250, 160, 25);
		logPanel.add(userNameText);
		userNameText.getText();

		passWord = new JLabel("Password");
		passWord.setBounds(550, 290, 80, 25);
		logPanel.add(passWord);

		passwordText = new JPasswordField();
		passwordText.setBounds(550, 315, 160, 25);
		logPanel.add(passwordText);

		loginButton = new JButton("Login");
		loginButton.setBounds(592, 350, 80, 25);
		logPanel.add(loginButton);
		loginButton.addActionListener(new loginButtonPress());

		newuserButton = new JButton("Request Access");
		newuserButton.setBounds(562, 390, 140, 25);
		logPanel.add(newuserButton);
		newuserButton.addActionListener(new NewUserScreen());	
	}

	// Log in Button	
	private final class loginButtonPress implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			//TEam 7 To Do
			//
			String userName = "abc"; //replace with username from ui
			String password = "123"; //replace with password from ui
			
			//fire request event with password
			eventHandler.fireAuthenticateUserRequest(userName, password);
			
			new CalenderScreen();
			

		}
	}

	 public EventHandler getEventHandler() {
		 return eventHandler;
	 }
	 
	 static class EventQueueListener extends EventAdapter {
	    	//listen to event queue
		 
			 @Override
			 public void authenticateUserUpdate(User u) {
				 //TEAM 7 TO DO
				 //User data type
				 //u.isAuthenticated() = true when user is authenticated; false when user is not authenticated.
				 //write code to handle user login and timeout. What happen if user is not authenticate.
				 
			 }
	    }
	 
	 
	//public synchronized void loginListener(loginListener) {
	//    if (!loginListenerList.contains(Approved)) {
	//       new calenderScreen();	
	//   }else if (!loginListenerList.contains(Denied)) {
	//      JOptionPane.showMessageDialog(null,"User Name and/or Password is Incorrect");
	//		new loginScreen();	
	//
} 	




