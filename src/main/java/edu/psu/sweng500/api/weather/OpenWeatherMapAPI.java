package edu.psu.sweng500.api.weather;

import java.util.ArrayList;

import com.google.gson.Gson;

/*
 * Author: Brian Abate
 * This class queries OpenWeatherMap to determine weather for
 * specific locations
 */
public class OpenWeatherMapAPI {

	private static final String API_KEY = "61544e3e538b756d02b74bdc831e7c2b";
	private static final String UNITS = "imperial"; // sets all OpenWeatherMap
													// received units
	private double temperature;
	private double humidity;
	private double dewpoint;
	private String g_zipcode;
	
	// Class structure used to store OpenWeatherMap requested data
	public static class ZipCode {
		Coord coord;
		Sys sys;
		ArrayList<Weather> weather = new ArrayList<Weather>();
		String base;
		Main main;
		Wind wind;
		Clouds clouds;
		double dt;
		int id;
		String name;
		int cod;
	}

	// Class structure used to store OpenWeatherMap coordinate data
	public static class Coord {
		double lon;
		double lat;
	}

	// Class structure used to store OpenWeatherMap sys data
	public static class Sys {
		int type;
		int id;
		double message;
		String country;
		int sunrise;
		int sunset;
	}

	// Class structure used to store OpenWeatherMap weather data
	public static class Weather {
		int id;
		String main;
		String description;
		String icon;
	}

	// Class structure used to store OpenWeatherMap main data
	public static class Main {
		double temp;
		double humidity;
		double pressure;
		double temp_min;
		double temp_max;
	}

	// Class structure used to store OpenWeatherMap wind data
	public static class Wind {
		double speed;
		double deg;
	}

	// Class structure used to store OpenWeatherMap clouds data
	public static class Clouds {
		double all;
	}

	public double getTemperature() {
		return temperature;
	}
	
	public double getHumidity() {
		return humidity;
	}
	
	public double getDewpoint() {
		return dewpoint;
	}
	
	public String getZipcode() {
		return g_zipcode;
	}
	
	public void setZipcode(String zip) {
		g_zipcode = zip;
	}
	/*
	 * This method uses zipcode and country code to query the OpenWeatherMap
	 * server for location weather information.
	 * 
	 * @param [in] zip - String of zip code for requested weather location
	 * 
	 * @param [in] countryCode - String of the country code for requested
	 * weather location
	 * 
	 * @return ZipCode object with requested weather information
	 */
	public ZipCode getWeatherFromZip(String zip, String countryCode) {
		ZipCode zipCode = null;
		String json;
		try {
			json = UrlReader.readUrl("http://api.openweathermap.org/data/2.5/weather?zip=" + zip + "," + countryCode
					+ "&units=" + UNITS + "&APPID=" + API_KEY);
			Gson gson = new Gson();
			zipCode = gson.fromJson(json, ZipCode.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return zipCode;
	}

	/*
	 * This method uses zipcode and country code to query the OpenWeatherMap
	 * server for temperature information.
	 * 
	 * @param [in] zip - String of zip code for requested weather location
	 * 
	 * @param [in] countryCode - String of the country code for requested
	 * weather location
	 * 
	 * @return Temperature as Double, otherwise Double Max Value
	 */
	public double getTempFromZip(String zip, String countryCode) {
		ZipCode zipCode = getWeatherFromZip(zip, countryCode);
		if (zipCode != null) {
			return zipCode.main.temp;
		}
		return Double.MAX_VALUE;
	}
	
	public ZipCode getWeatherFromZip(String zip) {
		String countryCode = "us";
		ZipCode zipCode = null;
		String json;
		try {
			json = UrlReader.readUrl("http://api.openweathermap.org/data/2.5/weather?zip=" + zip + "," + countryCode
					+ "&units=" + UNITS + "&APPID=" + API_KEY);
			Gson gson = new Gson();
			zipCode = gson.fromJson(json, ZipCode.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		temperature = zipCode.main.temp;
		humidity = zipCode.main.humidity;
		dewpoint = getDewpoint(temperature, humidity);
		g_zipcode = zip;
		return zipCode;
	}
	
	
	public void updateWeather() {
		String countryCode = "us";
		String zip = g_zipcode;
		ZipCode zipCode = null;
		String json;
		try {
			json = UrlReader.readUrl("http://api.openweathermap.org/data/2.5/weather?zip=" + zip + "," + countryCode
					+ "&units=" + UNITS + "&APPID=" + API_KEY);
			Gson gson = new Gson();
			zipCode = gson.fromJson(json, ZipCode.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		temperature = zipCode.main.temp;
		humidity = zipCode.main.humidity;
		dewpoint = getDewpoint(temperature, humidity);
		g_zipcode = zip;

	}
	
	private double getDewpoint(double temp, double humidity) {
		return temp - ((100-humidity) / 5);
	}
	
	public static void main(String[] args) {
		OpenWeatherMapAPI owm = new OpenWeatherMapAPI();
		ZipCode zip = owm.getWeatherFromZip("92109", "us");
		double temp = zip.main.temp;
		
	}

}
