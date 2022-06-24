package org.vang.john.hotelbooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vang.john.hotelbooking.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	boolean existsByEmail(String email);

	Optional<UserEntity> findByEmail(String email);

//	Optional<UserEntity> findByEmailAndPassword(String email, String password);
}
