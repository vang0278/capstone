package org.vang.john.hotelbooking.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "building")
public class BuildingEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty
	@Column(nullable = false)
	private String name;

	@NotEmpty
	@Column(nullable = false)
	private String address;

	@OneToMany(targetEntity = RoomEntity.class, fetch = FetchType.EAGER)
	List<RoomEntity> rooms = new ArrayList<RoomEntity>();

	public BuildingEntity() {
	}

	public BuildingEntity(String name, String address) {
		this.name = name;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<RoomEntity> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomEntity> rooms) {
		this.rooms = rooms;
	}

	@Override
	public String toString() {
		String rooms = this.rooms.stream().map(room -> room.getNumber()).toString();

		return "BuildingEntity [id=" + id + ", name=" + name + ", address=" + address + ", rooms=" + rooms + "]";
	}

}
