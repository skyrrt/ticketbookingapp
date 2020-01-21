package com.github.skyrrt.ticketbooker.booking.domain;

import com.github.skyrrt.ticketbooker.booking.domain.dto.CreateBookingDto;
import com.github.skyrrt.ticketbooker.booking.domain.exceptions.TooLateToBookException;
import com.github.skyrrt.ticketbooker.booking.domain.exceptions.UnallowedSeatBookingException;
import com.github.skyrrt.ticketbooker.screening.domain.ScreeningService;
import com.github.skyrrt.ticketbooker.screening.domain.dto.SeatDto;
import com.github.skyrrt.ticketbooker.ticket.domain.TicketType;
import com.github.skyrrt.ticketbooker.ticket.domain.dto.TicketDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookingValidatorTest {
    @Mock
    private ScreeningService screeningService;
    @InjectMocks
    private BookingValidator bookingValidator;

    @BeforeEach
    void setMockOutput() {
        when(screeningService.getScreeningDate(1l)).thenReturn(ZonedDateTime.now().plusMinutes(30));
        when(screeningService.getAvailableSeats(1l)).thenReturn(TestDataGenerator.roomWithOneSeatTaken(7,7, SeatDto.builder().seat(3).row(3).build()));
        when(screeningService.getRowSize(1l)).thenReturn(7);
    }

    @DisplayName("test if method returns true when given valid data")
    @Test
    void shouldReturnTrueWhenDatesAreCorrect() {
        CreateBookingDto dto = CreateBookingDto.builder()
                .name("Imie")
                .screeningId(1)
                .surname("Nazwisko")
                .ticketDtos(List.of(TicketDto.builder().place(1).row(1).type(TicketType.ADULT).build()))
                .build();
        assertTrue(bookingValidator.validate(dto));
    }

    @DisplayName("test if method throws exception when booking is made to late")
    @Test
    void shouldThrowExceptionWhenDatesAreNotValid() {
        when(screeningService.getScreeningDate(1l)).thenReturn(ZonedDateTime.now().plusMinutes(10));
        CreateBookingDto dto = CreateBookingDto.builder()
                .name("Imie")
                .screeningId(1)
                .surname("Nazwisko")
                .ticketDtos(List.of(TicketDto.builder().place(1).row(1).type(TicketType.ADULT).build()))
                .build();
        assertThrows(TooLateToBookException.class, () -> bookingValidator.validate(dto));
    }

    @DisplayName("test if method returns false when seat is invalid")
    @Test
    void shouldThrowExceptionWhenInvalidSeatIsGiven() {
        CreateBookingDto dto = CreateBookingDto.builder()
                .name("Imie")
                .screeningId(1)
                .surname("Nazwisko")
                .ticketDtos(List.of(TicketDto.builder().place(1).row(3).type(TicketType.ADULT).build()))
                .build();
        assertThrows(UnallowedSeatBookingException.class, () -> bookingValidator.validate(dto));
    }

    @DisplayName("test if method returns true when seat is margin seat")
    @Test
    void shouldHandleMarginSeats() {
        CreateBookingDto dto = CreateBookingDto.builder()
                .name("Imie")
                .screeningId(1)
                .surname("Nazwisko")
                .ticketDtos(List.of(TicketDto.builder().place(0).row(0).type(TicketType.ADULT).build()))
                .build();
        assertTrue(bookingValidator.validate(dto));
    }

}
