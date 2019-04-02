package uk.co.project.photoalbum.services.impls;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import uk.co.project.photoalbum.models.Kid;
import uk.co.project.photoalbum.repositories.KidRepository;

public class KidServiceImplTest {

	KidRepository mockKidRepository;
	KidServiceImpl kidServiceImpl;
	
	@Before
	public void setUp() {
		mockKidRepository = mock(KidRepository.class);
		kidServiceImpl = new KidServiceImpl(mockKidRepository);
	}
	
	@Test
	public void testGetKidGetsOptionalKid() {
		//Arrange
		Optional<Kid> expected = Optional.of(new Kid());
		
		//Act
		doReturn(expected).when(mockKidRepository).findById(any());
		Optional<Kid> actual = kidServiceImpl.getKid(any());
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testKidExistsReturnsExistsById() {
		//Arrange
		boolean expected = true;
		
		//Act
		doReturn(expected).when(mockKidRepository).existsById(any());
		boolean actual = kidServiceImpl.kidExists(any());
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSaveKidSavesKid() {
		//Arrange
		Optional<Kid> expected = Optional.of(new Kid());
		
		//Act
		doReturn(expected).when(mockKidRepository).save(any());
		kidServiceImpl.saveKid(null);
		
		//Assert
		verify(mockKidRepository, times(1)).save(any());
	}
}