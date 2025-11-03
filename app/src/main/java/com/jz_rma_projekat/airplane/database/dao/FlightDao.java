package com.jz_rma_projekat.airplane.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jz_rma_projekat.airplane.database.dto.FlightWithAirportsDto;
import com.jz_rma_projekat.airplane.database.entities.FlightEntity;

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

    // Insert a single flight
    @Insert
    void insert(FlightEntity entity);

    // Insert multiple flights at once
    @Insert
    void insertAll(List<FlightEntity> entities);

    // You can also add other queries like fetching flights
    @Query("SELECT * FROM flights WHERE id = :id")
    FlightEntity getFlightById(String id);

    @Query("SELECT * FROM flights")
    List<FlightEntity> getAllFlights();

    // ✅ Update a flight
    @Update
    void update(FlightEntity flight);

    // ✅ Delete a flight
    @Delete
    void delete(FlightEntity flight);

    // ✅ Delete all flights
    @Query("DELETE FROM flights")
    void deleteAll();

    // ✅ Get all flights (observed LiveData)
    @Query("SELECT * FROM flights ORDER BY departureTime ASC")
    LiveData<List<FlightEntity>> getAllFlightsLive();

    @Query("SELECT * FROM flights WHERE departureAirportId = :origin AND arrivalAirportId = :destination AND date(departureTime) = :date")
    List<FlightEntity> searchFlightsSync(String origin, String destination, String date);

    // ✅ Get all flights synchronously (non-LiveData)
    @Query("SELECT * FROM flights ORDER BY departureTime ASC")
    LiveData<List<FlightEntity>> getAllFlightsSync();

    // ✅ Find flights by flight number
    @Query("SELECT * FROM flights WHERE flightNumber = :flightNumber LIMIT 1")
    LiveData<FlightEntity> findFlightByNumber(String flightNumber);

    // ✅ Find flights departing from an airport
    @Query("SELECT * FROM flights WHERE departureAirportId = :departureAirportId ORDER BY departureTime ASC")
    LiveData<List<FlightEntity>> findFlightsByDepartureAirport(String departureAirportId);

    // ✅ Find flights arriving at an airport
    @Query("SELECT * FROM flights WHERE arrivalAirportId = :arrivalAirportId ORDER BY arrivalTime ASC")
    LiveData<List<FlightEntity>> findFlightsByArrivalAirport(String arrivalAirportId);

    // ✅ Find flights by both airports
    @Query("SELECT * FROM flights WHERE departureAirportId = :departureAirportId AND arrivalAirportId = :arrivalAirportId ORDER BY departureTime ASC")
    LiveData<List<FlightEntity>> findFlightsByRoute(String departureAirportId, String arrivalAirportId);

    // ✅ Find flights by status (e.g., “active”, “landed”, “cancelled”)
    @Query("SELECT * FROM flights WHERE status = :status ORDER BY departureTime ASC")
    LiveData<List<FlightEntity>> findFlightsByStatus(String status);

    @Query("SELECT * FROM flights WHERE departureAirportId = :origin AND arrivalAirportId = :destination AND date(departureTime) = :date")
    LiveData<List<FlightEntity>> searchFlights(String origin, String destination, String date);

}
