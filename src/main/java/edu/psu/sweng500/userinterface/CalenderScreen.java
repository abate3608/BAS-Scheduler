package edu.psu.sweng500.userinterface;


import javax.swing.*;
import javax.swing.table.*;

import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import edu.psu.sweng500.type.*;
import edu.psu.sweng500.userinterface.LogScreen;

public class CalenderScreen {
	static JLabel temperature;
	static JLabel humidity;
	static JLabel loginStatus;
	
	static JLabel monthName;
	static JLabel yearNumber;
	static JFrame panelLayout;
	static Container windowLayout;
	static JButton backBTN;
	static JButton nextBTN;
	static JButton newEventBTN;
	static JButton editEventBTN; 
	static JPanel roomPanel;
	static JPanel calenderWindow;
	static JPanel navigationWindow;
	static JTable calendarTable;
	static JScrollPane calenderScroll;
	static JComboBox<String> calendarYear;
	static DefaultTableModel calenderTable;

	static int year;
	static int month;
	static int day;
	static int getYear;
	static int getMonth;

	// Event listeners
	private final static EventHandler eventHandler = EventHandler.getInstance();

	private static DBWeatherTable weather = new DBWeatherTable();

	private static boolean isAuthenticated;

	private final static LogScreen loginScreen = new LogScreen();

	private BacnetConfig BacnetConfigScreen = new BacnetConfig();
	
