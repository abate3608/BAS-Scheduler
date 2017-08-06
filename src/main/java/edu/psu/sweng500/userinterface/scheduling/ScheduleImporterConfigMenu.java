package edu.psu.sweng500.userinterface.scheduling;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import edu.psu.sweng500.schedule.objects.XmlDomMap;

/**
 * 
 * @author awb
 */
public class ScheduleImporterConfigMenu 
{
	private static JFrame frame;
	private NodeSelectionPanel mappanel;
	private Path path;

	public ScheduleImporterConfigMenu()
	{
		frame = new JFrame( "Schedule Importer Configuration" );
		frame.setSize( 450, 700 );
		frame.setLocationRelativeTo( null );
		frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		frame.getContentPane().add( getFileSelectionPanel(), BorderLayout.PAGE_START );
		frame.getContentPane().add( (mappanel = new NodeSelectionPanel()), BorderLayout.CENTER );
		frame.getContentPane().add( getButtonsPanel(), BorderLayout.PAGE_END );
		
		frame.setResizable(false);
		
		frame.addWindowListener(getWindowAdapter());
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		frame.setAlwaysOnTop(false);
		
		frame.setVisible( true );
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
			
	/**
	 * 
	 * @return
	 */
	private JPanel getFileSelectionPanel()
	{
		// create file selection JPanel 
		JPanel panel = new JPanel( new FlowLayout(SwingConstants.LEADING, 10, 10) );
		panel.setBorder( BorderFactory.createTitledBorder("File Location") );

		// create text field
		JTextField selected = new JTextField( 20 );
		selected.setFont( selected.getFont().deriveFont(14f) );
		selected.setEditable( false );
		panel.add( selected );

		// create browse button
		JButton browse = new JButton("Browse...");
		browse.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent arg0 ) 
			{
				JFileChooser chooser = new JFileChooser( FileSystemView.getFileSystemView().getHomeDirectory() );
				chooser.setAcceptAllFileFilterUsed( false );
				chooser.addChoosableFileFilter(
						new FileNameExtensionFilter( "XML","xml" )
						);
				int returnValue = chooser.showOpenDialog( frame );
				if( returnValue == JFileChooser.APPROVE_OPTION )
				{
					try {
						path = chooser.getSelectedFile().toPath();
						mappanel.setPanel( path );
						selected.setText( path.toString() );
					} catch ( Exception e ) {
						JOptionPane.showMessageDialog( frame, e.getMessage() );
					}
				}
			}
		} );
		panel.add( browse );

		return panel;
	}
	
	/**
	 * 
	 * @return
	 */
	private JPanel getButtonsPanel()
	{
		// create buttons panel
		JPanel buttons = new JPanel( new FlowLayout(SwingConstants.LEADING, 10, 10) );
		
		// save button to save changes
		JButton save = new JButton("Save");
		save.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try {
					int confirm = JOptionPane.showConfirmDialog( null, 
							"Do you want to save this schedule import configuration?",
							"Save?", 
							JOptionPane.OK_CANCEL_OPTION);
					if( confirm == 0 )
					{
						final Path xdmPath = Paths.get("xmlDomMap.properties");
						XmlDomMap xmlDomMap = mappanel.buildXmlDomMap();
						xmlDomMap.writeMapToFile( xdmPath );
						System.setProperty( "xmlImport.domMap", xdmPath.toString() );
						System.setProperty( "xmlImport.location", path.toString() );
						frame.dispose();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (NullPointerException npe) {
					npe.printStackTrace();
				}
			} 
		});
		buttons.add( save );

		// cancel button to dispose window 
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				frame.dispose();
			}
		});
		buttons.add( cancel );
		
		return buttons;
	}

	/**
	 * Main method for testing GUI independently of system. 
	 * @param args
	 */
	public static void main( String[] args )
	{
		new ScheduleImporterConfigMenu();
	}
}
