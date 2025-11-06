package com.capstone.tele_ticketing_backend_1.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApproveSignupRequestDto {
    @NotBlank
    private String finalRole; // e.g., "l1_engineer"
}