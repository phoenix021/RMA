package com.jz_rma_projekat.airplane.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jz_rma_projekat.airplane.database.dao.AirlineDao;
import com.jz_rma_projekat.airplane.database.dao.AirportDao;
import com.jz_rma_projekat.airplane.database.dao.FlightDao;
import com.jz_rma_projekat.airplane.database.dao.RouteDao;
import com.jz_rma_projekat.airplane.database.dao.ScheduleDao;
import com.jz_rma_projekat.airplane.database.entities.AirlineEntity;
import com.jz_rma_projekat.airplane.database.entities.AirportEntity;
import com.jz_rma_projekat.airplane.database.entities.FlightEntity;
import com.jz_rma_projekat.airplane.database.entities.RouteEntity;
import com.jz_rma_projekat.airplane.database.entities.ScheduleEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AirportEntity.class, AirlineEntity.class, FlightEntity.class, RouteEntity.class, ScheduleEntity.class}, version = 7, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AirportDao airportDao();
    public abstract FlightDao flightDao();

    public abstract RouteDao routeDao();

    public abstract ScheduleDao scheduleDao();

    public abstract AirlineDao airlineDao();

    private static volatile AppDatabase INSTANCE;

    //private static final int NUMBER_OF_THREADS = 4;
    //public static final ExecutorService databaseWriteExecutor =
    //        Executors.newFixedThreadPool(NUMBER_OF_THREADS);

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
