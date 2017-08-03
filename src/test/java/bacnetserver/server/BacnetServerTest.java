package bacnetserver.server;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.psu.sweng500.bacnetserver.server.BacnetServer;

public class BacnetServerTest {
	BacnetServer b;
	
	@Before
	public void setup() {
		b = new BacnetServer();
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void testIsNumeric() {
		assertTrue(b.isNumeric("1"));
		assertFalse(b.isNumeric("s"));
	}
	
	@Test
	public void testRoomInfoUpdate () {
		
	}
	
}
