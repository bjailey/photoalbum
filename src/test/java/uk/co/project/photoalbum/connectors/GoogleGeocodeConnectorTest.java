package uk.co.nbrown.photoalbum.connectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import uk.co.nbrown.photoalbum.exceptions.InternalServerErrorException;

public class GoogleGeocodeConnectorTest {

	HttpURLConnection mockConnection;
	GoogleGeocodeConnector spyGoogleGeocodeConnector;
	
	@Before
	public void setUp() {
		mockConnection = mock(HttpURLConnection.class);
		spyGoogleGeocodeConnector = spy(GoogleGeocodeConnector.class);
	}
	
	@Test
	public void testConnectConnects() {
		//Arrange
		
		//Act
		spyGoogleGeocodeConnector.connect(mockConnection);
		
		//Assert
		verify(mockConnection, times(1)).setRequestProperty(any(), any());
	}
	
	@Test(expected = InternalServerErrorException.class)
	public void testConnectThrowsInternalServerErrorExceptionWhenProtocolExceptionThrown() throws ProtocolException {
		//Arrange
		
		//Act
		doThrow(new ProtocolException()).when(mockConnection).setRequestMethod(any());
		spyGoogleGeocodeConnector.connect(mockConnection);
		
		//Assert
		verify(mockConnection, times(1)).setRequestProperty(any(), any());
	}
	
	@Test(expected = InternalServerErrorException.class)
	public void testGetHttpUrlConnectionThrowsInternalServerErrorExceptionWhenIOExceptionThrown() {
		//Arrange
		
		//Act
		spyGoogleGeocodeConnector.getHttpUrlConnection("");
		
		//Assert
	}
	
	@Test
	public void testGetJsonResponseGetsJsonResponse() throws IOException {
		//Arrange
		String expected = "{\"test\":\"test\"}";
		InputStream inputStream = new ByteArrayInputStream(expected.getBytes());
		
		//Act
		doReturn(inputStream).when(mockConnection).getInputStream();
		String actual = spyGoogleGeocodeConnector.getJsonResponse(mockConnection).toString();
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetLocationGetsLocation() throws IOException {
		//Arrange
		String expected = "location";
		JSONObject innerJson = new JSONObject();
		innerJson.put("formatted_address", expected);
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(innerJson);
		JSONObject json = new JSONObject();
		json.put("results", jsonArray);
		
		//Act
		doNothing().when(spyGoogleGeocodeConnector).connect(any());
		doReturn(json).when(spyGoogleGeocodeConnector).getJsonResponse(any());
		String actual = spyGoogleGeocodeConnector.getLocation(any());
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test(expected = InternalServerErrorException.class)
	public void testGetLocationThrowsInternalServerErrorExceptionWhenIOExceptionThrown() throws IOException {
		//Arrange
		
		//Act
		doReturn(mockConnection).when(spyGoogleGeocodeConnector).getHttpUrlConnection(any());
		doNothing().when(spyGoogleGeocodeConnector).connect(any());
		doThrow(new IOException()).when(spyGoogleGeocodeConnector).getJsonResponse(any());
		spyGoogleGeocodeConnector.getLocation(any());
		
		//Assert
	}
}