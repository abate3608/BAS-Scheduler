package edu.psu.sweng500.userinterface;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.*;
import edu.psu.sweng500.userinterface.LogScreen.EventQueueListener;

public class NewUserScreen implements ActionListener {

	private JFrame regWin;
	private JPanel regPane;
	private JLabel firstName;
	private JLabel lastName;
	private JLabel email;
	private JLabel userName;
	private JLabel passWord;
	private JTextField firstNameTXT;
	private JTextField lastNameTXT;
	private JTextField emailTXT;
	private JTextField userNameTXT;
	private JPasswordField passwordText;
	private JButton newuserButton;
	private JButton cancelButton;

	// Event listeners
	private final EventHandler eventHandler = EventHandler.getInstance();
		
	public void actionPerformed(ActionEvent e) {
		
		// setup event
		eventHandler.addListener(new EventQueueListener());
				
		regWin = new JFrame("Global Schedular System Login");
		// regWin.setSize(1800, 750);
		regWin.setExtendedState(JFrame.MAXIMIZED_BOTH);
		regWin.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		regWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		regPane = new JPanel();
		regWin.add(regPane);
		newuserLayout(regPane);
		regWin.setVisible(true);
		
	}

	private final void newuserLayout(JPanel regPanel) {

		regPanel.setLayout(null);
		regPanel.setBorder(BorderFactory.createTitledBorder("New User Registration"));

		firstName = new JLabel("First Name");
		firstName.setBounds(550, 160, 80, 25);
		regPanel.add(firstName);

		firstNameTXT = new JTextField(20);
		firstNameTXT.setBounds(550, 185, 160, 25);
		regPanel.add(firstNameTXT);

		lastName = new JLabel("Last Name");
		lastName.setBounds(550, 225, 80, 25);
		regPanel.add(lastName);

		lastNameTXT = new JTextField(20);
		lastNameTXT.setBounds(550, 250, 160, 25);
		regPanel.add(lastNameTXT);

		email = new JLabel("Email");
		email.setBounds(550, 290, 80, 25);
		regPanel.add(email);

		emailTXT = new JTextField(20);
		emailTXT.setBounds(550, 315, 160, 25);
		regPanel.add(emailTXT);

		userName = new JLabel("UserName");
		userName.setBounds(550, 355, 80, 25);
		regPanel.add(userName);

		userNameTXT = new JTextField(20);
		userNameTXT.setBounds(550, 380, 160, 25);
		regPanel.add(userNameTXT);

		passWord = new JLabel("Password");
		passWord.setBounds(550, 420, 160, 25);
		regPanel.add(passWord);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(550, 445, 160, 25);
		regPanel.add(passwordText);

		newuserButton = new JButton("Request Access");
		newuserButton.setBounds(560, 550, 140, 25);
		regPanel.add(newuserButton);
		newuserButton.addActionListener(new userRegistration());

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(560, 590, 140, 25);
		regPanel.add(cancelButton);
		cancelButton.addActionListener(new cancelButtonPress());
	}

	private final class cancelButtonPress implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			firstNameTXT.setText(null);
			lastNameTXT.setText(null);
			userNameTXT.setText(null);
			emailTXT.setText(null);
			passwordText.setText(null);
			regWin.dispose();
						
		}
	}

	private final class userRegistration implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			//JOptionPane.showMessageDialog(null, "Submitting Registration Request");{
			createUser();
		}
		}
	//}

	public void createUser() {
		User u = new User(firstNameTXT.getText(), lastNameTXT.getText(), emailTXT.getText(), userNameTXT.getText(), passwordText.getText());
		eventHandler.fireCreteUser(u);
		
	}
	
	public EventHandler getEventHandler() {
		return eventHandler;
	}

	static class EventQueueListener extends EventAdapter {
		// listen to event queue

		@Override
		public void createUserRespond(User u, int err) {
			System.out.println("NewUserScreen > Create User Respond. User: " + u.getUserName() + " Error Code:" + err);
			if (err == 0)
			{
				//DO SOMETHING: user user created
			} else
			{
				//DO SOMETHING : cannot create user user. Use error code to determine fail reason.
				//for example, user name already create.
				//invalid email.
				//need to come up with error code.
			}
		}
	}

}
