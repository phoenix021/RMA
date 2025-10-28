package com.jz_rma_projekat.airplane.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jz_rma_projekat.airplane.database.entities.AirportEntity;

import java.util.List;

@Dao
public interface AirportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AirportEntity> airports);

    @Query("SELECT * FROM airports")
    List<AirportEntity> getAllAirports();

    @Query("DELETE FROM airports")
    void clear();

    @Query("SELECT COUNT(*) FROM airports")
    int getCount();

    // Optional: check if there’s at least one row
    @Query("SELECT EXISTS(SELECT 1 FROM airports LIMIT 1)")
    boolean hasAny();

    @Query("SELECT DISTINCT country FROM airports ORDER BY country ASC")
    List<String> getAllCountries();

    @Query("SELECT * FROM airports WHERE country = :country ORDER BY name ASC")
    List<AirportEntity> getAirportsByCountry(String country);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(AirportEntity airport);

    @Update
    void updateAirport(AirportEntity airport);

    // Delete an airport by its entity
    @Delete
    void delete(AirportEntity airport);

    // Delete by primary key
    @Query("DELETE FROM airports WHERE id = :airportId")
    void deleteById(long airportId);

    // Delete all airports
    @Query("DELETE FROM airports")
    void deleteAllAirports();

    @Query("SELECT * FROM airports WHERE name LIKE :name LIMIT 1")
    AirportEntity findByName(String name);

    @Query("SELECT * FROM airports WHERE country = :country ORDER BY name ASC")
    List<AirportEntity> findByCountry(String country);

    // Find all airports with partial name match (case-insensitive)
    @Query("SELECT * FROM airports WHERE name LIKE '%' || :partialName || '%'")
    List<AirportEntity> findAirportsWithPartialName(String partialName);

    // Find airport by its IATA code
    @Query("SELECT * FROM airports WHERE iataCode = :iataCode LIMIT 1")
    AirportEntity findByIataCode(String iataCode);

    // Count airports by country
    @Query("SELECT COUNT(*) FROM airports WHERE country = :country")
    int countAirportsInCountry(String country);

    // Get the top 5 airports with the most flights
   // @Query("SELECT airports.name, COUNT(flights.flight_number) AS flight_count " +
   //         "FROM airports " +
   //         "JOIN flights ON airports.id = flights.departureAirportId " +
   //         "GROUP BY airports.airportName " +
   //         "ORDER BY flight_count DESC " +
   //         "LIMIT 5")
   // List<AirportFlightCount> getTopAirportsByFlightCount();

    // Find multiple airports by their IDs
    @Query("SELECT * FROM airports WHERE id IN (:airportIds)")
    List<AirportEntity> findAirportsByIds(List<Long> airportIds);
}
