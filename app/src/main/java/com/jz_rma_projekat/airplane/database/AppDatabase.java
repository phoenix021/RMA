package com.jz_rma_projekat.airplane.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jz_rma_projekat.airplane.database.dao.AirportDao;
import com.jz_rma_projekat.airplane.database.entities.AirportEntity;

@Database(entities = {AirportEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AirportDao airportDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "aviation_db"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
