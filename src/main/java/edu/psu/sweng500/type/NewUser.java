package edu.psu.sweng500.type;

public class NewUser {
	private String newUserName;
	private boolean authenticated;

	public NewUser(String newUserName, boolean authenicated) {
		this.newUserName =  newUserName;
		this.authenticated = false;
	}

	public String getUserName() {
		return  newUserName;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}
}


