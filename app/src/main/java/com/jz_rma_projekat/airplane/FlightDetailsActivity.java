package com.jz_rma_projekat.airplane;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.jz_rma_projekat.airplane.R;
import com.jz_rma_projekat.airplane.database.entities.FlightEntity;
import com.jz_rma_projekat.airplane.ui.viewmodel.AirlineViewModel;
import com.jz_rma_projekat.airplane.ui.viewmodel.AirportViewModel;
import com.jz_rma_projekat.airplane.ui.viewmodel.FlightViewModel;

public class FlightDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_FLIGHT_ID = "flight_id";

    private FlightViewModel flightViewModel;

    private TextView tvFlightNumber, tvRoute, tvStatus, tvDepartureTime, tvArrivalTime, tvAirline;
    private TextView  tvFlightDate;
    private TextView  tvDepartureAirport, tvDepartureGate;
    private TextView  tvArrivalAirport, tvArrivalGate;

    private Button btnShowAirlineDetails;
    private Button btnShowAirportDetails;

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

        btnShowAirlineDetails = findViewById(R.id.btnShowAirlineDetails);
        btnShowAirportDetails = findViewById(R.id.btnShowAirportDetails);

        // Get the FlightEntity passed from previous activity
        FlightEntity flight = (FlightEntity) getIntent().getSerializableExtra("flight");

        if (flight != null) {
            populateFlightDetails(flight);
        }
    }

    private void populateFlightDetails(FlightEntity flight) {
        if (flight == null) return;

        // Flight general info
        tvFlightNumber.setText("Flight number: " + flight.getFlightNumber());
        tvFlightDate.setText("Flight date: "+flight.getFlightDate());
        tvRoute.setText("Route: " +flight.getDepartureAirportId() + " ➜ " + flight.getArrivalAirportId());
        tvStatus.setText(flight.getStatus());

        // Departure info
        tvDepartureTime.setText("Departure Time: " + flight.getDepartureTime());
        tvDepartureAirport.setText("Departure Airport: "+flight.getDepartureAirport() + " / " +
                flight.getDepartureIata() + " / " + flight.getDepartureIcao());
        tvDepartureGate.setText("Departure Gate: "+flight.getDepartureGate() + " / " + flight.getDepartureTerminal());

        // Arrival info
        tvArrivalTime.setText("Arrival Time: " + flight.getArrivalTime());
        tvArrivalAirport.setText("Arrival Airport: "+flight.getArrivalAirport() + " / " +
                flight.getArrivalIata() + " / " + flight.getArrivalIcao());
        tvArrivalGate.setText("Arrival Gate: " +flight.getArrivalGate() + " / " + flight.getArrivalTerminal());

        // Airline info
        tvAirline.setText("Airline: " + flight.getAirlineName() + " / " +
                flight.getAirlineIata() + " / " + flight.getAirlineIcao());

        if (flight.getAirlineName() != null){
            btnShowAirlineDetails.setOnClickListener(v -> {
                AirlineViewModel airportViewModel = new ViewModelProvider(this).get(AirlineViewModel.class);
                // 4️⃣ Observe LiveData
                airportViewModel.getAirlineByName(flight.getAirlineName()).observe(this, airline-> {
                    if (airline != null && !airline.getAirlineName().isEmpty()) {
                        Intent intent = new Intent(this, AirlineDetailsActivity.class);
                        intent.putExtra("airline", airline);
                        startActivity(intent);
                    }
                });
            });
        }

        if (flight.getDepartureIata() != null && !flight.getDepartureIata().isEmpty()){
            btnShowAirportDetails.setOnClickListener(v -> {
                AirportViewModel airportViewModel = new ViewModelProvider(this).get(AirportViewModel.class);
                airportViewModel.getAirportByIataCode(flight.getDepartureIata()).observe(this, airport -> {
                    if (airport != null ) {
                        Log.e("JELENA", "airport found "+ airport.getIataCode());
                        Intent intent = new Intent(this, AirportDetailsActivity.class);
                        intent.putExtra("airport", airport);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Airport details are not available at the moment", Toast.LENGTH_LONG).show();
                        Log.e("JELENA", "airport not found "+ flight.getDepartureIata());
                    }

                });
            });
        }
    }
}
