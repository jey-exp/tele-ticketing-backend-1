package com.capstone.tele_ticketing_backend_1.service;


import com.capstone.tele_ticketing_backend_1.dto.TriageTicketRequestDto;
import com.capstone.tele_ticketing_backend_1.dto.TicketDetailDto;
import com.capstone.tele_ticketing_backend_1.dto.TicketSummaryDto;
import com.capstone.tele_ticketing_backend_1.entities.ActivityType;
import com.capstone.tele_ticketing_backend_1.entities.AppUser;
import com.capstone.tele_ticketing_backend_1.entities.Ticket;
import com.capstone.tele_ticketing_backend_1.entities.TicketStatus;
import com.capstone.tele_ticketing_backend_1.entities.TicketSeverity;
import com.capstone.tele_ticketing_backend_1.exceptions.InvalidTicketStatusException;
import com.capstone.tele_ticketing_backend_1.exceptions.TicketNotFoundException;
import com.capstone.tele_ticketing_backend_1.exceptions.UserNotFoundException;
import com.capstone.tele_ticketing_backend_1.repo.TicketRepo;
import com.capstone.tele_ticketing_backend_1.repo.UserRepo;
import com.capstone.tele_ticketing_backend_1.service.ActivityLogService;
import com.capstone.tele_ticketing_backend_1.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TriageOfficerService {

    @Autowired
    private TicketRepo ticketRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ActivityLogService activityLogService;

    // Defines which statuses are considered "pending" for a Triage Officer.
    private static final List<TicketStatus> PENDING_STATUSES = List.of(
            TicketStatus.CREATED,
            TicketStatus.NEEDS_TRIAGING,
            TicketStatus.REOPENED
    );

    public List<TicketSummaryDto> getPendingTickets() {
        List<Ticket> tickets = ticketRepo.findAllByStatusIn(PENDING_STATUSES);
        return tickets.stream()
                .map(ticket -> new TicketSummaryDto(ticket.getId(), ticket.getTicketUid(), ticket.getTitle(), ticket.getStatus(), ticket.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @Transactional
    public TicketDetailDto triageTicket(Long ticketId, TriageTicketRequestDto dto, String triageOfficerUsername) {
        AppUser triageOfficer = userRepo.findByUsername(triageOfficerUsername)
                .orElseThrow(() -> new UserNotFoundException("Triage officer not found: " + triageOfficerUsername));

        Ticket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + ticketId));

        // State Validation: Ensure the ticket is in a state that can be triaged.
        if (!PENDING_STATUSES.contains(ticket.getStatus())) {
            throw new InvalidTicketStatusException("Ticket is not in a state that can be triaged. Current status: " + ticket.getStatus());
        }

        List<AppUser> engineersToAssign = userRepo.findAllById(dto.getAssignedToUserIds());
        if (engineersToAssign.size() != dto.getAssignedToUserIds().size()) {
            throw new UserNotFoundException("One or more specified engineers could not be found.");
        }

        // Log changes BEFORE applying them for a clear audit trail.
        if (dto.getPriority() != ticket.getPriority()) {
            activityLogService.createLog(ticket, triageOfficer, ActivityType.PRIORITY_CHANGE, "Priority changed from " + ticket.getPriority() + " to " + dto.getPriority(), false); // Public log
        }

        // Apply Updates to the Ticket
        ticket.setSeverity(dto.getSeverity());
        ticket.setPriority(dto.getPriority());
        ticket.setAssignedTo(new HashSet<>(engineersToAssign));
        ticket.setAssignedBy(triageOfficer);
        ticket.setStatus(TicketStatus.ASSIGNED);

        // Calculate and set SLA based on the new severity.
        int slaHours = calculateSlaHours(dto.getSeverity());
        ticket.setSlaDurationHours(slaHours);
        ticket.setSlaBreachAt(LocalDateTime.now().plusHours(slaHours));

        // Create an internal log for the assignment action.
        String assignedEngineersNames = engineersToAssign.stream().map(AppUser::getFullName).collect(Collectors.joining(", "));
        activityLogService.createLog(ticket, triageOfficer, ActivityType.ASSIGNMENT, "Assigned to " + assignedEngineersNames, true); // Internal-only log

        Ticket savedTicket = ticketRepo.save(ticket);

        // Return a clean DTO by calling the central mapper.
        return ticketService.mapTicketToDetailDto(savedTicket);
    }

    private int calculateSlaHours(TicketSeverity severity) {
        return switch (severity) {
            case CRITICAL -> 20;
            case HIGH -> 18;
            case MEDIUM -> 15;
            case LOW -> 10;
            case TRIVIAL -> 2;
            default -> 24; // Default fallback
        };
    }
}