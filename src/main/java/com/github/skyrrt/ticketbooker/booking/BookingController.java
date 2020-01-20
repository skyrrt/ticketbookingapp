package com.github.skyrrt.ticketbooker.booking;

import com.github.skyrrt.ticketbooker.booking.domain.BookingService;
import com.github.skyrrt.ticketbooker.booking.domain.dto.BookingResponseDto;
import com.github.skyrrt.ticketbooker.booking.domain.dto.CreateBookingDto;
import com.github.skyrrt.ticketbooker.booking.domain.exceptions.TooLateToBookException;
import com.github.skyrrt.ticketbooker.booking.domain.exceptions.UnallowedSeatBookingException;
import com.github.skyrrt.ticketbooker.screening.domain.exceptions.NoSuchScreeningExceptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@Slf4j
@AllArgsConstructor
class BookingController {
    private BookingService bookingService;

    @PostMapping(value = "/bookings",
            consumes = "application/json;charset=UTF-8",
            produces = "application/json;charset=UTF-8")
    ResponseEntity<BookingResponseDto> bookSeats(@RequestBody @Valid CreateBookingDto createBookingDto) {
        log.info("Attempt to create booking for screening: {}", createBookingDto.getScreeningId());
        try {
            return ResponseEntity.ok(bookingService.bookSeats(createBookingDto));
        } catch (NoSuchScreeningExceptions ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Screening with given id doesn't exist", ex);
        } catch (TooLateToBookException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You can book seats at least 15 minutes before screening", ex);
        } catch (UnallowedSeatBookingException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You left empty seat", ex);
        }
    }
}
