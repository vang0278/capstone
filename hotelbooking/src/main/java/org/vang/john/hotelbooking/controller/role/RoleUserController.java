package org.vang.john.hotelbooking.controller.role;

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
import org.vang.john.hotelbooking.entity.UserEntity;
import org.vang.john.hotelbooking.service.role.RoleUserService;

@Controller
@RequestMapping("/user")
public class RoleUserController {

	@Autowired
	RoleUserService roleUserService;

	@ModelAttribute("user")
	public UpdateUserDTO updateUserDTO() {
		return new UpdateUserDTO();
	}

	@GetMapping
	public String pageUser(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String loginName = auth.getName();

		UserEntity user = this.roleUserService.getUser(loginName);

		model.addAttribute("user", user);

		return "user";
	}

	@PostMapping("/updateName")
	public RedirectView updateName(RedirectAttributes redirectAttributes, //
			@ModelAttribute("user") UpdateUserDTO user, //
			@RequestParam("email") String loginName) {
		String redirectPath = "/user?success";

		boolean success = this.roleUserService.updateName(loginName, user.getLastName(), user.getFirstName());

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
	public RedirectView updateName(RedirectAttributes redirectAttributes, //
			@RequestParam String password, //
			@RequestParam String confirmPassword, //
			@RequestParam("email") String loginName) {
		String redirectPath = "/user?success";

		System.out.println("Enter");

		boolean success = this.roleUserService.changePassword(loginName, password, confirmPassword);

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
}
