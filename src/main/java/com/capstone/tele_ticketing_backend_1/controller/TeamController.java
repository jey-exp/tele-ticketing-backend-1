package com.capstone.tele_ticketing_backend_1.controller;

import com.capstone.tele_ticketing_backend_1.dto.TeamSummaryDto; // Import the new DTO
import com.capstone.tele_ticketing_backend_1.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
@PreAuthorize("hasRole('MANAGER')")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping
    // Dr. X's Fix: Change the return type to a list of DTOs.
    public ResponseEntity<List<TeamSummaryDto>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }
}