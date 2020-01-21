package com.github.skyrrt.ticketbooker.ticket.domain;

import com.github.skyrrt.ticketbooker.movie.Movie;
import com.github.skyrrt.ticketbooker.room.Room;
import com.github.skyrrt.ticketbooker.screening.domain.Screening;
import com.github.skyrrt.ticketbooker.screening.domain.dto.SeatDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TicketServiceTest {
    @Mock
    TicketRepository ticketRepository;

    @InjectMocks
    TicketService ticketService;

    @BeforeEach
    void setMockOutput() {
        List<Ticket> tickets = List.of(
                new Ticket(1, 1, 1, BigDecimal.TEN, null),
                new Ticket(2, 2, 2, BigDecimal.TEN, null)

        );
        when(ticketRepository.findAllByScreening(null)).thenReturn(tickets);
    }

    @DisplayName("test if correct amount of seats is returned(2)")
    @Test
    void shouldGetBookedSeatsReturnTwo() {
        assertEquals(2, ticketService.getBookedSeats(null).size());
    }

    @DisplayName("test if correct data is returned")
    @Test
    void shouldGetBookedSeatsReturnCorrectData() {
        List<SeatDto> seatDtos = ticketService.getBookedSeats(null);
        assertEquals(1,seatDtos.get(0).getRow());
        assertEquals(1,seatDtos.get(0).getSeat());
        assertEquals(2,seatDtos.get(1).getSeat());
        assertEquals(2,seatDtos.get(1).getRow());

    }

    @DisplayName("test if correct amount of seats is returned(0)")
    @Test
    void shouldGetBookedSeatsReturnZero() {
        Room room = new Room(1, "nazwa", 3, 3);
        Movie movie = new Movie(1, "nazwaFilmu", "nazwisko");
        Screening screening = new Screening(1, Instant.ofEpochMilli(Long.MIN_VALUE).atZone(ZoneOffset.UTC).plusMinutes(2), room, movie);
        assertEquals(0, ticketService.getBookedSeats(screening).size());
    }

}
