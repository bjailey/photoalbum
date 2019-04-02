package uk.co.project.photoalbum.services.impls;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.project.photoalbum.models.Kid;
import uk.co.project.photoalbum.repositories.KidRepository;
import uk.co.project.photoalbum.services.interfaces.KidService;

@Service
public class KidServiceImpl implements KidService {

	private KidRepository kidRepository;
	
	@Autowired
	public KidServiceImpl(KidRepository kidRepository) {
		this.kidRepository = kidRepository;
	}
	
	@Override
	public Optional<Kid> getKid(String kidId) {
		return kidRepository.findById(kidId);
	}
	
	@Override
	public boolean kidExists(String kidId) {
		return kidRepository.existsById(kidId);
	}
	
	@Override
	public void saveKid(Kid kid) {
		kidRepository.save(kid);
	}
}