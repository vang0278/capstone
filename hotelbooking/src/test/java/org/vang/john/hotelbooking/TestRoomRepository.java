package org.vang.john.hotelbooking;

import java.util.List;

import org.vang.john.hotelbooking.entity.RoomEntity;
import org.vang.john.hotelbooking.repository.RoomRepository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// this test should be ran after initializing the db with rooms (initController)
@SpringBootTest
public class TestRoomRepository {

	private List<RoomEntity> rooms;

	@Autowired
	RoomRepository roomRepo;

	@Test
	public void test_findAllByNumber() {
		Long expected = 102L;
		this.rooms = this.roomRepo.findAllByNumber(expected);

		assertNotEquals(this.rooms.size(), 0);
		this.rooms.forEach(r -> {
			Long actual = r.getNumber();
			System.out.printf("Room %d : %s\n", expected, r);
			assertEquals(expected, actual);
		});
	}

	@Test
	public void test_findAllBySmoking() {
		final Boolean expected = true;
		this.rooms = this.roomRepo.findAllBySmoking(expected);

		assertNotEquals(this.rooms.size(), 0);
		this.rooms.forEach(r -> {
			System.out.printf("Smoking %B : %s\n", expected, r);
			assertTrue(r.getSmoking());
			assertTrue(r.hasSmoking());
		});

		final Boolean expectedTwo = false;
		this.rooms = this.roomRepo.findAllBySmoking(expectedTwo);

		assertNotEquals(this.rooms.size(), 0);
		this.rooms.forEach(r -> {
			System.out.printf("Smoking %B : %s\n", expectedTwo, r);
			assertFalse(r.getSmoking());
			assertFalse(r.hasSmoking());
		});

	}

	@Test
	public void test_findAllByOccupancy() {
		final Long expected = 1L;
		this.rooms = this.roomRepo.findAllByOccupancy(expected);

		assertNotEquals(this.rooms.size(), 0);
		this.rooms.forEach(r -> {
			Long actual = r.getOccupancy();
			System.out.printf("Occupancy %d : %s\n", expected, r);
			assertEquals(expected, actual);
		});

		final Long expectedTwo = 2L;
		this.rooms = this.roomRepo.findAllByOccupancy(expectedTwo);

		assertNotEquals(this.rooms.size(), 0);
		this.rooms.forEach(r -> {
			Long actual = r.getOccupancy();
			System.out.printf("Occupancy %d : %s\n", expectedTwo, r);
			assertEquals(expectedTwo, actual);
		});

	}

}
