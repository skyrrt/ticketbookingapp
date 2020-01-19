package com.github.skyrrt.ticketbooker.ticket.domain;

import com.github.skyrrt.ticketbooker.booking.domain.Booking;
import com.github.skyrrt.ticketbooker.booking.domain.dto.CreateBookingDto;
import com.github.skyrrt.ticketbooker.screening.domain.Screening;
import com.github.skyrrt.ticketbooker.screening.domain.dto.SeatDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketService {
    private TicketRepository ticketRepository;

    public List<SeatDto> getBookedSeats(Screening screening) {
        List<Ticket> tickets = ticketRepository.findAllByScreening(screening);

        return tickets.stream()
                .map(ticket -> {
                    return SeatDto.builder().seat(ticket.getSeat()).row(ticket.getRowNum()).build();
                }).collect(Collectors.toList());
    }
}
