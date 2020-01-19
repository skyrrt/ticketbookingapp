package com.github.skyrrt.ticketbooker.booking.domain;

import com.github.skyrrt.ticketbooker.booking.domain.dto.CreateBookingDto;
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
public class BookingValidator {

    private void validate(CreateBookingDto createBookingDto, int rows, int places) {

    }

//    private boolean validateIfUserIsAllowedToBook(long screeningId) {
//        ZonedDateTime screeningDate = screeningService.getScreeningDate(screeningId);
//        ZonedDateTime bookingTime = ZonedDateTime.now(ZoneId.systemDefault());
//        return screeningDate.minusMinutes(15).isAfter(bookingTime);
//    }
//
//    private boolean validateSeats(CreateBookingDto createBookingDto) {
//        List<SeatDto> bookingSeats = convertBookingToSeatDtos(createBookingDto);
//        List<SeatDto> availableSeats = screeningService.getAvailableSeats(createBookingDto.getScreeningId());
//    }
//
//    private boolean validateSeat(SeatDto seatDto, List<SeatDto> availableSeats) {
//        if(isLeftMargin(seatDto)) {
//            return validateRightSide();
//        }
//    }
//
//    private boolean validateLeftSide(SeatDto seatDto, List<SeatDto> availableSeats) {
//
//    }
//    private boolean validateRightSide(SeatDto seatDto, List<SeatDto> availableSeats) {
//
//    }

    private boolean isLeftMargin(SeatDto seatDto) {
        return seatDto.getSeat() == 0;
    }

    private boolean isRightMargin(SeatDto seatDto, int rowSize) {
        return seatDto.getSeat() == rowSize - 1;
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
