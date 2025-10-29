package com.jz_rma_projekat.airplane;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_rma_projekat.airplane.ui.adapters.AirportListAdapter;
import com.jz_rma_projekat.airplane.ui.viewmodel.AirportViewModel;



public class AirportListActivity extends AppCompatActivity {

    AirportViewModel airportViewModel;
    AirportListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewAirports);
        adapter = new AirportListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        airportViewModel = new ViewModelProvider(this).get(AirportViewModel.class);

        // Observe LiveData
        airportViewModel.getAllAirports().observe(this, airports -> {
            adapter.submitList(airports);
        });
    }
}
