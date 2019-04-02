package uk.co.project.photoalbum.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import uk.co.project.photoalbum.exceptions.KidNotFoundException;
import uk.co.project.photoalbum.exceptions.PictureNotFoundException;
import uk.co.project.photoalbum.factories.KidFactory;
import uk.co.project.photoalbum.models.Kid;
import uk.co.project.photoalbum.models.Picture;
import uk.co.project.photoalbum.services.interfaces.KidService;
import uk.co.project.photoalbum.utils.Paginator;

@Controller
public class KidController {

	private KidFactory kidFactory;
	private KidService kidService;
	private PictureController pictureController;
	
	@Autowired
	public KidController(KidFactory kidFactory, KidService kidService, PictureController pictureController) {
		this.kidFactory = kidFactory;
		this.kidService = kidService;
		this.pictureController = pictureController;
	}
	
	public void addPicture(String kidId, MultipartFile file, LocalDateTime dateTime, Optional<String> geocode) {
		Kid kid = getKid(kidId);
		Picture picture = pictureController.createPicture(kidId, file, dateTime, geocode);
		int kidAge = getKidsAgeOnDate(kid, picture.getDate().toLocalDate());
		picture.setKidAge(kidAge);
		kid.addPicture(picture);
		kidService.saveKid(kid);
	}
	
	public void deletePicture(String kidId, String pictureId) {
		Kid kid = getKid(kidId);
		Picture picture = kid.getPicture(pictureId).orElseThrow(() -> new PictureNotFoundException());
		kid.getPictures().remove(picture);
		pictureController.deletePictureFile(picture.getFileName());
		kidService.saveKid(kid);
	}
	
	public Kid getKid(String kidId) {
		return kidService.getKid(kidId).orElseThrow(() -> new KidNotFoundException());
	}
	
	int getKidsAgeOnDate(Kid kid, LocalDate date) {
		LocalDate dateOfBirth = kid.getDateOfBirth();
		return dateOfBirth.until(date).getYears();
	}
	
	public List<Picture> getAllPictures(String kidId) {
		return getKid(kidId).getPictures();
	}
	
	public Picture getPicture(String kidId, String pictureId) {
		return getKid(kidId).getPicture(pictureId).orElseThrow(() -> new PictureNotFoundException());
	}

	public List<Picture> getPicturesInDateRange(String kidId, Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
		List<Picture> pictures = getKid(kidId).getPictures();
		pictureController.filterPicturesByDate(pictures, startDate, endDate);
		return pictures;
	}
	
	public void updateKid(String kidId, Optional<String> name, Optional<String> gender, Optional<LocalDate> dateOfBirth) {
		Kid kid = getKid(kidId);
		kidFactory.updateKid(kid, name, gender, dateOfBirth);
		kidService.saveKid(kid);
	}

	public List<Picture> getPaginatedPictures(String kidId, int pageNumber) {
		int pageSize = 2;
		List<Picture> pictures = getAllPictures(kidId);
		return Paginator.getListPage(pictures, pageNumber, pageSize);
	}
}