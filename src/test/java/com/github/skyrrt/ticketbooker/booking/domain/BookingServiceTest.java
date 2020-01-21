package com.github.skyrrt.ticketbooker.booking.domain;


import com.github.skyrrt.ticketbooker.booking.domain.dto.BookingResponseDto;
import com.github.skyrrt.ticketbooker.booking.domain.dto.CreateBookingDto;
import com.github.skyrrt.ticketbooker.movie.Movie;
import com.github.skyrrt.ticketbooker.room.Room;
import com.github.skyrrt.ticketbooker.screening.domain.Screening;
import com.github.skyrrt.ticketbooker.screening.domain.ScreeningService;
import com.github.skyrrt.ticketbooker.ticket.domain.TicketRepository;
import com.github.skyrrt.ticketbooker.ticket.domain.TicketType;
import com.github.skyrrt.ticketbooker.ticket.domain.dto.TicketDto;
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
public class BookingServiceTest {
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private BookingValidator bookingValidator;
    @Mock
    private ScreeningService screeningService;
    @Mock
    private BookingRepository bookingRepository;
    @InjectMocks
    private BookingService bookingService;

    @DisplayName("test if proper data is returned")
    @Test
    void shouldReturnProperData() {
        CreateBookingDto dto = CreateBookingDto.builder()
                .name("Imie")
                .screeningId(1)
                .surname("Nazwisko")
                .ticketDtos(List.of(TicketDto.builder().place(1).row(1).type(TicketType.ADULT).build()))
                .build();
        when(bookingValidator.validate(dto)).thenReturn(true);
        Room room = new Room(1, "nazwa", 7, 7);
        Movie movie = new Movie(1, "nazwaFilmu", "nazwisko");
        Screening screening = new Screening(1, Instant.ofEpochMilli(Long.MAX_VALUE).atZone(ZoneOffset.UTC), room, movie);
        when(screeningService.getScreening(1)).thenReturn(screening);
        when(bookingRepository.save(Booking.builder().screening(screening).build())).thenReturn(new Booking(1l, screening, null));
        BookingResponseDto bookingResponseDto = bookingService.bookSeats(dto);
        assertEquals(BigDecimal.valueOf(25.0), bookingResponseDto.getPrice());
        assertEquals(Instant.ofEpochMilli(Long.MAX_VALUE).atZone(ZoneOffset.UTC).minusMinutes(15), bookingResponseDto.getExpirationDate());

    }


}
