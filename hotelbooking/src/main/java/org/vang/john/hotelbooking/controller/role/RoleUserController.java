package org.vang.john.hotelbooking.controller.role;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.vang.john.hotelbooking.dto.UpdateUserDTO;
import org.vang.john.hotelbooking.entity.ReservationEntity;
import org.vang.john.hotelbooking.entity.UserEntity;
import org.vang.john.hotelbooking.service.ReservationService;
import org.vang.john.hotelbooking.service.role.RoleUserService;

@Controller
@RequestMapping("/user")
public class RoleUserController {

	@Autowired
	RoleUserService roleUserService;

	@Autowired
	private ReservationService reservationService;

	@ModelAttribute("user")
	public UpdateUserDTO updateUserDTO() {
		return new UpdateUserDTO();
	}

	@GetMapping
	public String pageUser(Authentication auth, //
			Model model) {

		String loginName = auth.getName();

		UserEntity user = this.roleUserService.getUser(loginName);
		model.addAttribute("user", user);

		List<ReservationEntity> reservations = this.reservationService.getReservations(user);
		model.addAttribute("reservations", reservations);

		return "user";
	}

	@PostMapping("/updateName")
	public RedirectView updateName(Authentication auth, //
			RedirectAttributes redirectAttributes, //
			@ModelAttribute("user") UpdateUserDTO user, //
			@RequestParam("email") String loginName) {
		String redirectPath = "/user?success";

		boolean success = true;
		String detectedLogin = auth.getName();
		System.out.println("trying to update: " + detectedLogin);

		if (detectedLogin.equalsIgnoreCase(loginName)) {
			success = this.roleUserService.updateName(loginName, user.getLastName(), user.getFirstName());
		} else {
			success = false; // only allowed to update self
		}

		if (!success) {
			redirectPath = "/user?error";
			String msg = "Unable to update user name for: " + loginName;
			redirectAttributes.addFlashAttribute("error_msg", msg);
		} else {
			String msg = "User name updated: " + loginName;
			redirectAttributes.addFlashAttribute("success_msg", msg);
		}

		RedirectView redirectView = new RedirectView(redirectPath, true);

		return redirectView;
	}

	@PostMapping("/updatePassword")
	public RedirectView updateName(Authentication auth, //
			RedirectAttributes redirectAttributes, //
			@RequestParam String password, //
			@RequestParam String confirmPassword, //
			@RequestParam("email") String loginName) {
		String redirectPath = "/user?success";

		boolean success = true;
		String detectedLogin = auth.getName();
		System.out.println("trying to update: " + detectedLogin);

		if (detectedLogin.equalsIgnoreCase(loginName)) {
			success = this.roleUserService.changePassword(loginName, password, confirmPassword);
		} else {
			success = false; // only allowed to update self
		}

		if (!success) {
			redirectPath = "/user?error";
			String msg = "Unable to change password user name for: " + loginName;
			redirectAttributes.addFlashAttribute("error_msg", msg);
		} else {
			String msg = "Password updated: " + loginName;
			redirectAttributes.addFlashAttribute("success_msg", msg);
		}

		RedirectView redirectView = new RedirectView(redirectPath, true);

		return redirectView;
	}

	@GetMapping("/book")
	public RedirectView bookRoom(Authentication auth, //
			RedirectAttributes redirectAttributes, //
			@RequestParam("building") Long buildingId, //
			@RequestParam("room") Long roomId) {
		String redirectPath = "/user?success";

		boolean success = this.reservationService.makeReservation(buildingId, roomId, auth.getName());

		if (!success) {
			redirectPath = "/user?error";
			String msg = "Unable to reserve room.";
			redirectAttributes.addFlashAttribute("error_msg", msg);
		} else {
			String msg = "Room is now reserved for you.";
			redirectAttributes.addFlashAttribute("success_msg", msg);
		}

		RedirectView redirectView = new RedirectView(redirectPath, true);

		return redirectView;
	}

	@GetMapping("/cancel")
	public RedirectView cancelRoom(Authentication auth, //
			RedirectAttributes redirectAttributes, //
			@RequestParam("confirmation") UUID roomid) {
		String redirectPath = "/user?success";

		boolean success = this.reservationService.cancelReservation(roomid, auth.getName());

		if (!success) {
			redirectPath = "/user?error";
			String msg = "Unable to cancel reservation.";
			redirectAttributes.addFlashAttribute("error_msg", msg);
		} else {
			String msg = "Reservation canceled.";
			redirectAttributes.addFlashAttribute("success_msg", msg);
		}

		RedirectView redirectView = new RedirectView(redirectPath, true);

		return redirectView;
	}
	
	@GetMapping("/checkIn")
	public RedirectView checkInRoom(Authentication auth, //
			RedirectAttributes redirectAttributes, //
			@RequestParam("confirmation") UUID roomid) {
		String redirectPath = "/user?success";

		boolean success = this.reservationService.checkInReservation(roomid, auth.getName());

		if (!success) {
			redirectPath = "/user?error";
			String msg = "Unable to check in to room.";
			redirectAttributes.addFlashAttribute("error_msg", msg);
		} else {
			String msg = "You are now checked in.";
			redirectAttributes.addFlashAttribute("success_msg", msg);
		}

		RedirectView redirectView = new RedirectView(redirectPath, true);

		return redirectView;
	}
	
	@GetMapping("/checkOut")
	public RedirectView checkOutRoom(Authentication auth, //
			RedirectAttributes redirectAttributes, //
			@RequestParam("confirmation") UUID roomid) {
		String redirectPath = "/user?success";

		boolean success = this.reservationService.checkOutReservation(roomid, auth.getName());

		if (!success) {
			redirectPath = "/user?error";
			String msg = "Unable to check out of room.";
			redirectAttributes.addFlashAttribute("error_msg", msg);
		} else {
			String msg = "You are now checked out.";
			redirectAttributes.addFlashAttribute("success_msg", msg);
		}

		RedirectView redirectView = new RedirectView(redirectPath, true);

		return redirectView;
	}
}
