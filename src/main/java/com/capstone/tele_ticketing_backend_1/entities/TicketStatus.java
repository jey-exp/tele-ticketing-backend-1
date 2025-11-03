package com.capstone.tele_ticketing_backend_1.entities;

public enum TicketStatus {
    CREATED,       // Initial state after customer creation
    AI_TRIAGED,    // AI has performed initial analysis
    NEEDS_TRIAGING,// Ready for a human Triage Officer
    ASSIGNED,      // Assigned to an engineer/team
    IN_PROGRESS,   // Actively being worked on
    FIXED,         // Engineer has implemented a fix
    RESOLVED,      // Customer has confirmed the fix
    REOPENED       // Customer has indicated the issue persists
}