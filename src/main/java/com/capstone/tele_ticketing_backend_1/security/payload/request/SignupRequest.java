package com.capstone.tele_ticketing_backend_1.security.payload.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Set;

@Data
public class SignupRequest {
	@NotBlank
	@Size(min = 3, max = 20)
	private String username;

	@NotBlank
	private String fullname;

	private Set<String> role;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;

	// Dr. X's Addition: New location fields. These can be optional for non-customer roles.
	private String city;
	private String state;
	private String postalCode;
}