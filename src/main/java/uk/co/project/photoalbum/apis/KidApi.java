package uk.co.project.photoalbum.apis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;

import uk.co.project.photoalbum.controllers.KidController;
import uk.co.project.photoalbum.exceptions.InternalServerErrorException;
import uk.co.project.photoalbum.exceptions.KidNotFoundException;
import uk.co.project.photoalbum.exceptions.PictureNotFoundException;
import uk.co.project.photoalbum.models.Kid;
import uk.co.project.photoalbum.models.Picture;

@RestController
@RequestMapping("/kids")
public class KidApi {

	private KidController kidController;
	
	@Autowired
	public KidApi(KidController kidController) {
		this.kidController = kidController;
	}
	
	@PostMapping("/{kidId}/pictures")
	public void addPicture(MultipartHttpServletRequest request, HttpServletResponse response,
			@PathVariable String kidId, @RequestPart MultipartFile file,
			@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime date,
			@RequestParam(required = false) Optional<String> geocode) {
		try {
			kidController.addPicture(kidId, file, date, geocode);
		} catch (KidNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kid not found");
		} catch (InternalServerErrorException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding picture");
		}	
	}
	
	@DeleteMapping("/{kidId}/pictures/{pictureId}")
	public void deletePicture(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String kidId, @PathVariable String pictureId) {
		try {
			kidController.deletePicture(kidId, pictureId);
		} catch (KidNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kid not found");
		} catch (PictureNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Picture not found");
		}
	}
	
	@GetMapping("/{kidId}")
	public Kid getKid(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String kidId) {
		try {
			return kidController.getKid(kidId);
		} catch (KidNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kid not found");
		}
	}
	
	@GetMapping(value = "/{kidId}/pictures", params = "page")
	public List<Picture> getPaginatedPictures(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String kidId, @RequestParam("page") int pageNumber) {
		try {
			return kidController.getPaginatedPictures(kidId, pageNumber);
		} catch (KidNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kid not found");
		} catch (IndexOutOfBoundsException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Picture page not found");
		}
	}
	
	@GetMapping("/{kidId}/pictures/{pictureId}")
	public Picture getPicture(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String kidId, @PathVariable String pictureId) {
		try {
			return kidController.getPicture(kidId, pictureId);
		} catch (KidNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kid not found");
		} catch (PictureNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Picture not found");
		}
	}
	
	@GetMapping("/{kidId}/pictures")
	public List<Picture> getPictures(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String kidId) {
		try {
			return kidController.getAllPictures(kidId);
		} catch (KidNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kid not found");
		} 
	}
	
	@GetMapping("/{kidId}/pictures/daterange")
	public List<Picture> getPicturesInDateRange(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String kidId, @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) Optional<LocalDate> startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) Optional<LocalDate> endDate) {
		try {
			return kidController.getPicturesInDateRange(kidId, startDate, endDate);
		} catch (KidNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kid not found");
		} 
	}
	
	@PutMapping("/{kidId}")
	public void updateKid(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String kidId, @RequestParam(required = false) Optional<String> name,
			@RequestParam(required = false) Optional<String> gender,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) Optional<LocalDate> dateOfBirth) {
		try {
			kidController.updateKid(kidId, name, gender, dateOfBirth);
		} catch (KidNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kid not found");
		}	
	}
}