package api.basgs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import edu.psu.sweng500.api.basgs.BASGS_API.API_Object;
import edu.psu.sweng500.database.Database;
import edu.psu.sweng500.database.MysqlConnection;
import edu.psu.sweng500.type.DBSiteTable;
import edu.psu.sweng500.api.basgs.MultiThreadedAPIServer;
import edu.psu.sweng500.api.basgs.TestClient;

public class ClientServiceThreadTest {
	
	private static final int PORT = 8888;
	private ClassLoader classLoader;
	private Socket clientSocket;
	private String uuidFutureTime = "";
	private String uuidAnyTime = "";
	private Date currentTime;
	private Date futureTime;
	private DateFormat df;
	private static DBSiteTable site = new DBSiteTable();
	
	public ClientServiceThreadTest() throws Exception{
		System.out.println("Starting ClientServiceThreadTest");
		
		MysqlConnection dao = new MysqlConnection();
		// test database
		dao.readDB();
		new Database(dao.getConnection()); // start the database

		Thread t1=new Thread(new ThreadedServerStarter());
		t1.start();
		
		classLoader = TestClient.class.getClassLoader();
		clientSocket = new Socket("localhost", PORT);
		
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Calendar calendar = Calendar.getInstance();
		currentTime = calendar.getTime();
		int hour = calendar.get(Calendar.HOUR_OF_DAY) + 1;
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		futureTime = calendar.getTime();
	}
	
	public class ThreadedServerStarter implements Runnable {

		@Override
		public void run() {
			new MultiThreadedAPIServer();//Start the API Server
			
		}
		
	}
	
	@Test
	public void testOrder() throws IOException {
		System.out.println("Test Order");
		testBasgsApiCreateWithinTempRangeFutureTime();
		testBasgsApiCreateWithinBadTemp();
		testBasgsApiRead();
		testBasgsApiUpdateTemp();
		testBasgsApiDeleteNotInProgress();
		testBasgsApiDeleteInProgress();
		testBasgsApiUpdateDelete();
	}
	
	public void testBasgsApiCreateWithinTempRangeFutureTime() throws IOException{
		System.out.println("Starting Create Test");
		String filename = "TestAPICreate.json";
		API_Object apiObjSend = readJsonStream(classLoader.getResourceAsStream(filename));
		
		if(apiObjSend.bacnet.size() > 0) {
			apiObjSend.bacnet.get(0).temperatureSetpoint = Integer.toString(67);
			apiObjSend.bacnet.get(0).eventStart = df.format(currentTime);
			apiObjSend.bacnet.get(0).eventStop = df.format(futureTime);
		}
		writeJsonStream(clientSocket.getOutputStream(),apiObjSend);
		
		API_Object apiObjRecv = null;
		boolean read = true;
		while(read) {
			apiObjRecv = readJsonStream(clientSocket.getInputStream());
			if(apiObjRecv != null) {
				Gson gson = new Gson();
				String json = gson.toJson(apiObjRecv);
				System.out.println(json);
				read = false;
			}
		}
		if(apiObjRecv.bacnet.size() > 0) {
			uuidFutureTime = apiObjRecv.bacnet.get(0).uuid;
		}
		assertNotNull(apiObjRecv);
		assertEquals(0,apiObjRecv.error);
	}
	
	public void testBasgsApiCreateWithinBadTemp() throws IOException{
		System.out.println("Starting Create Bad Temp Test");
		String filename = "TestAPICreate.json";
		API_Object apiObjSend = readJsonStream(classLoader.getResourceAsStream(filename));
		
		if(apiObjSend.bacnet.size() > 0) {
			apiObjSend.bacnet.get(0).temperatureSetpoint = Integer.toString(81);
		}
		writeJsonStream(clientSocket.getOutputStream(),apiObjSend);
		
		API_Object apiObjRecv = null;
		boolean read = true;
		while(read) {
			apiObjRecv = readJsonStream(clientSocket.getInputStream());
			if(apiObjRecv != null) {
				Gson gson = new Gson();
				String json = gson.toJson(apiObjRecv);
				System.out.println(json);
				read = false;
			}
		}
		if(apiObjRecv.bacnet.size() > 0) {
			uuidAnyTime = apiObjRecv.bacnet.get(0).uuid;
			assertEquals("68.0",apiObjRecv.bacnet.get(0).temperatureSetpoint);
		} else {
			
		}
		assertNotNull(apiObjRecv);
		assertEquals(0,apiObjRecv.error);
	}
	
	public void testBasgsApiRead() throws IOException{
		System.out.println("Starting Read Test");
		String filename = "TestAPIRead.json";
		API_Object apiObjSend = readJsonStream(classLoader.getResourceAsStream(filename));
		API_Object apiObjRecv = null;
		writeJsonStream(clientSocket.getOutputStream(),apiObjSend);
		boolean read = true;
		while(read) {
			apiObjRecv = readJsonStream(clientSocket.getInputStream());
			if(apiObjRecv != null) {
				Gson gson = new Gson();
				String json = gson.toJson(apiObjRecv);
				System.out.println(json);
				read = false;
			}
		}
		assertNotNull(apiObjRecv);
		assertEquals(0,apiObjRecv.error);
	}
	
