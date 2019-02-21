package user.services;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;







@Service("productService")
public class DeployProductServiceImpl implements DeployProductService {
	
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get("C:\\images");	

	
//	@Autowired
//	private DeployProductRepository productRepo;

//	@Override
//	public DeployProducts insertProduct(DeployProducts product) {
//		return productRepo.insert(product);
//	}

	
	@Override
	public void store(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}		
	}

	@Override
	public Resource loadFile(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	
	@Override
	public void init() {
		File directory=new File(rootLocation.toString());
		if(directory.exists()== false) {
			try {
				Files.createDirectory(rootLocation);
			}
			catch (IOException e) {
				throw new RuntimeException("Could not initialize storage!");
			}
			}
			else {
				System.out.println("Folder Already Exists!");
			}		
	}

	
	
}
