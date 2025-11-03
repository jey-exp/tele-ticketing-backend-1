package com.capstone.tele_ticketing_backend_1.service;



import com.capstone.tele_ticketing_backend_1.dto.DashboardActivityDto;
import com.capstone.tele_ticketing_backend_1.dto.DashboardStatsDto;
import com.capstone.tele_ticketing_backend_1.entities.AppUser;
import com.capstone.tele_ticketing_backend_1.entities.TicketStatus;
import com.capstone.tele_ticketing_backend_1.exceptions.UserNotFoundException;
import com.capstone.tele_ticketing_backend_1.repo.TicketActivityRepo;
import com.capstone.tele_ticketing_backend_1.repo.TicketRepo;
import com.capstone.tele_ticketing_backend_1.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentDashboardService {

    @Autowired
    private TicketRepo ticketRepo;

    @Autowired
    private TicketActivityRepo activityRepo;

    @Autowired
    private UserRepo userRepo;

    @Transactional(readOnly = true)
    public DashboardStatsDto getDashboardStats(String agentUsername) {
        AppUser agent = userRepo.findByUsername(agentUsername)
                .orElseThrow(() -> new UserNotFoundException("Agent not found: " + agentUsername));

        List<TicketStatus> activeStatuses = List.of(
                TicketStatus.CREATED, TicketStatus.ASSIGNED, TicketStatus.IN_PROGRESS,
                TicketStatus.NEEDS_TRIAGING, TicketStatus.REOPENED, TicketStatus.FIXED
        );

        long activeCount = ticketRepo.countByCreatedByAndStatusIn(agent, activeStatuses);
        long resolvedCount = ticketRepo.countByCreatedByAndStatus(agent, TicketStatus.RESOLVED);
        long feedbackCount = ticketRepo.countByCreatedByAndStatus(agent, TicketStatus.FIXED);

        DashboardStatsDto stats = new DashboardStatsDto();
        stats.setActiveTickets(activeCount);
        stats.setResolvedTickets(resolvedCount);
        stats.setFeedbackRequiredTickets(feedbackCount);

        return stats;
    }

    @Transactional(readOnly = true)
    public List<DashboardActivityDto> getRecentActivities(String agentUsername) {
        AppUser agent = userRepo.findByUsername(agentUsername)
                .orElseThrow(() -> new UserNotFoundException("Agent not found: " + agentUsername));

        return activityRepo.findFirst8ByTicket_CreatedByAndInternalOnlyFalseOrderByCreatedAtDesc(agent)
                .stream()
                .map(activity -> new DashboardActivityDto(
                        activity.getId(),
                        activity.getDescription(),
                        activity.getTicket().getTicketUid(),
                        activity.getTicket().getTitle(),
                        activity.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}