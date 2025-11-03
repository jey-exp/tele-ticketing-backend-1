package com.capstone.tele_ticketing_backend_1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentFeedbackDto {
    private Long feedbackId;
    private String ticketUid;
    private String ticketTitle;
    private String customerFullName;
    private Integer rating;
    private String comment;
    private LocalDateTime feedbackCreatedAt;
}