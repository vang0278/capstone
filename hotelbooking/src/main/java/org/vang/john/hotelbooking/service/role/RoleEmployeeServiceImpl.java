package org.vang.john.hotelbooking.service.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vang.john.hotelbooking.entity.UserEntity;
import org.vang.john.hotelbooking.repository.UserRepository;
import org.vang.john.hotelbooking.service.UserService;

@Service
public class RoleEmployeeServiceImpl implements RoleEmployeeService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	private UserService userService;

	@Override
	public List<UserEntity> findAll() {
		String role = "USER"; // access to this role

		List<UserEntity> users = new ArrayList<UserEntity>();
		List<UserEntity> dbUsers = this.userRepo.findAll();
		dbUsers.forEach(user -> {
			if (user.getRole().getName().compareToIgnoreCase(role) == 0) {
				users.add(user);
			}
		});

		return users;
	}

	@Override
	public List<UserEntity> search(String name) {
		List<UserEntity> dbUsers = this.findAll();
		List<UserEntity> users = new ArrayList<UserEntity>();

		if (name == null || name == "") {
			return users; // nothing
//			return dbUsers; // everything
		}

		String txt = name.toLowerCase();

		dbUsers.forEach(user -> {
			if (user.getFirstName().toLowerCase().contains(txt) //
					|| (user.getLastName().toLowerCase().contains(txt)) //
					|| (user.getEmail().toLowerCase().contains(txt)) //
			) {
				users.add(user);
				System.out.println("found >" + txt + "< " + user.toString());
//				System.out.println(user.getFirstName().toLowerCase().contains(txt)); //
//				System.out.println(user.getFirstName().toLowerCase()); //
//				System.out.println(user.getLastName().toLowerCase().contains(txt)); //
//				System.out.println(user.getLastName().toLowerCase()); //
//				System.out.println(user.getEmail().toLowerCase().contains(txt)); //
//				System.out.println(user.getEmail().toLowerCase()); //
			}
		});

		return users;
	}

	@Override
	public String resetUserPassword(String email) {
		Optional<UserEntity> dbUser = this.userRepo.findByEmail(email);
		if (dbUser.isEmpty()) {
			return null;
		}

		if (!dbUser.get().getRole().getName().equalsIgnoreCase("USER")) {
			return null;
		}

		return this.userService.resetPassword(email);
	}

}
