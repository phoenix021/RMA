package com.jz_rma_projekat.airplane.ui.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.jz_rma_projekat.airplane.database.AppDatabase;
import com.jz_rma_projekat.airplane.database.dao.AirportDao;
import com.jz_rma_projekat.airplane.database.entities.AirportEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AirportRepository {

    private final AirportDao airportDao;
    private final LiveData<List<AirportEntity>> allAirports;
    private final ExecutorService executorService;

    public AirportRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        airportDao = db.airportDao();
        allAirports = airportDao.getAllAirportsLive();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<AirportEntity>> getAllAirports() {
        return allAirports;
    }

    public void insert(AirportEntity airport) {
        executorService.execute(() -> airportDao.insert(airport));
    }

    public void deleteAll() {
        executorService.execute(airportDao::deleteAll);
    }

    public void delete(AirportEntity airport) {
        executorService.execute(() -> airportDao.delete(airport));
    }

    public void update(AirportEntity airport) {
        executorService.execute(() -> airportDao.update(airport));
    }
}
