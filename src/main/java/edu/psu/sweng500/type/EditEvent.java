package edu.psu.sweng500.type;



public class EditEvent {
	private String editEventName;
	private boolean authenticated;

	public EditEvent(String editEventName, boolean authenicated) {
		this.editEventName =  editEventName;
		this.authenticated = false;
	}

	public String getUserName() {
		return  editEventName;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}
}
