package com.capstone.tele_ticketing_backend_1.dto;

import com.capstone.tele_ticketing_backend_1.entities.TicketCategory;
import com.capstone.tele_ticketing_backend_1.entities.TicketPriority;
import com.capstone.tele_ticketing_backend_1.entities.TicketSeverity;
import com.capstone.tele_ticketing_backend_1.entities.TicketStatus;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class TicketDetailDto {
    private Long id;
    private String ticketUid;
    private String title;
    private String description;
    private TicketStatus status;
    private TicketCategory category;
    private TicketPriority priority;
    private TicketSeverity severity;
    private Set<UserSummaryDto> assignedTo;

    // Dr. X's Addition: Add the field that was causing the error.
    private UserSummaryDto assignedBy;

    private UserSummaryDto createdFor;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;
}