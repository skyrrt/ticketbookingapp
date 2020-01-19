package com.github.skyrrt.ticketbooker.screening.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Data
public class ScreeningDto {
    private ZonedDateTime screeningDate;
    private String roomName;
    private List<SeatDto> availableSeats;
}