package com.capstone.tele_ticketing_backend_1.dto;

import com.capstone.tele_ticketing_backend_1.entities.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketSummaryDto {
    private Long id;
    private String ticketUid;
    private String title;
    private TicketStatus status;
    private LocalDateTime createdAt;
}
