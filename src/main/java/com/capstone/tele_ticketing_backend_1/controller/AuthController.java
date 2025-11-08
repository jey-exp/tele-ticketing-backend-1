package com.capstone.tele_ticketing_backend_1.controller;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.tele_ticketing_backend_1.dto.Coordinates;
import com.capstone.tele_ticketing_backend_1.dto.InternalSignupRequestDto;
import com.capstone.tele_ticketing_backend_1.entities.AppUser;
import com.capstone.tele_ticketing_backend_1.entities.ERole;
import com.capstone.tele_ticketing_backend_1.entities.Role;
import com.capstone.tele_ticketing_backend_1.entities.UserSignupRequest;
import com.capstone.tele_ticketing_backend_1.exceptions.RoleNotFoundException;
import com.capstone.tele_ticketing_backend_1.exceptions.UserAlreadyExistsException;
import com.capstone.tele_ticketing_backend_1.repo.RoleRepo;
import com.capstone.tele_ticketing_backend_1.repo.UserRepo;
import com.capstone.tele_ticketing_backend_1.repo.UserSignupRequestRepo;
import com.capstone.tele_ticketing_backend_1.security.jwt.JwtUtils;
import com.capstone.tele_ticketing_backend_1.security.payload.request.LoginRequest;
import com.capstone.tele_ticketing_backend_1.security.payload.request.SignupRequest;
import com.capstone.tele_ticketing_backend_1.security.payload.response.JwtResponse;
import com.capstone.tele_ticketing_backend_1.security.payload.response.MessageResponse;
import com.capstone.tele_ticketing_backend_1.security.service.UserDetailsImpl;
import com.capstone.tele_ticketing_backend_1.service.GeocodingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final UserRepo userRepository;
	private final RoleRepo roleRepository;
	private final PasswordEncoder encoder;
	private final JwtUtils jwtUtils;
	private final GeocodingService geocodingService;
	private final UserSignupRequestRepo signupRequestRepo;

	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		log.info("Authentication attempt for user: {}", loginRequest.getUsername());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toList();

		log.info("Successful authentication for user: {} with roles: {}", loginRequest.getUsername(), roles);
		return ResponseEntity.ok(new JwtResponse(jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				roles.toString()
		));
	}

	@PostMapping("/signup")
	public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		log.info("User registration attempt for username: {}", signUpRequest.getUsername());
		Boolean exists = userRepository.existsByUsername(signUpRequest.getUsername());
		if (Boolean.TRUE.equals(exists)) {
			log.warn("Registration failed - username already exists: {}", signUpRequest.getUsername());
			throw new UserAlreadyExistsException("Error: Username is already taken!");
		}

		AppUser user = new AppUser(
				signUpRequest.getUsername(),
				encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getFullname()
		);

		// Set location fields and geocode the address
		user.setCity(signUpRequest.getCity());
		user.setState(signUpRequest.getState());
		user.setPostalCode(signUpRequest.getPostalCode());

		if (signUpRequest.getCity() != null && signUpRequest.getPostalCode() != null) {
			Coordinates coords = geocodingService.getCoordinates(
					signUpRequest.getCity(),
					signUpRequest.getState(),
					signUpRequest.getPostalCode()
			);
			if (coords != null) {
				user.setLatitude(coords.latitude());
				user.setLongitude(coords.longitude());
			}
		}

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null || strRoles.isEmpty()) {
			throw new RoleNotFoundException("Error: Role must be provided.");
		}

		strRoles.forEach(role -> {
			switch (role) {
				case "customer": {
					Role foundRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
							.orElseThrow(() -> new RoleNotFoundException("Error: Role 'CUSTOMER' is not found."));
					roles.add(foundRole);
					break;
				}
				case "agent": {
					Role foundRole = roleRepository.findByName(ERole.ROLE_AGENT)
							.orElseThrow(() -> new RoleNotFoundException("Error: Role 'AGENT' is not found."));
					roles.add(foundRole);
					break;
				}
				case "triage_officer": {
					Role foundRole = roleRepository.findByName(ERole.ROLE_TRIAGE_OFFICER)
							.orElseThrow(() -> new RoleNotFoundException("Error: Role 'TRIAGE_OFFICER' is not found."));
					roles.add(foundRole);
					break;
				}
				case "field_engineer": {
					Role foundRole = roleRepository.findByName(ERole.ROLE_FIELD_ENGINEER)
							.orElseThrow(() -> new RoleNotFoundException("Error: Role 'FIELD_ENGINEER' is not found."));
					roles.add(foundRole);
					break;
				}
				case "noc_engineer": {
					Role foundRole = roleRepository.findByName(ERole.ROLE_NOC_ENGINEER)
							.orElseThrow(() -> new RoleNotFoundException("Error: Role 'NOC_ENGINEER' is not found."));
					roles.add(foundRole);
					break;
				}
				case "l1_engineer": {
					Role foundRole = roleRepository.findByName(ERole.ROLE_L1_ENGINEER)
							.orElseThrow(() -> new RoleNotFoundException("Error: Role 'L1_ENGINEER' is not found."));
					roles.add(foundRole);
					break;
				}
				case "manager": {
					Role foundRole = roleRepository.findByName(ERole.ROLE_MANAGER)
							.orElseThrow(() -> new RoleNotFoundException("Error: Role 'MANAGER' is not found."));
					roles.add(foundRole);
					break;
				}
				case "team_lead": {
					Role foundRole = roleRepository.findByName(ERole.ROLE_TEAM_LEAD)
							.orElseThrow(() -> new RoleNotFoundException("Error: Role 'TEAM_LEAD' is not found."));
					roles.add(foundRole);
					break;
				}
				case "cxo": {
					Role foundRole = roleRepository.findByName(ERole.ROLE_CXO)
							.orElseThrow(() -> new RoleNotFoundException("Error: Role 'CXO' is not found."));
					roles.add(foundRole);
					break;
				}
				case "noc_admin": {
					Role foundRole = roleRepository.findByName(ERole.ROLE_NOC_ADMIN)
							.orElseThrow(() -> new RoleNotFoundException("Error: Role 'NOC_ADMIN' is not found."));
					roles.add(foundRole);
					break;
				}
				case "admin": {
					Role foundRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RoleNotFoundException("Error: Role 'ADMIN' is not found."));
					roles.add(foundRole);
					break;
				}
				default:
					throw new RoleNotFoundException("Error: Invalid role specified: " + role);
			}
		});

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	@PostMapping("/internal-signup")
	public ResponseEntity<MessageResponse> registerInternalUser(@Valid @RequestBody InternalSignupRequestDto signupRequest) {
		Boolean exists = userRepository.existsByUsername(signupRequest.getUsername());
		if (Boolean.TRUE.equals(exists)) {
			throw new UserAlreadyExistsException("Error: Username is already taken!");
		}

		// Dr. X's Note: We MUST hash the password before saving the request.
		UserSignupRequest request = new UserSignupRequest(
				signupRequest.getUsername(),
				signupRequest.getFullname(),
				encoder.encode(signupRequest.getPassword()), // Hash the password
				signupRequest.getPreferredRole()
		);
		signupRequestRepo.save(request);
		return ResponseEntity.ok(new MessageResponse("Signup request submitted successfully. Pending admin approval."));
	}
}