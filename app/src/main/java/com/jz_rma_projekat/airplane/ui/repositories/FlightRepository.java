package com.jz_rma_projekat.airplane.ui.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jz_rma_projekat.airplane.database.AppDatabase;
import com.jz_rma_projekat.airplane.database.api_models.ApiResponse;
import com.jz_rma_projekat.airplane.database.dao.FlightDao;
import com.jz_rma_projekat.airplane.database.dto.FlightInfoDto;
import com.jz_rma_projekat.airplane.database.dto.FlightsDto;
import com.jz_rma_projekat.airplane.database.entities.FlightEntity;
import com.jz_rma_projekat.airplane.database.api_models.FlightResponse;
import com.jz_rma_projekat.airplane.network.AviationStackApi;
import com.jz_rma_projekat.airplane.network.RetrofitClient;
import com.jz_rma_projekat.airplane.utils.mappers.FlightMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightRepository {

    private static final String TAG = "JELENA FlightRepository";
    private final AviationStackApi aviationStackApi;
    private final FlightDao flightDao;
    private final ExecutorService executor;

    public FlightRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        this.aviationStackApi = RetrofitClient.getApi();
        this.flightDao = db.flightDao();
        executor = Executors.newSingleThreadExecutor();
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

    public LiveData<List<FlightEntity>> searchFlights(String origin, String destination, String date) {
        MutableLiveData<List<FlightEntity>> flightsLiveData = new MutableLiveData<>();

        // Make API call
        aviationStackApi.getFlightsByRoute(
                RetrofitClient.API_KEY,
                origin,
                destination
        ).enqueue(new Callback<ApiResponse<FlightInfoDto>>() {
            @Override
            public void onResponse(Call<ApiResponse<FlightInfoDto>> call, Response<ApiResponse<FlightInfoDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FlightInfoDto> dtoList = response.body().getData();
                    if (dtoList == null || dtoList.isEmpty()) {
                        Log.w(TAG, "No flights found for given criteria.");
                        flightsLiveData.postValue(Collections.emptyList());
                        return;
                    }

                    // Convert DTOs to entities
                    List<FlightEntity> flights = FlightMapper.flightsInfoDtoToEntityList(dtoList);

                    // Save to database in background
                    executor.execute(() -> {
                        for (FlightEntity flight : flights) {
                            Log.e("Jelena", "Added flight " + flight.getDepartureAirportId() +"->" + flight.getArrivalAirportId() + " flight to db");
                            flightDao.insert(flight);
                        }
                        flightsLiveData.postValue(flights);
                    });
                } else {
                    Log.e(TAG, "API Response error: " + response.message());
                    // fallback: get from local DB
                    executor.execute(() -> {
                        List<FlightEntity> cached = flightDao.searchFlightsSync(origin, destination, date);
                        flightsLiveData.postValue(cached);
                    });
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<FlightInfoDto>> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
                // fallback: use cached data
                executor.execute(() -> {
                    List<FlightEntity> cached = flightDao.searchFlightsSync(origin, destination, date);
                    flightsLiveData.postValue(cached);
                });
            }
        });

        return flightsLiveData;
    }
    public LiveData<List<FlightEntity>> getAllFlights() {
        MutableLiveData<List<FlightEntity>> allFlightsLiveData = new MutableLiveData<>();

        executor.execute(() -> {
            List<FlightEntity> cachedFlights = flightDao.getAllFlightsSync(); // synchronous DB fetch

            if (cachedFlights != null && !cachedFlights.isEmpty()) {
                // ✅ Use cached data
                Log.d(TAG, "Loaded flights from local DB (" + cachedFlights.size() + ")");
                allFlightsLiveData.postValue(cachedFlights);
            } else {
                // Only call API if DB is empty
                Log.d(TAG, "No flights in DB, fetching from API...");

                aviationStackApi.getFlightsByRoute(
                        RetrofitClient.API_KEY,
                        100,   // limit
                        0      // offset
                ).enqueue(new Callback<ApiResponse<FlightsDto>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<FlightsDto>> call, Response<ApiResponse<FlightsDto>> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                            List<FlightsDto> dtoList = response.body().getData();
                            if (dtoList.isEmpty()) {
                                Log.w(TAG, "API returned empty flight list");
                                allFlightsLiveData.postValue(Collections.emptyList());
                                return;
                            }

                            List<FlightEntity> flights = new ArrayList<>();
                            for (FlightsDto flightDto : dtoList) {
                                flights.add(FlightMapper.fullFlightsDtoToEntity(flightDto));
                            }

                            executor.execute(() -> {
                                flightDao.insertAll(flights);
                                Log.d(TAG, "Saved " + flights.size() + " flights to local DB");
                                allFlightsLiveData.postValue(flights);
                            });
                        } else {
                            Log.e(TAG, "API Response error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<FlightsDto>> call, Throwable t) {
                        Log.e(TAG, "API call failed: " + t.getMessage());
                        allFlightsLiveData.postValue(Collections.emptyList());
                    }
                });
            }
        });

        return allFlightsLiveData;
    }

    public LiveData<FlightEntity> getFlightById(long id) {
        return flightDao.getFlightById(id);
    }

}

