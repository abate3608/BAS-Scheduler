package edu.psu.sweng500.api;

import java.io.*;
import java.net.*;
 
public class TestClient {
 
    public static void main(String argv[]) throws Exception {
 
        Socket clientSocket = new Socket("localhost", 7001);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        
     // How to read file into String before Java 7 
        InputStream is = new FileInputStream("C:\\Users\\Brian\\Desktop\\TestBasObj.json"); 
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        
        String line = buf.readLine(); 
        StringBuilder sb = new StringBuilder(); 
        while(line != null){ 
        	sb.append(line).append("\n"); 
        	line = buf.readLine(); 
        } 
        String fileAsString = sb.toString();
 
        outToServer.writeBytes(fileAsString);
 
        buf.close();
        clientSocket.close();
    }
}
