package com.capstone.tele_ticketing_backend_1.controller;



import com.capstone.tele_ticketing_backend_1.dto.*;
import com.capstone.tele_ticketing_backend_1.entities.Team;
import com.capstone.tele_ticketing_backend_1.service.TeamLeadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/team-lead")
@PreAuthorize("hasRole('TEAM_LEAD')")
public class TeamLeadController {

    @Autowired
    private TeamLeadService teamLeadService;

    @GetMapping("/tickets/active")
    public ResponseEntity<List<TicketSummaryDto>> getActiveTeamTickets() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(teamLeadService.getActiveTeamTickets(username));
    }

    @GetMapping("/tickets/sla-risk")
    public ResponseEntity<List<TicketSummaryDto>> getSlaRiskTeamTickets() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(teamLeadService.getSlaRiskTeamTickets(username));
    }

    @GetMapping("/team")
    public ResponseEntity<TeamDetailDto> getTeamDetails() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(teamLeadService.getTeamDetails(username));
    }

    // This single endpoint handles creation, renaming, and member updates.
    @PatchMapping("/team/members")
    public ResponseEntity<TeamDetailDto> updateTeamMembers(@RequestBody TeamMemberUpdateRequestDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(teamLeadService.updateTeam(dto, username));
    }

    @PatchMapping("/tickets/{id}/reassign")
    public ResponseEntity<TicketDetailDto> reassignTicket(@PathVariable Long id, @Valid @RequestBody ReassignTicketDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(teamLeadService.reassignTicket(id, dto, username));
    }

    @GetMapping("/team/members")
    public ResponseEntity<List<UserSummaryDto>> getTeamMembers() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(teamLeadService.getTeamMembers(username));
    }
}
