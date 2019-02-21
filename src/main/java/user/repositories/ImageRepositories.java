package user.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import user.models.ImageModel;


// for getting all the images from the database

@Repository("imageRepositories")
public interface ImageRepositories extends MongoRepository<ImageModel, UUID> {
	
	//default methods 

}
