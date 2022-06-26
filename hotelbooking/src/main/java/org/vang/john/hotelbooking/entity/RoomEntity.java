package org.vang.john.hotelbooking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "room")
public class RoomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Min(1)
	@Column(nullable = false)
	private Long number;

	@Min(1)
	@Column(nullable = false)
	private Long occupancy;

	@Column(nullable = false)
	private Boolean smoking;

	public RoomEntity() {
	}

	public RoomEntity(Long number, Long occupancy, Boolean smoking) {
		this.number = number;
		this.occupancy = occupancy;
		this.smoking = smoking;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Long getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(Long occupancy) {
		this.occupancy = occupancy;
	}

	public Boolean getSmoking() {
		return smoking;
	}

	public void setSmoking(Boolean smoking) {
		this.smoking = smoking;
	}

	@Override
	public String toString() {
		return "RoomEntity [id=" + id + ", number=" + number + ", occupancy=" + occupancy + ", smoking=" + smoking
				+ "]";
	}

	public Boolean hasSmoking() {
		return smoking;
	}

}
