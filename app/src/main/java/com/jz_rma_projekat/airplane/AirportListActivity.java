package com.jz_rma_projekat.airplane;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_rma_projekat.airplane.ui.adapters.AirportListAdapter;
import com.jz_rma_projekat.airplane.ui.viewmodel.AirportViewModel;

public class AirportListActivity extends AppCompatActivity {
    private AirportViewModel airportViewModel;
    private AirportListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_list);

        // Enable the up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 1️⃣ Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAirports);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2️⃣ Create adapter instance
        adapter = new AirportListAdapter();
        recyclerView.setAdapter(adapter);

        // 3️⃣ Get ViewModel
        airportViewModel = new ViewModelProvider(this).get(AirportViewModel.class);

        // 4️⃣ Observe LiveData
        airportViewModel.getAllAirports().observe(this, airports -> {
            if (airports != null) {
                adapter.submitList(airports);
            }
        });
    }
}

