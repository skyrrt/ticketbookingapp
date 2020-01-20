package com.github.skyrrt.ticketbooker.booking.domain.dto;

import com.github.skyrrt.ticketbooker.ticket.domain.dto.TicketDto;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CreateBookingDto {
    private int screeningId;
    @Pattern(regexp = "(^[A-Z][a-z]{2,}$)", message = "Invalid name")
    private String name;
    @Pattern(regexp = "(^[A-Z][a-z]{2,}$)|(^[A-Z][a-z]{2,}-[A-Z][a-z]{2,}$)", message = "Invalid surname")
    private String surname;
    @Size(min = 1, message = "You have to book at least one seat")
    private List<TicketDto> ticketDtos;
}
