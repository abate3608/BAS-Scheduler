package edu.psu.sweng500.userinterface.datepicker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.psu.sweng500.util.UIThemeColors;

public class DatePicker extends JPanel {

	/** default */
	private static final long serialVersionUID = 1L;
	public static final String DATE_SELECTION_EVENT = "date.selection";

	private int month = Calendar.getInstance().get( Calendar.MONTH );
	private int year = Calendar.getInstance().get( Calendar.YEAR );
	private int day = Calendar.getInstance().get( Calendar.DATE );
	
	private JLabel datelabel = new JLabel("", JLabel.CENTER);
	private JButton[] button = new JButton[42];

	public DatePicker()
	{
		JPanel calendar = new JPanel( new GridLayout(7, 7) );
		calendar.setPreferredSize( new Dimension(430, 180) );
		createCalendar( calendar );

		JPanel nav = new JPanel(new GridLayout(1, 3));
		createNav( nav );
		
		populateCalendar();
		this.setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS) );
		this.add( calendar, BorderLayout.CENTER );
		this.add( nav, BorderLayout.SOUTH );
	}
	
	private void createCalendar( JPanel calendar )
	{
		String[] header = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
		for( String d : header )
		{
			calendar.add( new JLabel( d, JLabel.CENTER ) );
		}
		for( int x = 0; x < button.length; x++ ) 
		{		
			final int selection = x;
			button[x] = new JButton();
			button[x].setBackground( Color.WHITE );
			button[x].addActionListener(new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent ae) 
				{
					day = Integer.parseInt( button[selection].getActionCommand() );
					for( int i = 0; i < button.length; i++ )
					{
						if( !button[i].getText().equals("") )
						{
							button[i].setBackground( Color.WHITE );
						}
					}
					button[selection].setBackground( UIThemeColors.CALENDAR_BLUE );
					firePropertyChange( DATE_SELECTION_EVENT, null, getDateSelection() );
				}
			});
			calendar.add(button[x]);
		}
	}
	
	private void createNav( JPanel nav )
	{
		JButton previous = new JButton("<< Previous");
		previous.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent ae) 
			{
				month--;
				populateCalendar();
			}
		});

		JButton next = new JButton("Next >>");
		next.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae) 
			{
				month++;
				populateCalendar();
			}
		});
		
		nav.add( previous );
		nav.add( datelabel );
		nav.add( next );
	}

	private void populateCalendar() 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1 );

		int dayOfWeek = cal.get( Calendar.DAY_OF_WEEK );
		int daysInMonth = cal.getActualMaximum( Calendar.DAY_OF_MONTH );

		for( int i = 0, date = 1; i < button.length; i++ )
		{
			if( i >= dayOfWeek-1 && date <= daysInMonth )
			{
				button[i].setText( "" + date );
				button[i].setEnabled( true );
				button[i].setBackground( Color.WHITE );
				preselectCurrentDate( button[i], date );
				date += 1;
			}
			else
			{
				button[i].setText( "" );
				button[i].setEnabled( false );
				button[i].setBackground( UIThemeColors.BG_TAN );
			}
		}
		datelabel.setText( sdf.format(cal.getTime()) );
	}

	private void preselectCurrentDate( JButton button, int date )
	{
		Calendar cal = Calendar.getInstance();
		if( year == cal.get( Calendar.YEAR ) 
				&& month == cal.get( Calendar.MONTH ) 
				&& date == cal.get( Calendar.DATE ) )
		{
			button.setBackground( UIThemeColors.CALENDAR_BLUE );
		}
	}
	
	public String getDateSelection() 
	{
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" ); ////////CHANGED 7/23
		Calendar cal = Calendar.getInstance();
		cal.set( year, month, day );
		return sdf.format( cal.getTime() );
	}
	
	/**
	 * Displays a {@link JDialog} with an embedded {@link DatePicker}.
	 * The dialog is disposed upon date selection.
	 * @return the selected date in the {@link String} format "yyyy-MM-dd"
	 */
	public static String showDatePickerDialog()
	{
		JDialog dialog = new JDialog();
		
		JTextField date = new JTextField();
		date.setEnabled( false );
		date.setVisible( false );
		
		DatePicker picker = new DatePicker();
		picker.addPropertyChangeListener(DATE_SELECTION_EVENT, new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent dateChange) 
			{
				date.setText( (String)dateChange.getNewValue() );
				dialog.dispose();
			}
		});

		dialog.setTitle("Select Date");
		dialog.setPreferredSize( new Dimension(450, 260) );
		dialog.setLocationRelativeTo( null );
		dialog.setModal(true);
		dialog.add( picker, BorderLayout.CENTER );
		dialog.pack();
		

		dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		dialog.setAlwaysOnTop(true);
		dialog.setResizable(false);
		
		dialog.setVisible( true );

		return date.getText();
	}
	

	public static void main( String[] args )
	{
		System.out.println( DatePicker.showDatePickerDialog() );
	}
}


