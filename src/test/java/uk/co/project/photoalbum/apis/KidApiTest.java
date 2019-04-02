package uk.co.nbrown.photoalbum.apis;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.server.ResponseStatusException;

import uk.co.nbrown.photoalbum.controllers.KidController;
import uk.co.nbrown.photoalbum.exceptions.InternalServerErrorException;
import uk.co.nbrown.photoalbum.exceptions.KidNotFoundException;
import uk.co.nbrown.photoalbum.exceptions.PictureNotFoundException;
import uk.co.nbrown.photoalbum.models.Kid;
import uk.co.nbrown.photoalbum.models.Picture;

public class KidApiTest {

	KidApi kidApi;
	KidController mockKidController;
	
	@Before
	public void setUp() {
		mockKidController = mock(KidController.class);
		kidApi = new KidApi(mockKidController);
	}
	
	@Test
	public void testAddPictureAddsPicture() {
		//Arrange
		
		//Act
		kidApi.addPicture(null, null, null, null, null, null);
		
		//Assert
		verify(mockKidController, times(1)).addPicture(any(), any(), any(), any());
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testAddPictureThrowsResponseStatusExceptionWhenKidNotFoundExceptionThrown() {
		//Arrange
		
		//Act
		doThrow(new KidNotFoundException()).when(mockKidController).addPicture(any(), any(), any(), any());
		kidApi.addPicture(null, null, null, null, null, null);
		
		//Assert
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testAddPictureThrowsResponseStatusExceptionWhenInternalServerErrorExceptionThrown() {
		//Arrange
		
		//Act
		doThrow(new InternalServerErrorException()).when(mockKidController).addPicture(any(), any(), any(), any());
		kidApi.addPicture(null, null, null, null, null, null);
		
		//Assert
	}
	
	@Test
	public void testDeletePictureDeletesPicture() {
		//Arrange
		
		//Act
		kidApi.deletePicture(null, null, null, null);
		
		//Assert
		verify(mockKidController, times(1)).deletePicture(any(), any());
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testDeletePictureThrowsResponseStatusExceptionWhenKidNotFoundExceptionThrown() {
		//Arrange
		
		//Act
		doThrow(new KidNotFoundException()).when(mockKidController).deletePicture(any(), any());
		kidApi.deletePicture(null, null, null, null);
		
		//Assert
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testDeletePictureThrowsResponseStatusExceptionWhenPictureNotFoundExceptionThrown() {
		//Arrange
		
		//Act
		doThrow(new PictureNotFoundException()).when(mockKidController).deletePicture(any(), any());
		kidApi.deletePicture(null, null, null, null);
		
		//Assert
	}
	
	@Test
	public void testGetKidGetsKid() {
		//Arrange
		Kid expected = new Kid();
		
		//Act
		doReturn(expected).when(mockKidController).getKid(any());
		Kid actual = kidApi.getKid(null, null, null);
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testGetKidThrowsResponseStatusExceptionWhenKidNotFoundExceptionThrown() {
		//Arrange
		
		//Act
		doThrow(new KidNotFoundException()).when(mockKidController).getKid(any());
		kidApi.getKid(null, null, null);
		
		//Assert
	}
	
	@Test
	public void testGetPaginatedPicturesGetsPaginatedPictures() {
		//Arrange
		List<Picture> expected = new ArrayList<>();
		
		//Act
		doReturn(expected).when(mockKidController).getPaginatedPictures(any(), anyInt());	
		List<Picture> actual = kidApi.getPaginatedPictures(null, null, null, 0);
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testGetPaginatedPicturesThrowsResponseStatusExceptionWhenKidNotFoundExceptionThrown() {
		//Arrange
		
		//Act
		doThrow(new KidNotFoundException()).when(mockKidController).getPaginatedPictures(any(), anyInt());
		kidApi.getPaginatedPictures(null, null, null, 0);
		
		//Assert
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testGetPaginatedPicturesThrowsResponseStatusExceptionWhenIndexOutOfBoundsExceptionThrown() {
		//Arrange
		
		//Act
		doThrow(new IndexOutOfBoundsException()).when(mockKidController).getPaginatedPictures(any(), anyInt());
		kidApi.getPaginatedPictures(null, null, null, 0);
		
		//Assert
	}
	
	@Test
	public void testGetPicturesInDateRangeGetsPicturesInDateRange() {
		//Arrange
		List<Picture> expected = new ArrayList<>();
		
		//Act
		doReturn(expected).when(mockKidController).getPicturesInDateRange(any(), any(), any());
		List<Picture> actual = kidApi.getPicturesInDateRange(null, null, null, null, null);
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testGetPicturesInDateRangeThrowsResponseStatusExceptionWhenKidNotFoundExceptionThrown() {
		//Arrange
		
		//Act
		doThrow(new KidNotFoundException()).when(mockKidController).getPicturesInDateRange(any(), any(), any());
		kidApi.getPicturesInDateRange(null, null, null, null, null);
		
		//Assert
	}
	
	@Test
	public void testGetPictureGetsPicture() {
		//Arrange
		Picture expected = new Picture();
		
		//Act
		doReturn(expected).when(mockKidController).getPicture(any(), any());
		Picture actual = kidApi.getPicture(null, null, null, null);
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testGetPictureThrowsResponseStatusExceptionWhenKidNotFoundExceptionThrown() {
		//Arrange
		
		//Act
		doThrow(new KidNotFoundException()).when(mockKidController).getPicture(any(), any());
		kidApi.getPicture(null, null, null, null);
		
		//Assert
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testGetPictureThrowsResponseStatusExceptionWhenPictureNotFoundExceptionThrown() {
		//Arrange
		
		//Act
		doThrow(new PictureNotFoundException()).when(mockKidController).getPicture(any(), any());
		kidApi.getPicture(null, null, null, null);
		
		//Assert
	}
	
	@Test
	public void testUpdateKidUpdatesKid() {
		//Arrange
		
		//Act
		kidApi.updateKid(null, null, null, null, null, null);
		
		//Assert
		verify(mockKidController, times(1)).updateKid(any(), any(), any(), any());
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testUpdateKidThrowsResponseStatusExceptionWhenKidNotFoundExceptionThrown() {
		//Arrange
		
		//Act
		doThrow(new KidNotFoundException()).when(mockKidController).updateKid(any(), any(), any(), any());
		kidApi.updateKid(null, null, null, null, null, null);
		
		//Assert
	}
	
	@Test
	public void testGetPicturesGetsPictures() {
		//Arrange
		List<Picture> expected = new ArrayList<>();
		
		//Act
		doReturn(expected).when(mockKidController).getAllPictures(any());
		List<Picture> actual = kidApi.getPictures(null, null, null);
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testgetPicturesThrowsResponseStatusExceptionWhenKidNotFoundExceptionThrown() {
		//Arrange
		
		//Act
		doThrow(new KidNotFoundException()).when(mockKidController).getAllPictures(any());
		kidApi.getPictures(null, null, null);
		
		//Assert
	}
}