package edu.psu.sweng500.userinterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.List;
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
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.DBScheduleTable;
import edu.psu.sweng500.type.ScheduleEvent;
import edu.psu.sweng500.userinterface.NewEventScreen.EventQueueListener;


public class EditEventScreen implements ActionListener {

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
	private JButton getButton;
	private JButton editEventButton;
	private JButton cancelButton;
	
	// Event listeners
	private final EventHandler eventHandler = EventHandler.getInstance(); /// ADDED 7/22

	static ArrayList<DBScheduleTable> schedules = new ArrayList<DBScheduleTable>();
	List lb = new List();
	
	public void setSchedules(ArrayList<DBScheduleTable> schedules) {
		this.schedules = schedules;
	}
	public void actionPerformed(ActionEvent e) {
		
		// setup event
		eventHandler.addListener(new EventQueueListener()); ///// ADDED 7/22

		newEventWin = new JFrame("Global Schedular System Edit Event");
		newEventWin.setSize(525, 505);
		newEventWin.setLocationRelativeTo(null);
		//newEventWin.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//newEventWin.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		newEventWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		newEventPane = new JPanel();
		newEventWin.add(newEventPane);
		newuserLayout(newEventPane);

		newEventWin.setVisible(true);

		JOptionPane.showMessageDialog(null, "Enter Event Name and Press 'Get Event Details Button'");
	}

	private final void newuserLayout(JPanel newEventPanel) {

		newEventPanel.setLayout(null);
		newEventPanel.setBorder(BorderFactory.createTitledBorder("Edit An Existing Event"));
		newEventPanel.setBackground(new Color(218, 247, 159)); //CHANGE Color
	    
	    
	    for(DBScheduleTable s : schedules) {
	    	lb.add(s.getName());
	    }
	    
	    lb.setBounds(20, 25, 300, 400);
	    lb.addMouseListener(new lbMouseClicked());
	    newEventPanel.add(lb);
	    
		/*eventName = new JLabel("Event Name");
		eventName.setBounds(250, 25, 90, 25);
		eventName.setForeground(Color.blue); //CHANGE Color
		eventName.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(eventName);

		eventNameTXT = new JTextField(20);
		eventNameTXT.setBounds(20, 50, 300, 25);
		newEventPanel.add(eventNameTXT);
		eventNameTXT.addKeyListener(new EnterButtonPress());
		eventNameTXT.addMouseListener(new EventMouseClicked());
*/
		eventStartTime = new JLabel("Event Start Time");
		eventStartTime.setBounds(lb.getWidth() + lb.getX() + 10, lb.getY(), 120, 25);
		eventStartTime.setForeground(Color.blue); //CHANGE Color
		eventStartTime.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(eventStartTime);
		

		eventStartTimeTXT = new JTextField(20);
		eventStartTimeTXT.setBounds(lb.getWidth() + lb.getX() + 10, eventStartTime.getY() + 25, 160, 25);
		newEventPanel.add(eventStartTimeTXT);
		eventStartTimeTXT.addKeyListener(new EnterButtonPress());
		eventStartTimeTXT.addMouseListener(new StartMouseClicked());
		

		eventEndTime = new JLabel("Event End Time");
		eventEndTime.setBounds(lb.getWidth() + lb.getX() + 10, eventStartTimeTXT.getY() + 25, 120, 25);
		eventEndTime.setForeground(Color.blue); //CHANGE Color
		eventEndTime.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(eventEndTime);

		eventEndTimeTXT = new JTextField(20);
		eventEndTimeTXT.setBounds(lb.getWidth() + lb.getX() + 10, eventEndTime.getY() + 25, 160, 25);
		newEventPanel.add(eventEndTimeTXT);
		eventEndTimeTXT.addKeyListener(new EnterButtonPress());
		eventEndTimeTXT.addMouseListener(new EndMouseClicked());

		eventDate = new JLabel("Date of Event");
		eventDate.setBounds(lb.getWidth() + lb.getX() + 10, eventEndTimeTXT.getY() + 25, 100, 25);
		eventDate.setForeground(Color.blue); //CHANGE Color
		eventDate.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(eventDate);

		eventDateTXT = new JTextField(20);
		eventDateTXT.setBounds(lb.getWidth() + lb.getX() + 10, eventDate.getY() + 25, 160, 25);
		newEventPanel.add(eventDateTXT);
		eventDateTXT.addKeyListener(new EnterButtonPress());
		eventDateTXT.addMouseListener(new DateMouseClicked());


		eventRoom = new JLabel("Event Room");
		eventRoom.setBounds(lb.getWidth() + lb.getX() + 10, eventDateTXT.getY() + 25, 160, 25);
		eventRoom.setForeground(Color.blue); //CHANGE Color
		eventRoom.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(eventRoom);

		eventRoomText = new JTextField(20);
		eventRoomText.setBounds(lb.getWidth() + lb.getX() + 10, eventRoom.getY() + 25, 160, 25);
		newEventPanel.add(eventRoomText);
		eventRoomText.addKeyListener(new EnterButtonPress());
		eventRoomText.addMouseListener(new RoomMouseClicked());

		lightSetting = new JLabel("Light Setting");
		lightSetting.setBounds(lb.getWidth() + lb.getX() + 10, eventRoomText.getY() + 25, 160, 25);
		lightSetting.setForeground(Color.blue); //CHANGE Color
		lightSetting.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(lightSetting);

		lightSettingTXT = new JTextField(20);
		lightSettingTXT.setBounds(lb.getWidth() + lb.getX() + 10, lightSetting.getY() + 25, 160, 25);
		newEventPanel.add(lightSettingTXT);
		lightSettingTXT.addKeyListener(new EnterButtonPress());
		lightSettingTXT.addMouseListener(new LightMouseClicked());

		temperatureSetting = new JLabel("Temperature Setting");
		temperatureSetting.setBounds(lb.getWidth() + lb.getX() + 10, lightSettingTXT.getY() + 25, 160, 25);
		temperatureSetting.setForeground(Color.blue); //CHANGE Color
		temperatureSetting.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(temperatureSetting);

		temperatureSettingTXT = new JTextField(20);
		temperatureSettingTXT.setBounds(lb.getWidth() + lb.getX() + 10, temperatureSetting.getY() + 25, 160, 25);
		newEventPanel.add(temperatureSettingTXT);
		temperatureSettingTXT.addKeyListener(new EnterButtonPress());
		temperatureSettingTXT.addMouseListener(new TempMouseClicked());


		/*getButton = new JButton("Get Event Details");
		getButton.setBounds(340, 50, 140, 25);
		newEventPanel.add(getButton);
		getButton.addActionListener(new getEvent());*/

		editEventButton = new JButton("Edit Event");
		editEventButton.setBounds(lb.getWidth() + lb.getX() + 10, temperatureSettingTXT.getY() + 50, 140, 25);
		newEventPanel.add(editEventButton);
		editEventButton.addActionListener(new editEvent());

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(lb.getWidth() + lb.getX() + 10, editEventButton.getY() + 30, 140, 25);
		newEventPanel.add(cancelButton);
		cancelButton.addActionListener(new cancelButtonPress());
	}

