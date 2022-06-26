package org.vang.john.hotelbooking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vang.john.hotelbooking.entity.RoomEntity;
import org.vang.john.hotelbooking.repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepo;

	@Override
	public RoomEntity add(RoomEntity room) {
		return this.roomRepo.save(room);
	}

	@Override
	public void delete(RoomEntity room) {
		this.roomRepo.delete(room);
	}

	@Override
	public RoomEntity save(RoomEntity room) {
		return this.roomRepo.save(room);
	}

	@Override
	public RoomEntity update(RoomEntity room) {
		return this.roomRepo.save(room);
	}

	@Override
	public RoomEntity findRoom(Long id) {
		Optional<RoomEntity> dbRoom = this.roomRepo.findById(id);
		if (dbRoom.isEmpty()) {
			return null;
		}

		return dbRoom.get();
	}
}
