package user.controller;




import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import user.models.ImageModel;
import user.services.FileStorageService;
import user.services.ImageService;





@RestController
@RequestMapping("productapi")
@CrossOrigin(origins="http://localhost:4200")

public class DisplayController {

//	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
 // getting all the products from the database.     
	@Autowired
	private ImageService imgservice;
	@Autowired
	private FileStorageService fileStorageService;
	
	
	@GetMapping("/downloadFile")
	public ResponseEntity<Resource> downloadFile(@RequestParam String fileName, HttpServletRequest request) {
		// Load file as Resource
		Resource resource = fileStorageService.loadFileAsResource(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
		System.out.println("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}
	

	@GetMapping("/getData")
	public List<ImageModel> getData(){
		return imgservice.getAll();
	}

   
}


