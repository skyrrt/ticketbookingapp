package com.github.skyrrt.ticketbooker.movie.domain;

import com.github.skyrrt.ticketbooker.movie.domain.dto.MovieScreeningDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
public class MovieService {
    public List<MovieScreeningDto> getMoviesInGivenInterval(String from, String to) {
        ZonedDateTime dateFrom = ZonedDateTime.parse(from);
        ZonedDateTime dateTo = ZonedDateTime.parse(to);
        return null;
    }

}
