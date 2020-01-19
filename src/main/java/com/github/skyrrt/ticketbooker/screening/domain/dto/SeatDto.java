package com.github.skyrrt.ticketbooker.screening.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SeatDto {
    private int row;
    private int seat;
}
