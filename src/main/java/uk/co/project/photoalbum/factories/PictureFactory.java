package uk.co.project.photoalbum.factories;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import uk.co.project.photoalbum.models.Picture;

@Component
public class PictureFactory {

	public Picture createPicture(String kidId, String fileName, LocalDateTime dateTime) {
		Picture picture = new Picture();
		picture.setKidId(kidId);
		picture.setFileName(fileName);
		picture.setDate(dateTime);
		return picture;
	}	
}