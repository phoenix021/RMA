package com.jz_rma_projekat.airplane.ui.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jz_rma_projekat.airplane.database.dao.FlightDao;
import com.jz_rma_projekat.airplane.database.entities.FlightEntity;
import com.jz_rma_projekat.airplane.database.api_models.FlightResponse;
import com.jz_rma_projekat.airplane.network.AviationStackApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightRepository {

    private static final String TAG = "FlightRepository";
    private final AviationStackApi aviationStackApi;
    private final FlightDao flightDao;

    public FlightRepository(AviationStackApi aviationStackApi, FlightDao flightDao) {
        this.aviationStackApi = aviationStackApi;
        this.flightDao = flightDao;
    }

    // API call to get the flights
    public LiveData<FlightResponse> getFlightsFromApi(String apiKey) {
        MutableLiveData<FlightResponse> flightsLiveData = new MutableLiveData<>();

        aviationStackApi.getFlights(apiKey).enqueue(new Callback<FlightResponse>() {
            @Override
            public void onResponse(Call<FlightResponse> call, Response<FlightResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    flightsLiveData.setValue(response.body());
                } else {
                    Log.e(TAG, "API Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<FlightResponse> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
            }
        });

        return flightsLiveData;
    }

    // Insert flight data into Room database
    public void insertFlight(FlightEntity flightEntity) {
        // Insert flight into database asynchronously
        new Thread(() -> flightDao.insert(flightEntity)).start();
    }

    // Get all flights from the database
    public LiveData<List<FlightEntity>> getAllFlightsFromDb() {
        return flightDao.getAllFlightsLive();
    }
}

