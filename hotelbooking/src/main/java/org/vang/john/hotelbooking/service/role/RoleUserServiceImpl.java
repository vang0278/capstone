package org.vang.john.hotelbooking.service.role;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vang.john.hotelbooking.entity.UserEntity;
import org.vang.john.hotelbooking.repository.UserRepository;
import org.vang.john.hotelbooking.service.UserService;

@Service
public class RoleUserServiceImpl implements RoleUserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Override
	public UserEntity getUser(String userEmail) {
		Optional<UserEntity> dbUser = this.userRepository.findByEmail(userEmail);
		if (dbUser.isEmpty()) {
			return null;
		}
		return dbUser.get();
	}

	@Override
	public boolean updateName(String userEmail, String lastName, String firstName) {
		UserEntity user = this.getUser(userEmail);
		if (user == null) {
			return false;
		}
		user.setLastName(lastName);
		user.setFirstName(firstName);

		this.userRepository.save(user);

		return true;
	}

	@Override
	public boolean changePassword(String userEmail, String password, String confirmPassword) {
		if (!password.equals(confirmPassword)) {
			return false;
		}

		return this.userService.changePassword(userEmail, confirmPassword);
	}

}
