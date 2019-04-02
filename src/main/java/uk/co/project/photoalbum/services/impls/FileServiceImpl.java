package uk.co.project.photoalbum.services.impls;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import uk.co.project.photoalbum.services.interfaces.FileService;

@Service
public class FileServiceImpl implements FileService {

	private final static String IMAGE_DIRECTORY = "C:/Users/jakeb/Desktop/Pictures/";
	
	@Override
	public void deleteFile(String fileName) {
		File file = new File(IMAGE_DIRECTORY, fileName);
		file.delete();
	}

	@Override
	public void saveFile(MultipartFile file, String fileName) throws IOException {
		File destinationFile = new File(IMAGE_DIRECTORY, fileName);
		file.transferTo(destinationFile);
	}
}