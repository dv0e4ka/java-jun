package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightFilter {

    public static List<Flight> filterFlightsAfterNow(List<Flight> flights) {
        LocalDateTime now = LocalDateTime.now();
        return flights.stream()
                .filter(
                        flight -> flight.getSegments().get(0).getDepartureDate().isAfter(now)
                )
                .collect(Collectors.toList());
    }

    public static List<Flight> filterFlightsDepartArriveDateTime(List<Flight> flights) {
        return flights.stream()
                .filter(
                        flight -> {
                            for (Segment segment : flight.getSegments()) {
                                if (segment.getDepartureDate().isAfter(segment.getArrivalDate())) {
                                    return false;
                                }
                            }
                            return true;
                        }
                )
                .collect(Collectors.toList());
    }

    public static List<Flight> filterByAmountOfTimeOnEarth(List<Flight> flights) {
        List<Flight> updatedFlightList = new ArrayList<>();
        for (Flight flight : flights) {
            Duration duration = Duration.ZERO;
            LocalDateTime arrive = null;
            for (Segment segment : flight.getSegments()) {
                if (arrive == null) {
                    arrive = segment.getArrivalDate();
                    continue;
                }
                duration = duration.plus(Duration.between(arrive, segment.getDepartureDate()));

            }
            if (duration.compareTo(Duration.ofHours(2)) < 0) {
                updatedFlightList.add(flight);
            }
        }
        return  updatedFlightList;
    }
}
