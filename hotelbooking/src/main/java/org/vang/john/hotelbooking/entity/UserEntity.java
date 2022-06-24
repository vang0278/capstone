package org.vang.john.hotelbooking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty
	@Column(nullable = false, length = 50)
	private String firstName;

	@NotEmpty
	@Column(nullable = false, length = 50)
	private String lastName;

	@Email
	@NotEmpty
	@Column(unique = true, nullable = false, length = 50)
	private String email;

	@NotEmpty
	@Column(nullable = false)
	private String password;

	@ManyToOne
	private RoleEntity role;

	public UserEntity() {
	}

	public UserEntity(String firstName, String lastName, String email, String password, RoleEntity role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public UserEntity(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", role=" + role + "]";
	}

}
