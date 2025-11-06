package com.capstone.tele_ticketing_backend_1.dto;

import lombok.Data;

@Data
public class UserDetailsDto {
    private Long id;
    private String username;
    private String fullName;
    private String role; // e.g., "ROLE_TRIAGE_OFFICER"
}
