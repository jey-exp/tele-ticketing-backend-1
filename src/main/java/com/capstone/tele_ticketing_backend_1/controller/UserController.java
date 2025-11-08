package com.capstone.tele_ticketing_backend_1.controller;


import com.capstone.tele_ticketing_backend_1.dto.UserSummaryDto;
import com.capstone.tele_ticketing_backend_1.repo.UserRepo;
import com.capstone.tele_ticketing_backend_1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    // Dr. X's Fix: Inject the service, not the repository.
    private final UserService userService;
    private final UserRepo userRepo;

    @GetMapping("/engineers")
    @PreAuthorize("hasRole('TRIAGE_OFFICER')")
    public ResponseEntity<List<UserSummaryDto>> getAssignableEngineers() {
        // The controller's only job is to call the service and return the response.
        return ResponseEntity.ok(userService.getAssignableEngineers());
    }

    // Dr. X's Addition: The new endpoint for the agent's form.
    @GetMapping("/customers")
    @PreAuthorize("hasRole('AGENT')")
    public ResponseEntity<List<UserSummaryDto>> getAllCustomers() {
        return ResponseEntity.ok(userService.getAllCustomers());
    }


    // In UserController.java
    @GetMapping("/unassigned-engineers")
    @PreAuthorize("hasRole('TEAM_LEAD')")
    public ResponseEntity<List<UserSummaryDto>> getUnassignedEngineers() {
        return ResponseEntity.ok(userService.getUnassignedEngineers());
    }

    @GetMapping("/cities")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<String>> getCustomerCities() {
        return ResponseEntity.ok(userService.getCustomerCities());
    }

}