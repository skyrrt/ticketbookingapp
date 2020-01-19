package com.github.skyrrt.ticketbooker.booking.domain;

import com.github.skyrrt.ticketbooker.booking.domain.dto.BookingResponseDto;
import com.github.skyrrt.ticketbooker.booking.domain.dto.CreateBookingDto;
import com.github.skyrrt.ticketbooker.screening.domain.Screening;
import com.github.skyrrt.ticketbooker.screening.domain.ScreeningService;
import com.github.skyrrt.ticketbooker.screening.domain.dto.SeatDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BookingService {
    private BookingRepository bookingRepository;
    private ScreeningService screeningService;

//    public BookingResponseDto bookSeats(CreateBookingDto createBookingDto) {
//
//    }


}
