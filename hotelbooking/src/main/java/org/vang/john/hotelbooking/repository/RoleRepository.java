package org.vang.john.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vang.john.hotelbooking.entity.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>{
	RoleEntity findByName(String role);
}
