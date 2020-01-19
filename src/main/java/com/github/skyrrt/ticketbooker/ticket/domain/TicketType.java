package com.github.skyrrt.ticketbooker.ticket.domain;

import java.math.BigDecimal;

public enum TicketType {
    ADULT(BigDecimal.valueOf(25.0)),
    STUDENT(BigDecimal.valueOf(18.0)),
    CHILD(BigDecimal.valueOf(12.50));

    private BigDecimal price;

    TicketType(BigDecimal price) {
        this.price = price;
    }
}
