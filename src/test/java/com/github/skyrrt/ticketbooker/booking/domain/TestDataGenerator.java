package com.github.skyrrt.ticketbooker.booking.domain;

import com.github.skyrrt.ticketbooker.screening.domain.dto.SeatDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestDataGenerator {
    public static List<SeatDto> roomWithOneSeatTaken(int rows, int places, SeatDto seatDto) {
        List<Integer> rowNumbers = IntStream.range(0,rows).boxed().collect(Collectors.toList());
        List<Integer> placeNumbers = IntStream.range(0,places).boxed().collect(Collectors.toList());
        List<SeatDto> seatDtos = rowNumbers.stream()
                .flatMap(row -> placeNumbers
                        .stream()
                        .map(place -> SeatDto.builder().row(row).seat(place).build())
                ).collect(Collectors.toList());
        return seatDtos.stream().filter(seatDto1 ->!seatDto.equals(seatDto1)).collect(Collectors.toList());
    }
}
