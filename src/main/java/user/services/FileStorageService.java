package user.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.Resource;
import javax.validation.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.AbstractFileResolvingResource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.connection.Stream;

import user.exception.HandlerException;
import user.exception.MyFileNotFoundException;
import user.property.FileStorageProperties;
import user.repositories.ImageRepositories;

public class FileStorageService {
	//private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
	private final java.nio.file.Path fileStorageLocation;
	@Autowired
	private ImageRepositories imagerepository;
	
	
    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
    	 this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
        	System.out.println("Could not create the directory where the uploaded files will be stored.");
        }
    }

    public String storeFile(MultipartFile file ,String fileid) throws HandlerException {
        // Normalize file name
        //String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    	System.out.println("Testing File Name *****"+fileid);
        try {
            // Check if the file's name contains invalid characters
            if(fileid.contains("..")) {
                throw new HandlerException("Sorry! Filename contains invalid path sequence " + fileid);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            java.nio.file.Path targetLocation = this.fileStorageLocation.resolve(fileid);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            
        } catch (IOException ex) {
        	throw new HandlerException("Could not store file " + fileid + ". Please try again!");
        	
        }
        return fileid;
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            java.nio.file.Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = (Resource) new UrlResource(filePath.toUri());
            if(((AbstractFileResolvingResource) resource).exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
      
    }
    
    public Resource loadFileAsFileId(String fileId) {
        try {
            java.nio.file.Path filePath = this.fileStorageLocation.resolve(fileId).normalize();
            Resource resource = (Resource) new UrlResource(filePath.toUri());
            if(((AbstractFileResolvingResource) resource).exists()) 
            {
                return resource;
            } else 
            {
                throw new MyFileNotFoundException("File not found " + fileId);
            }
        } catch (MalformedURLException ex) 
        {
            throw new MyFileNotFoundException("File not found " + fileId, ex);
        }
      
    }
   
    public Stream loadFiles() {
        try {
            return (Stream) Files.walk(this.fileStorageLocation, 1)
                .filter(path -> !path.equals(this.fileStorageLocation))
                .map(this.fileStorageLocation::relativize);
        }
        catch (IOException e) {
        	throw new RuntimeException("\"Failed to read stored file");
        }
	}
}
