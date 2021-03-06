package edu.psu.sweng500.api.basgs;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import edu.psu.sweng500.api.basgs.BASGS_API.API_Object;

/*
 * Author: Brian Abate
 * This class provides a client thread class to read/write json
 * to the client.
 */
public class ClientServiceThread extends Thread { 
    Socket myClientSocket;
    boolean m_bRunThread = true; 
    private BASGS_API api;
    private MultiThreadedAPIServer multiThreadedServer;
    
    /*
	 * Constructor for BASGS_API
	 */
    public ClientServiceThread() { 
       super(); 
    } 
		
    /*
	 * Constructor for BASGS_API
	 * @param [in] multiThreadedServer - instance of the server
	 * @param [in] s - Socket connection between client and server
	 */
    ClientServiceThread(MultiThreadedAPIServer multiThreadedServer, Socket s) { 
       myClientSocket = s; 
       this.multiThreadedServer = multiThreadedServer;
       api = new BASGS_API(this);
    } 
		
    /*
	 * This method is the threaded functionality to read and parse incoming json
	 * from client.
	 */
    public void run() {  
       System.out.println(
          "ClientServiceThread > Accepted Client Address - " + myClientSocket.getInetAddress().getHostName());
       try { 
    	  JsonReader reader = new JsonReader(new InputStreamReader(myClientSocket.getInputStream(), "UTF-8"));
          while(m_bRunThread && !myClientSocket.isClosed()) {
        	try {
	          	API_Object apiObj = readJsonStream(reader);
	          	if(apiObj != null) {
	          		api.handleApiObject(apiObj);
	          	}
        	} catch(Exception e) {
        		System.out.println("ClientServerThread >> Invalid JSON message received.");
        		API_Object apiObjError = new API_Object();
        		apiObjError.error = 1;
        		apiObjError.message = "Invalid JSON message format.";
        		writeJsonStream(apiObjError);
        	}
             
             if(!multiThreadedServer.getServiceStatus()) { 
                System.out.print("ClientServiceThread > Server has already stopped"); 
                m_bRunThread = false;
             }
          } 
       } catch(IOException ioe) {
    	   System.out.println("ClientServiceThread >> Exception caught - Connection reset by peer.");
    	   api.close();
       }
       finally { 
          try {  
             myClientSocket.close(); 
             System.out.println("ClientServiceThread > Thread Stopped"); 
          } catch(IOException ioe) { 
             ioe.printStackTrace(); 
          } 
       } 
    } 
    
    /*
	 * This class reads a client string and converts it to a Json instance
	 * @param [in] reader - reads data string from client
	 */
    public static API_Object readJsonStream(JsonReader reader) throws IOException {
        Gson gson = new Gson();
        API_Object apiObj = null;
     	apiObj = gson.fromJson(reader, API_Object.class);
        return apiObj;
    }
    
    /*
	 * This class writes Json object to a client
	 * @param [in] apiObj - object that is to be sent to the client
	 */
    public void writeJsonStream(API_Object apiObj) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(myClientSocket.getOutputStream(), "UTF-8"));
        writer.setIndent("  ");
        Gson gson = new Gson();
        gson.toJson(apiObj, API_Object.class, writer);
        writer.flush();
    }
 } 
