package com.github.skyrrt.ticketbooker.booking.domain.exceptions;

public class UnallowedSeatBookingException extends RuntimeException {
    public UnallowedSeatBookingException(String message) {
        super(message);
    }
}
