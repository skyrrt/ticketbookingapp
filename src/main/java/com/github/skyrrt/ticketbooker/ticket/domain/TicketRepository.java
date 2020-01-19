package com.github.skyrrt.ticketbooker.ticket.domain;

import com.github.skyrrt.ticketbooker.screening.domain.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t from Ticket t where t.booking.screening = :screening")
    List<Ticket> findAllByScreening(@Param("screening") Screening screening);
}
