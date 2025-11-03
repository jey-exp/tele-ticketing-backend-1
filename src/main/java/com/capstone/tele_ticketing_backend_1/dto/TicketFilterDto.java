package com.capstone.tele_ticketing_backend_1.dto;


import com.capstone.tele_ticketing_backend_1.entities.TicketStatus;
import lombok.Data;
import java.util.List;

@Data
public class TicketFilterDto {
    private List<TicketStatus> statuses;
    private Long teamId;
    private String city;
    private boolean isSlaAtRisk; // true if filtering for tickets at risk
    private boolean isSlaBreached; // true if filtering for breached tickets
}