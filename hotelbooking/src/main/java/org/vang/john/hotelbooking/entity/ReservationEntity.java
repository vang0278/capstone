package org.vang.john.hotelbooking.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "reservation")
public class ReservationEntity {

	@Id
	@GeneratedValue(generator = "uuid4")
	@GenericGenerator(name = "UUID", strategy = "uuid4")
	@Type(type = "org.hibernate.type.UUIDCharType")
	@Column(columnDefinition = "CHAR(36)")
	private UUID id;

	@ManyToOne
	private BuildingEntity building;

	@ManyToOne
	private RoomEntity room;

	@ManyToOne
	private UserEntity user;

	private Boolean active = true; // set to true when created

	private String status = "BOOKED"; // BOOKED, CANCELED, CHECKEDIN, CHECKEDOUT

	public ReservationEntity() {
	}

	public ReservationEntity(RoomEntity room, UserEntity user) {
		this.room = room;
		this.user = user;
	}

	public ReservationEntity(BuildingEntity building, RoomEntity room, UserEntity user) {
		this.building = building;
		this.room = room;
		this.user = user;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public BuildingEntity getBuilding() {
		return building;
	}

	public void setBuilding(BuildingEntity building) {
		this.building = building;
	}

	public RoomEntity getRoom() {
		return room;
	}

	public void setRoom(RoomEntity room) {
		this.room = room;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ReservationEntity [id=" + id + ", room=" + room.getNumber() + ", user=" + user.getEmail() + ", active="
				+ active + "]";
	}

	public String getConfirmation() {
		// first part of UUID
		String confirmation = id.toString().split("-")[0];
		return confirmation;
	}

}
