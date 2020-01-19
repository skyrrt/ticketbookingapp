package com.github.skyrrt.ticketbooker.booking.domain.dto;

import com.github.skyrrt.ticketbooker.ticket.domain.dto.TicketDto;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CreateBookingDto {
    private int screeningId;
    @Pattern(regexp = "(^[A-Z][a-z]{2,}$)")
    private String name;
    @Pattern(regexp = "(^[A-Z][a-z]{2,}$)|(^[A-Z][a-z]{2,}-[A-Z][a-z]{2,}$)")
    private String surname;
    @Size(min = 1)
    private List<TicketDto> ticketDtos;
}
