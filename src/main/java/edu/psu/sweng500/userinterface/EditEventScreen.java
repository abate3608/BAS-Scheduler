package edu.psu.sweng500.userinterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
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
//import edu.psu.sweng500.userinterface.NewEventScreen.StartDateButtonPress; //////////////////////////////Modified
import edu.psu.sweng500.userinterface.NewEventScreen.EventQueueListener;
import edu.psu.sweng500.userinterface.datepicker.DatePicker;


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
	private static final FocusListener HIGHLIGHTER = new FocusHighlighter();

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

		int width = 140;
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
=======
		newEventPanel.setBorder(BorderFactory.createTitledBorder("Schedule a New Event"));

		eventName = new JLabel("Event Name");
		eventName.setBounds(20, 25, 90, 25);
>>>>>>> branch 'master' of https://github.com/abate3608/BAS-Scheduler.git
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
		eventStartTimeTXT.setBounds(lb.getWidth() + lb.getX() + 10, eventStartTime.getY() + 25, width, 25);
		newEventPanel.add(eventStartTimeTXT);
		eventStartTimeTXT.addKeyListener(new EnterButtonPress());
		eventStartTimeTXT.addFocusListener(HIGHLIGHTER);


		eventEndTime = new JLabel("Event End Time");
		eventEndTime.setBounds(lb.getWidth() + lb.getX() + 10, eventStartTimeTXT.getY() + 25, width, 25);
		eventEndTime.setForeground(Color.blue); //CHANGE Color
		eventEndTime.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(eventEndTime);

		eventEndTimeTXT = new JTextField(20);
		eventEndTimeTXT.setBounds(lb.getWidth() + lb.getX() + 10, eventEndTime.getY() + 25, width, 25);
		newEventPanel.add(eventEndTimeTXT);
		eventEndTimeTXT.addKeyListener(new EnterButtonPress());
		eventEndTimeTXT.addFocusListener(HIGHLIGHTER);

		eventDate = new JLabel("Date of Event");
		eventDate.setBounds(lb.getWidth() + lb.getX() + 10, eventEndTimeTXT.getY() + 25, 100, 25);
		eventDate.setForeground(Color.blue); //CHANGE Color
		eventDate.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(eventDate);

		eventDateTXT = new JTextField(20);
		eventDateTXT.setBounds(lb.getWidth() + lb.getX() + 10, eventDate.getY() + 25, width, 25);
		newEventPanel.add(eventDateTXT);
		eventDateTXT.addKeyListener(new EnterButtonPress());
		eventDateTXT.addFocusListener(HIGHLIGHTER);

		//create button and there object
		JButton dateButton = new JButton("+");
		dateButton.setBounds(eventDateTXT.getWidth() + eventDateTXT.getX() + 5, eventDate.getY() + 25, 20, 25);
		newEventPanel.add(dateButton);
		//perform action listener
		dateButton.addActionListener(new EditDateButtonPress()) ;

		eventRoom = new JLabel("Event Room");
		eventRoom.setBounds(lb.getWidth() + lb.getX() + 10, eventDateTXT.getY() + 25, width, 25);
		eventRoom.setForeground(Color.blue); //CHANGE Color
		eventRoom.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(eventRoom);

		eventRoomText = new JTextField(20);
		eventRoomText.setBounds(lb.getWidth() + lb.getX() + 10, eventRoom.getY() + 25, width, 25);
		newEventPanel.add(eventRoomText);
		eventRoomText.addKeyListener(new EnterButtonPress());
		eventRoomText.addFocusListener(HIGHLIGHTER);

		lightSetting = new JLabel("Light Setting");
		lightSetting.setBounds(lb.getWidth() + lb.getX() + 10, eventRoomText.getY() + 25, width, 25);
		lightSetting.setForeground(Color.blue); //CHANGE Color
		lightSetting.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(lightSetting);

		lightSettingTXT = new JTextField(20);
		lightSettingTXT.setBounds(lb.getWidth() + lb.getX() + 10, lightSetting.getY() + 25, width, 25);
		newEventPanel.add(lightSettingTXT);
		lightSettingTXT.addKeyListener(new EnterButtonPress());
		lightSettingTXT.addFocusListener(HIGHLIGHTER);

		temperatureSetting = new JLabel("Temperature Setting");
		temperatureSetting.setBounds(lb.getWidth() + lb.getX() + 10, lightSettingTXT.getY() + 25, width, 25);
		temperatureSetting.setForeground(Color.blue); //CHANGE Color
		temperatureSetting.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(temperatureSetting);

		temperatureSettingTXT = new JTextField(20);
		temperatureSettingTXT.setBounds(lb.getWidth() + lb.getX() + 10, temperatureSetting.getY() + 25, width, 25);
		newEventPanel.add(temperatureSettingTXT);
		temperatureSettingTXT.addKeyListener(new EnterButtonPress());
		temperatureSettingTXT.addFocusListener(HIGHLIGHTER);


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
					eventDateTXT.setText(s.getStartDateTime().toString());
					eventRoomText.setText(s.getRoomName());
					lightSettingTXT.setText(String.valueOf(s.getLightIntensity()));
					temperatureSettingTXT.setText(String.valueOf(s.getTemperatureSetpoint()));

				}
			}

		}                                         
	} 

	//Date Picker
	public final class EditDateButtonPress implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {

			final JFrame f = new JFrame();
			//set text which is collected by date picker i.e. set date 
			eventDateTXT.setText(new DatePicker().setPickedDate());
		}

	}


	// Cnacel Button
	private final class cancelButtonPress implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			newEventWin.dispose();
		}
	}



	public void editedEvent(){

		if(eventNameTXT.getText().isEmpty() || eventStartTimeTXT.getText().isEmpty() || eventEndTimeTXT.getText().isEmpty() ||
				eventDateTXT.getText().isEmpty() || eventRoomText.getText().isEmpty() || lightSettingTXT.getText().isEmpty() 
				|| temperatureSettingTXT.getText().isEmpty()){
			JOptionPane.showMessageDialog(null,"A required Field is empty, Please complete all fields"); 

			newEventWin.setVisible(true); 

		}else{


			String eventName = eventNameTXT.getText(); 
			String startTime = eventStartTimeTXT.getText(); 
			String endTime= eventEndTimeTXT.getText(); 
			String eventDate = eventDateTXT.getText(); 
			String eventRoom = eventRoomText.getText(); 
			String lightIntensity = lightSettingTXT.getText(); 
			String temperatureSetpoint = temperatureSettingTXT.getText(); 


			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); /// TODO REMOVE SECONDS

			try{
				Date startDateTime = df.parse(eventDate + " " + startTime);
				Date endDateTime = df.parse(eventDate + " " + endTime); /// ADD END DATE NOT IMPLEMENTD



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
				JOptionPane.showMessageDialog(null, "Submitting New Event Request");

			}
			catch(ParseException e){

				JOptionPane.showMessageDialog(null,"Date entered is not the correct format"); 
			}

		}

	}

	//Submit Button
	private final class editEvent implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			editedEvent();
		}
	}

	//Enter Button Press
	private final class EnterButtonPress extends KeyAdapter {

		public void keyPressed(KeyEvent e) {


			if (e.getKeyCode()== KeyEvent.VK_ENTER) {

				editedEvent();
			}
		}

	}		
	private final class getEvent implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			//JOptionPane.showMessageDialog(null, "Retrieving Event Information");



		}

	}

}

