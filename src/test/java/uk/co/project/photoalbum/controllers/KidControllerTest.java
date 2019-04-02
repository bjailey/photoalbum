package uk.co.nbrown.photoalbum.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import uk.co.nbrown.photoalbum.exceptions.KidNotFoundException;
import uk.co.nbrown.photoalbum.exceptions.PictureNotFoundException;
import uk.co.nbrown.photoalbum.factories.KidFactory;
import uk.co.nbrown.photoalbum.models.Kid;
import uk.co.nbrown.photoalbum.models.Picture;
import uk.co.nbrown.photoalbum.services.interfaces.KidService;

public class KidControllerTest {

	KidController spyKidController;
	KidFactory mockKidFactory;
	KidService mockKidService;
	PictureController mockPictureController;
	
	@Before
	public void setUp() {
		mockKidFactory = mock(KidFactory.class);
		mockKidService = mock(KidService.class);
		mockPictureController = mock(PictureController.class);
		spyKidController = spy(new KidController(mockKidFactory, mockKidService,
				mockPictureController));
	}
	
	@Test
	public void testAddPictureAddsPicture() {
		//Arrange
		Kid kid = new Kid();
		Picture picture = new Picture();
		picture.setDate(LocalDateTime.now());
		
		//Act
		doReturn(kid).when(spyKidController).getKid(any());
		doReturn(0).when(spyKidController).getKidsAgeOnDate(any(), any());
		doReturn(picture).when(mockPictureController).createPicture(any(), any(), any(), any());
		spyKidController.addPicture(any(), any(), any(), any());
		boolean result = kid.getPictures().contains(picture);
		
		//Assert
		assertTrue(result);
	}
	
	@Test
	public void testDeletePictureDeletesPicture() {
		//Arrange
		String pictureId = "photo id";
		Picture picture = new Picture();
		picture.setId(pictureId);
		List<Picture> pictures = new ArrayList<>();
		pictures.add(picture);
		Kid kid = new Kid();
		kid.setPictures(pictures);
		
		//Act
		doReturn(kid).when(spyKidController).getKid(any());
		spyKidController.deletePicture("", pictureId);
		boolean result = pictures.contains(picture);
		
		//Assert
		assertFalse(result);
	}
	
	@Test
	public void testDeletePictureDeletesPictureFile() {
		//Arrange
		String pictureId = "photo id";
		Picture picture = new Picture();
		picture.setId(pictureId);
		List<Picture> pictures = new ArrayList<>();
		pictures.add(picture);
		Kid kid = new Kid();
		kid.setPictures(pictures);
		
		//Act
		doReturn(kid).when(spyKidController).getKid(any());
		spyKidController.deletePicture("", pictureId);
		
		//Assert
		verify(mockPictureController, times(1)).deletePictureFile(any());
	}
	
	@Test(expected = PictureNotFoundException.class)
	public void testDeletePictureThrowsPictureNotFoundExceptionWhenPictureNotFound() {
		//Arrange
		List<Picture> pictures = new ArrayList<>();
		Kid kid = new Kid();
		kid.setPictures(pictures);
		
		//Act
		doReturn(kid).when(spyKidController).getKid(any());
		spyKidController.deletePicture("", "");
	}
	
	@Test
	public void testGetAllPicturesGetsAllPictures() {
		//Arrange
		List<Picture> expected = new ArrayList<>();
		Kid kid = new Kid();
		kid.setPictures(expected);
		
		//Act
		doReturn(kid).when(spyKidController).getKid(any());
		List<Picture> actual = spyKidController.getAllPictures(any());
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetKidGetsKid() {
		//Arrange
		Kid expected = new Kid();
		Optional<Kid> optional = Optional.ofNullable(expected);
		
		//Act
		doReturn(optional).when(mockKidService).getKid(any());
		Kid actual = spyKidController.getKid(any());
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test(expected = KidNotFoundException.class)
	public void testGetKidThrowsKidNotFoundExceptionWhenKidNotFound() {
		//Arrange
		Optional<Kid> optional = Optional.ofNullable(null);
		
		//Act
		doReturn(optional).when(mockKidService).getKid(any());
		spyKidController.getKid(any());
		
		//Assert
	}
	
	@Test
	public void testGetKidsAgeOnDateGetsKidsAgeOnDate() {
		//Arrange
		Kid kid = new Kid();
		kid.setDateOfBirth(LocalDate.parse("2000-01-01"));
		LocalDate date = LocalDate.parse("2010-01-01");
		int expected = 10;
		
		//Act
		int actual = spyKidController.getKidsAgeOnDate(kid, date);
		
		//Assert
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetPaginatedPicturesGetsPaginatedPictures() {
		//Arrange
		List<Picture> expected = new ArrayList<>();
		expected.add(new Picture());
		
		//Act
		doReturn(expected).when(spyKidController).getAllPictures(any());
		List<Picture> actual = spyKidController.getPaginatedPictures("", 1);
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetPictureGetsPicture() {
		//Arrange
		String pictureId = "id";
		Picture expected = new Picture();
		expected.setId(pictureId);
		Kid kid = new Kid();
		kid.addPicture(expected);
		
		//Act
		doReturn(kid).when(spyKidController).getKid(any());
		Picture actual = spyKidController.getPicture("", pictureId);
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test(expected = PictureNotFoundException.class)
	public void testGetPictureThrowsPictureNotFoundExceptionWhenPictureNotFound() {
		//Arrange
		Kid kid = new Kid();
		
		//Act
		doReturn(kid).when(spyKidController).getKid(any());
		spyKidController.getPicture(any(), any());
		
		//Assert
	}
	
	@Test
	public void testGetPicturesInDateRangeGetsPicturesInDateRange() {
		//Arrange
		List<Picture> expected = new ArrayList<>();
		Kid kid = new Kid();
		kid.setPictures(expected);
		
		//Act
		doReturn(kid).when(spyKidController).getKid(any());
		List<Picture> actual = spyKidController.getPicturesInDateRange(any(), any(), any());
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testUpdateKidUpdatesKid() {
		//Arrange
		Kid kid = new Kid();
		
		//Act
		doReturn(kid).when(spyKidController).getKid(any());
		spyKidController.updateKid(any(), any(), any(), any());
		
		//Assert
		verify(mockKidFactory, times(1)).updateKid(any(), any(), any(), any());
	}
}