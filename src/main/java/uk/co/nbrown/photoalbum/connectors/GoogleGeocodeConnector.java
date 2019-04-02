package uk.co.nbrown.photoalbum.connectors;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import uk.co.nbrown.photoalbum.connectors.interfaces.GeocodeConnector;
import uk.co.nbrown.photoalbum.exceptions.InternalServerErrorException;

@Component
public class GoogleGeocodeConnector implements GeocodeConnector {

	private final static String API_KEY = "AIzaSyBdgo7MFAKcIMIxWHMzHmedsY7bOemTrRY";
	private final static String API_URL_FORMAT = "https://maps.googleapis.com/maps/api/geocode/json?latlng=%s&key=%s";
	
	void connect(HttpURLConnection conn) {
		conn.setConnectTimeout(20000);
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			throw new InternalServerErrorException();
		}
		conn.setRequestProperty("Accept", "application/json");
	}
	
	HttpURLConnection getHttpUrlConnection(String url) {
		try {
			return (HttpURLConnection) new URL(String.format(url)).openConnection();
		} catch (IOException e) {
			throw new InternalServerErrorException();
		}
	}
	
	JSONObject getJsonResponse(HttpURLConnection conn) throws IOException {
		Scanner scanner = new Scanner(conn.getInputStream());
		String output = "";
		while (scanner.hasNext()) {
			output += scanner.nextLine();
		}
		scanner.close();
		return new JSONObject(output);
	}
	
	@Override
	public String getLocation(String geocode) {
		String url = String.format(API_URL_FORMAT, geocode, API_KEY);
		HttpURLConnection conn = getHttpUrlConnection(url);
		connect(conn);
		try {
			JSONObject json = getJsonResponse(conn);
			return getLocationFromJson(json);
		} catch (IOException e) {
			throw new InternalServerErrorException();
		}
	}
	
	private String getLocationFromJson(JSONObject json) {
		return json.getJSONArray("results")
				.getJSONObject(0)
				.optString("formatted_address");
	}
}