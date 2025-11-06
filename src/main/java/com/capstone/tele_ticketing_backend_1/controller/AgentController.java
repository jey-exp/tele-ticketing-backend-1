package com.capstone.tele_ticketing_backend_1.controller;

import com.capstone.tele_ticketing_backend_1.dto.*;
import com.capstone.tele_ticketing_backend_1.service.AgentTicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agent")
@PreAuthorize("hasRole('AGENT')")
@RequiredArgsConstructor
public class AgentController {

    private final AgentTicketService agentTicketService;

    @PostMapping("/tickets")
    // 1. Change the return type here from Ticket to TicketDetailDto
    public ResponseEntity<TicketDetailDto> createTicket(@Valid @RequestBody AgentCreateTicketRequestDto createDto) {
        String agentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. The variable 'createdTicket' is now correctly typed as TicketDetailDto
        TicketDetailDto createdTicket = agentTicketService.createTicketForCustomer(createDto, agentUsername);

        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @GetMapping("/tickets/created-by-me")
    public ResponseEntity<List<TicketSummaryDto>> getTicketsCreatedByMe() {
        String agentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TicketSummaryDto> tickets = agentTicketService.getAgentCreatedTickets(agentUsername);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/tickets/active")
    public ResponseEntity<List<TicketSummaryDto>> getActiveTickets() {
        String agentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(agentTicketService.getAgentActiveTickets(agentUsername));
    }

    // Dr. X's Addition: A dedicated endpoint for the agent's notification feed.
    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationDto>> getNotifications() {
        String agentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(agentTicketService.getNotifications(agentUsername));
    }

    @PostMapping("/tickets/{id}/feedback")
    public ResponseEntity<TicketDetailDto> addFeedback(@PathVariable Long id, @Valid @RequestBody FeedbackRequestDto feedbackRequestDto) {
        String agentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        TicketDetailDto updatedTicket = agentTicketService.addFeedbackForCustomer(id, feedbackRequestDto, agentUsername);
        return ResponseEntity.ok(updatedTicket);
    }

    @GetMapping("/tickets/feedback-pending")
    public ResponseEntity<List<TicketSummaryDto>> getFeedbackPendingTickets() {
        String agentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(agentTicketService.getAgentFeedbackPendingTickets(agentUsername));
    }
}