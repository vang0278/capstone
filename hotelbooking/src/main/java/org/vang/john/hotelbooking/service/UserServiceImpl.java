package org.vang.john.hotelbooking.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.vang.john.hotelbooking.entity.RoleEntity;
import org.vang.john.hotelbooking.entity.UserEntity;
import org.vang.john.hotelbooking.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleService roleService; // get the Roles

	@Lazy
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public boolean notExistsByEmail(String email) {
		return !this.userRepo.existsByEmail(email);
	}

	@Override
	public boolean existsByEmail(String email) {
		return this.userRepo.existsByEmail(email);
	}

	@Override
	public UserEntity findByEmail(String email) {
		UserEntity user = null;
		Optional<UserEntity> dbUser = this.userRepo.findByEmail(email);
		if (dbUser.isPresent()) {
			user = dbUser.get();
		}
		return user;
	}

	@Override
	public String resetPassword(String email) {
		// reset to a random word from list
		String[] wordList = { "Blue", "Yellow", "Purple", //
				"Pink", "Orange", "Brown", //
				"Black", "White", "Gold", //
				"Silver" };
		
		int index = (int)(Math.random() * wordList.length);
		String word = wordList[index];
		
		Optional<UserEntity> dbUser = this.userRepo.findByEmail(email);
		
		if(dbUser.isEmpty()) {
			return null;
		}

		UserEntity user = dbUser.get();

		user.setPassword(this.passwordEncoder.encode(word));
		this.userRepo.save(user);
		
		return word;
	}

	@Override
	public UserEntity saveAsAdmin(UserEntity user) {
		return this.saveHelper(user, UserRole.ADMIN.toString());
	}

	@Override
	public UserEntity saveAsEmployee(UserEntity user) {
		return this.saveHelper(user, UserRole.EMPLOYEE.toString());
	}

	@Override
	public UserEntity saveAsUser(UserEntity user) {
		return this.saveHelper(user, UserRole.USER.toString());
	}

	// save by copying over data
	private UserEntity saveHelper(UserEntity user, String role) {
		this.roleService.getRole(UserRole.ADMIN.toString(), true); // get/create role from db
		this.roleService.getRole(UserRole.EMPLOYEE.toString(), true); // get/create role from db
		this.roleService.getRole(UserRole.USER.toString(), true); // get/create role from db

		RoleEntity userRole = this.roleService.getRole(role, true); // get/create role from db
		UserEntity newUser = new UserEntity();
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(this.passwordEncoder.encode(user.getPassword()));
		newUser.setRole(userRole);
		return this.userRepo.save(newUser);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		System.out.println("<<Load User By Username>>");

		Optional<UserEntity> dbUser = this.userRepo.findByEmail(email);

		if (dbUser.isEmpty()) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		UserEntity user = dbUser.get();

		GrantedAuthority auth = new SimpleGrantedAuthority("ROLE_" + user.getRole().getName());
		User springUser = new User(user.getEmail(), user.getPassword(), Arrays.asList(auth));
		return springUser;
	}

}
