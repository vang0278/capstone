package org.vang.john.hotelbooking.service;

import org.vang.john.hotelbooking.entity.RoomEntity;

public interface RoomService {

	RoomEntity add(RoomEntity room);

	void delete(RoomEntity room);

	RoomEntity save(RoomEntity room);

	RoomEntity update(RoomEntity room);
	
	RoomEntity findRoom(Long id);

}
