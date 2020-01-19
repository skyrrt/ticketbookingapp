package com.github.skyrrt.ticketbooker.booking.domain.exceptions;

public class UnallowedSeatBookingException extends Exception {
    UnallowedSeatBookingException(String message) {
        super(message);
    }
}
