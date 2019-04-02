package uk.co.nbrown.photoalbum.services.interfaces;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	void deleteFile(String fileName);
	void saveFile(MultipartFile file, String fileName) throws IOException;

}