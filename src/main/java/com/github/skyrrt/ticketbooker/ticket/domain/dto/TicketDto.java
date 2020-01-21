package com.github.skyrrt.ticketbooker.ticket.domain.dto;

import com.github.skyrrt.ticketbooker.ticket.domain.TicketType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketDto {
    private int row;
    private int place;
    private TicketType type;
}
