package edu.psu.sweng500.userinterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.List;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.DBScheduleTable;
import edu.psu.sweng500.userinterface.NewEventScreen.EndDateButtonPress;
//import edu.psu.sweng500.userinterface.NewEventScreen.EnterButtonPress;
import edu.psu.sweng500.userinterface.datepicker.DatePicker;
import edu.psu.sweng500.userinterface.datepicker.TimePicker;


public class EditEventScreen implements ActionListener {

	private static JFrame newEventWin;
	private JPanel newEventPane;
	private JLabel eventStartTime;
	private JLabel eventEndTime;
	private JLabel eventDate;
	private JLabel eventRoom;
	private JLabel lightSetting;
	private JLabel temperatureSetting;
	
	private JLabel eventName;
	private JTextField eventNameTXT;
	private JLabel endDate;
	private JTextField endEventDateTXT;
	
	private JComboBox eventStartTimeTXT;
	private JComboBox eventEndTimeTXT;
	private JTextField eventDateTXT;
	private JTextField eventRoomText; 
	private JTextField lightSettingTXT;
	private JTextField temperatureSettingTXT;
	private JButton editEventButton;
	private JButton cancelButton;
	
	private TimePicker timeStart;////////////////////////////////////////////////////////////////////CHANGED 7/29
	private TimePicker timeEnd;////////////////////////////////////////////////////////////////////////CHANGEd 7/29
	
	
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
		newEventWin.setSize(525, 575);
		newEventWin.setLocationRelativeTo(null);
		//newEventWin.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//newEventWin.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		newEventWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		newEventPane = new JPanel();
		newEventWin.add(newEventPane);
		newuserLayout(newEventPane);

		newEventWin.setVisible(true);

