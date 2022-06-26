package org.vang.john.hotelbooking.service;

import java.util.List;
import java.util.UUID;

import org.vang.john.hotelbooking.entity.ReservationEntity;
import org.vang.john.hotelbooking.entity.UserEntity;

public interface ReservationService {

	List<ReservationEntity> searchConfirmation(String txt);

	boolean isRoomAvailable(Long roomId);

	boolean makeReservation(Long buildingId, Long roomId, String email);

	boolean cancelReservation(UUID id, String email);
	boolean checkInReservation(UUID id, String email);
	boolean checkOutReservation(UUID id, String email);

	List<ReservationEntity> getReservations(UserEntity user);
	
}
