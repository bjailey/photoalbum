package uk.co.project.photoalbum.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uk.co.project.photoalbum.models.Kid;

@Repository
public interface KidRepository extends CrudRepository<Kid, String> {
	
}