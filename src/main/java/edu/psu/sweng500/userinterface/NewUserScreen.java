package edu.psu.sweng500.userinterface;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.*;
import edu.psu.sweng500.userinterface.LogScreen.EventQueueListener;


public class NewUserScreen implements ActionListener {

	private static JFrame regWin;
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
	private static final FocusListener HIGHLIGHTER = new FocusHighlighter();

	// Event listeners
	private final EventHandler eventHandler = EventHandler.getInstance();
		
	public void actionPerformed(ActionEvent e) {
		
		// setup event
		eventHandler.addListener(new EventQueueListener());
				
		regWin = new JFrame("Global Schedular System Login");
		regWin.setSize(210, 410);
		regWin.setLocationRelativeTo(null);
	//	regWin.setExtendedState(JFrame.MAXIMIZED_BOTH);
	//	regWin.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		regWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		regPane = new JPanel();
		regWin.add(regPane);
		newuserLayout(regPane);
		
		regWin.addWindowListener(getWindowAdapter());
		regWin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		regWin.setAlwaysOnTop(true);
		regWin.setResizable(false);
		regWin.setVisible(true);
		
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
		            	regWin.setState(JFrame.NORMAL);
		                
		            }
		        };
		    }
	private final void newuserLayout(JPanel regPanel) {

		regPanel.setLayout(null);
		regPanel.setBorder(BorderFactory.createTitledBorder("New User Registration"));

		firstName = new JLabel("First Name");
		firstName.setBounds(20, 25, 80, 25);
		firstName.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		regPanel.add(firstName);

		firstNameTXT = new JTextField(20);
		firstNameTXT.setBounds(20, 50, 160, 25);
		regPanel.add(firstNameTXT);
		firstNameTXT.addKeyListener(new EnterButtonPress());
		firstNameTXT.addFocusListener(HIGHLIGHTER);

		lastName = new JLabel("Last Name");
		lastName.setBounds(20, 75, 80, 25);
		regPanel.add(lastName);
		lastName.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		
		
		lastNameTXT = new JTextField(20);
		lastNameTXT.setBounds(20, 100, 160, 25);
		regPanel.add(lastNameTXT);
		lastNameTXT.addKeyListener(new EnterButtonPress());
		lastNameTXT.addFocusListener(HIGHLIGHTER);

		email = new JLabel("Email");
		email.setBounds(20, 125, 80, 25);
		email.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		regPanel.add(email);

		emailTXT = new JTextField(20);
		emailTXT.setBounds(20, 150, 160, 25);
		regPanel.add(emailTXT);
		emailTXT.addKeyListener(new EnterButtonPress());
		emailTXT.addFocusListener(HIGHLIGHTER);

		userName = new JLabel("UserName");
		userName.setBounds(20, 175, 80, 25);
		userName.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		regPanel.add(userName);

		userNameTXT = new JTextField(20);
		userNameTXT.setBounds(20, 200, 160, 25);
		regPanel.add(userNameTXT);
		userNameTXT.addKeyListener(new EnterButtonPress());
		userNameTXT.addFocusListener(HIGHLIGHTER);

		passWord = new JLabel("Password");
		passWord.setBounds(20, 225, 160, 25);
		passWord.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		regPanel.add(passWord);
		
		passwordText = new JPasswordField(20);
		passwordText.setBounds(20, 250, 160, 25);
		regPanel.add(passwordText);
		passwordText.addKeyListener(new EnterButtonPress());
		passwordText.addFocusListener(HIGHLIGHTER);
		
		newuserButton = new JButton("Request Access");
		newuserButton.setBounds(30, 285, 140, 25);
		regPanel.add(newuserButton);
		newuserButton.addActionListener(new userRegistration());

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(30, 315, 140, 25);
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
		
		 if(firstNameTXT.getText().isEmpty() || lastNameTXT.getText().isEmpty() || emailTXT.getText().isEmpty() ||
				 userNameTXT.getText().isEmpty() || passwordText.getText().isEmpty()){
		     JOptionPane.showMessageDialog(null,"A required Field is empty, Please complete all fields"); 
		 
		     regWin.setVisible(true); 
		 
		 } else{ User u = new User(firstNameTXT.getText(), lastNameTXT.getText(), emailTXT.getText(), userNameTXT.getText(), passwordText.getText());
		eventHandler.fireCreteUser(u);
	}
		
	}
	
	//Enter Button Press
	private final class EnterButtonPress extends KeyAdapter {

		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode()== KeyEvent.VK_ENTER) {
				
				createUser();
			}
		}
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
				JOptionPane.showMessageDialog(null,"Registration Approved Please Log In");
				regWin.dispose();	
			}
			 if (err == 1)
			{
				
				//DO SOMETHING: user user created
				JOptionPane.showMessageDialog(null,"Email Authentication Error, Please provide a different email address");
			}	
			 if (err == 2)
			 {
				
			//DO SOMETHING: user user created
				JOptionPane.showMessageDialog(null,"Username Authentication Error, Pleae provide a different User Name");
				}
//			}else if (err == 3){
//				
//				//DO SOMETHING: user user created
//				JOptionPane.showMessageDialog(null,"Password Authentication Error, Please provide a different Password");	
			}
			//{
				//DO SOMETHING : cannot create user user. Use error code to determine fail reason.
				//for example, user name already create.
				//invalid email.
				//need to come up with error code.
			}
		}
	//}
//}
