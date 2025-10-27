package com.jz_rma_projekat.airplane.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
}
