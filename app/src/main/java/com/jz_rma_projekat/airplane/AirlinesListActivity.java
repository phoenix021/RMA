package com.jz_rma_projekat.airplane;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_rma_projekat.airplane.database.entities.AirlineEntity;
import com.jz_rma_projekat.airplane.database.entities.AirportEntity;
import com.jz_rma_projekat.airplane.ui.adapters.AirlineListAdapter;
import com.jz_rma_projekat.airplane.ui.adapters.AirportListAdapter;
import com.jz_rma_projekat.airplane.ui.viewmodel.AirlineViewModel;
import com.jz_rma_projekat.airplane.ui.viewmodel.AirportViewModel;

public class AirlinesListActivity extends AppCompatActivity {
    private AirlineViewModel airlineViewModel;
    private AirlineListAdapter adapter;
    private AirlineEntity selectedAirline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airline_list);

        // Enable the up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 1️⃣ Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAirlines);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2️⃣ Create adapter instance
        adapter = new AirlineListAdapter();
        recyclerView.setAdapter(adapter);

        // 3️⃣ Get ViewModel
        airlineViewModel = new ViewModelProvider(this).get(AirlineViewModel.class);

        // 4️⃣ Observe LiveData
        airlineViewModel.getAllAirlines().observe(this, airlines -> {
            if (airlines != null) {
                adapter.submitList(airlines);
            }
        });

        adapter.setOnAirlineClickListener(airline -> {
            Toast.makeText(this, "Selected: " + airline.getAirlineName(), Toast.LENGTH_SHORT).show();
            selectedAirline = airline;

            Intent intent = new Intent(this, AirlineDetailsActivity.class);
            intent.putExtra("airline", airline);
            startActivity(intent);
        });
    }
}