		//JOptionPane.showMessageDialog(null, "Enter Event Name and Press 'Get Event Details Button'");
	}

	private final void newuserLayout(JPanel newEventPanel) {

		int width = 140;
		newEventPanel.setLayout(null);

		newEventPanel.setBorder(BorderFactory.createTitledBorder("Edit An Existing Event"));
		//newEventPanel.setBackground(new Color(218, 247, 159)); //CHANGE Color

		lb.clear();
		for(DBScheduleTable s : schedules) {
			lb.add(s.getName());
			
		}
		

//		String[] timeList = {
//				 "0:30",
//		         "1:00",
//		         "1:30",
//		         "2:00",
//		         "2:30",
//		         "3:00",
//		         "3:30",
//		         "4:00",
//		         "4:30",
//		         "5:00",
//		         "5:30",
//		         "6:00",
//		         "6:30",
//		         "7:00",
//		         "7:30",
//		         "8:00",
//		         "8:30",
//		         "9:00",
//		         "9:30",
//		         "10:00",
//		         "10:30",
//		         "11:00",
//		         "11:30",
//		         "12:00",
//		         "12:30",
//		         "13:00",
//		         "13:30",
//		         "14:00",
//		         "14:30",
//		         "15:00",
//		         "15:30",
//		         "16:00",
//		         "16:30",
//		         "17:00",
//		         "17:30",
//		         "18:00",
//		         "18:30",
//		         "19:00",
//		         "19:30",
//		         "20:00",
//		         "20:30",
//		         "21:00",
//		         "21:30",
//		         "22:00",
//		         "22:30",
//		         "23:00",
//		         "23:30",
//		         "24:00"
//		     
//		};

		
		lb.setBounds(20, 25, 150, 300); ///////////////////////////////////////////////////////////////////CHANGED 7/29
		lb.addMouseListener(new lbMouseClicked());
		newEventPanel.add(lb);

		

		eventName = new JLabel("Event Name");
		eventName.setBounds(lb.getWidth() + lb.getX() + 10, lb.getY(), 120, 25);
		newEventPanel.add(eventName);

		eventNameTXT = new JTextField(20);
		eventNameTXT.setBounds(lb.getWidth() + lb.getX() + 10, eventName.getY() + 25, width, 25);
		newEventPanel.add(eventNameTXT);
		eventNameTXT.addKeyListener(new EnterButtonPress());
		//eventNameTXT.addMouseListener(new EventMouseClicked());
		 
		
		
		eventStartTime = new JLabel("Event Start Time");
		eventStartTime.setBounds(lb.getWidth() + lb.getX() + 10, eventNameTXT.getY() + 25, width, 25);
		newEventPanel.add(eventStartTime);


//		//eventStartTimeTXT = new JTextField(20);
//		eventStartTimeTXT = new JComboBox(timeList);
//		eventStartTimeTXT.setEditable(true);
//		eventStartTimeTXT.setBounds(lb.getWidth() + lb.getX() + 10, eventStartTime.getY() + 25, width, 25);
//		newEventPanel.add(eventStartTimeTXT);
//		eventStartTimeTXT.addKeyListener(new EnterButtonPress());
//		eventStartTimeTXT.addFocusListener(HIGHLIGHTER);
		
		
		timeStart = new TimePicker();///////////////////////////////////////////////////////////////////////////////////CHANGED 7/29
		//timeStart.setBounds(20, 100, 160, 25); //Location
		timeStart.setBounds(lb.getWidth() + lb.getX() + 10, eventStartTime.getY() + 25, width, 25);
		 newEventPanel.add(timeStart);


		eventEndTime = new JLabel("Event End Time");
		//eventEndTime.setBounds(lb.getWidth() + lb.getX() + 10, eventStartTimeTXT.getY() + 25, width, 25);
		eventEndTime.setBounds(lb.getWidth() + lb.getX() + 10, timeStart.getY() + 25, width, 25); /////////////////////////CHANGED 7/29
		newEventPanel.add(eventEndTime);

		/*//eventEndTimeTXT = new JTextField(20);
		eventEndTimeTXT = new JComboBox(timeList);
		eventEndTimeTXT.setEditable(true);
		eventEndTimeTXT.setBounds(lb.getWidth() + lb.getX() + 10, eventEndTime.getY() + 25, width, 25);
		newEventPanel.add(eventEndTimeTXT);
		eventEndTimeTXT.addKeyListener(new EnterButtonPress());
		eventEndTimeTXT.addFocusListener(HIGHLIGHTER);*/
		
		timeEnd = new TimePicker(); //////////////////////////////////////////////////////////////////////////////////CHANGED 7/29
		//timeEnd.setBounds(20, 150, 160, 25); //Location
		timeEnd.setBounds(lb.getWidth() + lb.getX() + 10, eventEndTime.getY() + 25, width, 25);
		 newEventPanel.add(timeEnd);

		eventDate = new JLabel("Start Date of Event");
		//eventDate.setBounds(lb.getWidth() + lb.getX() + 10, eventEndTimeTXT.getY() + 25, 100, 25);
		eventDate.setBounds(lb.getWidth() + lb.getX() + 10, timeEnd.getY() + 25, 100, 25); ////////////////////CHANGED 7/29
		newEventPanel.add(eventDate);

		eventDateTXT = new JTextField(20);
		eventDateTXT.setBounds(lb.getWidth() + lb.getX() + 10, eventDate.getY() + 25, width, 25);
		newEventPanel.add(eventDateTXT);
		eventDateTXT.addKeyListener(new EnterButtonPress());
		eventDateTXT.addFocusListener(HIGHLIGHTER);

		//create button and there object
		JButton dateButton = new JButton("Start Date");/////////////////////////////////////////////////////////////////////////////////CHANGED 7/29
		dateButton.setBounds(eventDateTXT.getWidth() + eventDateTXT.getX() + 5, eventDate.getY() + 25, 100, 25); /////////////////////CHANGED 7/29
		//JButton dateButton = new JButton("+");
		//dateButton.setBounds(eventDateTXT.getWidth() + eventDateTXT.getX() + 5, eventDate.getY() + 25, 20, 25);
		newEventPanel.add(dateButton);
		//perform action listener
		dateButton.addActionListener(new StartDateButtonPress()) ;
		
		endDate = new JLabel("End Date of Event"); ////////////////////////////////////////////////////////////////////////////////CHANged 7/29
		endDate.setBounds(lb.getWidth() + lb.getX() + 10, eventDateTXT.getY() + 25, width, 25);
		//endDate.setFont(new Font("Arial",Font.BOLD,14));//CHANGE Color
		newEventPanel.add(endDate);

		
		endEventDateTXT = new JTextField(20);
		endEventDateTXT.setBounds(lb.getWidth() + lb.getX() + 10, endDate.getY() + 25, width, 25);
		newEventPanel.add(endEventDateTXT);
		endEventDateTXT.addKeyListener(new EnterButtonPress());
		endEventDateTXT.addFocusListener(HIGHLIGHTER);
		
		//create button and there object
		JButton enddateButton = new JButton("End Date");
		enddateButton.setBounds(endEventDateTXT.getWidth() + endEventDateTXT.getX() + 5, endDate.getY() + 25, 100, 25);
		newEventPanel.add(enddateButton);
		//perform action listener
		enddateButton.addActionListener(new EndDateButtonPress()) ;
		
		
		eventRoom = new JLabel("Event Room");
		eventRoom.setBounds(lb.getWidth() + lb.getX() + 10, endEventDateTXT.getY() + 25, width, 25); /////////***********************
		newEventPanel.add(eventRoom);
		
		eventRoomText = new JTextField(20);
		eventRoomText.setBounds(lb.getWidth() + lb.getX() + 10, eventRoom.getY() + 25, width, 25);
		newEventPanel.add(eventRoomText);
		eventRoomText.addKeyListener(new EnterButtonPress());
		eventRoomText.addFocusListener(HIGHLIGHTER);

		lightSetting = new JLabel("Light Setting");
		lightSetting.setBounds(lb.getWidth() + lb.getX() + 10, eventRoomText.getY() + 25, width, 25);
		newEventPanel.add(lightSetting);

		lightSettingTXT = new JTextField(20);
		lightSettingTXT.setBounds(lb.getWidth() + lb.getX() + 10, lightSetting.getY() + 25, width, 25);
		newEventPanel.add(lightSettingTXT);
		lightSettingTXT.addKeyListener(new EnterButtonPress());
		lightSettingTXT.addFocusListener(HIGHLIGHTER);

		temperatureSetting = new JLabel("Temperature Setting");
		temperatureSetting.setBounds(lb.getWidth() + lb.getX() + 10, lightSettingTXT.getY() + 25, width, 25);
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
		
		//lb.select(0);
		//populateEditFields();
		
	}

	private void populateEditFields() {
		String name = lb.getSelectedItem();

		for(DBScheduleTable s : schedules) {
			if (name.equalsIgnoreCase(s.getName())) {
				DateFormat dateFormat = new SimpleDateFormat("H:mm");
				
				//eventStartTimeTXT.getEditor().setItem(dateFormat.format(s.getStartDateTime()));
				//eventEndTimeTXT.getEditor().setItem(dateFormat.format(s.getEndDateTime()));
				
				
				eventNameTXT.setText(s.getName());
				
				
				timeStart.setSelectedItem(s.getStartDateTime()); ///////////////////////////////CHANGED 7/29
				timeEnd.setSelectedItem(s.getStartDateTime()); /////////////////////////////////CHANGED 7/29
				
				
				eventDateTXT.setText(s.getStartDateTime().toString());
				
				endEventDateTXT.setText(s.getEndDateTime().toString());
				
				eventRoomText.setText(s.getRoomName());
				lightSettingTXT.setText(String.valueOf(s.getLightIntensity()));
				temperatureSettingTXT.setText(String.valueOf(s.getTemperatureSetpoint()));

			}
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
			populateEditFields();

		}                                         
	} 

	//Start Date Picker
	public final class StartDateButtonPress implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {

			final JFrame f = new JFrame();
			//set text which is collected by date picker i.e. set date 
			eventDateTXT.setText( DatePicker.showDatePickerDialog() );
		}

	}

	// EndDate Picker
	public final class EndDateButtonPress implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {

			//set text which is collected by date picker i.e. set date 
			endEventDateTXT.setText( DatePicker.showDatePickerDialog() );
			
		}

	}

	// Cnacel Button
	private final class cancelButtonPress implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			newEventWin.dispose();
		}
	}



	public void editedEvent(){

		/////////////////////////////CHANGED 7/29
		
		if(eventNameTXT.getText().isEmpty() /*|| eventStartTimeTXT.getSelectedItem().toString().isEmpty() || eventEndTimeTXT.getSelectedItem().toString().isEmpty() */
				||eventDateTXT.getText().isEmpty() || endEventDateTXT.getText().isEmpty() || eventRoomText.getText().isEmpty() || lightSettingTXT.getText().isEmpty() 
				|| temperatureSettingTXT.getText().isEmpty()){
			JOptionPane.showMessageDialog(null,"A required Field is empty, Please complete all fields"); 

			newEventWin.setVisible(true); 

		}else{

			DateFormat clockStart = new SimpleDateFormat("HH:mm:ss"); ///////////////////////////////////////CHANGED 7/29
			DateFormat clockEnd = new SimpleDateFormat("HH:mm:ss"); /////////////////////////////////////////Changed 7/29

			String eventName = eventNameTXT.getText(); ////////////////////////////////////////////////CHANGED 7/29
			
			//String startTime = eventStartTimeTXT.getSelectedItem().toString(); 
			//String endTime= eventEndTimeTXT.getSelectedItem().toString(); 
			
			String startTime = clockStart.format((Date)timeStart.getSelectedItem());///////////////////////////////////////CHANGED 7/29
			String endTime= clockEnd.format((Date)timeEnd.getSelectedItem());///////////////////////////////////////CHANGED 7/29
			String eventDate = eventDateTXT.getText(); 
			
			String endeventDate = endEventDateTXT.getText();
			
			String eventRoom = eventRoomText.getText(); 
			String lightIntensity = lightSettingTXT.getText(); 
			String temperatureSetpoint = temperatureSettingTXT.getText(); 


			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			try{
				Date startDateTime = df.parse(eventDate + " " + startTime);
				Date endDateTime = df.parse(endeventDate + " " + endTime); /// ADD END DATE NOT IMPLEMENTD



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
				
				
				eventHandler.fireUpdateEvent(s); ///////////////////////////////////////////////////////CHANGED 7/29
				
				
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

	static class EventQueueListener extends EventAdapter {
		// listen to event queue

		
		
////////////////////////////////////////////////////////////////////////////////////////////////////////CHANGED BELOW		
		
		@Override
		public void createEventRespond(DBScheduleTable s, int err) {
			System.out.println("NewEventScreen > Create event respond received. Name: " + s.getName() + " Error Code:" + err);
			if (err == 0) //good
			{
				//new CalenderScreen();
				JOptionPane.showMessageDialog(null,"Event Scheduled");
				newEventWin.dispose();

			}	
			/*		
			 else if (err == 1){

			//DO SOMETHING: user user created
			JOptionPane.showMessageDialog(null,"Start Time Conflict, Please Provide a different Start Time");
			}	

			else if (err == 2) {

					//DO SOMETHING: user user created
			JOptionPane.showMessageDialog(null,"End Time Conflict, Please Provide a different End Time");

			} 

			else if (err == 3){

			//DO SOMETHING: user user created
			JOptionPane.showMessageDialog(null,"Room Conflict, The room that is selected is occupied during this time. Please select a different room");
			}	

			else if (err == 4){

				//DO SOMETHING: user user created
				JOptionPane.showMessageDialog(null,"Date Conflict, Please select a different date");
				}	*/


			else			
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

