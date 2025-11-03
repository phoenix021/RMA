package com.jz_rma_projekat.airplane.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jz_rma_projekat.airplane.database.entities.AirportEntity;
import com.jz_rma_projekat.airplane.database.entities.FlightEntity;
import com.jz_rma_projekat.airplane.database.api_models.FlightResponse;
import com.jz_rma_projekat.airplane.ui.repositories.AirportRepository;
import com.jz_rma_projekat.airplane.ui.repositories.FlightRepository;

import java.util.List;

public class FlightViewModel extends AndroidViewModel {

    private final FlightRepository flightRepository;

    private final LiveData<List<FlightEntity>> allFlights = null;

    private final MutableLiveData<FlightResponse> flightsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<FlightEntity>> allFlightsFromDbLiveData = new MutableLiveData<>();

    public FlightViewModel(@NonNull Application application) {
        super(application);
        this.flightRepository = new FlightRepository(application);
        //this.allFlights = flightRepository.getAllFlightsFromDb();
    }

    // Fetch flights from API
    public void fetchFlightsFromApi(String apiKey) {
        flightRepository.getFlightsFromApi(apiKey).observeForever(flightsLiveData::setValue);
    }

    // Fetch all flights from the Room database
    public void fetchFlightsFromDb() {
        flightRepository.getAllFlightsFromDb().observeForever(allFlightsFromDbLiveData::setValue);
    }

    // Insert flight into the database
    public void insertFlight(FlightEntity flightEntity) {
        flightRepository.insertFlight(flightEntity);
    }

    // LiveData for flights from API
    public LiveData<FlightResponse> getFlightsLiveData() {
        return flightsLiveData;
    }

    // LiveData for flights from DB
    public LiveData<List<FlightEntity>> getAllFlightsFromDbLiveData() {
        return allFlightsFromDbLiveData;
    }

    public LiveData<List<FlightEntity>> searchFlights(AirportEntity origin, AirportEntity destination, String date) {
        return flightRepository.searchFlights(origin.getIataCode(), destination.getIataCode(), date);
    }

    public LiveData<List<FlightEntity>> getAllFlights() {
        return flightRepository.getAllFlights();
    }
}

