package com.capstone.tele_ticketing_backend_1.dto;


import com.capstone.tele_ticketing_backend_1.entities.TicketCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateTicketRequestDto {

    // Dr. X's Note: Every ticket must have a title.
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    // Dr. X's Note: A detailed description is essential for diagnosis.
    @NotBlank(message = "Description cannot be blank")
    @Size(min = 10, max = 5000, message = "Description must be between 10 and 5000 characters")
    private String description;

    // Dr. X's Note: The customer must categorize their issue.
    // We use the Enum to ensure they can only send valid categories.
    @NotNull(message = "Category cannot be null")
    private TicketCategory category;

    // Dr. X's Note: As discussed, we'll accept a list of strings for dummy attachment URLs for now.
    // This can be an empty list if there are no attachments.
    private List<String> attachments;

    // IMPORTANT: Notice what is NOT here.
    // The DTO does not contain 'status', 'priority', 'severity', or 'assignedTo'.
    // Those fields are set by the *system* or by *privileged users* (like a Triage Officer),
    // never by the customer creating the ticket. This is a critical security and design principle.
}