package com.github.skyrrt.ticketbooker.screening.domain.dto;

import java.time.ZonedDateTime;
import java.util.List;

public class ScreeningDto {
    private ZonedDateTime screeningTime;
    private String roomName;
    private List<SeatDto> availableSeats;
}
(^[A-Z][a-z]{2,}$)|(^[A-Z][a-z]{2,}-[A-Z][a-z]{2,}$)
