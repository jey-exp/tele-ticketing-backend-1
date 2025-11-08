package com.capstone.tele_ticketing_backend_1.exceptions;

public class ApiKeyNotFoundException extends RuntimeException {
    public ApiKeyNotFoundException(String message) {
        super(message);
    }
}
