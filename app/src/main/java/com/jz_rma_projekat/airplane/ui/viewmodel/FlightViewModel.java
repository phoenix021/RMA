package com.jz_rma_projekat.airplane.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jz_rma_projekat.airplane.database.entities.FlightEntity;
import com.jz_rma_projekat.airplane.database.api_models.FlightResponse;
import com.jz_rma_projekat.airplane.ui.repositories.FlightRepository;

import java.util.List;

public class FlightViewModel extends ViewModel {

    private final FlightRepository flightRepository;

    private final MutableLiveData<FlightResponse> flightsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<FlightEntity>> allFlightsFromDbLiveData = new MutableLiveData<>();

    public FlightViewModel(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
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
}

