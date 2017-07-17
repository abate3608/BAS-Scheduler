package edu.psu.sweng500.userinterface;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.DBBacnetDevicesTable;

public class BacnetConfig {
	public static JFrame bacnetWin;
	private JPanel bacnetPanel;
	private JLabel lblObject_Identifier;
	private JLabel lblDevice_Address_Binding;
	private JLabel lblPort;

	private static JTextField txtObject_Identifier;
	private static JTextField txtDevice_Address_Binding;
	private static JTextField txtPort;

	private JButton btnUpdate;
	private JButton btnCancel;

	private String object_identifier;
	private String device_address_binding;
	private String port;

	// Event listeners
	private final EventHandler eventHandler = EventHandler.getInstance();

	public BacnetConfig() {
		this.object_identifier = "";
		this.device_address_binding = "";
		this.port = "";

	}

	public void create () {

		// setup event
		eventHandler.addListener(new EventQueueListener());

		bacnetWin = new JFrame("BACnet Server Configuration");
		bacnetWin.setSize(290, 250);
		bacnetWin.setLocationRelativeTo(null);  
		bacnetWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		bacnetPanel = new JPanel();
		bacnetWin.add(bacnetPanel);
		panelLayout(bacnetPanel);
		bacnetWin.setVisible(true);
		
		eventHandler.fireGetBacnetDeviceRequest();
	}

	public void dispose () {
		//logWin.dispose();
		bacnetWin.setVisible(false); 
	}

	private final void panelLayout(JPanel logPanel) {

		logPanel.setLayout(null);
		logPanel.setBorder(BorderFactory.createTitledBorder("BACnet Server Configuration"));

		lblObject_Identifier = new JLabel("BACnet ID");
		lblObject_Identifier.setBounds(20, 25, 80, 25);
		logPanel.add(lblObject_Identifier);

		txtObject_Identifier = new JTextField();
		txtObject_Identifier.setBounds(90, 25, 160, 25);
		logPanel.add(txtObject_Identifier);

		//userNameText.getText();
		txtObject_Identifier.addKeyListener(new EnterButtonPress());
		txtObject_Identifier.addMouseListener(new txtObject_IdentifierMouseClicked());

		lblDevice_Address_Binding = new JLabel("IP Address");
		lblDevice_Address_Binding.setBounds(20, 55, 80, 25);
		logPanel.add(lblDevice_Address_Binding);

		txtDevice_Address_Binding = new JTextField();
		txtDevice_Address_Binding.setBounds(90, 55, 160, 25);
		logPanel.add(txtDevice_Address_Binding);

		//userNameText.getText();
		txtDevice_Address_Binding.addKeyListener(new EnterButtonPress());
		txtDevice_Address_Binding.addMouseListener(new txtDevice_Address_BindingMouseClicked());

		lblPort = new JLabel("Port");
		lblPort.setBounds(20, 85, 80, 25);
		logPanel.add(lblPort);

		txtPort = new JTextField();
		txtPort.setBounds(90, 85, 160, 25);
		logPanel.add(txtPort);

		//userNameText.getText();
		txtPort.addKeyListener(new EnterButtonPress());
		txtPort.addMouseListener(new txtPortMouseClicked());

		btnUpdate = new JButton("Update");
		btnUpdate.setBounds(55, 135, 80, 25);
		logPanel.add(btnUpdate);
		btnUpdate.addActionListener(new updateButtonPress());

		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(150, 135, 80, 25);
		logPanel.add(btnCancel);
		btnCancel.addActionListener(new cancelButtonPress());
	}


	private final class updateButtonPress implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TEam 7 To Do

			updateBacnetDevice();
			bacnetWin.setVisible(false); 
			//logWin.dispose(); 

		}
	}

	private final class cancelButtonPress implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			bacnetWin.setVisible(false);
			bacnetWin.dispose();
			//logWin.dispose(); 

		}
	}

	//Enter Button Press
	private final class EnterButtonPress extends KeyAdapter {

		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode()== KeyEvent.VK_ENTER) {
				updateBacnetDevice();

				bacnetWin.setVisible(false); 
				bacnetWin.dispose(); 
			}
		}
	}

	private void updateBacnetDevice() {
		object_identifier = txtObject_Identifier.getText();
		device_address_binding = txtDevice_Address_Binding.getText();
		port = txtPort.getText();


		DBBacnetDevicesTable d = new DBBacnetDevicesTable ();
		d.setObject_Identifier(object_identifier);
		d.setDevice_Address_Binding(device_address_binding);
		d.setPort(port);
		// fire request event
		eventHandler.fireSetBacnetDeviceRequest(d);	
	}

	//User Name Highlight
	public final class txtObject_IdentifierMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {		
		}

		public void mouseEntered(MouseEvent arg0) {
			txtObject_Identifier.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			txtObject_Identifier.setBackground(Color.white );			
		}

		public void mousePressed(MouseEvent arg0) {
			txtObject_Identifier.setBackground(Color.white );		
		}

		public void mouseReleased(MouseEvent arg0) {
			txtObject_Identifier.setBackground(Color.white );
		}                                         
	}               


	public final class txtDevice_Address_BindingMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {	
		}

		public void mouseEntered(MouseEvent arg0) {
			txtDevice_Address_Binding.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			txtDevice_Address_Binding.setBackground(Color.white );
		}

		public void mousePressed(MouseEvent arg0) {
			txtDevice_Address_Binding.setBackground(Color.white );
		}

		public void mouseReleased(MouseEvent arg0) {
			txtDevice_Address_Binding.setBackground(Color.white );
		}                                         
	}     

	public final class txtPortMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {	
		}

		public void mouseEntered(MouseEvent arg0) {
			txtPort.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			txtPort.setBackground(Color.white );
		}

		public void mousePressed(MouseEvent arg0) {
			txtPort.setBackground(Color.white );
		}

		public void mouseReleased(MouseEvent arg0) {
			txtPort.setBackground(Color.white );
		}                                         
	} 

	public EventHandler getEventHandler() {
		return eventHandler;
	}

	static class EventQueueListener extends EventAdapter {
		// listen to event queue

		@Override
		public void bacnetDeviceUpdate(DBBacnetDevicesTable d) {
			try {
				txtObject_Identifier.setText(d.getObject_Identifier());
				txtDevice_Address_Binding.setText(d.getDevice_Address_Binding());
				txtPort.setText(d.getPort());	

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
