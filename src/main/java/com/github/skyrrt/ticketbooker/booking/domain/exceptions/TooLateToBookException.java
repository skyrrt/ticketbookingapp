package com.github.skyrrt.ticketbooker.booking.domain.exceptions;

public class TooLateToBookException extends RuntimeException {
    public TooLateToBookException(String message) {
        super(message);
    }
}
