package org.vang.john.hotelbooking.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vang.john.hotelbooking.dto.UserDTO;
import org.vang.john.hotelbooking.service.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	@Autowired
	private UserService userService;

	@ModelAttribute("user")
	public UserDTO userDTO() {
		return new UserDTO();
	}

	@GetMapping
	public String showRegistrationForm(Model model) {
		return "registration";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") @Valid UserDTO user, BindingResult result) {
		boolean existing = this.userService.existsByEmail(user.getEmail());
		if (existing) {
			result.rejectValue("email", null, "There is already an account registered with that email.");
		}

		if (result.hasErrors()) {
			return "registration";
		}

		this.userService.saveAsUser(user);

		return "redirect:/registration?success";

	}

}
