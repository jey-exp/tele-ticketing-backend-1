package com.capstone.tele_ticketing_backend_1.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.Set;

@Data
public class ReassignTicketDto {
    @NotEmpty(message = "At least one engineer must be assigned.")
    private Set<Long> newAssigneeUserIds;
}