package com.gridnine.testing;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        List<Flight> flightsAfterNow = FlightFilter.filterFlightsAfterNow(flights);
        System.out.println("фильтрация вылетов до текущего момента");
        System.out.println(flightsAfterNow);
        System.out.println();


        List<Flight> flightsDepartArriveValidated = FlightFilter.filterFlightsDepartArriveDateTime(flightsAfterNow);
        System.out.println("фильтрация сегментов корректной даты и времени вылета по отношению даты и времени прилета");
        System.out.println(flightsDepartArriveValidated);
        System.out.println();

        List<Flight> flightsAmountOfTimeOnEarthFiltered = FlightFilter.filterByAmountOfTimeOnEarth(
                flightsDepartArriveValidated
        );
        System.out.println("фильтрация по общей сумме времени нахождения на земле менее 2 часов");
        System.out.println(flightsAmountOfTimeOnEarthFiltered);
    }
}