package com.capstone.tele_ticketing_backend_1.dto;


import com.capstone.tele_ticketing_backend_1.entities.TicketCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class AgentCreateTicketRequestDto {

    @NotBlank(message = "Customer username cannot be blank")
    private String customerUsername;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 10, max = 5000, message = "Description must be between 10 and 5000 characters")
    private String description;

    @NotNull(message = "Category cannot be null")
    private TicketCategory category;

    private List<String> attachments;
}
