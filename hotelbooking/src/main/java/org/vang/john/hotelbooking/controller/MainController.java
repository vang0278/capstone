package org.vang.john.hotelbooking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.vang.john.hotelbooking.dto.SearchBuildingDTO;
import org.vang.john.hotelbooking.entity.BuildingEntity;
import org.vang.john.hotelbooking.entity.RoomEntity;
import org.vang.john.hotelbooking.exception.UnknownUserRoleException;
import org.vang.john.hotelbooking.service.BuildingService;
import org.vang.john.hotelbooking.service.ReservationService;

@Controller
public class MainController {

	@Autowired
	private BuildingService buildingService;

	@Autowired
	private ReservationService reservationService;

	@ModelAttribute("search")
	public SearchBuildingDTO searchBuildingDTO() {
		return new SearchBuildingDTO();
	}

	@GetMapping("/")
	public String index(Model model) {
		List<BuildingEntity> buildings = this.buildingService.findAllBuildings(); //
		model.addAttribute("buildings", buildings);
		model.addAttribute("ignore_button", true);
		return "index";
	}

	@PostMapping("/")
	public String checkAvailability(Model model, //
			@ModelAttribute("search") SearchBuildingDTO search) {
		boolean _smoking = search.isYesSmoking();
		long _occupancy = search.isDoubleOccupancy() ? 2 : 1;
		boolean _anySmoking = search.isAnySmoking();
		boolean _anyOccupancy = search.isAnyOccupancy();

		List<BuildingEntity> buildings = this.buildingService.findAllBuildingsWith(_smoking, _occupancy, //
				_anySmoking, _anyOccupancy); //

		model.addAttribute("buildings", buildings);

		return "index";
	}

	@GetMapping("/show")
	public String showAvailability(Authentication auth, //
			Model model, //
			@ModelAttribute("search") SearchBuildingDTO search, //
			@ModelAttribute("id") Long id) {
		// try to insert signed in user, into model
		try {
			String role = auth.getAuthorities().iterator().next().toString(); // get the first/only role
			if (role.equalsIgnoreCase("role_USER")) {
				System.out.println("ADDING to model. " + role + " detected for: /show");
				model.addAttribute("ROLE_USER", role);
			} else {
				System.out.println(role + " detected for: /show");
			}
		} catch (Exception e) {
			System.out.println("no user detected for: /show");
		}

		boolean _smoking = search.isYesSmoking();
		long _occupancy = search.isDoubleOccupancy() ? 2 : 1;
		boolean _anySmoking = search.isAnySmoking();
		boolean _anyOccupancy = search.isAnyOccupancy();

		// re-list buildings
		List<BuildingEntity> buildings = this.buildingService.findAllBuildingsWith(_smoking, _occupancy, //
				_anySmoking, _anyOccupancy); //

		model.addAttribute("buildings", buildings);

		Optional<BuildingEntity> dbBuilding = this.buildingService.findById(id);

		if (dbBuilding.isEmpty()) {
			return "index"; //
		}

		// list rooms for the building
		List<RoomEntity> rooms = this.buildingService.findAllRoomsWith(_smoking, _occupancy, //
				_anySmoking, _anyOccupancy, //
				id); //

		model.addAttribute("viewBuilding", dbBuilding.get());
		model.addAttribute("rooms", rooms);

		model.addAttribute("reservation", this.reservationService);

		return "index";
	}

	@GetMapping("/userpanel")
	public String determineUserPanel(Authentication auth) throws UnknownUserRoleException {
		String role = "testing";

		// user should already be signed in
		role = auth.getAuthorities().iterator().next().toString(); // get the first/only role

		System.out.printf("user panel == >%s<\n", role);

		if (role.equalsIgnoreCase("role_ADMIN")) {
			return "redirect:/admin";
		}

		if (role.equalsIgnoreCase("role_EMPLOYEE")) {
			return "redirect:/employee";
		}

		if (role.equalsIgnoreCase("role_USER")) {
			return "redirect:/user";
		}

		throw new UnknownUserRoleException("Unknown UserRole: " + role);
	}

}
