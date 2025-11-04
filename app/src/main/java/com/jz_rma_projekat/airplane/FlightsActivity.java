package com.jz_rma_projekat.airplane;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_rma_projekat.airplane.ui.adapters.FlightListAdapter;
import com.jz_rma_projekat.airplane.ui.viewmodel.FlightViewModel;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_rma_projekat.airplane.R;
import com.jz_rma_projekat.airplane.database.entities.FlightEntity;
import com.jz_rma_projekat.airplane.ui.adapters.FlightListAdapter;
import com.jz_rma_projekat.airplane.ui.viewmodel.FlightViewModel;

import java.util.List;

    public class FlightsActivity extends AppCompatActivity {

        private FlightViewModel flightViewModel;
        private FlightListAdapter flightAdapter;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_flights);

            RecyclerView recyclerView = findViewById(R.id.recyclerViewFlights);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // ViewModel initialization
            flightViewModel = new ViewModelProvider(this).get(FlightViewModel.class);
            flightAdapter = new FlightListAdapter();
            recyclerView.setAdapter(flightAdapter);
            // Observe flight data
            flightViewModel.getAllFlights().observe(this, flights -> {
                if (flights != null && !flights.isEmpty()) {
                    flightAdapter.submitList(flights);
                    flightAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "No flights available", Toast.LENGTH_SHORT).show();
                }
            });

            flightAdapter.setOnFlightClickListener(flight -> {
                // You can open a new activity, dialog, or show a Toast
                String msg = "Flight: " + flight.getFlightNumber() +
                        "\nFrom: " + flight.getDepartureAirportId() +
                        "\nTo: " + flight.getArrivalAirportId() +
                        "\nStatus: " + flight.getStatus();
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();


                //Intent intent = new Intent(FlightsActivity.this, FlightDetailsActivity.class);
                //intent.putExtra(FlightDetailsActivity.EXTRA_FLIGHT_ID, flight.getId());
                //startActivity(intent);

                Intent intent = new Intent(FlightsActivity.this, FlightDetailsActivity.class);
                intent.putExtra("flight", flight); // flightEntity must implement Serializable or Parcelable
                startActivity(intent);
            });
        }
    }
