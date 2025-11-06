package com.capstone.tele_ticketing_backend_1.dto;

import com.capstone.tele_ticketing_backend_1.entities.ERole;
import com.capstone.tele_ticketing_backend_1.entities.TicketPriority;
import com.capstone.tele_ticketing_backend_1.entities.TicketSeverity;
import lombok.Data;

@Data
public class AiTriageSuggestionDto {
    private Long ticketId;
    private String ticketUid;
    private String title;

    // We use proper enums in our DTO for type safety.
    private TicketPriority suggestedPriority;
    private TicketSeverity suggestedSeverity;
    private ERole suggestedRole;
}