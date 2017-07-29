package edu.psu.sweng500.userinterface.datepicker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.psu.sweng500.util.UIThemeColors;

public class DatePicker extends JPanel {

	/** default */
	private static final long serialVersionUID = 1L;
	public static final String DATE_SELECTION_EVENT = "date.selection";

	private int month = Calendar.getInstance().get( Calendar.MONTH );
	private int year = java.util.Calendar.getInstance().get( Calendar.YEAR );
	private String day = "";
	
	private JLabel datelabel = new JLabel("", JLabel.CENTER);
	private JButton[] button = new JButton[42];

	public DatePicker()
	{
		JPanel calendar = new JPanel( new GridLayout(7, 7) );
		calendar.setPreferredSize( new Dimension(430, 120) );
		createCalendar( calendar );

		JPanel nav = new JPanel(new GridLayout(1, 3));
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

		populateCalendar();
		this.setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS) );
		this.add( calendar, BorderLayout.CENTER );
		this.add( nav, BorderLayout.SOUTH );
		this.setVisible( true );
	}
	
	private void createCalendar( JPanel calendar )
	{
		String[] header = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
		for( String d : header )
		{
			calendar.add( new JLabel( d ) );
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
					day = button[selection].getActionCommand();
					for( int i = 0; i < button.length; i++ )
					{
						if( !button[i].getText().equals("") )
						{
							button[i].setBackground( Color.WHITE );
						}
					}
					button[selection].setBackground( UIThemeColors.CALENDAR_BLUE );
					firePropertyChange( DATE_SELECTION_EVENT, null, setPickedDate() );
				}
			});
			calendar.add(button[x]);
		}
	}

	private void populateCalendar() 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
		Calendar cal = Calendar.getInstance();			
		cal.set(year, month, 1);

		int dayOfWeek = cal.get( Calendar.DAY_OF_WEEK );
		int daysInMonth = cal.getActualMaximum( Calendar.DAY_OF_MONTH );

		for( int i = 0, date = 1; i < button.length; i++ )
		{
			if( i >= dayOfWeek-1 && date <= daysInMonth )
			{
				button[i].setText( "" + date );
				button[i].setEnabled( true );
				button[i].setBackground( Color.WHITE );
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

	private String setPickedDate() 
	{
		if ( !day.equals("") )
		{
			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" ); ////////CHANGED 7/23
			Calendar cal = Calendar.getInstance();
			cal.set( year, month, Integer.parseInt(day) );
			return sdf.format( cal.getTime() );
		}
		return "";
	}
	
	/**
	 * 
	 * @return
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
		dialog.setPreferredSize( new Dimension(450, 200) );
		dialog.setLocationRelativeTo( null );
		dialog.setModal(true);
		dialog.add( picker, BorderLayout.CENTER );
		dialog.pack();
		dialog.setVisible( true );

		return date.getText();
	}
	
	public static void main( String[] args )
	{
		System.out.println( DatePicker.showDatePickerDialog() );
	}
}


