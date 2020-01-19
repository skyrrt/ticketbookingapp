package com.github.skyrrt.ticketbooker.booking.domain.exceptions;

public class TooLateToBookException extends Exception {
    TooLateToBookException(String message) {
        super(message);
    }
}
