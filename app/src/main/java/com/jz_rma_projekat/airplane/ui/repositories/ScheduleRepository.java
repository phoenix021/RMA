package com.jz_rma_projekat.airplane.ui.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.jz_rma_projekat.airplane.database.AppDatabase;
import com.jz_rma_projekat.airplane.database.dao.ScheduleDao;
import com.jz_rma_projekat.airplane.database.entities.ScheduleEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScheduleRepository {

    private final ScheduleDao scheduleDao;
    private final LiveData<List<ScheduleEntity>> allSchedules;
    private final ExecutorService executorService;

    public ScheduleRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        scheduleDao = db.scheduleDao();
        allSchedules = scheduleDao.getAllSchedulesLive();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<ScheduleEntity>> getAllSchedules() {
        return allSchedules;
    }

    public void insert(ScheduleEntity schedule) {
        executorService.execute(() -> scheduleDao.insert(schedule));
    }

    public void update(ScheduleEntity schedule) {
        executorService.execute(() -> scheduleDao.update(schedule));
    }

    public void delete(ScheduleEntity schedule) {
        executorService.execute(() -> scheduleDao.delete(schedule));
    }

    public void deleteAll() {
        executorService.execute(scheduleDao::deleteAll);
    }
}

