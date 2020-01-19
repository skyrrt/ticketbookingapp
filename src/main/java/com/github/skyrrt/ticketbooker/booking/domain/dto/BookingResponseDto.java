package com.github.skyrrt.ticketbooker.booking.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingResponseDto {
    private BigDecimal price;
    private BigDecimal expirationDate;
}
