package org.vang.john.hotelbooking.service;

import java.util.List;
import java.util.Optional;

import org.vang.john.hotelbooking.entity.BuildingEntity;
import org.vang.john.hotelbooking.entity.RoomEntity;

public interface BuildingService {

	List<BuildingEntity> findAllBuildings();

	List<BuildingEntity> findAllBuildingsWith(boolean smoking, Long occupancy, //
			boolean ignoreSmoking, boolean ignoreOccupancy); //

	List<RoomEntity> findAllRoomsWith(boolean smoking, Long occupancy, //
			boolean ignoreSmoking, boolean ignoreOccupancy, //
			Long buildingId); //

	Optional<BuildingEntity> findById(Long id);

	Optional<BuildingEntity> findByName(String name);

	void save(BuildingEntity building);

	RoomEntity getRoom(BuildingEntity building, Long roomNumber);

	boolean addRoom(BuildingEntity building, RoomEntity room);

	boolean updateRoom(BuildingEntity building, Long roomNumber, RoomEntity room);

	void deleteRoom(BuildingEntity building, Long roomNumber);

}
