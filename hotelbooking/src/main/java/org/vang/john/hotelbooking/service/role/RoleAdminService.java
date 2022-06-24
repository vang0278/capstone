package org.vang.john.hotelbooking.service.role;

import java.util.List;

import org.vang.john.hotelbooking.entity.UserEntity;

public interface RoleAdminService {

	List<UserEntity> findAllEmployees();

//	boolean updateEmployee(Long id, UserEntity employee);

	boolean removeEmployee(Long id);
	
	String resetEmployeePassword(Long id);
	
	boolean isEmailAvailble(String email);
	
	void createEmployee(UserEntity employee);
}
