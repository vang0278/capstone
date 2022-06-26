package org.vang.john.hotelbooking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vang.john.hotelbooking.entity.BuildingEntity;
import org.vang.john.hotelbooking.entity.RoomEntity;
import org.vang.john.hotelbooking.repository.BuildingRepository;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private BuildingRepository buildingRepo;

	@Autowired
	private RoomService roomService;

	@Override
	public List<BuildingEntity> findAllBuildings() {
		return this.buildingRepo.findAll();
	}

	@Override
	public List<BuildingEntity> findAllBuildingsWith(boolean smoking, Long occupancy, //
			boolean ignoreSmoking, boolean ignoreOccupancy) {
		List<BuildingEntity> buildings = this.buildingRepo.findAll();
		List<BuildingEntity> buildingsWith = new ArrayList<BuildingEntity>();

		buildings.forEach(building -> {
			for (RoomEntity room : building.getRooms()) {
				if ((ignoreSmoking || smoking == room.getSmoking())
						&& (ignoreOccupancy || occupancy == room.getOccupancy())) {
					buildingsWith.add(building);
					break;
				}

			}
		});

		return buildingsWith;
	}

	@Override
	public List<RoomEntity> findAllRoomsWith(boolean smoking, Long occupancy, //
			boolean ignoreSmoking, boolean ignoreOccupancy, //
			Long buildingId) {

		List<RoomEntity> rooms = new ArrayList<RoomEntity>();

		Optional<BuildingEntity> dbBuilding = this.buildingRepo.findById(buildingId);
		if (dbBuilding.isEmpty()) {
			return rooms;
		}

		for (RoomEntity room : dbBuilding.get().getRooms()) {
			if ((ignoreSmoking || smoking == room.getSmoking()) //
					&& (ignoreOccupancy || occupancy == room.getOccupancy())) {
				rooms.add(room);
			}
		}

		return rooms;
	}

	@Override
	public Optional<BuildingEntity> findById(Long id) {
		return this.buildingRepo.findById(id);
	}

	@Override
	public Optional<BuildingEntity> findByName(String name) {
		return this.buildingRepo.findByName(name);
	}

	public void save(BuildingEntity building) {
		this.buildingRepo.save(building);
	}

	@Override
	public RoomEntity getRoom(BuildingEntity building, Long roomNumber) {
		RoomEntity found = null;
		for (RoomEntity room : building.getRooms()) {
			if (room.getNumber() == roomNumber) {
				found = room;
				break;
			}
		}
		return found;
	}

	@Override
	public boolean addRoom(BuildingEntity building, RoomEntity room) {

		// check is room exists
		RoomEntity exist = this.getRoom(building, room.getNumber());
		if (exist != null) {
			return false;
		}

		boolean added = building.getRooms().add(room);

		if (!added) {
			return false;
		}

		// save
		this.roomService.add(room);
		this.buildingRepo.save(building);

		return true;
	}

	@Override
	public boolean updateRoom(BuildingEntity building, Long roomNumber, RoomEntity room) {

		// check is room exists
		RoomEntity exist = this.getRoom(building, roomNumber);
		if (exist == null) {
			return false;
		}

		// update
		exist.setOccupancy(room.getOccupancy());
		exist.setSmoking(room.getSmoking());

		// save
		this.roomService.update(exist);
		this.buildingRepo.save(building);

		return true;
	}

	@Override
	public void deleteRoom(BuildingEntity building, Long roomNumber) {

		// check is room exists
		RoomEntity exist = this.getRoom(building, roomNumber);
		if (exist == null) {
			return;
		}

		building.getRooms().remove(exist);
		this.roomService.delete(exist);
		this.buildingRepo.save(building);

	}

}
