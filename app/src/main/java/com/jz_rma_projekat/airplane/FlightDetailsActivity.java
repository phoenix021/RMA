package com.jz_rma_projekat.airplane;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.jz_rma_projekat.airplane.R;
import com.jz_rma_projekat.airplane.database.entities.FlightEntity;
import com.jz_rma_projekat.airplane.ui.viewmodel.FlightViewModel;

public class FlightDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_FLIGHT_ID = "flight_id";

    private FlightViewModel flightViewModel;

    private TextView tvFlightNumber, tvRoute, tvStatus, tvDepartureTime, tvArrivalTime, tvAirline;
    private TextView  tvFlightDate;
    private TextView  tvDepartureAirport, tvDepartureGate;
    private TextView  tvArrivalAirport, tvArrivalGate;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_details);

        // Find all views
        tvFlightNumber = findViewById(R.id.tvFlightNumber);
        tvFlightDate = findViewById(R.id.tvFlightDate);
        tvRoute = findViewById(R.id.tvRoute);
        tvStatus = findViewById(R.id.tvStatus);

        tvDepartureTime = findViewById(R.id.tvDepartureTime);
        tvDepartureAirport = findViewById(R.id.tvDepartureAirport);
        tvDepartureGate = findViewById(R.id.tvDepartureGate);

        tvArrivalTime = findViewById(R.id.tvArrivalTime);
        tvArrivalAirport = findViewById(R.id.tvArrivalAirport);
        tvArrivalGate = findViewById(R.id.tvArrivalGate);

        tvAirline = findViewById(R.id.tvAirline);

        // Get the FlightEntity passed from previous activity
        FlightEntity flight = (FlightEntity) getIntent().getSerializableExtra("flight");

        if (flight != null) {
            populateFlightDetails(flight);
        }

        flightViewModel = new ViewModelProvider(this).get(FlightViewModel.class);

        // Get flight ID from intent
        long flightId = getIntent().getLongExtra(EXTRA_FLIGHT_ID, -1);
        if (flightId != -1) {
            flightViewModel.getFlightById(flightId).observe(this, this::populateFlightDetails);
        }
    }

    private void populateFlightDetails(FlightEntity flight) {
        if (flight == null) return;

        // Flight general info
        tvFlightNumber.setText(flight.getFlightNumber());
        tvFlightDate.setText(flight.getFlightDate());
        tvRoute.setText(flight.getDepartureAirportId() + " ➜ " + flight.getArrivalAirportId());
        tvStatus.setText(flight.getStatus());

        // Departure info
        tvDepartureTime.setText(flight.getDepartureTime());
        tvDepartureAirport.setText(flight.getDepartureAirport() + " / " +
                flight.getDepartureIata() + " / " + flight.getDepartureIcao());
        tvDepartureGate.setText(flight.getDepartureGate() + " / " + flight.getDepartureTerminal());

        // Arrival info
        tvArrivalTime.setText(flight.getArrivalTime());
        tvArrivalAirport.setText(flight.getArrivalAirport() + " / " +
                flight.getArrivalIata() + " / " + flight.getArrivalIcao());
        tvArrivalGate.setText(flight.getArrivalGate() + " / " + flight.getArrivalTerminal());

        // Airline info
        tvAirline.setText(flight.getAirlineName() + " / " +
                flight.getAirlineIata() + " / " + flight.getAirlineIcao());
    }
}
