package api.weather;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.psu.sweng500.api.weather.OpenWeatherMapAPI;
import edu.psu.sweng500.api.weather.OpenWeatherMapAPI.ZipCode;

public class OpenWeatherMapAPITest {
	
	OpenWeatherMapAPI owmApi;
	
	@Before
	public void setup() {
		owmApi = new OpenWeatherMapAPI();
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void testOpenWeatherMapAPI() {
		assertNotNull(owmApi);
	}
	
	@Test
	public void testGetWeatherFromZip() {
		double temperature = Double.MAX_VALUE;
		double humidity = Double.MAX_VALUE;
		double dewpoint = Double.MAX_VALUE;

		owmApi.setZipcode(null);
		ZipCode zip = owmApi.getWeatherFromZip("92109");
		
		assertNotNull(zip);
		assertTrue(temperature != owmApi.getTemperature());
		assertTrue(humidity != owmApi.getHumidity());
		assertTrue(dewpoint != owmApi.getDewpoint());
		assertNotNull(owmApi.getZipcode());
	}
	
	@Test
	public void testUpdateWeather() {
		double temperature = Double.MAX_VALUE;
		double humidity = Double.MAX_VALUE;
		double dewpoint = Double.MAX_VALUE;
		String zipcode = "92109";
		
		owmApi.setZipcode(zipcode);
		owmApi.updateWeather();
		
		assertTrue(temperature != owmApi.getTemperature());
		assertTrue(humidity != owmApi.getHumidity());
		assertTrue(dewpoint != owmApi.getDewpoint());
		assertTrue(zipcode.equals(owmApi.getZipcode()));
	}
	
	@Test
	public void testGetTempFromZip() {
		double temp = owmApi.getTempFromZip("92109","US");
		assertTrue(temp != Double.MAX_VALUE);
	}
	
	@Test
	public void testGetWeatherFromZipCountryCode() {
		ZipCode zip = owmApi.getWeatherFromZip("92019", "US");
		assertNotNull(zip);
	}

}
