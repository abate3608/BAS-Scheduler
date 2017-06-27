package edu.psu.sweng500.api;

import java.io.*;
import java.net.*;

import org.apache.camel.Main;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import edu.psu.sweng500.api.BASGS_API.API_Object;

public class TestClient {

	public static void main(String argv[]) throws Exception {

		// How to read file into String before Java 7
		String filename = "TestCreateObj.json";
		// Getting ClassLoader obj
		ClassLoader classLoader = TestClient.class.getClassLoader();
		// Getting resource(File) from class loader
		System.out.println(classLoader.getResource(filename).getFile().toString());
//		BufferedReader buf = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(filename)));
//		
		Socket clientSocket = new Socket("localhost", 8888);
//		DataInputStream input = new DataInputStream(clientSocket.getInputStream());
//		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
//
//		String line = buf.readLine();
//		StringBuilder sb = new StringBuilder();
//		while (line != null) {
//			sb.append(line).append("\n");
//			line = buf.readLine();
//		}
//		String fileAsString = sb.toString();
//
//		outToServer.writeBytes(fileAsString);
		
		API_Object apiObj = readJsonStream(classLoader.getResourceAsStream(filename));
		writeJsonStream(clientSocket.getOutputStream(),apiObj);
		
		API_Object apiObj2;
		while(true) {
			apiObj2 = readJsonStream(clientSocket.getInputStream());
			if(apiObj2 != null)
				System.out.println("Received msg: " + apiObj2.message);
		}

		//buf.close();
		//clientSocket.close();
	}
	
	public static API_Object readJsonStream(InputStream in) throws IOException {
	       JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
	       Gson gson = new Gson();
	       API_Object apiObj = null;
    	   apiObj = gson.fromJson(reader, API_Object.class);
	       return apiObj;
	   }
	
	public static void writeJsonStream(OutputStream out, API_Object apiObj) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        Gson gson = new Gson();
        gson.toJson(apiObj, API_Object.class, writer);
        writer.flush();
    }
}
