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
public class RoleAdminServiceImpl implements RoleAdminService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	private UserService userService;

	@Override
	public List<UserEntity> findAllEmployees() {
		String role = "EMPLOYEE"; // access to this role

		List<UserEntity> users = new ArrayList<UserEntity>();
		List<UserEntity> dbUsers = this.userRepo.findAll();
		dbUsers.forEach(user -> {
			if (user.getRole().getName().compareToIgnoreCase(role) == 0) {
				users.add(user);
			}
		});

		return users;
	}

//	@Override
//	public boolean updateEmployee(Long id, UserEntity employee) {
//		Optional<UserEntity> dbEmployee = this.userRepo.findById(id);
//		if (dbEmployee.isEmpty()) {
//			return false;
//		}
//
//		if (!dbEmployee.get().getRole().getName().equalsIgnoreCase("EMPLOYEE")) {
//			return false;
//		}
//
//		UserEntity emp = dbEmployee.get();
//
//		// update the info
//		// DONOT change email or role
//		// password reset is done
//		emp.setFirstName(employee.getFirstName());
//		emp.setLastName(employee.getLastName());
//
//		this.userRepo.save(emp);
//
//		return true;
//	}

	@Override
	public boolean removeEmployee(Long id) {
		Optional<UserEntity> dbEmployee = this.userRepo.findById(id);
		if (dbEmployee.isEmpty()) {
			return false;
		}

		if (!dbEmployee.get().getRole().getName().equalsIgnoreCase("EMPLOYEE")) {
			return false;
		}

		this.userRepo.delete(dbEmployee.get());

		return true;
	}

	@Override
	public String resetEmployeePassword(Long id) {
		Optional<UserEntity> dbEmployee = this.userRepo.findById(id);
		if (dbEmployee.isEmpty()) {
			return null;
		}

		if (!dbEmployee.get().getRole().getName().equalsIgnoreCase("EMPLOYEE")) {
			return null;
		}

		return this.userService.resetPassword(dbEmployee.get().getEmail());
	}

	@Override
	public boolean isEmailAvailble(String email) {
		return !this.userService.existsByEmail(email); // use UserService
	}

	@Override
	public void createEmployee(UserEntity employee) {
		this.userService.saveAsEmployee(employee); // use UserService
	}

}
