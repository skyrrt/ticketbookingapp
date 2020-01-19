package com.github.skyrrt.ticketbooker.booking.domain;

import com.github.skyrrt.ticketbooker.booking.domain.dto.BookingResponseDto;
import com.github.skyrrt.ticketbooker.booking.domain.dto.CreateBookingDto;
import com.github.skyrrt.ticketbooker.screening.domain.Screening;
import com.github.skyrrt.ticketbooker.screening.domain.ScreeningService;
import com.github.skyrrt.ticketbooker.ticket.domain.Ticket;
import com.github.skyrrt.ticketbooker.ticket.domain.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BookingService {
    private TicketRepository ticketRepository;
    private BookingValidator bookingValidator;
    private ScreeningService screeningService;
    private BookingRepository bookingRepository;

    @Transactional
    public BookingResponseDto bookSeats(CreateBookingDto createBookingDto) {
        bookingValidator.validate(createBookingDto);
        Booking booking = Booking.builder()
                .screening(screeningService.getScreening(createBookingDto.getScreeningId()))
                .build();
        booking = bookingRepository.save(booking);
        saveReservedTickets(createBookingDto, booking);
        return createBookingResponse(createBookingDto, booking.getScreening());
    }

    public void saveReservedTickets(CreateBookingDto createBookingDto, Booking booking) {
        List<Ticket> tickets = createBookingDto.getTicketDtos().stream()
                .map(ticketDto -> Ticket.builder()
                        .booking(booking)
                        .price(ticketDto.getType().getPrice())
                        .rowNum(ticketDto.getRow())
                        .seat(ticketDto.getPlace())
                        .build()
                ).collect(Collectors.toList());
        ticketRepository.saveAll(tickets);
    }

    private BookingResponseDto createBookingResponse(CreateBookingDto createBookingDto, Screening screening) {
        return BookingResponseDto.builder()
                .expirationDate(screening.getScreeningDate().minusMinutes(15))
                .price(calculatePrice(createBookingDto))
                .build();
    }

    private BigDecimal calculatePrice(CreateBookingDto tickets) {
        return tickets.getTicketDtos().stream()
                .map(ticket -> ticket.getType().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
