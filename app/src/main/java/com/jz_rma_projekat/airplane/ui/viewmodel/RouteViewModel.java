package com.jz_rma_projekat.airplane.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jz_rma_projekat.airplane.database.entities.RouteEntity;
import com.jz_rma_projekat.airplane.ui.repositories.RouteRepository;

import java.util.List;

public class RouteViewModel extends AndroidViewModel {

    private final RouteRepository repository;
    private final LiveData<List<RouteEntity>> allRoutes;

    public RouteViewModel(@NonNull Application application) {
        super(application);
        repository = new RouteRepository(application);
        allRoutes = repository.getAllRoutes();
    }

    public LiveData<List<RouteEntity>> getAllRoutes() {
        return allRoutes;
    }

    public void insert(RouteEntity route) {
        repository.insert(route);
    }

    public void update(RouteEntity route) {
        repository.update(route);
    }

    public void delete(RouteEntity route) {
        repository.delete(route);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}

