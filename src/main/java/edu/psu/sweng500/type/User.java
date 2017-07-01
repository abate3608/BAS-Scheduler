package edu.psu.sweng500.type;

public class User {

	private String userName;
	private boolean authenticated;

	public User(String userName, boolean b) {
		this.userName = userName;
		this.authenticated = false;
	}

	public String getUserName() {
		return userName;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}
	
	public void setAuthenticated(boolean b) {
		authenticated = b;
	}
}
