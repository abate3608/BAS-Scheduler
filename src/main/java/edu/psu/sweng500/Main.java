package edu.psu.sweng500;

import edu.psu.sweng500.bacnetserver.server.BacnetServer;
/**
 * This is the application main class.
 * When the built jar file is run, this class's main method
 * will be run.
 * 
 */
public class Main 
{
	public static void main( String args[] )
	{
		System.out.println("The main class has been run!");
		
    	try {
    		new BacnetServer();
    		while (true){
    			Thread.sleep(2000);
    		}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}
