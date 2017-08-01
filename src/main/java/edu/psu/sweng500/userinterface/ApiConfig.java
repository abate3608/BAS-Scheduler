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

public class ApiConfig {
	public static JFrame apiWin;
	private JPanel apiPanel;
	private JLabel lblIpAddr;
	private JLabel lblPort;

	private static JTextField txtIpAddr;
	private static JTextField txtPort;

	private JButton btnOk;

	private final String IP_ADDR = "127.0.0.1";
	private final String PORT = "8888";


	public ApiConfig() {

	}

	public void create () {

		apiWin = new JFrame("API Server Configuration");
		apiWin.setSize(290, 250);
		apiWin.setLocationRelativeTo(null);  
		apiWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		apiPanel = new JPanel();
		apiWin.add(apiPanel);
		panelLayout(apiPanel);
		apiWin.setVisible(true);
	}

	public void dispose () {
		//logWin.dispose();
		apiWin.setVisible(false); 
	}

	private final void panelLayout(JPanel logPanel) {

		logPanel.setLayout(null);
		logPanel.setBorder(BorderFactory.createTitledBorder("API Server Configuration"));

		lblIpAddr = new JLabel("IP Address");
		lblIpAddr.setBounds(20, 25, 80, 25);
		logPanel.add(lblIpAddr);

		txtIpAddr = new JTextField(IP_ADDR);
		txtIpAddr.setBounds(90, 25, 160, 25);
		logPanel.add(txtIpAddr);

		lblPort = new JLabel("Port");
		lblPort.setBounds(20, 55, 80, 25);
		logPanel.add(lblPort);

		txtPort = new JTextField(PORT);
		txtPort.setBounds(90, 55, 160, 25);
		logPanel.add(txtPort);

		btnOk = new JButton("OK");
		btnOk.setBounds(55, 135, 80, 25);
		logPanel.add(btnOk);
		btnOk.addActionListener(new OkButtonPress());
	}


	private final class OkButtonPress implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TEam 7 To Do
			apiWin.setVisible(false); 
			//logWin.dispose(); 

		}
	}            

}
