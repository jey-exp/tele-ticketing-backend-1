package com.capstone.tele_ticketing_backend_1.dto;


import lombok.Data;

@Data
public class TeamSummaryDto {
    private Long id;
    private String name;
    private UserSummaryDto teamLead; // Reuse our existing DTO
}
