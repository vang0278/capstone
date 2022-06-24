package org.vang.john.hotelbooking.dto;

import javax.persistence.Column;

public class UpdateUserDTO {

	private String firstName;

	private String lastName;

	public UpdateUserDTO() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
