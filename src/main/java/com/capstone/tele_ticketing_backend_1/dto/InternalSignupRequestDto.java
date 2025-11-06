package com.capstone.tele_ticketing_backend_1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InternalSignupRequestDto {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    private String fullname;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    private String preferredRole; // e.g., "l1_engineer"
}
