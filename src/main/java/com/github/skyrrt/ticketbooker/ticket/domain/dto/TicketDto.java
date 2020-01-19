package com.github.skyrrt.ticketbooker.ticket.domain.dto;

import com.github.skyrrt.ticketbooker.ticket.domain.TicketType;
import lombok.Data;

@Data
public class TicketDto {
    private int row;
    private int place;
    private TicketType type;
}
