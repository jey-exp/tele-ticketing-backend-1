package com.capstone.tele_ticketing_backend_1.controller;


import com.capstone.tele_ticketing_backend_1.dto.TicketFilterDto;
import com.capstone.tele_ticketing_backend_1.dto.TicketSummaryDto;
import com.capstone.tele_ticketing_backend_1.entities.TicketStatus;
import com.capstone.tele_ticketing_backend_1.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/manager")
@PreAuthorize("hasRole('MANAGER')")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketSummaryDto>> getAllTickets(
            @RequestParam(required = false) List<TicketStatus> statuses,
            @RequestParam(required = false) Long teamId,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Boolean slaAtRisk,
            @RequestParam(required = false) Boolean slaBreached
    ) {
        TicketFilterDto filters = new TicketFilterDto();
        filters.setStatuses(statuses);
        filters.setTeamId(teamId);
        filters.setCity(city);
        filters.setSlaAtRisk(slaAtRisk != null && slaAtRisk);
        filters.setSlaBreached(slaBreached != null && slaBreached);

        List<TicketSummaryDto> tickets = managerService.findTicketsByCriteria(filters);
        return ResponseEntity.ok(tickets);
    }
}