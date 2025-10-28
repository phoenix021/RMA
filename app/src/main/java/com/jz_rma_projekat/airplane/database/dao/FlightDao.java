package com.jz_rma_projekat.airplane.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.jz_rma_projekat.airplane.database.dto.FlightWithAirportsDto;

import java.util.List;

@Dao
public interface FlightDao {

    // Get all flights along with airport details (departure and arrival)
    @Query("SELECT flights.*, departure_airports.name AS departureAirportName, " +
            "arrival_airports.name AS arrivalAirportName " +
            "FROM flights " +
            "JOIN airports AS departure_airports ON flights.departureAirportId = departure_airports.id " +
            "JOIN airports AS arrival_airports ON flights.arrivalAirportId = arrival_airports.id")
    List<FlightWithAirportsDto> getAllFlightsWithAirportInfo();


}
