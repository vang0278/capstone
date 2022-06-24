package org.vang.john.hotelbooking.controller.role;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class RoleUserController {

	@GetMapping
	public String pageUser() {
		return "user";
	}

}
