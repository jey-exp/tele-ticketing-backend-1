package com.capstone.tele_ticketing_backend_1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleChangeRequestDto {
    @NotBlank
    private String newRole; // e.g., "team_lead"
}
