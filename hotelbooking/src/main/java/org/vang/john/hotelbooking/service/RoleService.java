package org.vang.john.hotelbooking.service;

import org.vang.john.hotelbooking.entity.RoleEntity;

public interface RoleService {
	RoleEntity getRole(String role, boolean createIfNotExist);
}
