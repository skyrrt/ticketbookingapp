package com.github.skyrrt.ticketbooker.screening.domain;

import com.github.skyrrt.ticketbooker.movie.Movie;
import com.github.skyrrt.ticketbooker.room.Room;
import com.github.skyrrt.ticketbooker.screening.domain.dto.MovieScreeningDto;
import com.github.skyrrt.ticketbooker.screening.domain.dto.ScreeningDto;
import com.github.skyrrt.ticketbooker.screening.domain.dto.SeatDto;
import com.github.skyrrt.ticketbooker.screening.domain.exceptions.NoSuchScreeningExceptions;
import com.github.skyrrt.ticketbooker.ticket.domain.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ScreeningServiceTest {

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private ScreeningService screeningService;

    @BeforeEach
    void setMockOutput() {
        Room room = new Room(1, "nazwa", 3, 3);
        Movie movie = new Movie(1, "nazwaFilmu", "nazwisko");
        Screening screening = new Screening(1, Instant.ofEpochMilli(Long.MIN_VALUE).atZone(ZoneOffset.UTC).plusMinutes(2), room, movie);
        when(screeningRepository.findById(1l)).thenReturn(Optional.of(screening));
        when(screeningRepository.findById(2l)).thenReturn(Optional.ofNullable(null));

    }

    @DisplayName("Test if method getAvailableSeats calculates proper amount of seats")
    @Test
    void shouldReturnAvailableSeats() {
        List<SeatDto> list = screeningService.getAvailableSeats(1l);
        assertEquals(9, list.size());
    }

    @DisplayName("Test if method getAvailableSeats throws proper exception")
    @Test
    void shouldThrowException() {
        assertThrows(NoSuchScreeningExceptions.class,() -> screeningService.getAvailableSeats(2));
    }

    @DisplayName("test if getScreening returns proper screening")
    @Test
    void shouldReturnScreening() {
        assertEquals(screeningService.getScreening(1l), screeningRepository.findById(1l).get());
    }

    @DisplayName("test if getScreening throws proper exception")
    @Test
    void shouldThrowExceptionNoSuchScreeningException() {
        assertThrows(NoSuchScreeningExceptions.class, () -> screeningService.getScreening(2l));
    }

    @DisplayName("test if getAvailableSeats returns proper value when there is booked seat")
    @Test
    void shouldReturnSeatsWithoutBookedOne() {
        when(ticketService.getBookedSeats(screeningRepository.findById(1l).get())).thenReturn(List.of(SeatDto.builder().seat(1).row(1).build()));
        assertEquals(8, screeningService.getAvailableSeats(1l).size());
    }

    @DisplayName("test if getScreeningDetails gives proper informations")
    @Test
    void shouldReturnCorrectInfroAboutScreening() {
        ScreeningDto screeningDto = screeningService.getScreeningDetails(1l);
        assertEquals(9, screeningDto.getAvailableSeats().size());
        assertEquals("nazwa",screeningDto.getRoomName());
        assertEquals(Instant.ofEpochMilli(Long.MIN_VALUE).atZone(ZoneOffset.UTC).plusMinutes(2), screeningService.getScreeningDetails(1l).getScreeningDate());
    }

    @DisplayName("test if getScreeningDetails throws proper exception")
    @Test
    void shouldThrowProperException() {
        assertThrows(NoSuchScreeningExceptions.class, () -> screeningService.getScreeningDetails(2l));
    }

    @DisplayName("test if getMovieScreeningsInGivenInterval returns proper amount of records")
    @Test
    void shouldReturnOneRecord() {
        Room room = new Room(1, "nazwa", 3, 3);
        Movie movie = new Movie(1, "nazwaFilmu", "nazwisko");
        Screening screening = new Screening(1, Instant.ofEpochMilli(Long.MIN_VALUE).atZone(ZoneOffset.UTC).plusMinutes(2), room, movie);
        Screening screening2 = new Screening(2, Instant.ofEpochMilli(Long.MIN_VALUE).atZone(ZoneOffset.UTC).plusMinutes(2), room, movie);
        String date = Instant.ofEpochMilli(Long.MIN_VALUE).atZone(ZoneOffset.UTC).toString();
        String to = Instant.ofEpochMilli(Long.MIN_VALUE).atZone(ZoneOffset.UTC).plusMinutes(4).toString();
        when(screeningRepository.getAllBetweenDates(any(),any())).thenReturn(List.of(movie));
        assertEquals(1, screeningService.getMovieScreeningsInGivenInterval(date, to).size());
    }

    @DisplayName("test if getMovieScreeningsInGivenInterval returns empty list")
    @Test
    void shouldReturnEmptyList() {
        Room room = new Room(1, "nazwa", 3, 3);
        Movie movie = new Movie(1, "nazwaFilmu", "nazwisko");
        Screening screening = new Screening(1, Instant.ofEpochMilli(Long.MIN_VALUE).atZone(ZoneOffset.UTC).plusMinutes(0), room, movie);
        String date = Instant.ofEpochMilli(Long.MAX_VALUE).atZone(ZoneOffset.UTC).toString();
        String to = Instant.ofEpochMilli(Long.MIN_VALUE).atZone(ZoneOffset.UTC).plusMinutes(4).toString();
        when(screeningRepository.getAllBetweenDates(any(),any())).thenReturn(List.of());
        List<MovieScreeningDto> list = screeningService.getMovieScreeningsInGivenInterval(date, to);
        assertEquals(0, screeningService.getMovieScreeningsInGivenInterval(to, date).size());
    }
}
