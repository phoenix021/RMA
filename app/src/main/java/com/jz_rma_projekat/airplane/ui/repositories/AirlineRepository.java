package com.jz_rma_projekat.airplane.ui.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jz_rma_projekat.airplane.database.AppDatabase;
import com.jz_rma_projekat.airplane.database.api_models.AirlineResponse;
import com.jz_rma_projekat.airplane.database.dao.AirlineDao;
import com.jz_rma_projekat.airplane.database.entities.AirlineEntity;
import com.jz_rma_projekat.airplane.network.AviationStackApi;
import com.jz_rma_projekat.airplane.network.RetrofitClient;
import com.jz_rma_projekat.airplane.utils.mappers.AirlineMapper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AirlineRepository {
    private final AirlineDao airlineDao;
    private final AviationStackApi api;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final MutableLiveData<List<AirlineEntity>> airlinesLiveData = new MutableLiveData<>();

    public AirlineRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        airlineDao = db.airlineDao();
        api = RetrofitClient.getApi();
    }

    public LiveData<List<AirlineEntity>> getAllAirlines() {
        executor.execute(() -> {
            boolean hasAny = airlineDao.hasAny();
            if (hasAny) {
                List<AirlineEntity> cached = airlineDao.getAllAirlines();
                airlinesLiveData.postValue(cached);
                Log.d("AirlineRepository", "Loaded " + cached.size() + " airlines from DB");
            } else {
                Log.d("AirlineRepository", "Fetching airlines from API...");
                fetchAndSaveAllAirlines();
            }
        });
        return airlinesLiveData;
    }

    private void fetchAndSaveAllAirlines() {
        Call<AirlineResponse> call = api.getAirlines(RetrofitClient.API_KEY, 100, 0);
        call.enqueue(new Callback<AirlineResponse>() {
            @Override
            public void onResponse(Call<AirlineResponse> call, Response<AirlineResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    List<AirlineEntity> entities = AirlineMapper.toEntityList(response.body().getData());
                    executor.execute(() -> {
                        airlineDao.insertAirlines(entities);
                        Log.d("AirlineRepository", "💾 Inserted " + entities.size() + " airlines");
                        airlinesLiveData.postValue(entities);
                    });
                } else {
                    Log.e("AirlineRepository", "API failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AirlineResponse> call, Throwable t) {
                Log.e("AirlineRepository", "API error", t);
            }
        });
    }

    public LiveData<AirlineEntity> getAirlineByName(String airlineName) {
        return airlineDao.getAirlineByName(airlineName);
    }
}

