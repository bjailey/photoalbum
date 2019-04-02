package uk.co.nbrown.photoalbum.services.interfaces;

import java.util.Optional;

import uk.co.nbrown.photoalbum.models.Kid;

public interface KidService {

	boolean kidExists(String id);
	Optional<Kid> getKid(String id);
	void saveKid(Kid kid);
}