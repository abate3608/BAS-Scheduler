package edu.psu.sweng500.database;

import java.sql.*;

import edu.psu.sweng500.eventqueue.event.EventHandler;


public class MysqlAccess {

	private Connection connect = null;
    private Statement statement = null;

    private ResultSet rt = null;
    
    // Event listeners
    private final EventHandler eventHandler = new EventHandler();
    
    public void readDB() throws Exception {
    	try {
    	/*
    	 * Database name: psuteam7
    	 * user: psuteam7
    	 * password:psuteam7123
    	 * The connection to the database and its credentials. 
    	 */
    		connect = DriverManager.getConnection("jdbc:mysql://50.63.144.233:3306/psuteam7?useSSL=false","psuteam7","psuteam7123");
    		if (connect != null) {
				System.out.println("Connection Successful!!");
			}
		} catch (SQLException ex) {
			System.out.println("Error Connecting");
			ex.printStackTrace();
		} 
    		
    		statement = connect.createStatement();
    		/*
    		 * Created a view to pull JOINED Tables data. The below select statement will pull all data from the view table. 
    		 */
    		rt = statement.executeQuery("select * from psuteam7.ScheduleView");
     
         /*
          * While loop which will display all data in the view table. 
          */
           while((rt.next())) 
        	   System.out.println(rt.getInt(1)+" "+rt.getInt(2)+" "+rt.getString(3)+" "+rt.getString(4)+" "+rt.getString(5)+" "+rt.getString(6)+" "
        			   +rt.getInt(7)+" "+rt.getString(8)+" "+rt.getString(9)+" "+rt.getInt(10)+" "+rt.getByte(11)+" "+rt.getInt(12)+" "+rt.getByte(13));
        	   connect.close();
    	} 
    	
    
    public EventHandler getEventHandler() {
        return eventHandler;
    }
    
    
    
    

}
