package uk.co.nbrown.photoalbum.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uk.co.nbrown.photoalbum.models.Kid;

@Repository
public interface KidRepository extends CrudRepository<Kid, String> {
	
}