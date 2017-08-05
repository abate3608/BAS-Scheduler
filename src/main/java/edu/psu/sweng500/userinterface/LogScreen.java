package edu.psu.sweng500.userinterface;

import javax.swing.*;

import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.*;
import java.awt.*;
import java.awt.event.*;

public class LogScreen {

	public static JFrame logWin;
	private JPanel logPane;
	private JLabel userLabel;
	private JLabel passWord;
	private static JTextField userNameText;
	private static JPasswordField passwordText;
	private JButton loginButton;
	private JButton newuserButton;
	
	private static final FocusListener HIGHLIGHTER = new FocusHighlighter();

	String userNameGet;
	String passwordTextGet;

	private static String userName;
	String password;
	// Event listeners
	private final EventHandler eventHandler = EventHandler.getInstance();

	public LogScreen() {
		userName = "";
		
	}
	
	public void create () {

		// setup event
		eventHandler.addListener(new EventQueueListener());

		logWin = new JFrame("Global Schedular System Login");
		logWin.setSize(210, 250);
		logWin.setLocationRelativeTo(null);  
		//logWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	logWin.setExtendedState(JFrame.MAXIMIZED_BOTH);
	//	logWin.setSize(Toolkit.getDefaultToolkit().getScreenSize());

		logPane = new JPanel();
		logWin.add(logPane);
		panelLayout(logPane);
		logWin.setAlwaysOnTop(true);
		logWin.setResizable(false);
		
		logWin.addWindowListener(getWindowAdapter());
		logWin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		logWin.setVisible(true);
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
                logWin.setState(JFrame.NORMAL);
                
            }
        };
    }
	
	public void dispose () {
		//logWin.dispose();
		logWin.setVisible(false); 
	}
	
	public String getUserName () {
		return userName;
	}
	
	private final void panelLayout(JPanel logPanel) {

		logPanel.setLayout(null);
		logPanel.setBorder(BorderFactory.createTitledBorder("Login"));
		
		userLabel = new JLabel("Username");
		userLabel.setBounds(20, 25, 80, 25);
		userLabel.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		logPanel.add(userLabel);

		userNameText = new JTextField();
		userNameText.setBounds(20, 50, 160, 25);
		logPanel.add(userNameText);
		//userNameText.getText();
		userNameText.addKeyListener(new EnterButtonPress());
		userNameText.addFocusListener(HIGHLIGHTER);
		
		passWord = new JLabel("Password");
		passWord.setBounds(20, 75, 80, 25);
		passWord.setFont(new Font("Arial",Font.BOLD,14)); //CHANGE Color
		logPanel.add(passWord);

		passwordText = new JPasswordField();
		passwordText.setBounds(20, 100, 160, 25);
		logPanel.add(passwordText);
		passwordText.addFocusListener(HIGHLIGHTER);
		passwordText.addKeyListener(new EnterButtonPress());

		loginButton = new JButton("Login");
		loginButton.setBounds(55, 135, 80, 25);
		logPanel.add(loginButton);
		loginButton.addActionListener(new loginButtonPress());

		newuserButton = new JButton("Request Access");
		newuserButton.setBounds(27, 165, 140, 25);
		logPanel.add(newuserButton);
		newuserButton.addActionListener(new NewUserScreen());
	}

	// Log in Button
	private final class loginButtonPress implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TEam 7 To Do
			userName = userNameText.getText();
			password = passwordText.getText();
			logWin.setVisible(false); 
			//logWin.dispose(); 
			// fire request event with password
			eventHandler.fireAuthenticateUserRequest(userName, password);		

		}
	}

	//Enter Button Press
	private final class EnterButtonPress extends KeyAdapter {

		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode()== KeyEvent.VK_ENTER) {
				userName = userNameText.getText();
				password = passwordText.getText();
				logWin.setVisible(false); 
				logWin.dispose(); 

				// fire request event with password
				eventHandler.fireAuthenticateUserRequest(userName, password);	
			}
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
				
				//new CalenderScreen();
				//logWin.dispose();
				
			} else if (u.getUserName() == userName && !u.isAuthenticated()){
				//DO SOMETHING : login fail
				//userNameText.setText(null);
				JOptionPane.showMessageDialog(null, "Login Fail. Please Enter Correct UserName and Password");
				passwordText.setText(null);
				logWin.setVisible(true); 
				passwordText.requestFocusInWindow();
			}
			else
			{
				// Do Nothing - Was not the Log In Screens Request.
			}
		}
	}

	
}
