package edu.psu.sweng500.api.basgs;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import edu.psu.sweng500.api.basgs.BASGS_API.API_Object;

public class TestClient {

	public static void main(String argv[]) throws Exception {

		// How to read file into String before Java 7
		String filename = "";
		// Getting ClassLoader obj
		ClassLoader classLoader = TestClient.class.getClassLoader();
		System.out.println(classLoader.getResource(filename).getFile().toString());
		Socket clientSocket = new Socket("localhost", 8888);
		
		 Scanner scanner = new Scanner(System. in); 
		boolean run = true;
		boolean read;
		API_Object apiObj2;
		while(run) {
			read = true;
			System.out.println("Select The following action:");
			System.out.println("1: Create Event(s)");
			System.out.println("2: Read Events");
			System.out.println("3: Update Event(s)");
			System.out.println("4: Delete Event(s)");
			System.out.println("5: Exit");
			String input = scanner. nextLine();
			switch(Integer.parseInt(input)){
				case 1:
					filename = "TestAPICreate.json";
					break;
				case 2:
					filename = "TestAPIRead.json";
					break;
				case 3:
					filename = "TestAPIUpdate.json";
					break;
				case 4:
					filename = "TestAPIDelete.json";
					break;
				case 5:
					run = false;
					break;
				default:
					System.out.println("Select 1-4");
			}
			
			if(run) {
				API_Object apiObj = readJsonStream(classLoader.getResourceAsStream(filename));
				writeJsonStream(clientSocket.getOutputStream(),apiObj);
				
				while(read) {
					apiObj2 = readJsonStream(clientSocket.getInputStream());
					if(apiObj2 != null) {
						Gson gson = new Gson();
						String json = gson.toJson(apiObj2);
						System.out.println(json);
						read = false;
					}
				}
			}
		}

		//buf.close();
		clientSocket.close();
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
