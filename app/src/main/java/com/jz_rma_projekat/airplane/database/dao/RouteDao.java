package com.jz_rma_projekat.airplane.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jz_rma_projekat.airplane.database.entities.RouteEntity;

import java.util.List;

@Dao
public interface RouteDao {

    // Insert or Update a Route
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateRoute(RouteEntity route);

    // Insert multiple routes
    @Insert
    void insertAllRoutes(List<RouteEntity> routes);

    // Get all routes
    @Query("SELECT * FROM routes ORDER BY departureAirportIata, arrivalAirportIata")
    List<RouteEntity> getAllRoutes();

    // Find routes by departure airport
    @Query("SELECT * FROM routes WHERE departureAirportIata = :departureAirportId")
    List<RouteEntity> findRoutesByDepartureAirport(long departureAirportId);

    // Find routes by arrival airport
    @Query("SELECT * FROM routes WHERE arrivalAirportIata = :arrivalAirportId")
    List<RouteEntity> findRoutesByArrivalAirport(long arrivalAirportId);

    // Get route by a pair of airports
    @Query("SELECT * FROM routes WHERE departureAirportIata = :departureAirportId AND arrivalAirportIata = :arrivalAirportId")
    RouteEntity findRouteByAirports(long departureAirportId, long arrivalAirportId);

    // Delete route by its ID
    @Query("DELETE FROM routes WHERE id = :routeId")
    void deleteRouteById(long routeId);

    // Delete all routes
    @Query("DELETE FROM routes")
    void deleteAllRoutes();
}