	//Event Name Highlight
	public final class EventMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {		
		}

		public void mouseEntered(MouseEvent arg0) {
			eventNameTXT.setBackground(new Color(146, 157, 225)); //CHANGE Color
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

	
	//list box clicked
	public final class lbMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {		
		}

		public void mouseEntered(MouseEvent arg0) {
			
		}

		public void mouseExited(MouseEvent arg0) {
					
		}

		public void mousePressed(MouseEvent arg0) {
					
		}

		public void mouseReleased(MouseEvent arg0) {
			String name = lb.getSelectedItem();
			
			for(DBScheduleTable s : schedules) {
		    	if (name.equalsIgnoreCase(s.getName())) {
		    		eventStartTimeTXT.setText(s.getStartDateTime().toString());
		    		eventEndTimeTXT.setText(s.getEndDateTime().toString());
		    		eventRoomText.setText(s.getRoomName());
		    		lightSettingTXT.setText(String.valueOf(s.getLightIntensity()));
		    		temperatureSettingTXT.setText(String.valueOf(s.getTemperatureSetpoint()));
		    		
		    	}
		    }
			
		}                                         
	} 
	
	//Event Start Time Highlight
	public final class StartMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {		
		}

		public void mouseEntered(MouseEvent arg0) {
			eventStartTimeTXT.setBackground(new Color(146, 157, 225)); //CHANGE Color
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
			eventEndTimeTXT.setBackground(new Color(146, 157, 225)); //CHANGE Color
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
			eventDateTXT.setBackground(new Color(146, 157, 225)); //CHANGE Color
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
			eventRoomText.setBackground(new Color(146, 157, 225)); //CHANGE Color
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
			lightSettingTXT.setBackground(new Color(146, 157, 225)); //CHANGE Color
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
			temperatureSettingTXT.setBackground(new Color(146, 157, 225)); //CHANGE Color
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
	
	// Cnacel Button
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

	//Submit Button
	private final class editEvent implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "Submitting Editited Event Request");
			
			try {

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

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); /// TODO REMOVE SECONDS


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

			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		

	}

	//Enter Button Press
	private final class EnterButtonPress extends KeyAdapter {

		public void keyPressed(KeyEvent e) {

			
			if (e.getKeyCode()== KeyEvent.VK_ENTER) {
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

				//eventHandler.fireCreateEvent(se);
			}
		}
	}
	
	private final class getEvent implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			//JOptionPane.showMessageDialog(null, "Retrieving Event Information");
			
			
			
		}

	}

}
