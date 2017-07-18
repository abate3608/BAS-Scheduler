package edu.psu.sweng500.userinterface.scheduling;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

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

public class ScheduleImporterConfigMenu 
{
	private static JFrame frame;
	private NodeSelectionPanel mappanel;

	public ScheduleImporterConfigMenu()
	{
		frame = new JFrame( "Schedule Importer Configuration" );
		frame.setSize( 420, 300 );
		frame.setLocationRelativeTo( null );
		frame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		frame.getContentPane().add( getFileSelectionPanel(), BorderLayout.PAGE_START );
		frame.getContentPane().add( (mappanel = new NodeSelectionPanel()), BorderLayout.CENTER );
		frame.getContentPane().add( getButtonsPanel(), BorderLayout.PAGE_END );
		frame.setVisible( true );
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
						Path path = chooser.getSelectedFile().toPath();
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
				// TODO Auto-generated method stub
				XmlDomMap xmlDomMap = mappanel.buildXmlDomMap();
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

	public static void main( String[] args )
	{
		new ScheduleImporterConfigMenu();
	}
}
