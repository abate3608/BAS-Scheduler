package edu.psu.sweng500.type;

public class BacnetDevice {
	 private int port;
	    private String ipAddress;
	    private String objectIdentifier;
	    
	    public BacnetDevice(String objectIdentifier, int port, String ipAddress) {
	    	this.objectIdentifier = objectIdentifier;
	        this.port = port;
	        this.ipAddress = ipAddress;
	    }

	    public BacnetDevice(String objectIdentifier) {
	    	this.objectIdentifier = objectIdentifier;
	        this.port = 0xBAC0;
	        this.ipAddress = "192.168.30.1";
		}

		public String getObjectIdentifier()
	    {
	    	return objectIdentifier;
	    }
	    public int getPort() {
	        return port;
	    }

	    public String getIpAddress() {
	        return ipAddress;
	    }
}
