package org.vang.john.hotelbooking.controller.role;

import java.util.List;

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
import org.vang.john.hotelbooking.entity.UserEntity;
import org.vang.john.hotelbooking.service.role.RoleEmployeeService;

@Controller
@RequestMapping("/employee")
public class RoleEmployeeController {

	@Autowired
	private RoleEmployeeService roleEmployeeService;

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
		System.out.println("Looking for: " + search.getSearch()); //
		List<UserEntity> foundUsers = this.roleEmployeeService.search(search.getSearch());

		model.addAttribute("foundUsers", foundUsers);

		return "employee";
	}

	@GetMapping("/resetUserPassword")
	public RedirectView resetEmployeePassword(RedirectAttributes redirectAttributes, //
			@RequestParam String email) {
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
}
