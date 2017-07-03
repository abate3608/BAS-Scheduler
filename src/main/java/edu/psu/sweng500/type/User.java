package edu.psu.sweng500.type;

public class User {
	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	private String password;
	private boolean authenticated;

	public User(String userName, boolean b) {
		this.userName = userName;
		this.authenticated = false;
	}

	public User(String firstName, String lastName, String email, String userName, String password) {
		this.userName = userName;
		this.authenticated = false;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
	public boolean isAuthenticated() {
		return authenticated;
	}
	
	public void setAuthenticated(boolean b) {
		authenticated = b;
	}
}
