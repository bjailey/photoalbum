package uk.co.project.photoalbum.services.interfaces;

import java.util.Optional;

import uk.co.project.photoalbum.models.Kid;

public interface KidService {

	boolean kidExists(String id);
	Optional<Kid> getKid(String id);
	void saveKid(Kid kid);
}