package edu.psu.sweng500.api;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
 
/*
 * Author: Brian Abate
 * This class provides a multi threaded TCP server for client
 * applications to connect to
 */
public class MultiThreadedAPIServer {
   ServerSocket myServerSocket;
   private final int PORT = 8888;
   boolean ServerOn = true;
   
   /*
	 * Constructor that instanciates server
	 */
   public MultiThreadedAPIServer() { 
      try {
         myServerSocket = new ServerSocket(PORT);
      } catch(IOException ioe) { 
         System.out.println("Could not create server socket on port " + String.valueOf(PORT) + ". Quitting.");
         System.exit(-1);
      } 
		
      Calendar now = Calendar.getInstance();
      SimpleDateFormat formatter = new SimpleDateFormat(
         "E yyyy.MM.dd 'at' hh:mm:ss a zzz");
      System.out.println("It is now : " + formatter.format(now.getTime()));
      
      while(ServerOn) { 
         try { 
            Socket clientSocket = myServerSocket.accept();
            ClientServiceThread cliThread = new ClientServiceThread(this, clientSocket);
            cliThread.start(); 
         } catch(IOException ioe) { 
            System.out.println("Exception found on accept. Ignoring. Stack Trace :"); 
            ioe.printStackTrace(); 
         }  
      } 
      try { 
         myServerSocket.close(); 
         System.out.println("Server Stopped"); 
      } catch(Exception ioe) { 
         System.out.println("Error Found stopping server socket"); 
         System.exit(-1); 
      } 
   }
   
   /*
	 * This method returns the servers status
	 * @return the status of the server
	 */
   public boolean getServiceStatus() {
	   return ServerOn;
   }
	
   /*
	 * The main method used for running the BASGS_API and starts Camel Spring
	 * Main.
	 * 
	 * @param [in] args - String array of arguements passed in.
	 */
   public static void main (String[] args) { 
      new MultiThreadedAPIServer();   
   } 
	
   
   
   
}