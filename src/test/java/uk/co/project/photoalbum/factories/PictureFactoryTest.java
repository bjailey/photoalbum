package uk.co.project.photoalbum.factories;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import uk.co.project.photoalbum.models.Picture;

public class PictureFactoryTest {

	PictureFactory pictureFactory;
	
	@Before
	public void setUp() {
		pictureFactory = new PictureFactory();
	}
	
	@Test
	public void testCreatePictureCreatesPicture() {
		//Arrange
		String kidId = "kid id";
		String fileName = "file name";
		LocalDateTime date = LocalDateTime.parse("2000-01-01T01:01:01");
		Picture expected = new Picture();
		expected.setKidId(kidId);
		expected.setFileName(fileName);
		expected.setDate(date);
		
		//Act
		Picture actual = pictureFactory.createPicture(kidId, fileName, date);
		
		//Assert
		assertEquals(expected, actual);
	}
}