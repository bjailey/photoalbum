package uk.co.project.photoalbum.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import uk.co.project.photoalbum.connectors.interfaces.GeocodeConnector;
import uk.co.project.photoalbum.exceptions.InternalServerErrorException;
import uk.co.project.photoalbum.factories.PictureFactory;
import uk.co.project.photoalbum.models.Picture;
import uk.co.project.photoalbum.services.interfaces.FileService;

@Controller
public class PictureController {

	private FileService fileService;
	private GeocodeConnector geocodeConnector;
	private PictureFactory pictureFactory;
	
	@Autowired
	public PictureController(PictureFactory pictureFactory, FileService fileService,
			GeocodeConnector geocodeConnector) {
		this.pictureFactory = pictureFactory;
		this.fileService = fileService;
		this.geocodeConnector = geocodeConnector;
		
	}
	
	public Picture createPicture(String kidId, MultipartFile file, LocalDateTime dateTime,
			Optional<String> geocode) {
		String fileName = file.getOriginalFilename();
		Picture picture = pictureFactory.createPicture(kidId, fileName, dateTime);
		geocode.ifPresent(value -> picture.setLocation(geocodeConnector.getLocation(value)));
		try {
			fileService.saveFile(file, fileName);
		} catch (IOException e) {
			throw new InternalServerErrorException();
		}
		return picture;
	}
	
	public void deletePictureFile(String fileName) {
		fileService.deleteFile(fileName);
	}
	
	public void filterPicturesByDate(List<Picture> pictures, Optional<LocalDate> startDate,
			Optional<LocalDate> endDate) {
		startDate.ifPresent(
				value -> pictures.removeIf(picture -> picture.getDate().toLocalDate().isBefore(value)));
		endDate.ifPresent(
				value -> pictures.removeIf(picture -> picture.getDate().toLocalDate().isAfter(value)));
	}
}