package com.jz_rma_projekat.airplane.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jz_rma_projekat.airplane.database.entities.ScheduleEntity;
import com.jz_rma_projekat.airplane.ui.repositories.ScheduleRepository;

import java.util.List;

public class ScheduleViewModel extends AndroidViewModel {

    private final ScheduleRepository repository;
    private final LiveData<List<ScheduleEntity>> allSchedules;

    public ScheduleViewModel(@NonNull Application application) {
        super(application);
        repository = new ScheduleRepository(application);
        allSchedules = repository.getAllSchedules();
    }

    public LiveData<List<ScheduleEntity>> getAllSchedules() {
        return allSchedules;
    }

    public void insert(ScheduleEntity schedule) {
        repository.insert(schedule);
    }

    public void update(ScheduleEntity schedule) {
        repository.update(schedule);
    }

    public void delete(ScheduleEntity schedule) {
        repository.delete(schedule);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
