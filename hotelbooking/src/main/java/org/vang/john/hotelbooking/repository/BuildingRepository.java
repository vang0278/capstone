package org.vang.john.hotelbooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vang.john.hotelbooking.entity.BuildingEntity;

@Repository
public interface BuildingRepository extends JpaRepository<BuildingEntity, Long> {

	Optional<BuildingEntity> findByName(String name);

}
