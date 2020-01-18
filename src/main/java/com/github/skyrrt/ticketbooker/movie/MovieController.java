package com.github.skyrrt.ticketbooker.movie;

import com.github.skyrrt.ticketbooker.movie.domain.MovieService;
import com.github.skyrrt.ticketbooker.movie.domain.dto.MovieScreeningDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
class MovieController {

    private MovieService movieService;

    @GetMapping("movies")
    ResponseEntity<List<MovieScreeningDto>> getMovies(@RequestParam String from, @RequestParam String to) {
        try {
            List<MovieScreeningDto> movies = movieService.getMoviesInGivenInterval(from, to);
            if(movies.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(movies);
        } catch (DateTimeParseException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while parsing date", ex);
        }
    }
}
