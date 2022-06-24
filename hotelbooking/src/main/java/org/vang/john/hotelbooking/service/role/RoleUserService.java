package org.vang.john.hotelbooking.service.role;

import org.vang.john.hotelbooking.entity.UserEntity;

public interface RoleUserService {

	UserEntity getUser(String userEmail);
	
	boolean updateName(String userEmail, String lastName, String firstName);
	
	boolean changePassword(String userEmail, String password, String confirmPassword);
	
	//get reservations

}
