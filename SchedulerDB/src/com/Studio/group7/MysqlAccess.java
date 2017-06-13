package com.Studio.group7;

import java.sql.*;


public class MysqlAccess {

	private Connection connect = null;
    private Statement statement = null;

    private ResultSet rt = null;
    
    public void readDB() throws Exception {
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    	/*
    	 * Database name: BASscheduler
    	 * user: group7
    	 * password:#group7
    	 * The connection to the database and its credentials. 
    	 */
    		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/BASscheduler?useSSL=false","group7","#group7");
    		
    		statement = connect.createStatement();
    		/*
    		 * Created a view to pull JOINED Tables data. The below select statement will pull all data from the view table. 
    		 */
    		rt = statement.executeQuery("select * from BASscheduler.ScheduleView");
     
         /*
          * While loop which will display all data in the view table. 
          */
           while((rt.next())) 
        	   System.out.println(rt.getInt(1)+" "+rt.getInt(2)+" "+rt.getString(3)+" "+rt.getString(4)+" "+rt.getString(5)+" "+rt.getString(6)+" "
        			   +rt.getInt(7)+" "+rt.getString(8)+" "+rt.getString(9)+" "+rt.getInt(10)+" "+rt.getByte(11)+" "+rt.getInt(12)+" "+rt.getByte(13));
        	   connect.close();
    	}catch(Exception e) {
    		System.out.println(e);
    	}
   }
}
