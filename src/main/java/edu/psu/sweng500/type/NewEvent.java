package edu.psu.sweng500.type;

public class NewEvent {
	private String newEventName;
	private boolean authenticated;

	public NewEvent(String newEventName, boolean authenicated) {
		this.newEventName =  newEventName;
		this.authenticated = false;
	}

	public String getUserName() {
		return  newEventName;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}
}
