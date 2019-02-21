package user.services;

import java.util.List;
import java.util.UUID;

import user.document.User;



public interface UserServices {


	User create(User user);
	User findByUserEmail(String userEmail);



}
