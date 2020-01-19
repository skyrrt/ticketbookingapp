package com.github.skyrrt.ticketbooker.screening.domain;

import com.github.skyrrt.ticketbooker.movie.Movie;
import com.github.skyrrt.ticketbooker.screening.domain.dto.ScreeningDateDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query("SELECT distinct s.movie FROM Screening s where s.screeningDate between :from AND :to")
    List<Movie> getAllBetweenDates(@Param("from") ZonedDateTime from, @Param("to") ZonedDateTime to);

    @Query("SELECT NEW com.github.skyrrt.ticketbooker.screening.domain.dto.ScreeningDateDto(s.id, s.screeningDate) from Screening s" +
            " where s.movie = :movie and s.screeningDate between :from AND :to")
    List<ScreeningDateDto> getMovieScreeningDates(@Param("movie") Movie movie, @Param("from") ZonedDateTime from, @Param("to") ZonedDateTime to);

    @Query("SELECT s.screeningDate FROM Screening s where s.id = :id")
    ZonedDateTime getScreeningTime(@Param("id") long id);

    @Query("SELECT s.room.seatsInRow FROM Screening s where s.id = :id")
    int getRowSize(@Param("id") long id);
}
