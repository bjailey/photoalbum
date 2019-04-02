package uk.co.project.photoalbum.models;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class KidTest {

	Kid kid;
	
	@Before
	public void setUp() {
		kid = new Kid();
	}
	
	@Test
	public void testRemovePictureRemovesPicture() {
		//Arrange
		String id = "id";
		Picture picture = new Picture();
		picture.setId(id);
		List<Picture> pictures = new ArrayList<>();
		pictures.add(picture);
		kid.setPictures(pictures);
		
		//Act
		kid.removePicture(id);
		boolean result = pictures.contains(picture);
		
		//Assert
		assertFalse(result);
	}
}