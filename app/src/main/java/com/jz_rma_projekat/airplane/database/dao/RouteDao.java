package com.jz_rma_projekat.airplane.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

    // Insert or replace
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RouteEntity route);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RouteEntity> routes);

    // Update route
    @Update
    void update(RouteEntity route);

    // Delete route
    @Delete
    void delete(RouteEntity route);

    // Delete all
    @Query("DELETE FROM routes")
    void deleteAll();

    // Observed list of all routes
    @Query("SELECT * FROM routes ORDER BY departureAirportIata, arrivalAirportIata")
    LiveData<List<RouteEntity>> getAllRoutesLive();

    // Find by departure airport
    @Query("SELECT * FROM routes WHERE departureAirportIata = :departureAirportIata")
    LiveData<List<RouteEntity>> findRoutesByDepartureAirport(String departureAirportIata);

    // Find by arrival airport
    @Query("SELECT * FROM routes WHERE arrivalAirportIata = :arrivalAirportIata")
    LiveData<List<RouteEntity>> findRoutesByArrivalAirport(String arrivalAirportIata);

    // Get one by both airports
    @Query("SELECT * FROM routes WHERE departureAirportIata = :departureAirportIata AND arrivalAirportIata = :arrivalAirportIata LIMIT 1")
    LiveData<RouteEntity> findRouteByAirports(String departureAirportIata, String arrivalAirportIata);
}

