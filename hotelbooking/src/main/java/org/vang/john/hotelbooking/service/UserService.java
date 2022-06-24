package org.vang.john.hotelbooking.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.vang.john.hotelbooking.entity.UserEntity;

public interface UserService extends UserDetailsService {

	boolean notExistsByEmail(String email);
	boolean existsByEmail(String email);

	UserEntity findByEmail(String email);

	String resetPassword(String email);

	UserEntity saveAsAdmin(UserEntity user);

	UserEntity saveAsEmployee(UserEntity user);

	UserEntity saveAsUser(UserEntity user);

}
