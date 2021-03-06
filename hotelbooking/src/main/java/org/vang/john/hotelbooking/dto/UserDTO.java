package org.vang.john.hotelbooking.dto;

import org.vang.john.hotelbooking.entity.UserEntity;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@FieldMatch.List({
		@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match."),
		@FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match.") })
public class UserDTO extends UserEntity{
	
	@Email(message = "Please enter your email in the correct format.")
	@NotEmpty(message = "Please confirm your email.")
	private String confirmEmail;

	@NotEmpty(message = "Please confirm your password.")
	private String confirmPassword;
		
	public String getConfirmEmail() {
		return confirmEmail;
	}
	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
}