	public CalenderScreen() {

		// setup event
		eventHandler.addListener(new EventQueueListener());

		// JAVA FRAME SETUP
		panelLayout = new JFrame("Global Schedular System");
		// panelLayout.setSize(1375, 750); //USED TO SIZE FRAME
		panelLayout.setExtendedState(JFrame.MAXIMIZED_BOTH);
		panelLayout.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		panelLayout.setLayout(new GridLayout());
		panelLayout.validate();
		panelLayout.pack();
		panelLayout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelLayout.setResizable(true);
		panelLayout.setVisible(true);

		windowLayout = panelLayout.getContentPane();
		windowLayout.setLayout(null); // "NULL" LAYOUT
		
		temperature = new JLabel ("Temperature");
		temperature.setBounds(900, 50, 200, 30);
		humidity = new JLabel ("Humidity");
		humidity.setBounds(1100, 50, 200, 30);
		
		loginStatus = new JLabel ("User: ");
		loginStatus.setBounds(900, 70, 200, 30);
		
		monthName = new JLabel("January");

		calendarYear = new JComboBox<String>();
		calendarYear.addActionListener(new calendarYearPress());
		calendarYear.setBounds(800, 115, 80, 20);

		calenderTable = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return true;
			}
		};

		
		// Calendar Table
		calendarTable = new JTable(calenderTable);
		calendarTable.setColumnSelectionAllowed(true); // Single cell selection
		calendarTable.setRowSelectionAllowed(true);// Single cell selection
		calendarTable.setRowHeight(calendarTable.getRowHeight() + 72);
		calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		calendarTable.getTableHeader().setResizingAllowed(true);
		calendarTable.getTableHeader().setReorderingAllowed(false);

		// Calendar Scrolling
		calenderScroll = new JScrollPane(calendarTable);
		calenderScroll.setBounds(300, 140, 1035, 550); // Used to change
		// calendar Pane size
		// and Location

		// Back and Next Buttons
		backBTN = new JButton("<< BACK");
		nextBTN = new JButton("NEXT >>");
		backBTN.setBounds(300, 115, 148, 25);
		nextBTN.setBounds(1170, 115, 148, 25);
		backBTN.addActionListener(new backBTNPress());
		nextBTN.addActionListener(new nextBTNPress());

		// schedule event Button
		newEventBTN = new JButton("New Event");
		newEventBTN.setBounds(15, 140, 148, 25); // Button Sizing and Location
		newEventBTN.addActionListener(new NewEventScreen());

		// edit event Button
		// -------------------------------------------------------------NEED
		// ACTIONLISTNER
		editEventBTN = new JButton("Edit Event");
		editEventBTN.setBounds(150, 140, 148, 25); // Button Sizing and Location
		editEventBTN.addActionListener(new EditEventScreen());
		
		// roomPanel
		roomPanel = new JPanel(null);
		roomPanel.setBorder(BorderFactory.createTitledBorder("Monthly Events"));
		roomPanel.setBounds(16, 170, 280, 520);
		roomPanel.setLayout(new BoxLayout(roomPanel,BoxLayout.PAGE_AXIS)); 

		// Calendar Window -DONT MOVE POSITION
		calenderWindow = new JPanel(null);
		calenderWindow.setSize(Toolkit.getDefaultToolkit().getScreenSize());

		// calenderWindow.setSize(1000,500);
		calenderWindow.setBorder(BorderFactory.createTitledBorder("Global Schedular Hompage"));
		// Add controls to calenderWindow
		
		
		 
		
		 windowLayout.setLayout(new BorderLayout());
		 windowLayout.add(createMenuBar(), BorderLayout.NORTH);
		windowLayout.add(calenderWindow);
		calenderWindow.setBackground(null);
		
		
		calenderWindow.add(temperature);
		calenderWindow.add(humidity);
		calenderWindow.add(loginStatus);
		
		calenderWindow.add(monthName);
		calenderWindow.add(calendarYear);
		calenderWindow.add(backBTN);
		calenderWindow.add(nextBTN);
		calenderWindow.add(newEventBTN);
		calenderWindow.add(editEventBTN);
		calenderWindow.add(calenderScroll);
		calenderWindow.add(roomPanel);

		// Create calendar
		GregorianCalendar cal = new GregorianCalendar(); // Used to generate
		// calendar
		day = cal.get(GregorianCalendar.DAY_OF_MONTH);
		month = cal.get(GregorianCalendar.MONTH);
		year = cal.get(GregorianCalendar.YEAR);
		getMonth = month;
		getYear = year;

		// Add days
		String[] headers = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
		for (int i = 0; i < 7; i++) {
			calenderTable.addColumn(headers[i]);
		}
		calendarTable.getParent().setBackground(calendarTable.getBackground()); // Set
		// background
		// ^^DO
		// NOT
		// MOVE^^
		calenderTable.setColumnCount(7);
		calenderTable.setRowCount(6);

		for (int i = year; i <= year + 100; i++) {
			calendarYear.addItem(String.valueOf(i));
		}


		loginScreen.create();

		updateCalendar(month, year); // Refresh calendar

	}

	public JMenuBar createMenuBar () {
		
		JMenuBar b = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		b.add(fileMenu);
		JMenuItem fileMenuExitItem = new JMenuItem("Exit");
		fileMenu.add(fileMenuExitItem);
		
		JMenu viewMenu = new JMenu("View");
		b.add(viewMenu);
		JMenuItem viewMenuCalendarItem = new JMenuItem("Calendar");
		viewMenu.add(viewMenuCalendarItem);
		JMenuItem viewMenuEventsItem = new JMenuItem("Events");
		viewMenu.add(viewMenuEventsItem);
		JMenuItem viewMenuRoomsItem = new JMenuItem("Rooms");
		viewMenu.add(viewMenuRoomsItem);
		
		JMenu configMenu = new JMenu("Configuration");
		b.add(configMenu);
		JMenuItem configMenuSystemItem = new JMenuItem("System");
		configMenu.add(configMenuSystemItem);
		
		JMenuItem configMenuBacnetItem = new JMenuItem("BACnet Server");
		configMenuBacnetItem.addActionListener(new configMenuBacnetItemClick());
		configMenu.add(configMenuBacnetItem);
		
		
		JMenuItem configMenuXMLItem = new JMenuItem("XML Importer");
		configMenu.add(configMenuXMLItem);
		JMenuItem configMenuApiItem = new JMenuItem("API");
		configMenu.add(configMenuApiItem);
		
		JMenu helpMenu = new JMenu("Help");
		b.add(helpMenu);
		JMenuItem helpMenuAboutItem = new JMenuItem("About");
		helpMenu.add(helpMenuAboutItem);
		
		
		return b;
		
	}
	
	
	public static void updateCalendar(int month, int year) {

		String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		int numDays, startMonth; // Number Of Days, Start Of Month
		backBTN.setEnabled(true);
		nextBTN.setEnabled(true);
		if (month == 0 && year <= year - 10) {
			backBTN.setEnabled(false);
		}
		if (month == 11 && year >= year + 100) {
			nextBTN.setEnabled(false);
		}
		monthName.setText(monthNames[month]);
		monthName.setBounds(750 - monthName.getPreferredSize().width / 2, 112, 180, 25); // Adjust
		// Month
		// Text
		// location
		calendarYear.setSelectedItem(String.valueOf(year));
		// Clear table
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				calenderTable.setValueAt(null, i, j);
			}
		}

		// Get monthly configuration
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		numDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		startMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);


		// Draw calendar
		for (int i = 1; i <= numDays; i++) {
			int calendarRow = new Integer((i + startMonth - 2) / 7);
			int calendarColumn = (i + startMonth - 2) % 7;
			calenderTable.setValueAt(i, calendarRow, calendarColumn);
		}
		// Apply renderers
		calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new tblCalendarRenderer());

		//clear room panel because month refresh
		roomPanel.removeAll();
		roomPanel.revalidate();
		roomPanel.repaint();   // This is required in some cases

		// request events
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
		Date Start;
		Date Stop;
		String stringCurrentMonth= Integer.toString(month+1);

		try {
			Start = df.parse(stringCurrentMonth + "/01/" + year);
			Stop = df.parse(stringCurrentMonth + "/" + numDays + "/" + year);

			eventHandler.fireGetEvents(Start, Stop);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}





	}

	// use to show scheduled events
	static class tblCalendarRenderer extends DefaultTableCellRenderer {

		public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
				int row, int column) {

			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			if (column == 0 || column == 6) {
				setBackground(new Color(255, 255, 255));
			} else {
				setBackground(new Color(255, 255, 255));
			}
			if (value != null) {
				if (Integer.parseInt(value.toString()) == day && getMonth == month && getYear == year) { // use
					// to
					// schedule
					// events
					setBackground(new Color(220, 220, 255));
				}
			}
			setBorder(null);
			setForeground(Color.black);
			return this;
		}
	}

	static class backBTNPress implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (getMonth == 0) {
				getMonth = 11;
				getYear -= 1;
			} else {
				getMonth -= 1;
			}
			updateCalendar(getMonth, getYear);
		}
	}

	static class nextBTNPress implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (getMonth == 11) {
				getMonth = 0;
				getYear += 1;
			} else {
				getMonth += 1;
			}
			updateCalendar(getMonth, getYear);
		}
	}

	static class calendarYearPress implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (calendarYear.getSelectedItem() != null) {
				String b = calendarYear.getSelectedItem().toString();
				getYear = Integer.parseInt(b);
				updateCalendar(getMonth, getYear);

			}

		}
	}

	public EventHandler getEventHandler() {
		return eventHandler;
	}

	private final class configMenuBacnetItemClick implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			BacnetConfigScreen.create();

		}
	}
	static class EventQueueListener extends EventAdapter {
		// listen to event queue

		@Override
		public void eventUpdate(ArrayList<DBScheduleTable> sList) {
			for(DBScheduleTable s : sList) {
				System.out.println("CalendarScreen > Schedule event update received. Schedule Name: " + s.getName());
	
				if (!isAuthenticated) {
					System.out.println("CalendarScreen > User is not authenticated. Exist update calendar screen.");
					return;
				}
				boolean hasComponent = false;
				String eventDes = "<html>" + s.getName() + ": " + s.getDescription() + " "+ s.getStartDateTime() + " - " + s.getEndDateTime()+"</hmtl>";
				for (Component jc : roomPanel.getComponents()) {
					if ( jc instanceof JLabel ) {
						if (((JLabel) jc).getText().equals(eventDes)) { hasComponent = true; }
					}
				}
				if (!hasComponent) { roomPanel.add(new JLabel(eventDes)); }
			}
		}

		@Override
		public void weatherInfoUpdate(DBWeatherTable w) {
			System.out.println("CalendarScreen > Received weather data from DB for SiteID: " + w.getSiteId() + " Temperature: " + w.getTemperature());
			weather = w; //write data from db to local variable
			temperature.setText("Outside Temperature: " + w.getTemperature() + " F");
			humidity.setText("Humdity: " + w.getHumidity() + " %");
		}

		@Override
		public void authenticateUserUpdate(User u) {
			System.out.println("CalendarScreen > Authentication user update received. User: " + u.getUserName() + " isAuthenicated:" + u.isAuthenticated());
			isAuthenticated = false;
			if (u.getUserName() == loginScreen.getUserName())
			{
				isAuthenticated = u.isAuthenticated();
				if (isAuthenticated) {
					//update the calendar screen
					if (calendarYear.getSelectedItem() != null) {
						String b = calendarYear.getSelectedItem().toString();
						getYear = Integer.parseInt(b);
						updateCalendar(getMonth, getYear);
						loginStatus.setText("User: " + u.getUserName());
					}
				}
				//new CalenderScreen();
				//logWin.dispose();

			} 
		}
	}
}
