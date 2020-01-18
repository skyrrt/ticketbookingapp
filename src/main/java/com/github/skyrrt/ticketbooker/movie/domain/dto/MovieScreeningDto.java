package com.github.skyrrt.ticketbooker.movie.domain.dto;

import com.github.skyrrt.ticketbooker.screening.domain.dto.ScreeningDateDto;

import java.util.List;

public class MovieScreeningDto {
    private String movieTitle;
    private List<ScreeningDateDto> screenings;
}
