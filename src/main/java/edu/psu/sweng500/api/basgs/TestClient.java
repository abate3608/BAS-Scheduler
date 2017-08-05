package edu.psu.sweng500.api.basgs;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import edu.psu.sweng500.api.basgs.BASGS_API.API_Object;

public class TestClient {
	
	public static void main(String argv[]) throws Exception {
		SSLSocketFactory f = (SSLSocketFactory) SSLSocketFactory.getDefault();
		// How to read file into String before Java 7
		String filename = "";
		String uuid = "";
		// Getting ClassLoader obj
		ClassLoader classLoader = TestClient.class.getClassLoader();
		System.out.println(classLoader.getResource(filename).getFile().toString());
		SSLSocket clientSocket = (SSLSocket) f.createSocket("localhost", 8888);
		clientSocket.setEnabledCipherSuites(clientSocket.getSupportedCipherSuites());

		clientSocket.startHandshake();
		//Socket clientSocket = new Socket("localhost", 8888);
		
		final int CREATE = 1;
		final int READ = 2;
		final int UPDATE = 3;
		final int DELETE = 4;
		final int EXIT = 5;
		
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
				case CREATE:
					filename = "TestAPICreate.json";
					break;
				case READ:
					filename = "TestAPIRead.json";
					break;
				case UPDATE:
					filename = "TestAPIUpdate.json";
					break;
				case DELETE:
					filename = "TestAPIDelete.json";
					break;
				case EXIT:
					run = false;
					break;
				default:
					System.out.println("Select 1-5");
			}
			
			if(run) {
				API_Object apiObj = readJsonStream(classLoader.getResourceAsStream(filename));
				if((Integer.parseInt(input) == UPDATE ||
						Integer.parseInt(input) == DELETE) &&
						apiObj.bacnet.size() > 0) {
					System.out.println("Setting UUID: " + uuid);
					apiObj.bacnet.get(0).uuid = uuid;
				}
				writeJsonStream(clientSocket.getOutputStream(),apiObj);
				
				while(read) {
					apiObj2 = readJsonStream(clientSocket.getInputStream());
					if(apiObj2 != null) {
						if(Integer.parseInt(input) == CREATE &&
								apiObj2.bacnet.size() > 0) {
							uuid = apiObj2.bacnet.get(0).uuid;
							System.out.println("Getting UUID: " + uuid);
						} 
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
