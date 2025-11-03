package com.capstone.tele_ticketing_backend_1.service;


import com.capstone.tele_ticketing_backend_1.entities.Ticket;
import com.capstone.tele_ticketing_backend_1.entities.TicketStatus;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;
import java.util.List;

public class TicketSpecification {

    public static Specification<Ticket> hasStatusIn(List<TicketStatus> statuses) {
        return (root, query, criteriaBuilder) -> {
            if (statuses == null || statuses.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filter if list is empty
            }
            return root.get("status").in(statuses);
        };
    }

    public static Specification<Ticket> inTeam(Long teamId) {
        return (root, query, criteriaBuilder) -> {
            if (teamId == null) {
                return criteriaBuilder.conjunction();
            }
            // Joins Ticket -> assignedTo (users) -> team
            return criteriaBuilder.equal(root.join("assignedTo").get("team").get("id"), teamId);
        };
    }

    public static Specification<Ticket> inCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null || city.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            // Joins Ticket -> createdFor (user) -> city
            return criteriaBuilder.equal(root.join("createdFor").get("city"), city);
        };
    }

    public static Specification<Ticket> isAtSlaRisk() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoHoursFromNow = now.plusHours(2);
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("slaBreachAt"), now, twoHoursFromNow);
    }

    public static Specification<Ticket> isSlaBreached() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("slaBreachAt"), LocalDateTime.now());
    }
}
