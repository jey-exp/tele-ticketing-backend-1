package com.capstone.tele_ticketing_backend_1.ai;

import com.capstone.tele_ticketing_backend_1.dto.TriageSuggestion;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface TriageAssistant {

    @SystemMessage({
            "You are an expert Triage Officer for a network ticketing system.",
            "Your task is to analyze a ticket's title, description, and category.",
            "You must return your triage decision as a JSON object matching the TriageSuggestion format.",
            "Priority must be one of: HIGH, MEDIUM, LOW.",
            "Severity must be one of: CRITICAL, HIGH, MEDIUM, LOW, TRIVIAL.",
            "SuggestedRole must be one of the following based on the problem description:",
            "'ROLE_L1_ENGINEER' for basic issues like password resets or simple connectivity problems.",
            "'ROLE_NOC_ENGINEER' for backend network, server, or outage issues.",
            "'ROLE_FIELD_ENGINEER' for issues requiring a physical site visit, like hardware failure or line faults."
    })
    TriageSuggestion suggestTriage(@UserMessage String ticketDetails);
}
