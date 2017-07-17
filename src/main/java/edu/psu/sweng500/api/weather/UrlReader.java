package edu.psu.sweng500.api.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/*
 * Author: Brian Abate
 * This class provides access to submit URL requests and receive requested data
 */
public class UrlReader {

	/*
	 * This method allows for a specificed URL to be used to request and provide
	 * data
	 * 
	 * @param [in] urlString - String of URL to request data from
	 * 
	 * @return String of the requested data from the specificed URL
	 */
	public static String readUrl(String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

}
