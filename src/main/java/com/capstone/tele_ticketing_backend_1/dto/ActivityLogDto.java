package com.capstone.tele_ticketing_backend_1.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ActivityLogDto {
    private Long id;
    private String description;
    private String activityType;
    private UserSummaryDto user;
    private LocalDateTime createdAt;
    private boolean internalOnly;
}