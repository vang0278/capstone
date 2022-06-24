package org.vang.john.hotelbooking.service.role;

import java.util.List;

import org.vang.john.hotelbooking.entity.UserEntity;

public interface RoleEmployeeService {
	
	List<UserEntity> findAll();
	List<UserEntity> search(String name);
	String resetUserPassword(String email);
	
//	UserEntity getConfirmation(String confirmation);
	

}
