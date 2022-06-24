package org.vang.john.hotelbooking.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vang.john.hotelbooking.entity.BuildingEntity;
import org.vang.john.hotelbooking.entity.RoomEntity;
import org.vang.john.hotelbooking.entity.UserEntity;
import org.vang.john.hotelbooking.service.BuildingService;
import org.vang.john.hotelbooking.service.UserService;

@Controller
@RequestMapping("/init")
public class InitController {

	@Autowired
	private UserService userService;

	@Autowired
	private BuildingService buildingService;

	@RequestMapping
	public String createDefaults() {
		this.createDefaultAdmin();
		this.createDefaultEmployee();
		this.createDefaultUser();

		this.createBuildings();
		this.createTestBuildings();

		return "redirect:/";
	}

//	@RequestMapping("/admin")
	public void createDefaultAdmin() {
		String firstName = "admin";
		String lastName = "default";
		String email = "admin@default.com";
		String password = "admin";

		UserEntity user = new UserEntity();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);

		if (this.userService.notExistsByEmail(email)) {
			this.userService.saveAsAdmin(user);
		}
	}

//	@RequestMapping("/employee")
	public void createDefaultEmployee() {
		String firstName = "employee";
		String lastName = "default";
		String email = "employee@default.com";
		String password = "employee";

		UserEntity user = new UserEntity();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);

		if (this.userService.notExistsByEmail(email)) {
			this.userService.saveAsEmployee(user);
		}
	}

//	@RequestMapping("/user")
	public void createDefaultUser() {
		String firstName = "user";
		String lastName = "default";
		String email = "user@default.com";
		String password = "user";

		UserEntity user = new UserEntity();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);

		if (this.userService.notExistsByEmail(email)) {
			this.userService.saveAsUser(user);
		}
	}

//	@RequestMapping("/building")
	public void createBuildings() {
//		Radisson RED
//		609 S 3rd Street, Minneapolis, Minnesota 55415
//		(612) 255-0554 
//		rhi_mpmn@radissonredamericas.com
//24 rooms		
		createBuildingHelper("Radisson RED", "609 S 3rd Street, Minneapolis, Minnesota 55415", 24);

//		Canopy by Hilton
//		708 S 3rd Street, Minneapolis, Minnesota, 55415
//		+1 612-332-0696
//16
		createBuildingHelper("Canopy by Hilton", "708 S 3rd Street, Minneapolis, Minnesota, 55415", 16);

//		Moxy Minneapolis Downtown (marriott)
//		247 Chicago Ave, Minneapolis, MN 55415
//		+16124001810
//12	
		createBuildingHelper("Moxy Minneapolis Downtown", "247 Chicago Ave, Minneapolis, MN 55415", 12);

//		Aloft Minneapolis
//		900 S Washington Ave, Minneapolis, MN 55415
//		(612) 455-8400
//20
		createBuildingHelper("Aloft", "900 S Washington Ave, Minneapolis, MN 55415", 20);

//		Renaissance Minneapolis Hotel, The Depot
//		225 3rd Ave S, Minneapolis, MN 55401
//		(612) 375-1700
//16
		createBuildingHelper("Renaissance", "225 3rd Ave S, Minneapolis, MN 55401", 16);

//		Residence Inn by Marriott Minneapolis Downtown at The Depot
//		425 S 2nd St, Minneapolis, MN 55401
//		(612) 340-1300
//20
		createBuildingHelper("Residence Inn", "425 S 2nd St, Minneapolis, MN 55401", 20);

	}

	private void createBuildingHelper(String name, String address, int numRooms) {
		if (this.buildingService.findByName(name).isPresent()) {
			return;
		}

		BuildingEntity building = new BuildingEntity();
		building.setName(name);
		building.setAddress(address);
		for (int i = 1; i <= numRooms; i++) {
			RoomEntity room = new RoomEntity();
			room.setNumber(100L + i);
			boolean isSmoking = Math.random() > .5;
			Long occupancy = (Math.random() > .5) ? 1L : 2L;

			room.setSmoking(isSmoking);
			room.setOccupancy(occupancy);

			this.buildingService.addRoom(building, room);
		}

	}

	public void createTestBuildings() {
		createTestBuildingsHelper("Smoking 1", "Testing", true, 1L);
		createTestBuildingsHelper("Smoking 2", "Testing", true, 2L);
		createTestBuildingsHelper("Non-Smoking 1", "Testing", false, 1L);
		createTestBuildingsHelper("Non-Smoking 2", "Testing", false, 2L);
	}

	private void createTestBuildingsHelper(String name, String address, boolean smoking, Long occupancy) {
		if (this.buildingService.findByName(name).isPresent()) {
			return;
		}

		BuildingEntity building = new BuildingEntity();
		building.setName(name);
		building.setAddress(address);

		RoomEntity room = new RoomEntity();
		room.setNumber(100L);

		room.setSmoking(smoking);
		room.setOccupancy(occupancy);

		this.buildingService.addRoom(building, room);

	}

}
