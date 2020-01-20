package com.github.skyrrt.ticketbooker.screening.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class MovieScreeningDto {
    private String movieTitle;
    private List<ScreeningDateDto> screenings;
}
