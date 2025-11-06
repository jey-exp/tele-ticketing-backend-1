package com.capstone.tele_ticketing_backend_1.controller;

import com.capstone.tele_ticketing_backend_1.dto.AiTriageSuggestionDto;
import com.capstone.tele_ticketing_backend_1.dto.TriageTicketRequestDto;
import com.capstone.tele_ticketing_backend_1.dto.TicketDetailDto;
import com.capstone.tele_ticketing_backend_1.dto.TicketSummaryDto;
import com.capstone.tele_ticketing_backend_1.service.TriageOfficerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/triage")
@PreAuthorize("hasRole('TRIAGE_OFFICER')")
public class TriageOfficerController {

    @Autowired
    private TriageOfficerService triageOfficerService;

    @GetMapping("/tickets/pending")
    public ResponseEntity<List<TicketSummaryDto>> getPendingTickets() {
        return ResponseEntity.ok(triageOfficerService.getPendingTickets());
    }

    // Dr. X's Note: We use PATCH here because it's a partial update of the ticket resource.
    @PatchMapping("/tickets/{id}")
    public ResponseEntity<TicketDetailDto> triageTicket(@PathVariable Long id, @Valid @RequestBody TriageTicketRequestDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        TicketDetailDto triagedTicket = triageOfficerService.triageTicket(id, dto, username);
        return ResponseEntity.ok(triagedTicket);
    }

    @GetMapping("/tickets/ai-suggestions")
    public ResponseEntity<List<AiTriageSuggestionDto>> getAiSuggestions() {
        return ResponseEntity.ok(triageOfficerService.getAiTriageSuggestions());
    }
}