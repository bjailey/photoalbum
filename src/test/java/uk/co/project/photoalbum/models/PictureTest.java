package uk.co.project.photoalbum.models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PictureTest {

	Picture photo;
	
	@Before
	public void setUp() {
		photo = new Picture();
	}
	
	@Test
	public void testEqualsReturnsFalseWhenGivenNull() {
		//Arrange
		
		//Act
		boolean result = photo.equals(null);
		
		//Assert
		assertFalse(result);
	}
	
	@Test
	public void testEqualsReturnsFalseWhenGivenDifferentObjectType() {
		//Arrange
		Object object = new Object();
		
		//Act
		boolean result = photo.equals(object);
		
		//Assert
		assertFalse(result);
	}
	
	@Test
	public void testEqualsReturnsTrueWhenGivenSameInstance() {
		//Arrange
		
		//Act
		boolean result = photo.equals(photo);
		
		//Assert
		assertTrue(result);
	}
	
}