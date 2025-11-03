package com.capstone.tele_ticketing_backend_1.exceptions;

public class InvalidTicketStatusException extends RuntimeException {
    public InvalidTicketStatusException(String message) {
        super(message);
    }
}
