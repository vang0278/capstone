package org.vang.john.hotelbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vang.john.hotelbooking.entity.RoleEntity;
import org.vang.john.hotelbooking.repository.RoleRepository;

enum UserRole {
	ADMIN {
		public String toString() {
			return "ADMIN";
		}
	},
	EMPLOYEE {
		public String toString() {
			return "EMPLOYEE";
		}
	},
	USER {
		public String toString() {
			return "USER";
		}
	};
}

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepo;

	@Override
	public RoleEntity getRole(String role, boolean createIfNotExist) {
		RoleEntity dbRole = this.roleRepo.findByName(role);
		if (dbRole != null || !createIfNotExist) {
			return dbRole;
		}

		dbRole = new RoleEntity(role); // create new
		this.roleRepo.save(dbRole);
		dbRole = this.roleRepo.findByName(role);
		return dbRole;

	}

}
