package uk.co.project.photoalbum.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import uk.co.project.photoalbum.connectors.interfaces.GeocodeConnector;
import uk.co.project.photoalbum.exceptions.InternalServerErrorException;
import uk.co.project.photoalbum.factories.PictureFactory;
import uk.co.project.photoalbum.models.Picture;
import uk.co.project.photoalbum.services.interfaces.FileService;

public class PictureControllerTest {

	FileService mockFileService;
	GeocodeConnector mockGeocodeConnector;
	PictureController pictureController;
	PictureFactory mockPictureFactory;
	
	@Before
	public void setUp() {
		mockFileService = mock(FileService.class);
		mockGeocodeConnector = mock(GeocodeConnector.class);
		mockPictureFactory = mock(PictureFactory.class);
		pictureController = new PictureController(mockPictureFactory, mockFileService, mockGeocodeConnector);
	}
	
	@Test
	public void testCreatePictureCreatesPicture() {
		//Arrange
		MultipartFile file = mock(MultipartFile.class);
		Optional<String> geocode = Optional.ofNullable(null);
		Picture expected = new Picture();
		
		//Act
		doReturn(expected).when(mockPictureFactory).createPicture(any(), any(), any());
		Picture actual = pictureController.createPicture(null, file, null, geocode);
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCreatePictureSetsPictureLocationWhenGeocodeIsNotNull() {
		//Arrange
		MultipartFile file = mock(MultipartFile.class);
		Optional<String> geocode = Optional.ofNullable("");
		Picture picture = new Picture();
		String expected = "location";
		
		//Act
		doReturn(picture).when(mockPictureFactory).createPicture(any(), any(), any());
		doReturn(expected).when(mockGeocodeConnector).getLocation(any());
		String actual = pictureController.createPicture(null, file, null, geocode).getLocation();
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test(expected = InternalServerErrorException.class)
	public void testCreatePictureThrowsInternalServerErrorExceptionWhenSavingFileThrowsException() throws IOException {
		//Arrange
		MultipartFile file = mock(MultipartFile.class);
		Optional<String> geocode = Optional.ofNullable(null);
		Picture picture = new Picture();
		
		//Act
		doReturn(picture).when(mockPictureFactory).createPicture(any(), any(), any());
		doThrow(new IOException()).when(mockFileService).saveFile(any(), any());
		pictureController.createPicture(null, file, null, geocode);
		
		//Assert
	}
	
	@Test
	public void testDeletePictureFileDeletesPictureFile() {
		//Arrange
		
		//Act
		pictureController.deletePictureFile(null);
		
		//Assert
		verify(mockFileService, times(1)).deleteFile(any());
	}
	
	@Test
	public void testFilterPicturesByDateRemovesPicturesFromBeforeStartDate() {
		//Arrange
		Optional<LocalDate> startDate = Optional.ofNullable(LocalDate.parse("2000-01-01"));
		Optional<LocalDate> endDate = Optional.ofNullable(null);
		Picture pictureOne = new Picture();
		Picture pictureTwo = new Picture();
		Picture pictureThree = new Picture();
		pictureOne.setDate(LocalDateTime.parse("1999-01-01T01:01:01"));
		pictureTwo.setDate(LocalDateTime.parse("2001-01-01T01:01:01"));
		pictureThree.setDate(LocalDateTime.parse("2003-01-01T01:01:01"));
		
		List<Picture> expected = new ArrayList<>();
		expected.add(pictureTwo);
		expected.add(pictureThree);
		
		List<Picture> actual = new ArrayList<>();
		actual.add(pictureOne);
		actual.add(pictureTwo);
		actual.add(pictureThree);
		
		//Act
		pictureController.filterPicturesByDate(actual, startDate, endDate);
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFilterPicturesByDateRemovesPicturesFromAfterEndDate() {
		//Arrange
		Optional<LocalDate> startDate = Optional.ofNullable(null);
		Optional<LocalDate> endDate = Optional.ofNullable(LocalDate.parse("2002-01-01"));
		Picture pictureOne = new Picture();
		Picture pictureTwo = new Picture();
		Picture pictureThree = new Picture();
		pictureOne.setDate(LocalDateTime.parse("1999-01-01T01:01:01"));
		pictureTwo.setDate(LocalDateTime.parse("2001-01-01T01:01:01"));
		pictureThree.setDate(LocalDateTime.parse("2003-01-01T01:01:01"));
		
		List<Picture> expected = new ArrayList<>();
		expected.add(pictureOne);
		expected.add(pictureTwo);
		
		List<Picture> actual = new ArrayList<>();
		actual.add(pictureOne);
		actual.add(pictureTwo);
		actual.add(pictureThree);
		
		//Act
		pictureController.filterPicturesByDate(actual, startDate, endDate);
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFilterPicturesByDateRemovesPicturesNotBetweenDates() {
		//Arrange
		Optional<LocalDate> startDate = Optional.ofNullable(LocalDate.parse("2000-01-01"));
		Optional<LocalDate> endDate = Optional.ofNullable(LocalDate.parse("2002-01-01"));
		Picture pictureOne = new Picture();
		Picture pictureTwo = new Picture();
		Picture pictureThree = new Picture();
		pictureOne.setDate(LocalDateTime.parse("1999-01-01T01:01:01"));
		pictureTwo.setDate(LocalDateTime.parse("2001-01-01T01:01:01"));
		pictureThree.setDate(LocalDateTime.parse("2003-01-01T01:01:01"));
		
		List<Picture> expected = new ArrayList<>();
		expected.add(pictureTwo);
		
		List<Picture> actual = new ArrayList<>();
		actual.add(pictureOne);
		actual.add(pictureTwo);
		actual.add(pictureThree);
		
		//Act
		pictureController.filterPicturesByDate(actual, startDate, endDate);
		
		//Assert
		assertEquals(expected, actual);
	}
}