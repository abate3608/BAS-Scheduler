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
	
	public DBSiteTable (int pid, String pname, String pdescription, String paddress, String paddress2, String pcity, String pstate, String pzipCode, String pcountryCode) {
		id = pid;
		name = pname;
		description = pdescription;
		address = paddress;
		address2 = paddress2;
		city = pcity;
		state = pstate;
		zipCode = pzipCode;
		countryCode = pcountryCode;
	}
	
	public void setId (int pid) {
		id = pid;
	}
	
	public int getId() {
		return id;
	}
	
	public void setName (String pname) {
		name = pname;
	}
	
	public String getName () {
		return name;
	}
	
	public void setDescription (String pdescription) {
		description = pdescription;
	}
	
	public String getDescription () {
		return description;
	}
	
	public void setAddress (String paddress) {
		address = paddress;
	}
	
	public String getAddress () {
		return address;
	}
	
	public void setAddress2 (String paddress2) {
		address2 = paddress2;
	}
	
	public String getAddress2 () {
		return address2;
	}
	
	public void setCity (String pcity) {
		city = pcity;
	}
	
	public String getCity () {
		return city;
	}
	
	public void setState (String pstate) {
		state = pstate;
	}
	
	public String getState () {
		return state;
	}
	
	public void setZipCode (String pzipCode) {
		zipCode = pzipCode;
	}
	
	public String getZipCode () {
		return zipCode;
	}
	
	public void setCountryCode(String pcountryCode) {
		countryCode = pcountryCode;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
}

