package com.github.skyrrt.ticketbooker.booking.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
public class BookingResponseDto {
    private BigDecimal price;
    private ZonedDateTime expirationDate;
}
