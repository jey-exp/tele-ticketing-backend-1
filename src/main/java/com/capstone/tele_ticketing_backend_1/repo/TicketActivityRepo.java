package com.capstone.tele_ticketing_backend_1.repo;

import com.capstone.tele_ticketing_backend_1.entities.ActivityType;
import com.capstone.tele_ticketing_backend_1.entities.AppUser;
import com.capstone.tele_ticketing_backend_1.entities.TicketActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketActivityRepo extends JpaRepository<TicketActivity, Long> {

    // Gets all logs for a ticket, sorted by most recent first. (For internal users)
    List<TicketActivity> findByTicketIdOrderByCreatedAtDesc(Long ticketId);

    // Gets only public logs for a ticket. (For customers/agents)
    List<TicketActivity> findByTicketIdAndInternalOnlyFalseOrderByCreatedAtDesc(Long ticketId);

    List<TicketActivity> findFirst8ByTicket_CreatedForAndInternalOnlyFalseOrderByCreatedAtDesc(AppUser user);

    List<TicketActivity> findAllByTicket_CreatedForAndInternalOnlyFalseOrderByCreatedAtDesc(AppUser user);

    List<TicketActivity> findFirst8ByTicket_CreatedByAndInternalOnlyFalseOrderByCreatedAtDesc(AppUser user);

    // Finds all public activities for tickets CREATED BY a specific user, ordered by most recent.
    List<TicketActivity> findAllByTicket_CreatedByAndInternalOnlyFalseOrderByCreatedAtDesc(AppUser user);

    List<TicketActivity> findAllByActivityTypeAndInternalOnlyTrueOrderByCreatedAtDesc(ActivityType activityType);
    List<TicketActivity> findAllByActivityTypeInOrderByCreatedAtDesc(List<ActivityType> activityTypes);
}