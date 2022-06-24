package org.vang.john.hotelbooking.controller.role;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.vang.john.hotelbooking.entity.UserEntity;
import org.vang.john.hotelbooking.service.role.RoleAdminService;

@Controller
@RequestMapping("/admin")
public class RoleAdminController {

	@Autowired
	private RoleAdminService roleAdminService;

	@ModelAttribute("employee")
	public UserEntity userEntity() {
		return new UserEntity();
	}

	@GetMapping
	public String pageAdmin(Model model) {
		List<UserEntity> employees = this.roleAdminService.findAllEmployees();

		model.addAttribute("employees", employees);

		return "admin";
	}

	@PostMapping("/createEmployee")
	public RedirectView registerEmployeeAccount(RedirectAttributes redirectAttributes, //
			@ModelAttribute("employee") @Valid UserEntity employee, BindingResult result) {
		boolean emailAvailable = this.roleAdminService.isEmailAvailble(employee.getEmail());
		String redirectPath = "/admin?success";

		if (!emailAvailable) {
			String msg = "There is already an account registered with that email.";
			redirectAttributes.addFlashAttribute("error_msg", msg);
			result.rejectValue("email", null, msg);
		}

		if (result.hasErrors()) {
			redirectPath = "/admin?error";
			redirectAttributes.addFlashAttribute("employee", employee);
		} else {
			this.roleAdminService.createEmployee(employee);
			String msg = "Employee created.";
			redirectAttributes.addFlashAttribute("success_msg", msg);
		}

		RedirectView redirectView = new RedirectView(redirectPath, true);

		return redirectView;
	}

	@GetMapping("/deleteEmployee")
	public RedirectView removeEmployee(RedirectAttributes redirectAttributes, //
			@RequestParam Long id) {
		String redirectPath = "/admin?success";

		boolean success = this.roleAdminService.removeEmployee(id);

		if (!success) {
			redirectPath = "/admin?error";
			String msg = "Unable to remove Employee: " + id;
			redirectAttributes.addFlashAttribute("error_msg", msg);
		} else {
			String msg = "Removed Employee: " + id;
			redirectAttributes.addFlashAttribute("success_msg", msg);

		}

		RedirectView redirectView = new RedirectView(redirectPath, true);

		return redirectView;
	}

	@GetMapping("/resetEmployeePassword")
	public RedirectView resetEmployeePassword(RedirectAttributes redirectAttributes, //
			@RequestParam Long id) {
		String redirectPath = "/admin?success";

		String newPassword = this.roleAdminService.resetEmployeePassword(id);

		if (newPassword == null) {
			redirectPath = "/admin?error";
			String msg = "Unable to rest password for Employee: " + id;
			redirectAttributes.addFlashAttribute("error_msg", msg);
		} else {
			String msg = "Password reset Employee: " + id + "<br>Password: " + newPassword;
			redirectAttributes.addFlashAttribute("success_msg", msg);

		}

		RedirectView redirectView = new RedirectView(redirectPath, true);

		return redirectView;
	}

}
