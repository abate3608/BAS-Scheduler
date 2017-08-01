package edu.psu.sweng500.userinterface.calendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import edu.psu.sweng500.userinterface.BacnetConfig;
import edu.psu.sweng500.userinterface.scheduling.ScheduleImporterConfigMenu;

/**
 * Menu bar displayed on the CalendarScreen.
 * 
 * @author awb
 */
public class BasgsMenuBar extends JMenuBar
{
	/** default */
	private static final long serialVersionUID = 1L;
	
	public BasgsMenuBar() 
	{
		JMenu fileMenu = new JMenu("File");
		this.add(fileMenu);
		JMenuItem fileMenuExitItem = new JMenuItem("Exit");
		fileMenuExitItem.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JOptionPane.showMessageDialog(null, "User Signed Out");
				System.exit(0);
			}
		});
		fileMenu.add(fileMenuExitItem);

//		JMenu viewMenu = new JMenu("View");
//		this.add(viewMenu);
//		JMenuItem viewMenuCalendarItem = new JMenuItem("Calendar");
//		viewMenu.add(viewMenuCalendarItem);
//		JMenuItem viewMenuEventsItem = new JMenuItem("Events");
//		viewMenu.add(viewMenuEventsItem);
//		JMenuItem viewMenuRoomsItem = new JMenuItem("Rooms");
//		viewMenu.add(viewMenuRoomsItem);

		JMenu configMenu = new JMenu("Configuration");
		this.add(configMenu);
		JMenuItem configMenuSystemItem = new JMenuItem("System");
		configMenu.add(configMenuSystemItem);

		JMenuItem configMenuBacnetItem = new JMenuItem("BACnet Server");
		configMenuBacnetItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				new BacnetConfig().create();
			}
		});
		configMenu.add(configMenuBacnetItem);

		JMenuItem configMenuXMLItem = new JMenuItem("XML Importer");
		configMenuXMLItem.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				new ScheduleImporterConfigMenu();
			}
		});
		configMenu.add(configMenuXMLItem);
		JMenuItem configMenuApiItem = new JMenuItem("API");
		configMenu.add(configMenuApiItem);

//		JMenu helpMenu = new JMenu("Help");
//		this.add(helpMenu);
//		JMenuItem helpMenuAboutItem = new JMenuItem("About");
//		helpMenu.add(helpMenuAboutItem);
	}

}
