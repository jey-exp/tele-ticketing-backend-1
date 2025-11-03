package com.capstone.tele_ticketing_backend_1.dto;

import com.capstone.tele_ticketing_backend_1.entities.TicketPriority;
import com.capstone.tele_ticketing_backend_1.entities.TicketSeverity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Set;

@Data
public class TriageTicketRequestDto {

    @NotNull(message = "Severity must be provided")
    private TicketSeverity severity;

    @NotNull(message = "Priority must be provided")
    private TicketPriority priority;

    // Dr. X's Note: We expect a set of user IDs for the engineers to be assigned.
    // This is efficient and secure.
    @NotEmpty(message = "At least one engineer must be assigned")
    private Set<Long> assignedToUserIds;
}