package com.github.skyrrt.ticketbooker.screening.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class ScreeningDateDto {
    private long screeningId;
    private ZonedDateTime screeningDate;
}
