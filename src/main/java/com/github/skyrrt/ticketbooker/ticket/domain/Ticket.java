package com.github.skyrrt.ticketbooker.ticket.domain;

import com.github.skyrrt.ticketbooker.booking.domain.Booking;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int rowNum;
    private int seat;
    private BigDecimal price;
    @ManyToOne
    private Booking booking;
}
