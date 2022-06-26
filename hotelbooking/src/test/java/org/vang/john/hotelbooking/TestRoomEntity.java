package org.vang.john.hotelbooking;

import org.vang.john.hotelbooking.entity.RoomEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TestRoomEntity {

	private RoomEntity room;

	@BeforeEach
	public void createNewRoom() {
		this.room = new RoomEntity();
	}

	@Test
	public void testRoomId() {
		Long expected = 123L;
		this.room.setId(expected);

		Long actual = this.room.getId();

		assertEquals(expected, actual);
	}

	@Test
	public void testRoomNumber() {
		Long expected = 101L;
		this.room.setNumber(expected);

		Long actual = this.room.getNumber();

		assertEquals(expected, actual);
	}

	@Test
	public void testRoomOccupancy() {
		Long expected = 2l;
		this.room.setOccupancy(expected);

		Long actual = this.room.getOccupancy();

		assertEquals(expected, actual);
	}

	@Test
	public void testRoomSmoking() {
		Boolean expected = true;
		this.room.setSmoking(expected);

		Boolean actual = this.room.getSmoking();

		assertEquals(expected, actual);

		actual = this.room.hasSmoking();

		assertEquals(expected, actual);
	}

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	public void testRoomSetSmoking(Boolean expected) {
//		Boolean expected = true;
		this.room.setSmoking(expected);

		Boolean actual = this.room.getSmoking();

		assertEquals(expected, actual);

		actual = this.room.hasSmoking();

		assertEquals(expected, actual);
	}

}
