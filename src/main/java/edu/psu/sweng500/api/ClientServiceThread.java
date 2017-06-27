package edu.psu.sweng500.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import edu.psu.sweng500.api.BASGS_API.API_Object;

public class ClientServiceThread extends Thread { 
    Socket myClientSocket;
    boolean m_bRunThread = true; 
    private BASGS_API api;
    private MultiThreadedAPIServer multiThreadedServer;
    
    public ClientServiceThread() { 
       super(); 
    } 
		
    ClientServiceThread(MultiThreadedAPIServer multiThreadedServer, Socket s) { 
       myClientSocket = s; 
       this.multiThreadedServer = multiThreadedServer;
       api = new BASGS_API(this);
    } 
		
    public void run() {  
       System.out.println(
          "Accepted Client Address - " + myClientSocket.getInetAddress().getHostName());
       try { 
          
          while(m_bRunThread) {
        	JsonReader reader = new JsonReader(new InputStreamReader(myClientSocket.getInputStream(), "UTF-8"));
          	API_Object apiObj = readJsonStream(reader);
          	api.parseMsg(apiObj);
          	

             
             if(!multiThreadedServer.getServiceStatus()) { 
                System.out.print("Server has already stopped"); 
                m_bRunThread = false;
             }
          } 
       } catch(Exception e) { 
          e.printStackTrace(); 
       } 
       finally { 
          try {  
             myClientSocket.close(); 
             System.out.println("...Stopped"); 
          } catch(IOException ioe) { 
             ioe.printStackTrace(); 
          } 
       } 
    } 
    
    public static API_Object readJsonStream(JsonReader reader) throws IOException {
        Gson gson = new Gson();
        API_Object apiObj = null;
     	apiObj = gson.fromJson(reader, API_Object.class);
        return apiObj;
    }
    
    public void writeJsonStream(API_Object apiObj) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(myClientSocket.getOutputStream(), "UTF-8"));
        writer.setIndent("  ");
        Gson gson = new Gson();
        gson.toJson(apiObj, API_Object.class, writer);
        writer.flush();
    }
 } 
