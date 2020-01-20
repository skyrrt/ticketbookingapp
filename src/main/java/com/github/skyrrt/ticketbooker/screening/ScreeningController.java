package com.github.skyrrt.ticketbooker.screening;

import com.github.skyrrt.ticketbooker.screening.domain.dto.MovieScreeningDto;
import com.github.skyrrt.ticketbooker.screening.domain.ScreeningService;
import com.github.skyrrt.ticketbooker.screening.domain.dto.ScreeningDto;
import com.github.skyrrt.ticketbooker.screening.domain.exceptions.NoSuchScreeningExceptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeParseException;
import java.util.List;


@Slf4j
@RestController
@AllArgsConstructor
class ScreeningController {
    private ScreeningService screeningService;

    @GetMapping("/screenings")
    ResponseEntity<List<MovieScreeningDto>> getMovieScreenings(@RequestParam String from, @RequestParam String to) {
        log.info("Get screenings with from: {} to: {}", from, to);
        try {
            List<MovieScreeningDto> movies = screeningService.getMovieScreeningsInGivenInterval(from, to);
            if(movies.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(movies);
        } catch (DateTimeParseException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while parsing date", ex);
        }
    }

    @GetMapping("/screenings/{screeningId}")
    ResponseEntity<ScreeningDto> getScreeningDetails(@PathVariable long screeningId) {
        log.info("Get screening details. ScreeningId: {}", screeningId);
        try {
            return ResponseEntity.ok(screeningService.getScreeningDetails(screeningId));
        } catch (NoSuchScreeningExceptions ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening not found", ex);
        }
    }
}
