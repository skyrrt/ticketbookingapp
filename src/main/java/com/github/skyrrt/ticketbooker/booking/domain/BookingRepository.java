package com.github.skyrrt.ticketbooker.booking.domain;

import com.github.skyrrt.ticketbooker.screening.domain.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByScreening(Screening screening);
}
