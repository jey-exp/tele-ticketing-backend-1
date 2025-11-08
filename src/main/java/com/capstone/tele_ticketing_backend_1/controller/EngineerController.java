package com.capstone.tele_ticketing_backend_1.controller;
import com.capstone.tele_ticketing_backend_1.dto.EngineerUpdateDto;
import com.capstone.tele_ticketing_backend_1.dto.TicketDetailDto;
import com.capstone.tele_ticketing_backend_1.dto.TicketSummaryDto;
import com.capstone.tele_ticketing_backend_1.service.EngineerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/engineer")
@PreAuthorize("hasAnyRole('L1_ENGINEER', 'NOC_ENGINEER', 'FIELD_ENGINEER')")
@RequiredArgsConstructor
public class EngineerController {

    private final EngineerService engineerService;

    @GetMapping("/tickets/assigned")
    public ResponseEntity<List<TicketSummaryDto>> getAssignedTickets() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(engineerService.getAssignedTickets(username));
    }

    @PatchMapping("/tickets/{id}")
    public ResponseEntity<TicketDetailDto> updateTicket(@PathVariable Long id, @Valid @RequestBody EngineerUpdateDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        TicketDetailDto updatedTicket = engineerService.updateTicket(id, dto, username);
        return ResponseEntity.ok(updatedTicket);
    }
}