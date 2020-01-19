package com.github.skyrrt.ticketbooker.booking.domain;

import com.github.skyrrt.ticketbooker.booking.domain.dto.CreateBookingDto;
import com.github.skyrrt.ticketbooker.booking.domain.exceptions.TooLateToBookException;
import com.github.skyrrt.ticketbooker.booking.domain.exceptions.UnallowedSeatBookingException;
import com.github.skyrrt.ticketbooker.screening.domain.Screening;
import com.github.skyrrt.ticketbooker.screening.domain.ScreeningService;
import com.github.skyrrt.ticketbooker.screening.domain.dto.SeatDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
class BookingValidator {
    private ScreeningService screeningService;

    public boolean validate(CreateBookingDto createBookingDto) {
        return validateIfUserIsAllowedToBook(createBookingDto.getScreeningId()) && validateSeats(createBookingDto);
    }

    private boolean validateIfUserIsAllowedToBook(long screeningId) {
        ZonedDateTime screeningDate = screeningService.getScreeningDate(screeningId);
        ZonedDateTime bookingTime = ZonedDateTime.now(ZoneId.systemDefault());
        if(!screeningDate.minusMinutes(15).isAfter(bookingTime)) {
            throw new TooLateToBookException("It's too late to book seats");
        }
        return true;
    }

    private boolean validateSeats(CreateBookingDto createBookingDto) {
        List<SeatDto> bookingSeats = convertBookingToSeatDtos(createBookingDto);
        List<SeatDto> availableSeats = prepareListAssumingBookingIsValid(createBookingDto);
        bookingSeats.forEach(seatDto -> {
            boolean isSeatValid = validateSeat(seatDto, availableSeats, createBookingDto.getScreeningId());
            if(!isSeatValid) {
                throw new UnallowedSeatBookingException("Requested booking not allowed");
            }
        });
        return true;
    }

    private List<SeatDto> prepareListAssumingBookingIsValid(CreateBookingDto createBookingDto) {
        List<SeatDto> bookingSeats = convertBookingToSeatDtos(createBookingDto);
        List<SeatDto> availableSeats = screeningService.getAvailableSeats(createBookingDto.getScreeningId());
        return availableSeats.stream()
                .filter(seat -> !bookingSeats.contains(seat))
                .collect(Collectors.toList());
    }

    private boolean validateSeat(SeatDto seatDto, List<SeatDto> availableSeats, long screeningId) {
        int rowSize = screeningService.getRowSize(screeningId);
        if(isLeftMargin(seatDto)) {
            return validateRightSide(seatDto, availableSeats);
        }
        if(isRightMargin(seatDto, rowSize)) {
            return validateLeftSide(seatDto, availableSeats);
        }

        return validateLeftSide(seatDto, availableSeats) && validateRightSide(seatDto, availableSeats);
    }

    private boolean validateLeftSide(SeatDto seatDto, List<SeatDto> availableSeats) {
        List<SeatDto> leftTwoSeats = List.of(
                SeatDto.builder().row(seatDto.getRow()).seat(seatDto.getSeat() - 1).build(),
                SeatDto.builder().row(seatDto.getRow()).seat(seatDto.getSeat() - 2).build()
        );
        if(availableSeats.contains(leftTwoSeats.get(0)) && !availableSeats.contains(leftTwoSeats.get(1)))
            return false;
        return true;

    }
    private boolean validateRightSide(SeatDto seatDto, List<SeatDto> availableSeats) {
        List<SeatDto> rightTwoSeats = List.of(
                SeatDto.builder().row(seatDto.getRow()).seat(seatDto.getSeat() + 1).build(),
                SeatDto.builder().row(seatDto.getRow()).seat(seatDto.getSeat() + 2).build()
        );

        if(availableSeats.contains(rightTwoSeats.get(0)) && !availableSeats.contains(rightTwoSeats.get(1)))
            return false;
        return true;    }

    private boolean isLeftMargin(SeatDto seatDto) {
        return seatDto.getSeat() == 0 || seatDto.getSeat() == 1;
    }

    private boolean isRightMargin(SeatDto seatDto, int rowSize) {
        return seatDto.getSeat() == rowSize - 1 || seatDto.getSeat() == rowSize - 2;
    }

    private List<SeatDto> convertBookingToSeatDtos(CreateBookingDto createBookingDto) {
        return createBookingDto.getTicketDtos().stream()
                .map(ticketDto -> {
                    return SeatDto.builder()
                            .row(ticketDto.getRow())
                            .seat(ticketDto.getPlace())
                            .build();
                }).collect(Collectors.toList());
    }
}
