package com.capstone.tele_ticketing_backend_1.controller;


import com.capstone.tele_ticketing_backend_1.dto.*;
import com.capstone.tele_ticketing_backend_1.service.CustomerTicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@CrossOrigin(origins = "*", maxAge = 3600) // Dr. X's Note: Secures all endpoints in this controller.
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerTicketService customerTicketService;

    @PostMapping("/tickets")
    public ResponseEntity<TicketDetailDto> createTicket(@Valid @RequestBody CreateTicketRequestDto createTicketRequestDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        TicketDetailDto createdTicket = customerTicketService.createTicket(createTicketRequestDto, username);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @GetMapping("/tickets/active")
    public ResponseEntity<List<TicketSummaryDto>> getActiveTickets() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TicketSummaryDto> tickets = customerTicketService.getCustomerActiveTickets(username);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/tickets/feedback-pending")
    public ResponseEntity<List<TicketSummaryDto>> getFeedbackPendingTickets() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TicketSummaryDto> tickets = customerTicketService.getCustomerFeedbackTickets(username);
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/tickets/{id}/feedback")
    public ResponseEntity<TicketDetailDto> addFeedback(@PathVariable Long id, @Valid @RequestBody FeedbackRequestDto feedbackRequestDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // The service now returns a safe DTO object.
        TicketDetailDto updatedTicket = customerTicketService.addFeedback(id, feedbackRequestDto, username);

        return ResponseEntity.ok(updatedTicket);
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationDto>> getNotifications() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(customerTicketService.getNotifications(username));
    }


}
