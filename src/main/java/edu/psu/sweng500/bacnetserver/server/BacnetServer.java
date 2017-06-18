package edu.psu.sweng500.bacnetserver.server;

import edu.psu.sweng500.eventqueue.event.EventHandler;

public class BacnetServer {
	
	
    
	 // Event listeners
    private final EventHandler eventHandler = new EventHandler();
    
	public BacnetServer()
	{
		
	}
	
	 public EventHandler getEventHandler() {
		 return eventHandler;
	 }
	 
	
}
