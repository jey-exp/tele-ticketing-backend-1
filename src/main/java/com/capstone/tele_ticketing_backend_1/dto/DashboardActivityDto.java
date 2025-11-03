package com.capstone.tele_ticketing_backend_1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DashboardActivityDto {
    private Long activityId;
    private String description;
    private String ticketUid;
    private String ticketTitle;
    private LocalDateTime createdAt;
}