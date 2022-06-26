package org.vang.john.hotelbooking.controller.role;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.vang.john.hotelbooking.dto.SearchUserDTO;
import org.vang.john.hotelbooking.entity.ReservationEntity;
import org.vang.john.hotelbooking.entity.UserEntity;
import org.vang.john.hotelbooking.service.ReservationService;
import org.vang.john.hotelbooking.service.role.RoleEmployeeService;

@Controller
@RequestMapping("/employee")
public class RoleEmployeeController {

	// const/final
	// do some logging when searching for users and reservations
	private static final Logger log = LoggerFactory.getLogger(RoleEmployeeController.class);

	@Autowired
	private RoleEmployeeService roleEmployeeService;

	@Autowired
	private ReservationService reservationService;

	@ModelAttribute("search")
	public SearchUserDTO searchUserDTO() {
		return new SearchUserDTO();
	}

	@GetMapping
	public String pageEmployee(Model model) {

		List<UserEntity> users = this.roleEmployeeService.findAll();

		model.addAttribute("users", users);

		return "employee";
	}

	@PostMapping
	public String findUser(Model model, @ModelAttribute("search") SearchUserDTO search) {
		log.info("Looking for: " + search.getSearch()); // LOG INFO
		List<UserEntity> foundUsers = this.roleEmployeeService.search(search.getSearch());

		model.addAttribute("foundUsers", foundUsers);
		
		List<ReservationEntity> foundReservations = this.reservationService.searchConfirmation(search.getSearch());
		model.addAttribute("foundReservations", foundReservations);
		
		if(foundUsers!=null) {
			foundUsers.forEach(u->log.info("found user: " + u.toString())); // LOG INFO
		}
		
		if(foundReservations != null) {
			foundReservations.forEach(r->log.info("found reservation: " + r.toString())); // LOG INFO
		}

		return "employee";
	}

	@PostMapping("/resetUserPassword")
	public RedirectView resetUserPassword(RedirectAttributes redirectAttributes, //
			@ModelAttribute("search") SearchUserDTO search, //
			@RequestParam String email) {

		redirectAttributes.addFlashAttribute("search", search);
		String redirectPath = "/employee?success";

		String newPassword = this.roleEmployeeService.resetUserPassword(email);

		if (newPassword == null) {
			redirectPath = "/employee?error";
			String msg = "Unable to rest password for User: " + email;
			redirectAttributes.addFlashAttribute("error_msg", msg);
		} else {
			String msg = "Password reset User: " + email + "<br>Password: " + newPassword;
			redirectAttributes.addFlashAttribute("success_msg", msg);
		}

		RedirectView redirectView = new RedirectView(redirectPath, true);

		return redirectView;
	}

	@PostMapping("/viewUserReservations")
	public RedirectView viewUserReservations(RedirectAttributes redirectAttributes, //
			@ModelAttribute("search") SearchUserDTO search, //
			@RequestParam String email) {

		redirectAttributes.addFlashAttribute("search", search);

		UserEntity user = this.roleEmployeeService.getUser(email);
		redirectAttributes.addFlashAttribute("user", user);

		List<ReservationEntity> reservations = this.reservationService.getReservations(user);
		redirectAttributes.addFlashAttribute("reservations", reservations);

		String redirectPath = "/employee";

		RedirectView redirectView = new RedirectView(redirectPath, true);

		return redirectView;
	}
}
