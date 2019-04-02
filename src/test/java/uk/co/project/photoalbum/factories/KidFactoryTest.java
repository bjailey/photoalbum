package uk.co.project.photoalbum.factories;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import uk.co.project.photoalbum.models.Kid;

public class KidFactoryTest {
	
	String name;
	String gender;
	LocalDate dateOfBirth;
	KidFactory kidFactory;
	
	@Before
	public void setUp() {
		dateOfBirth = LocalDate.parse("2000-01-01");
		gender = "gender";
		name = "name";
		kidFactory = new KidFactory();
	}
	
	@Test
	public void testCreateKidCreatesKid() {
		//Arrange
		Kid expected = new Kid();
		expected.setDateOfBirth(dateOfBirth);
		expected.setGender(gender);
		expected.setName(name);
		
		//Act
		Kid actual = kidFactory.createKid(name, gender, dateOfBirth);
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testUpdateKidSetsNameWhenNameIsPresent() {
		//Arrange
		Optional<String> nameOpt = Optional.ofNullable(name);
		Optional<String> genderOpt = Optional.ofNullable(null);
		Optional<LocalDate> dateOfBirthOpt = Optional.ofNullable(null);
		Kid kid = new Kid();
		String expected = name;
		
		//Act
		kidFactory.updateKid(kid, nameOpt, genderOpt, dateOfBirthOpt);
		String actual = kid.getName();
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testUpdateKidSetsGenderWhenGenderIsPresent() {
		//Arrange
		Optional<String> nameOpt = Optional.ofNullable(null);
		Optional<String> genderOpt = Optional.ofNullable(gender);
		Optional<LocalDate> dateOfBirthOpt = Optional.ofNullable(null);
		Kid kid = new Kid();
		String expected = gender;
		
		//Act
		kidFactory.updateKid(kid, nameOpt, genderOpt, dateOfBirthOpt);
		String actual = kid.getGender();
		
		//Assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void testUpdateKidSetsDateOfBirthWhenDateOfBirthIsPresent() {
		//Arrange
		Optional<String> nameOpt = Optional.ofNullable(null);
		Optional<String> genderOpt = Optional.ofNullable(null);
		Optional<LocalDate> dateOfBirthOpt = Optional.ofNullable(dateOfBirth);
		Kid kid = new Kid();
		LocalDate expected = dateOfBirth;
		
		//Act
		kidFactory.updateKid(kid, nameOpt, genderOpt, dateOfBirthOpt);
		LocalDate actual = kid.getDateOfBirth();
		
		//Assert
		assertEquals(expected, actual);
	}
}