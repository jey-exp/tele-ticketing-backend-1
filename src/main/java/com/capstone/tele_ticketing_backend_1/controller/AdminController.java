package com.capstone.tele_ticketing_backend_1.controller;

import com.capstone.tele_ticketing_backend_1.dto.ApproveSignupRequestDto;
import com.capstone.tele_ticketing_backend_1.dto.RoleChangeRequestDto;
import com.capstone.tele_ticketing_backend_1.dto.UserDetailsDto;
import com.capstone.tele_ticketing_backend_1.entities.AppUser;
import com.capstone.tele_ticketing_backend_1.entities.UserSignupRequest;
import com.capstone.tele_ticketing_backend_1.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
// ... other imports

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')") // Dr. X's Fix: Changed from 'MANAGER' to 'ADMIN'
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // All methods inside this controller are now protected for ADMIN only.
    @GetMapping("/signup-requests")
    public ResponseEntity<List<UserSignupRequest>> getPendingRequests() {
        return ResponseEntity.ok(adminService.getPendingSignupRequests());
    }

    @PostMapping("/signup-requests/{id}/approve")
    public ResponseEntity<AppUser> approveRequest(@PathVariable Long id, @Valid @RequestBody ApproveSignupRequestDto dto) {
        return ResponseEntity.ok(adminService.approveSignupRequest(id, dto));
    }

    @DeleteMapping("/signup-requests/{id}/reject")
    public ResponseEntity<Void> rejectRequest(@PathVariable Long id) {
        adminService.rejectSignupRequest(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<AppUser> changeUserRole(@PathVariable Long id, @Valid @RequestBody RoleChangeRequestDto dto) {
        return ResponseEntity.ok(adminService.changeUserRole(id, dto));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDetailsDto>> getAllInternalUsers() {
        return ResponseEntity.ok(adminService.getAllInternalUsers());
    }
}
