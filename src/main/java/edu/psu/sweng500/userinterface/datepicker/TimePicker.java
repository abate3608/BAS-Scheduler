package edu.psu.sweng500.userinterface.datepicker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;



public class TimePicker extends JComboBox<Date>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TimePicker() {
		super(getTimePickerModel());
        
        this.setBackground(Color.WHITE); //CHANGE Color
        this.setFont(new Font("Arial",Font.ITALIC,14));
        
        this.setRenderer(new DefaultListCellRenderer() {

            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private DateFormat format = new SimpleDateFormat("HH:mm:ss");

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Date) {
                    value = format.format((Date) value);
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
   	 });
	}

	private static  DefaultComboBoxModel<Date> getTimePickerModel(){
		
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
       
        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        DefaultComboBoxModel<Date> model = new DefaultComboBoxModel<>();
       
        do {
            model.addElement(calendar.getTime());
            calendar.add(Calendar.MINUTE, 15); /// CHANGED
        } while (calendar.getTime().before(end.getTime()));

        return model;
		
	}
	
	public static int getTimePickerIndex(JComboBox<Date> jbox, Date date) {
		int size = jbox.getItemCount();
		for (int i = 0; i < size; i++) {
		  Date item = jbox.getItemAt(i);
		  DateFormat hf = new SimpleDateFormat("HH:mm:ss");
		  int result = hf.format(item).compareTo(hf.format(date));
		  if(result == 0) {
			  System.out.println(item);
			  return i;
		  }
		}
		return -1;
	}
}

