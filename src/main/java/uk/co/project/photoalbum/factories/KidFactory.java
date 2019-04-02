package uk.co.project.photoalbum.factories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import uk.co.project.photoalbum.models.Kid;

@Component
public class KidFactory {

	public Kid createKid(String name, String gender, LocalDate dateOfBirth) {
		Kid kid = new Kid();
		kid.setName(name);
		kid.setGender(gender);
		kid.setDateOfBirth(dateOfBirth);
		return kid;
	}
	
	public void updateKid(Kid kid, Optional<String> name, Optional<String> gender, Optional<LocalDate> dateOfBirth) {
		name.ifPresent(value -> kid.setName(value));
		gender.ifPresent(value -> kid.setGender(value));
		dateOfBirth.ifPresent(value -> kid.setDateOfBirth(value));
	}
}