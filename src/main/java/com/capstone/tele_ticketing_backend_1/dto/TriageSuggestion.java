package com.capstone.tele_ticketing_backend_1.dto;

import lombok.Data;

@Data
public class TriageSuggestion {
    // These must be Strings, as the AI works best with simple types.
    // We will validate and convert them to enums in our service.
    private String suggestedPriority; // "HIGH", "MEDIUM", "LOW"
    private String suggestedSeverity; // "CRITICAL", "HIGH", "MEDIUM", "LOW", "TRIVIAL"
    private String suggestedRole;     // "ROLE_FIELD_ENGINEER", "ROLE_NOC_ENGINEER", "ROLE_L1_ENGINEER"
}
