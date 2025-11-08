package com.capstone.tele_ticketing_backend_1.controller;



import com.capstone.tele_ticketing_backend_1.dto.DashboardActivityDto;
import com.capstone.tele_ticketing_backend_1.dto.DashboardStatsDto;
import com.capstone.tele_ticketing_backend_1.service.AgentDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/agent/dashboard")
@PreAuthorize("hasRole('AGENT')")
@RequiredArgsConstructor
public class AgentDashboardController {

    private final AgentDashboardService agentDashboardService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDto> getStats() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(agentDashboardService.getDashboardStats(username));
    }

    @GetMapping("/recent-activities")
    public ResponseEntity<List<DashboardActivityDto>> getRecentActivities() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(agentDashboardService.getRecentActivities(username));
    }
}