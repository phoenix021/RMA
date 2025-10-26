package com.jz_rma_projekat.airplane.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.jz_rma_projekat.airplane.database.dao.AirportDao;
import com.jz_rma_projekat.airplane.database.entities.AirportEntity;

@Database(entities = {AirportEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AirportDao airportDao();
}
