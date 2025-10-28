package com.jz_rma_projekat.airplane.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.jz_rma_projekat.airplane.database.entities.AirlineEntity;

import java.util.List;

@Dao
public interface AirlineDao {

    // Fetch all airlines
    @Query("SELECT * FROM airlines")
    List<AirlineEntity> getAllAirlines();

    // Insert a list of airlines
    @Insert
    void insertAirlines(List<AirlineEntity> airlines);

    // Insert a single airline
    @Insert
    void insertAirline(AirlineEntity airline);

    // Method to get the count of all airline records
    @Query("SELECT COUNT(*) FROM airlines")
    int getCount();

    // Method to check if any airline record exists
    @Query("SELECT EXISTS(SELECT 1 FROM airlines LIMIT 1)")
    boolean hasAny();

}
