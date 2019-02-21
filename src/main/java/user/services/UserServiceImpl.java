package user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.document.User;
import user.repositories.UserRepositories;



@Service
public class UserServiceImpl implements UserServices {
	
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

	
	

}
