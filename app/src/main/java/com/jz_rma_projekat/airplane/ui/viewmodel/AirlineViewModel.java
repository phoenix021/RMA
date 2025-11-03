package com.jz_rma_projekat.airplane.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jz_rma_projekat.airplane.database.entities.AirlineEntity;
import com.jz_rma_projekat.airplane.ui.repositories.AirlineRepository;

import java.util.List;

public class AirlineViewModel extends AndroidViewModel {

    private final AirlineRepository repository;
    private final LiveData<List<AirlineEntity>> airlines;

    public AirlineViewModel(@NonNull Application application) {
        super(application);
        repository = new AirlineRepository(application);
        airlines = repository.getAllAirlines();
    }

    public LiveData<List<AirlineEntity>> getAllAirlines() {
        return airlines;
    }
}

