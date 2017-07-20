package edu.psu.sweng500.userinterface.datepicker;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
 
//create class and extend with JFrame
public class DatePickerExample extends JFrame
{	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		//add JPanel to the contentPane
		private JPanel contentPane;
		//declare variable
		private JTextField txtDate;
	
		/**
		 * Launch the application.
		 */
		public static void main(String[] args)// main method
		{	// it will call the run method on that object
			EventQueue.invokeLater(new Runnable()
			{	
				public void run()
				{
					try // try block 
					{
					     //create frame object
					     DatePickerExample frame = new DatePickerExample();
					     frame.setVisible(true);//set visible true
				        }
				        catch (Exception e) //catch the exception
				        {
				         	e.printStackTrace();
				        }
			        }          
		        });
	        }
 
	/**
	 * Create the frame.
	 */
	//create constructor of class
	public DatePickerExample() 
	{
		//set title
		setTitle("Date Picker ");
		//set close operation on frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//set bounds of frame
		setBounds(100, 100, 450, 300);
		//create new JPanel in contentPane
		contentPane = new JPanel();
		//set border of frame
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//set contentPane 
		setContentPane(contentPane);
		//set layout null
		contentPane.setLayout(null);
		
		//create text field
		txtDate = new JTextField();
		//set bounds of text field
		txtDate.setBounds(101, 107, 86, 20);
		//add text field to contentPane
		contentPane.add(txtDate);
		//set columns
		txtDate.setColumns(10);
		
		//create button and there object
		JButton btnNewButton = new JButton("New button");
		//perform action listener
		btnNewButton.addActionListener(new ActionListener() 
		{	
			//performed action
			public void actionPerformed(ActionEvent arg0) 
			{
				//create frame new object  f
				final JFrame f = new JFrame();
				//set text which is collected by date picker i.e. set date 
				txtDate.setText(new DatePicker().setPickedDate());
			}
		});
		//set button bound
		btnNewButton.setBounds(223, 106, 27, 23);
		//add button in contentPane
		contentPane.add(btnNewButton);
	}
}
