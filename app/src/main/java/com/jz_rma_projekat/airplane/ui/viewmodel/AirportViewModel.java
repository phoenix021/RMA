package com.jz_rma_projekat.airplane.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jz_rma_projekat.airplane.database.entities.AirportEntity;
import com.jz_rma_projekat.airplane.ui.repositories.AirportRepository;

import java.util.List;

public class AirportViewModel extends AndroidViewModel {

    private final AirportRepository repository;
    private final LiveData<List<AirportEntity>> allAirports;

    public AirportViewModel(@NonNull Application application) {
        super(application);
        repository = new AirportRepository(application);
        allAirports = repository.getAirports();
    }

    public LiveData<List<AirportEntity>> getAllAirports() {
        return allAirports;
    }

    public void insert(AirportEntity airport) {
        repository.insert(airport);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void delete(AirportEntity airport) {
        repository.delete(airport);
    }

    public void update(AirportEntity airport) {
        repository.update(airport);
    }
}

