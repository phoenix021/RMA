package com.jz_rma_projekat.airplane.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jz_rma_projekat.airplane.database.entities.ScheduleEntity;

import java.util.List;

@Dao
public interface ScheduleDao {

    // Insert or Update a Schedule
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateSchedule(ScheduleEntity schedule);

    // Insert multiple schedules
    @Insert
    void insertAllSchedules(List<ScheduleEntity> schedules);

    // Get all schedules
    @Query("SELECT * FROM schedules ORDER BY departureTime")
    List<ScheduleEntity> getAllSchedules();

    // Find schedules by route ID
    @Query("SELECT * FROM schedules WHERE id = :routeId")
    List<ScheduleEntity> findSchedulesByRoute(long routeId);

    // Find schedules by specific schedule ID
    @Query("SELECT * FROM schedules WHERE id = :scheduleId LIMIT 1")
    ScheduleEntity findScheduleById(long scheduleId);

    // Delete schedule by its ID
    @Query("DELETE FROM schedules WHERE id = :scheduleId")
    void deleteScheduleById(long scheduleId);

    // Delete all schedules
    @Query("DELETE FROM schedules")
    void deleteAllSchedules();

    // Insert or replace
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ScheduleEntity schedule);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ScheduleEntity> schedules);

    // Update
    @Update
    void update(ScheduleEntity schedule);

    // Delete
    @Delete
    void delete(ScheduleEntity schedule);

    // Delete all
    @Query("DELETE FROM schedules")
    void deleteAll();

    // Observed list of all schedules
    @Query("SELECT * FROM schedules ORDER BY departureTime")
    LiveData<List<ScheduleEntity>> getAllSchedulesLive();

    // Find schedules by flight number
    @Query("SELECT * FROM schedules WHERE flightNumber = :flightNumber")
    LiveData<List<ScheduleEntity>> findSchedulesByFlightLive(String flightNumber);

    // Find schedule by ID
    @Query("SELECT * FROM schedules WHERE id = :scheduleId LIMIT 1")
    LiveData<ScheduleEntity> findScheduleByIdLive(long scheduleId);
}

