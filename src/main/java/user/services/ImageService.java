package user.services;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import user.document.User;
import user.models.ImageModel;
import user.repositories.ImageRepositories;
import user.repositories.UserRepositories;
@Service("userService")

public class ImageService implements UserServices{
	
	
	MongoTemplate mongoTemplate;


     @Autowired
	private ImageRepositories imageRepositories;
	@Autowired
	private UserRepositories userrepositories;
	@Override
	public User create(User user) {
		
		return userrepositories.insert(user);
	}

     @Override
	public User findByUserEmail(String userEmail) {
		return userrepositories.findByUserEmail(userEmail);
	}
	
	
	public List<ImageModel> getAll() {
	
		return imageRepositories.findAll();
		
		}
	
	
	
	}

