package com.github.skyrrt.ticketbooker.screening.domain;

import com.github.skyrrt.ticketbooker.booking.domain.BookingService;
import com.github.skyrrt.ticketbooker.movie.Movie;
import com.github.skyrrt.ticketbooker.screening.domain.dto.MovieScreeningDto;
import com.github.skyrrt.ticketbooker.screening.domain.dto.ScreeningDto;
import com.github.skyrrt.ticketbooker.screening.domain.dto.SeatDto;
import com.github.skyrrt.ticketbooker.screening.domain.exceptions.NoSuchScreeningExceptions;
import com.github.skyrrt.ticketbooker.ticket.domain.TicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@AllArgsConstructor
public class ScreeningService {
    private ScreeningRepository screeningRepository;
    private TicketService ticketService;

    public List<MovieScreeningDto> getMovieScreeningsInGivenInterval(String from, String to) {
        ZonedDateTime dateFrom = ZonedDateTime.parse(from);
        ZonedDateTime dateTo = ZonedDateTime.parse(to);

        List<Movie> movies =  screeningRepository.getAllBetweenDates(dateFrom,dateTo);
        return movies.stream().map(movie -> {
            return MovieScreeningDto.builder()
                    .movieTitle(movie.getMovieName())
                    .screenings(screeningRepository.getMovieScreeningDates(movie,dateFrom, dateTo))
                    .build();
        }).collect(Collectors.toList());
    }

    public ScreeningDto getScreeningDetails(long screeningId) {
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new NoSuchScreeningExceptions("There is no such screening with given id"));

        return ScreeningDto.builder()
                .availableSeats(getAvailableSeats(screening.getId()))
                .roomName(screening.getRoom().getRoomName())
                .screeningDate(screening.getScreeningDate())
                .build();

    }
    public List<SeatDto> getAvailableSeats(long screeningId) {
        Screening screening = screeningRepository.findById(screeningId).orElseThrow(() -> new NoSuchScreeningExceptions("There is no such screening with given id"));
        List<SeatDto> allSeats = getAllRoomSeats(screening.getRoom().getRowQuantity(), screening.getRoom().getSeatsInRow());
        List<SeatDto> reservedSeats = getReservedSeats(screening);
        return allSeats.stream()
                .filter(seat -> !reservedSeats.contains(seat))
                .collect(Collectors.toList());
    }

    public ZonedDateTime getScreeningDate(long screeningId) {
        return screeningRepository.getScreeningTime(screeningId);
    }

    private List<SeatDto> getAllRoomSeats(int rows, int places) {
        List<Integer> rowNumbers = IntStream.range(0,rows).boxed().collect(Collectors.toList());
        List<Integer> placeNumbers = IntStream.range(0,places).boxed().collect(Collectors.toList());

        return rowNumbers.stream()
                .flatMap(row -> placeNumbers.stream()
                                            .map(place -> createSeat(row, place))
                ).collect(Collectors.toList());
    }

    private List<SeatDto> getReservedSeats(Screening screening) {
        return ticketService.getBookedSeats(screening);
    }

    private SeatDto createSeat(int row, int seat) {
        return SeatDto.builder()
                .seat(seat)
                .row(row)
                .build();
    }
}
