package org.vang.john.hotelbooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vang.john.hotelbooking.entity.RoomEntity;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

	Optional<RoomEntity> findByNumber(Long number);

	List<RoomEntity> findAllBySmoking(Boolean smoking);

	List<RoomEntity> findAllByOccupancy(Long occupancy);

}
