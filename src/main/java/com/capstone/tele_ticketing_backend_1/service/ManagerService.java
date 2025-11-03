package com.capstone.tele_ticketing_backend_1.service;


import com.capstone.tele_ticketing_backend_1.dto.TicketFilterDto;
import com.capstone.tele_ticketing_backend_1.dto.TicketSummaryDto;
import com.capstone.tele_ticketing_backend_1.entities.Ticket;
import com.capstone.tele_ticketing_backend_1.repo.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    @Autowired
    private TicketRepo ticketRepo;

    @Transactional(readOnly = true)
    public List<TicketSummaryDto> findTicketsByCriteria(TicketFilterDto filters) {
        // Dr. X's Fix: This is the non-deprecated way to start a dynamic query.
        Specification<Ticket> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        // The rest of the logic remains unchanged.
        if (filters.getStatuses() != null && !filters.getStatuses().isEmpty()) {
            spec = spec.and(TicketSpecification.hasStatusIn(filters.getStatuses()));
        }
        if (filters.getTeamId() != null) {
            spec = spec.and(TicketSpecification.inTeam(filters.getTeamId()));
        }
        if (filters.getCity() != null) {
            spec = spec.and(TicketSpecification.inCity(filters.getCity()));
        }
        if (filters.isSlaAtRisk()) {
            spec = spec.and(TicketSpecification.isAtSlaRisk());
        }
        if (filters.isSlaBreached()) {
            spec = spec.and(TicketSpecification.isSlaBreached());
        }

        List<Ticket> tickets = ticketRepo.findAll(spec);

        return tickets.stream()
                .map(ticket -> new TicketSummaryDto(
                        ticket.getId(), ticket.getTicketUid(), ticket.getTitle(),
                        ticket.getStatus(), ticket.getCreatedAt()))
                .collect(Collectors.toList());
    }
}