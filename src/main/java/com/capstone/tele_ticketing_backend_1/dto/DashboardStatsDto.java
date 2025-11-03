package com.capstone.tele_ticketing_backend_1.dto;

import lombok.Data;

@Data
public class DashboardStatsDto {
    private long activeTickets;
    private long resolvedTickets;
    private long feedbackRequiredTickets;
}