	public void testBasgsApiUpdateTemp() throws IOException{
		System.out.println("Starting Update Test");
		String filename = "TestAPIUpdate.json";
		API_Object apiObjSend = readJsonStream(classLoader.getResourceAsStream(filename));
		if(apiObjSend.bacnet.size() > 0) {
			apiObjSend.bacnet.get(0).uuid = uuidFutureTime;
			apiObjSend.bacnet.get(0).eventStart = df.format(currentTime);
			apiObjSend.bacnet.get(0).eventStop = df.format(futureTime);
			apiObjSend.bacnet.get(0).temperatureSetpoint = Integer.toString(77);
		}
		writeJsonStream(clientSocket.getOutputStream(),apiObjSend);
		
		API_Object apiObjRecv = null;
		boolean read = true;
		while(read) {
			apiObjRecv = readJsonStream(clientSocket.getInputStream());
			if(apiObjRecv != null) {
				Gson gson = new Gson();
				String json = gson.toJson(apiObjRecv);
				System.out.println(json);
				read = false;
			}
		}
		assertNotNull(apiObjRecv);
		assertEquals(0,apiObjRecv.error);
		if(apiObjRecv.bacnet.size() > 0) {
			assertEquals("77.0",apiObjRecv.bacnet.get(0).temperatureSetpoint);
		} else {
			assertEquals(0,1); //Force Failure
		}
		
		
	}
	
	public void testBasgsApiDeleteNotInProgress() throws IOException{
		System.out.println("Starting Delete Test");
		String filename = "TestAPIDelete.json";
		API_Object apiObjSend = readJsonStream(classLoader.getResourceAsStream(filename));
		if(apiObjSend.bacnet.size() > 0) {
			apiObjSend.bacnet.get(0).uuid = uuidAnyTime;
		}
		writeJsonStream(clientSocket.getOutputStream(),apiObjSend);
		
		API_Object apiObjRecv = null;
		boolean read = true;
		while(read) {
			apiObjRecv = readJsonStream(clientSocket.getInputStream());
			if(apiObjRecv != null) {
				Gson gson = new Gson();
				String json = gson.toJson(apiObjRecv);
				System.out.println(json);
				read = false;
			}
		}
		assertNotNull(apiObjRecv);
		assertEquals(0,apiObjRecv.error);
	}
	
	public void testBasgsApiDeleteInProgress() throws IOException{
		System.out.println("Starting Delete Test");
		String filename = "TestAPIDelete.json";
		API_Object apiObjSend = readJsonStream(classLoader.getResourceAsStream(filename));
		if(apiObjSend.bacnet.size() > 0) {
			apiObjSend.bacnet.get(0).uuid = uuidFutureTime;
		}
		writeJsonStream(clientSocket.getOutputStream(),apiObjSend);
		
		API_Object apiObjRecv = null;
		boolean read = true;
		while(read) {
			apiObjRecv = readJsonStream(clientSocket.getInputStream());
			if(apiObjRecv != null) {
				Gson gson = new Gson();
				String json = gson.toJson(apiObjRecv);
				System.out.println(json);
				read = false;
			}
		}
		assertNotNull(apiObjRecv);
		assertEquals(3,apiObjRecv.error);
	}
	
	protected void tearDown() throws Exception {
		System.out.println("Running: tearDown");
		clientSocket.close();
	}
	
	public void testBasgsApiUpdateDelete() throws IOException{
		System.out.println("Starting Update/Delete Test");
		String filename = "TestAPIUpdate.json";
		API_Object apiObjSend = readJsonStream(classLoader.getResourceAsStream(filename));
		if(apiObjSend.bacnet.size() > 0) {
			apiObjSend.bacnet.get(0).uuid = uuidFutureTime;
		}
		writeJsonStream(clientSocket.getOutputStream(),apiObjSend);
		
		API_Object apiObjRecv = null;
		boolean read = true;
		while(read) {
			apiObjRecv = readJsonStream(clientSocket.getInputStream());
			if(apiObjRecv != null) {
				Gson gson = new Gson();
				String json = gson.toJson(apiObjRecv);
				System.out.println(json);
				read = false;
			}
		}
		assertNotNull(apiObjRecv);
		assertEquals(0,apiObjRecv.error);
		
		filename = "TestAPIDelete.json";
		apiObjSend = readJsonStream(classLoader.getResourceAsStream(filename));
		if(apiObjSend.bacnet.size() > 0) {
			apiObjSend.bacnet.get(0).uuid = uuidFutureTime;
		}
		writeJsonStream(clientSocket.getOutputStream(),apiObjSend);
		
		apiObjRecv = null;
		read = true;
		while(read) {
			apiObjRecv = readJsonStream(clientSocket.getInputStream());
			if(apiObjRecv != null) {
				Gson gson = new Gson();
				String json = gson.toJson(apiObjRecv);
				System.out.println(json);
				read = false;
			}
		}
		assertNotNull(apiObjRecv);
		assertEquals(0,apiObjRecv.error);
		
		
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
