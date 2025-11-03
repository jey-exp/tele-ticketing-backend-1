package com.capstone.tele_ticketing_backend_1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long activityId;
    private String ticketUid;
    private String ticketTitle;
    private String description;
    private String activityType;
    private LocalDateTime createdAt;
}