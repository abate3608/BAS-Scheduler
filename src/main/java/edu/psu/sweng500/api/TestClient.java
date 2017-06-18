package edu.psu.sweng500.api;

import java.io.*;
import java.net.*;

import org.apache.camel.Main;

import java.io.File;
 
public class TestClient {
 
    public static void main(String argv[]) throws Exception {
 
        Socket clientSocket = new Socket("localhost", 7002);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        
     // How to read file into String before Java 7 
        String filename = "TestBaseObj.json";
        // Getting ClassLoader obj
        ClassLoader classLoader = TestClient.class.getClassLoader();
        // Getting resource(File) from class loader
        System.out.println(classLoader.getResource(filename).getFile().toString());
        BufferedReader buf = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(filename)));
        
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
