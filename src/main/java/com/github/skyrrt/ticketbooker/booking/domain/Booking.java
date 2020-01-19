package com.github.skyrrt.ticketbooker.booking.domain;

import javax.persistence.*;

import com.github.skyrrt.ticketbooker.ticket.domain.Ticket;
import com.github.skyrrt.ticketbooker.screening.domain.Screening;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn
    private Screening screening;
    @OneToMany(mappedBy = "booking")
    private List<Ticket> tickets;
}
