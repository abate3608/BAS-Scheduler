package edu.psu.sweng500.userinterface;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.psu.sweng500.eventqueue.event.EventAdapter;
import edu.psu.sweng500.eventqueue.event.EventHandler;
import edu.psu.sweng500.type.DBSiteTable;

public class SysConfig {
	public static JFrame frame;
	private JPanel panel;
	private JLabel lblSysID;
	private JLabel lblSysName;
	private JLabel lblDescription;
	private JLabel lblAddress;
	private JLabel lblAddress2;
	private JLabel lblCity;
	private JLabel lblState;
	private JLabel lblZip;

	

	private static JTextField txtSysID;
	private static JTextField txtSysName;
	private static JTextField txtDescription;
	private static JTextField txtAddress;
	private static JTextField txtAddress2;
	private static JTextField txtCity;
	private static JTextField txtState;
	private static JTextField txtZip;

	private JButton btnUpdate;
	private JButton btnCancel;

	DBSiteTable site = new DBSiteTable();

	// Event listeners
	private final EventHandler eventHandler = EventHandler.getInstance();

	public SysConfig() {
		this.site.setCountryCode("US");

	}

	public void create () {

		// setup event
		eventHandler.addListener(new EventQueueListener());

		frame = new JFrame("System Configuration");
		frame.setSize(290, 350);
		frame.setLocationRelativeTo(null);  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		frame.add(panel);
		panelLayout(panel);
		
		
		frame.addWindowListener(getWindowAdapter());
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setVisible(true);
		
		eventHandler.fireSiteInfoRequest();
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
            	frame.setState(JFrame.NORMAL);
                
            }
        };
    }
	public void dispose () {
		//logWin.dispose();
		frame.setVisible(false); 
	}

	private final void panelLayout(JPanel panel) {

		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder("System Configuration"));
		int lblx = 20;
		int lbly = 25;
		int lblw = 80;
		int lblh = 25;
		int	txtx = lblx + lblw;
		int txty = lbly;
		int txtw = 160;
		int txth = 25;
		
		lblSysID = new JLabel("System ID");
		lblSysID.setBounds(lblx, lbly, lblw, lblh);
		panel.add(lblSysID);

		txtSysID = new JTextField();
		txtSysID.setBounds(txtx, txty, txtw, txth);
		panel.add(txtSysID);

		//userNameText.getText();
		txtSysID.addKeyListener(new EnterButtonPress());
		txtSysID.addMouseListener(new txtSysIDMouseClicked());
		txtSysID.setEditable(false);
		
		lbly += 30;
		txty += 30;
		lblSysName = new JLabel("Name");
		lblSysName.setBounds(lblx, lbly, lblw, lblh);
		panel.add(lblSysName);

		txtSysName = new JTextField();
		txtSysName.setBounds(txtx, txty, txtw, txth);
		panel.add(txtSysName);

		//userNameText.getText();
		txtSysName.addKeyListener(new EnterButtonPress());
		txtSysName.addMouseListener(new txtSysNameMouseClicked());
		//txtSysName.setEditable(false);
		
		lbly += 30;
		txty += 30;
		lblDescription = new JLabel("Descripiotn");
		lblDescription.setBounds(lblx, lbly, lblw, lblh);
		panel.add(lblDescription);

		txtDescription = new JTextField();
		txtDescription.setBounds(txtx, txty, txtw, txth);
		panel.add(txtDescription);

		//userNameText.getText();
		txtDescription.addKeyListener(new EnterButtonPress());
		txtDescription.addMouseListener(new txtDescriptionMouseClicked());
		//txtDescription.setEditable(false);
		
		lbly += 30;
		txty += 30;
		lblAddress = new JLabel("Address");
		lblAddress.setBounds(lblx, lbly, lblw, lblh);
		panel.add(lblAddress);

		txtAddress = new JTextField();
		txtAddress.setBounds(txtx, txty, txtw, txth);
		panel.add(txtAddress);

		//userNameText.getText();
		txtAddress.addKeyListener(new EnterButtonPress());
		txtAddress.addMouseListener(new txtAddressMouseClicked());
		//txtAddress.setEditable(false);
		
		lbly += 30;
		txty += 30;
		lblAddress2 = new JLabel("Address2");
		lblAddress2.setBounds(lblx, lbly, lblw, lblh);
		panel.add(lblAddress2);

		txtAddress2 = new JTextField();
		txtAddress2.setBounds(txtx, txty, txtw, txth);
		panel.add(txtAddress2);

		//userNameText.getText();
		txtAddress2.addKeyListener(new EnterButtonPress());
		txtAddress2.addMouseListener(new txtAddress2MouseClicked());
		//txtAddress2.setEditable(false);
		
		lbly += 30;
		txty += 30;
		lblCity = new JLabel("City");
		lblCity.setBounds(lblx, lbly, lblw, lblh);
		panel.add(lblCity);

		txtCity = new JTextField();
		txtCity.setBounds(txtx, txty, txtw, txth);
		panel.add(txtCity);

		//userNameText.getText();
		txtCity.addKeyListener(new EnterButtonPress());
		txtCity.addMouseListener(new txtCityMouseClicked());
		//txtCity.setEditable(false);
		
		lbly += 30;
		txty += 30;
		lblState = new JLabel("State");
		lblState.setBounds(lblx, lbly, lblw, lblh);
		panel.add(lblState);

		txtState = new JTextField();
		txtState.setBounds(txtx, txty, txtw, txth);
		panel.add(txtState);

		//userNameText.getText();
		txtState.addKeyListener(new EnterButtonPress());
		txtState.addMouseListener(new txtStateMouseClicked());
		//txtState.setEditable(false);
		
		lbly += 30;
		txty += 30;
		lblZip = new JLabel("Zip Code");
		lblZip.setBounds(lblx, lbly, lblw, lblh);
		panel.add(lblZip);

		txtZip = new JTextField();
		txtZip.setBounds(txtx, txty, txtw, txth);
		panel.add(txtZip);

		//userNameText.getText();
		txtZip.addKeyListener(new EnterButtonPress());
		txtZip.addMouseListener(new txtZipMouseClicked());
		//txtZip.setEditable(false);
		
		lbly += 40;
		txty += 30;
		btnUpdate = new JButton("Update");
		btnUpdate.setBounds(55, lbly, 80, 25);
		panel.add(btnUpdate);
		btnUpdate.addActionListener(new updateButtonPress());

		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(145, lbly, 80, 25);
		panel.add(btnCancel);
		btnCancel.addActionListener(new cancelButtonPress());
	}


	private final class updateButtonPress implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TEam 7 To Do

			UpdateSiteInfo();
			frame.setVisible(false); 
			//logWin.dispose(); 

		}
	}

	private final class cancelButtonPress implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			frame.setVisible(false);
			frame.dispose();
			//logWin.dispose(); 

		}
	}

	//Enter Button Press
	private final class EnterButtonPress extends KeyAdapter {

		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode()== KeyEvent.VK_ENTER) {
				UpdateSiteInfo();

				frame.setVisible(false); 
				frame.dispose(); 
			}
		}
	}

	private void UpdateSiteInfo() {
		site.setId(Integer.valueOf(txtSysID.getText()));
		site.setName(txtSysName.getText());
		site.setDescription(txtDescription.getText());
		site.setAddress(txtAddress.getText());
		site.setAddress2(txtAddress2.getText());
		site.setCity(txtCity.getText());
		site.setState(txtState.getText());
		site.setZipCode(txtZip.getText());
		site.setCountryCode("US");
		
		// fire request event
		eventHandler.fireSiteInfoUpdateDB(site);	
	}

	//User Name Highlight
	public final class txtSysIDMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {		
		}

		public void mouseEntered(MouseEvent arg0) {
			txtSysID.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			txtSysID.setBackground(Color.white );			
		}

		public void mousePressed(MouseEvent arg0) {
			txtSysID.setBackground(Color.white );		
		}

		public void mouseReleased(MouseEvent arg0) {
			txtSysID.setBackground(Color.white );
		}                                         
	}               


	public final class txtSysNameMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {	
		}

		public void mouseEntered(MouseEvent arg0) {
			txtSysName.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			txtSysName.setBackground(Color.white );
		}

		public void mousePressed(MouseEvent arg0) {
			txtSysName.setBackground(Color.white );
		}

		public void mouseReleased(MouseEvent arg0) {
			txtSysName.setBackground(Color.white );
		}                                         
	}    
	
	public final class txtDescriptionMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {	
		}

		public void mouseEntered(MouseEvent arg0) {
			txtDescription.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			txtDescription.setBackground(Color.white );
		}

		public void mousePressed(MouseEvent arg0) {
			txtDescription.setBackground(Color.white );
		}

		public void mouseReleased(MouseEvent arg0) {
			txtDescription.setBackground(Color.white );
		}                                         
	}

	public final class txtAddressMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {	
		}

		public void mouseEntered(MouseEvent arg0) {
			txtAddress.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			txtAddress.setBackground(Color.white );
		}

		public void mousePressed(MouseEvent arg0) {
			txtAddress.setBackground(Color.white );
		}

		public void mouseReleased(MouseEvent arg0) {
			txtAddress.setBackground(Color.white );
		}                                         
	}
	
	public final class txtAddress2MouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {	
		}

		public void mouseEntered(MouseEvent arg0) {
			txtAddress2.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			txtAddress2.setBackground(Color.white );
		}

		public void mousePressed(MouseEvent arg0) {
			txtAddress2.setBackground(Color.white );
		}

		public void mouseReleased(MouseEvent arg0) {
			txtAddress2.setBackground(Color.white );
		}                                         
	}

	public final class txtCityMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {	
		}

		public void mouseEntered(MouseEvent arg0) {
			txtCity.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			txtCity.setBackground(Color.white );
		}

		public void mousePressed(MouseEvent arg0) {
			txtCity.setBackground(Color.white );
		}

		public void mouseReleased(MouseEvent arg0) {
			txtCity.setBackground(Color.white );
		}                                         
	}
	
	public final class txtStateMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {	
		}

		public void mouseEntered(MouseEvent arg0) {
			txtState.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			txtState.setBackground(Color.white );
		}

		public void mousePressed(MouseEvent arg0) {
			txtState.setBackground(Color.white );
		}

		public void mouseReleased(MouseEvent arg0) {
			txtCity.setBackground(Color.white );
		}                                         
	}
	
	
	public final class txtZipMouseClicked implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {	
		}

		public void mouseEntered(MouseEvent arg0) {
			txtZip.setBackground(Color.LIGHT_GRAY );
		}

		public void mouseExited(MouseEvent arg0) {
			txtZip.setBackground(Color.white );
		}

		public void mousePressed(MouseEvent arg0) {
			txtZip.setBackground(Color.white );
		}

		public void mouseReleased(MouseEvent arg0) {
			txtZip.setBackground(Color.white );
		}                                         
	} 

	public EventHandler getEventHandler() {
		return eventHandler;
	}

	static class EventQueueListener extends EventAdapter {
		// listen to event queue

		@Override
		public void siteInfoUpdate(DBSiteTable s) {
			try {
				txtSysID.setText(String.valueOf(s.getId()));
				txtSysName.setText(s.getName());
				txtDescription.setText(s.getDescription());
				txtAddress.setText(s.getAddress());
				txtAddress2.setText(s.getAddress2());
				txtCity.setText(s.getCity());
				txtState.setText(s.getState());
				txtZip.setText(s.getZipCode());
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
