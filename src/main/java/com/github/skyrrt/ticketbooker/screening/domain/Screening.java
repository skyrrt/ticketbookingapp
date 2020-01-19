package com.github.skyrrt.ticketbooker.screening.domain;

import com.github.skyrrt.ticketbooker.movie.Movie;
import com.github.skyrrt.ticketbooker.room.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private ZonedDateTime screeningDate;
    @ManyToOne
    @JoinColumn
    private Room room;
    @ManyToOne
    @JoinColumn
    private Movie movie;
}
