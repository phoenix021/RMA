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
}
