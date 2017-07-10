package edu.psu.sweng500.type;

public class DBSiteTable {
	private int id;
	private String name;
	private String description;
	private String address;
	private String address2;
	private String city;
	private String state;
	private String zipCode;
	private String countryCode;
	
	public DBSiteTable() {
		id = 0;
		name = "";
		description = "";
		address = "";
		address2 = "";
		city = "";
		state = "";
		zipCode = "";
		countryCode = "";
	}
	
	public DBSiteTable (int id, String name, String description, String address, String address2, String city, String state, String zipCode, String countryCode) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.countryCode = countryCode;
	}
	
	public void setId (int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public String getName () {
		return name;
	}
	
	public void setDescription (String description) {
		this.description = description;
	}
	
	public String getDescription () {
		return description;
	}
	
	public void setAddress (String address) {
		this.address = address;
	}
	
	public String getAddress () {
		return address;
	}
	
	public void setAddress2 (String address2) {
		this.address2 = address2;
	}
	
	public String getAddress2 () {
		return address2;
	}
	
	public void setCity (String city) {
		this.city = city;
	}
	
	public String getCity () {
		return city;
	}
	
	public void setState (String state) {
		this.state = state;
	}
	
	public String getState () {
		return state;
	}
	
	public void setZipCode (String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getZipCode () {
		return zipCode;
	}
	
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
}

