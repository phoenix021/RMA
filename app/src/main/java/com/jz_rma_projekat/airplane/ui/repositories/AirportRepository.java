package com.jz_rma_projekat.airplane.ui.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jz_rma_projekat.airplane.database.AppDatabase;
import com.jz_rma_projekat.airplane.database.api_models.AirportsResponse;
import com.jz_rma_projekat.airplane.database.dao.AirportDao;
import com.jz_rma_projekat.airplane.database.dto.AirportDto;
import com.jz_rma_projekat.airplane.database.entities.AirportEntity;
import com.jz_rma_projekat.airplane.network.AviationStackApi;
import com.jz_rma_projekat.airplane.network.RetrofitClient;
import com.jz_rma_projekat.airplane.utils.mappers.AirportMapper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AirportRepository {

    private final AirportDao airportDao;
    private final LiveData<List<AirportEntity>> allAirports;
    private final ExecutorService executorService;
    private final AviationStackApi aviationStackApi;

    private final MutableLiveData<List<AirportEntity>> airportsLiveData = new MutableLiveData<>();

    public AirportRepository(Application application) {
        this.aviationStackApi = RetrofitClient.getApi();
        AppDatabase db = AppDatabase.getInstance(application);
        airportDao = db.airportDao();
        allAirports = airportDao.getAllAirportsLive();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<AirportEntity>> getAirports() {
        Executors.newSingleThreadExecutor().execute(() -> {
            boolean hasAny = airportDao.hasAny();
            if (hasAny) {
                List<AirportEntity> cachedAirports = airportDao.getAllAirports();
                airportsLiveData.postValue(cachedAirports);
                Log.d("AirportRepository", "✅ Loaded airports from local DB (" + cachedAirports.size() + ")");
            } else {
                Log.d("AirportRepository", "🌍 Fetching airports from API...");
                fetchAndSaveAllAirports();
            }
        });
        return airportsLiveData;
    }

    private void fetchAndSaveAllAirports() {
        fetchAirportsPage(0);
    }

    private void fetchAirportsPage(int offset) {
        Call<AirportsResponse> call = aviationStackApi.getAirports(RetrofitClient.API_KEY, 100, offset);

        call.enqueue(new Callback<AirportsResponse>() {
            @Override
            public void onResponse(Call<AirportsResponse> call, Response<AirportsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AirportsResponse data = response.body();
                    List<AirportDto> airports = data.getData();

                    if (airports != null && !airports.isEmpty()) {
                        List<AirportEntity> entities = AirportMapper.toEntityList(airports);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            airportDao.insertAll(entities);
                            Log.d("AirportRepository", "💾 Saved " + entities.size() + " airports to DB");

                            int nextOffset = offset + 1000;
                            if (nextOffset < data.getPagination().getTotal()) {
                                fetchAirportsPage(nextOffset);
                            } else {
                                airportsLiveData.postValue(airportDao.getAllAirports());
                                Log.d("AirportRepository", "✅ Finished fetching all airports");
                            }
                        });
                    }
                } else {
                    Log.e("AirportRepository", "❌ Failed response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AirportsResponse> call, Throwable t) {
                Log.e("AirportRepository", "💥 API call failed", t);
            }
        });
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

    public LiveData<AirportEntity> getAirportByIataCode(String airportIataCode) {
        return airportDao.getAirportByIataCode(airportIataCode);
    }

    public LiveData<AirportEntity> getAirportById(String airportId) {
        return airportDao.getAirportById(airportId);
    }
}
