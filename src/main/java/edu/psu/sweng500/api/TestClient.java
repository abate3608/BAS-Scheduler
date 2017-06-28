package edu.psu.sweng500.api;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import edu.psu.sweng500.api.BASGS_API.API_Object;

public class TestClient {

	public static void main(String argv[]) throws Exception {

		// How to read file into String before Java 7
		String filename = "TestAPI.json";
		// Getting ClassLoader obj
		ClassLoader classLoader = TestClient.class.getClassLoader();
		System.out.println(classLoader.getResource(filename).getFile().toString());
		Socket clientSocket = new Socket("localhost", 8888);
		
		API_Object apiObj = readJsonStream(classLoader.getResourceAsStream(filename));
		writeJsonStream(clientSocket.getOutputStream(),apiObj);
		
		API_Object apiObj2;
		while(true) {
			apiObj2 = readJsonStream(clientSocket.getInputStream());
			if(apiObj2 != null) {
				Gson gson = new Gson();
				String json = gson.toJson(apiObj2);
				System.out.println(json);
			}
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
