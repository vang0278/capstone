package org.vang.john.hotelbooking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vang.john.hotelbooking.entity.BuildingEntity;
import org.vang.john.hotelbooking.entity.ReservationEntity;
import org.vang.john.hotelbooking.entity.RoomEntity;
import org.vang.john.hotelbooking.entity.UserEntity;
import org.vang.john.hotelbooking.repository.ReservationRepository;

// ReservationServiceImpl
// should be used directly with Controller
// so that it's functions can be  expanded in the future
@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationRepository reservationRepo;

	@Autowired
	private RoomService roomService;

	@Autowired
	private UserService userService;

	@Autowired
	private BuildingService buildingService;

	@Override
	public List<ReservationEntity> searchConfirmation(String txt) {
		List<ReservationEntity> reservations = this.reservationRepo.findAll();
		List<ReservationEntity> found = new ArrayList<ReservationEntity>();

		if (reservations == null) {
			return found;
		}

		reservations.forEach(res -> {

//			System.out.println(res.getConfirmation());

			if (res.getConfirmation().contains(txt.toLowerCase())) {
				found.add(res);
				System.out.println("Confirmation found >" + txt + "< " + res.getConfirmation());
			}
		});

		return found;
	}

	@Override
	public boolean isRoomAvailable(Long roomId) {
		RoomEntity room = this.roomService.findRoom(roomId);

		if (room == null) {
			return false;
		}

		boolean isBooked = this.reservationRepo.existsByActiveTrueAndRoom(room);
		return !isBooked;
	}

	@Override
	public boolean makeReservation(Long buildingId, Long roomId, String email) {

		// BuildingService returns optional
		Optional<BuildingEntity> dbBuilding = this.buildingService.findById(buildingId);
		if (dbBuilding.isEmpty()) {
			return false;
		}
		BuildingEntity building = dbBuilding.get();

		RoomEntity room = this.roomService.findRoom(roomId);

		if (room == null) {
			return false;
		}

		if (!this.isRoomAvailable(roomId)) {
			return false;
		}

		UserEntity user = this.userService.findByEmail(email);

		if (user == null) {
			return false;
		}

		ReservationEntity reservation = new ReservationEntity(building, room, user);

		this.reservationRepo.save(reservation);

		return true;
	}

	private boolean actionResevationHelp(UUID id, String email, String oldStatus, String newStatus,
			boolean finalActiveStatus) {

		System.out.println(id + " Reservation action: " + newStatus);

		System.out.println("Exists?: " + this.reservationRepo.existsById(id));

		// fix UUID in entity
//		List<ReservationEntity> all = this.reservationRepo.findAll();
//		all.forEach(r->System.out.println(r));
//		System.out.println("Done listing: " + this.reservationRepo.existsById(all.get(0).getId()));

		Optional<ReservationEntity> dbReservation = this.reservationRepo.findById(id);

		if (dbReservation.isEmpty()) {
			System.out.println("Reservation not found.");
			return false;
		}

		ReservationEntity res = dbReservation.get();

		// verify email
		if (!res.getUser().getEmail().equalsIgnoreCase(email)) {
			System.out.println("Reservation emails don't match.");
			return false;
		}

		if (!res.getActive()) {
			System.out.println("Reservation is not active.");
			return false;
		}

		if (!res.getStatus().equalsIgnoreCase(oldStatus)) {
			System.out.println("Reservation wrong status detected.");
			return false;
		}

		// can only do if ACTIVE and "oldStatus"

		res.setStatus(newStatus);
		res.setActive(finalActiveStatus);

		this.reservationRepo.save(res);

		System.out.println("Reservation action completed.");
		return true;
	}

	@Override
	public boolean cancelReservation(UUID id, String email) {
		return this.actionResevationHelp(id, email, "BOOKED", "CANCELED", false);
	}

	@Override
	public boolean checkInReservation(UUID id, String email) {
		return this.actionResevationHelp(id, email, "BOOKED", "CHECKEDIN", true);
	}

	@Override
	public boolean checkOutReservation(UUID id, String email) {
		return this.actionResevationHelp(id, email, "CHECKEDIN", "CHECKEDOUT", false);
	}

	@Override
	public List<ReservationEntity> getReservations(UserEntity user) {
		return this.reservationRepo.findByUser(user);
	}

}
