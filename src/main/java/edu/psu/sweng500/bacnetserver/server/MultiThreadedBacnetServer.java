package edu.psu.sweng500.bacnetserver.server;


public class MultiThreadedBacnetServer implements Runnable {

	boolean ServerOn = true;
	BacnetServer server = null;
	public MultiThreadedBacnetServer () {

	}

	public void start() {

		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				BacnetServer.stop();
			}
		});
		server = new BacnetServer();


		while(ServerOn) { 
			try {
				server.eventHandler.fireUpdateOccStatus();
				server.eventHandler.fireRoomInfoRequest();
				Thread.sleep(60000);
				System.out.println("BACnet Server Thread > Status " + getServiceStatus());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //2 minutes  
		} 
		try { 
			BacnetServer.stop(); 
			System.out.println("Server Stopped"); 
		} catch(Exception ioe) { 
			System.out.println("Error Found stopping server socket"); 
			System.exit(-1); 
		} 
	}

	/*
	 * This method returns the servers status
	 * @return the status of the server
	 */
	public boolean getServiceStatus() {
		this.ServerOn = server.status();
		return ServerOn;
	}


	@Override
	public void run() {
		this.start();//Start the API Server	
	}
}
