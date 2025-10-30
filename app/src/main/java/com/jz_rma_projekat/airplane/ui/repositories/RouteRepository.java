package com.jz_rma_projekat.airplane.ui.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.jz_rma_projekat.airplane.database.AppDatabase;
import com.jz_rma_projekat.airplane.database.dao.RouteDao;
import com.jz_rma_projekat.airplane.database.entities.RouteEntity;
import com.jz_rma_projekat.airplane.network.AviationStackApi;
import com.jz_rma_projekat.airplane.network.RetrofitClient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RouteRepository {
    private final RouteDao routeDao;
    private final LiveData<List<RouteEntity>> allRoutes;
    private final ExecutorService executorService;
    private final AviationStackApi aviationStackApi;

    public RouteRepository(Application application) {
        this.aviationStackApi = RetrofitClient.getApi();
        AppDatabase db = AppDatabase.getInstance(application);
        routeDao = db.routeDao();
        allRoutes = routeDao.getAllRoutesLive();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<RouteEntity>> getAllRoutes() {
        return allRoutes;
    }

    public void insert(RouteEntity route) {
        executorService.execute(() -> routeDao.insert(route));
    }

    public void update(RouteEntity route) {
        executorService.execute(() -> routeDao.update(route));
    }

    public void delete(RouteEntity route) {
        executorService.execute(() -> routeDao.delete(route));
    }

    public void deleteAll() {
        executorService.execute(routeDao::deleteAll);
    }
}

