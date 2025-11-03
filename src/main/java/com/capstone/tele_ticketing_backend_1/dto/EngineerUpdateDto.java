package com.capstone.tele_ticketing_backend_1.dto;
import com.capstone.tele_ticketing_backend_1.entities.TicketStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EngineerUpdateDto {

    @NotNull(message = "New status cannot be null")
    private TicketStatus newStatus;

    // The update text/comment is optional.
    private String updateText;
}