package com.capstone.tele_ticketing_backend_1.entities;

import com.capstone.tele_ticketing_backend_1.entities.ActivityType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_activities")
@Data
@NoArgsConstructor
public class TicketActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    @JsonBackReference
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", nullable = false, length = 50)
    private ActivityType activityType;

    @Lob
    @Column(name = "activity_description")
    private String description;

    // Dr. X's Addition: This flag will control visibility.
    // true = Only internal staff (engineers, triage, etc.) can see it.
    // false = Everyone, including the customer, can see it.
    @Column(name = "is_internal_only", nullable = false)
    private boolean internalOnly = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}