package uk.co.nbrown.photoalbum.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import uk.co.nbrown.photoalbum.models.Picture;

public class PaginatorTest {

	@Test
	public void testGetPaginatedPicturesGetsCorrectSizeList() {
		//Arrange
		List<Picture> pictures = Arrays.asList(new Picture(), new Picture(), new Picture());
		int expected = 2;
		
		//Act
		int actual = Paginator.getListPage(pictures, 1, expected).size();
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetPaginatedPicturesGetsRemainingPicturesWhenPageNotFull() {
		//Arrange
		List<Picture> pictures = Arrays.asList(new Picture(), new Picture(), new Picture());
		List<Picture> expected = Arrays.asList(new Picture());
		
		//Act
		List<Picture> actual = Paginator.getListPage(pictures, 2, 2);
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetPaginatedPicturesThrowsIndexOutOfBoundsExceptionWhenStartIndexGreaterThanLastIndex() {
		//Arrange
		List<Picture> pictures = Arrays.asList(new Picture(), new Picture(), new Picture());
		
		//Act
		Paginator.getListPage(pictures, 3, 2);
		
		//Assert
	}
}
