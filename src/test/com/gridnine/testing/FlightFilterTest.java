package com.gridnine.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightFilterTest {
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
    }

    //    тестируем метод 'filterFlightsAfterNow(List<Flight> ...)'
    @Test
    void shouldAddFlightAfterNow() {
        Segment segment = new Segment(now.plusSeconds(1), now.plusHours(2));
        List<Flight> flightsToFilter = List.of(new Flight(List.of(segment)));

        List<Flight> flights = FlightFilter.filterFlightsAfterNow(flightsToFilter);

        assertEquals(1, flights.size(), "ошибка при фильтрации вылета после текущего момента");
    }

    @Test
    void shouldFilterFlightListWithMoreThanOneSegmentsDepartAfterNow() {
        Segment segment1 = new Segment(now.plusSeconds(1), now.plusHours(2));
        Segment segment2 = new Segment(now.plusHours(3), now.plusHours(4));
        List<Flight> flightsToFilter = List.of(new Flight(List.of(segment1, segment2)));

        List<Flight> flights = FlightFilter.filterFlightsAfterNow(flightsToFilter);

        assertEquals(1, flights.size(), "ошибка фильтрации вылета со множеством сегментов " +
                "после текущего момента");
    }

    @Test
    void shouldFilterFlightListWithMoreThanOneFlightsDepartAfterNow() {
        Segment segment1 = new Segment(now.plusSeconds(1), now.plusHours(2));
        Segment segment2 = new Segment(now.plusHours(3), now.plusHours(4));

        Segment segment3 = new Segment(now.plusHours(5), now.plusHours(6));
        Segment segment4= new Segment(now.plusHours(7), now.plusHours(9));

        List<Flight> flightsToFilter = List.of(
                new Flight(List.of(segment3, segment4)),
                new Flight(List.of(segment1, segment2))
        );

        List<Flight> flights = FlightFilter.filterFlightsAfterNow(flightsToFilter);

        assertEquals(2, flights.size(), "ошибка фильтрации вылета после текущего момента");
    }

    @Test
    void shouldFilterFlightListWithMoreThanOneFlightsDepartAfterAndBeforeNow() {
        Segment segment1 = new Segment(now.plusSeconds(1), now.plusHours(2));
        Segment segment2 = new Segment(now.plusHours(3), now.plusHours(4));

        Segment segment3 = new Segment(now.minusSeconds(1), now.plusHours(2));
        Segment segment4= new Segment(now.plusHours(7), now.plusHours(9));

        List<Flight> flightsToFilter = List.of(
                new Flight(List.of(segment3, segment4)),
                new Flight(List.of(segment1, segment2))
        );

        List<Flight> flights = FlightFilter.filterFlightsAfterNow(flightsToFilter);

        assertEquals(1, flights.size(), "ошибка фильтрации множества вылетов после текущего момента");
    }

    @Test
    void shouldNotAddFlightBeforeNow() {
        Segment segment = new Segment(now.minusSeconds(1), now.plusHours(2));
        List<Flight> flightsToFilter = List.of(new Flight(List.of(segment)));

        List<Flight> flights = FlightFilter.filterFlightsAfterNow(flightsToFilter);

        assertEquals(0, flights.size(), "ошибка фильтрации вылета до текущего момента");
    }

    @Test
    void shouldFilterFlightListWithMoreThanOneFlightsDepartBeforeNow() {
        Segment segment1 = new Segment(now.minusSeconds(1), now.plusHours(2));
        Segment segment2 = new Segment(now.plusHours(3), now.plusHours(4));

        Segment segment3 = new Segment(now.minusHours(1), now.plusHours(2));
        Segment segment4= new Segment(now.plusHours(7), now.plusHours(9));

        List<Flight> flightsToFilter = List.of(
                new Flight(List.of(segment3, segment4)),
                new Flight(List.of(segment1, segment2))
        );

        List<Flight> flights = FlightFilter.filterFlightsAfterNow(flightsToFilter);

        assertEquals(0, flights.size(), "ошибка при фильтрации множества полетов до текущего момента ");
    }

    //    тестируем метод 'filterFlightsDepartArriveDateTime(List<Flight> ...)'
    @Test
    void shouldFilterFlightsWithCorrectDepartArriveDateTime() {
        Segment segment1 = new Segment(now.plusSeconds(1), now.plusHours(2));
        Segment segment2 = new Segment(now.plusHours(3), now.plusHours(4));

        Segment segment3 = new Segment(now.plusHours(5), now.plusHours(6));
        Segment segment4= new Segment(now.plusHours(7), now.plusHours(9));

        List<Flight> flightsToFilter = List.of(
                new Flight(List.of(segment3, segment4)),
                new Flight(List.of(segment1, segment2))
        );

        List<Flight> flights = FlightFilter.filterFlightsDepartArriveDateTime(flightsToFilter);

        assertEquals(2, flights.size(), "ошибка фильтрации корректных значений вылета-прилета");
    }

    @Test
    void shouldNotFilterFlightWithIncorrectDepartArriveDateTime() {
        Segment segment1 = new Segment(now.plusHours(8), now.plusHours(2));
        Segment segment2 = new Segment(now.plusHours(3), now.plusHours(4));

        Segment segment3 = new Segment(now.plusHours(5), now.plusHours(3));
        Segment segment4= new Segment(now.plusHours(7), now.plusHours(9));

        List<Flight> flightsToFilter = List.of(
                new Flight(List.of(segment3, segment4)),
                new Flight(List.of(segment1, segment2))
        );

        List<Flight> flights = FlightFilter.filterFlightsDepartArriveDateTime(flightsToFilter);

        assertEquals(0, flights.size(), "ошибка фильтрации не корректных значений вылета-прилета");
    }


    //    тестируем метод 'filterByAmountOfTimeOnEarth(List<Flight> ...)'
    @Test
    void shouldFilterWhenBeingOnTheEarthLessThanTwoHours() {
        Segment segment1 = new Segment(now.plusSeconds(1), now.plusHours(1));
        Segment segment2 = new Segment(now.plusMinutes(179), now.plusHours(4));

        Segment segment3 = new Segment(now.plusHours(5), now.plusHours(6));
        Segment segment4= new Segment(now.plusHours(7), now.plusHours(9));

        List<Flight> flightsToFilter = List.of(
                new Flight(List.of(segment3, segment4)),
                new Flight(List.of(segment1, segment2))
        );

        List<Flight> flights = FlightFilter.filterByAmountOfTimeOnEarth(flightsToFilter);

        assertEquals(2, flights.size(), "ошибка фильтрации вылетов при прибывании " +
                "меньше 2 часов на земле");
    }

    @Test
    void shouldFilterWhenBeingOnTheEarthMoreThanTwoHours() {
        Segment segment1 = new Segment(now.plusSeconds(1), now.plusHours(1));
        Segment segment2 = new Segment(now.plusMinutes(180), now.plusHours(4));

        Segment segment3 = new Segment(now.plusHours(5), now.plusHours(6));
        Segment segment4 = new Segment(now.plusHours(7), now.plusHours(10));
        Segment segment5 = new Segment(now.plusHours(11), now.plusHours(12));

        Segment segment6 = new Segment(now.plusDays(1), now.plusDays(2));
        Segment segment7 = new Segment(now.plusDays(3), now.plusDays(4));



        List<Flight> flightsToFilter = List.of(
                new Flight(List.of(segment1, segment2)),
                new Flight(List.of(segment3, segment4, segment5)),
                new Flight(List.of(segment6, segment7))
        );

        List<Flight> flights = FlightFilter.filterByAmountOfTimeOnEarth(flightsToFilter);

        assertEquals(0, flights.size(), "ошибка фильтрации вылетов при прибывании " +
                "больше 2 часов на земле");
    }
}