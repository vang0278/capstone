package org.vang.john.hotelbooking.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vang.john.hotelbooking.entity.ReservationEntity;
import org.vang.john.hotelbooking.entity.RoomEntity;
import org.vang.john.hotelbooking.entity.UserEntity;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {

	List<ReservationEntity> findByIdContains(String txt);

//	boolean existsByActiveAndRoom(Boolean isActive, RoomEntity room);

	boolean existsByActiveTrueAndRoom(RoomEntity room);

	List<ReservationEntity> findByRoom(RoomEntity room); // list all known

	List<ReservationEntity> findByUser(UserEntity user);

}
