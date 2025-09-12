package com.jz_rma_projekat.airplane;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private FlightAdapter adapter;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);

       // ChipGroup chipGroup = view.findViewById(R.id.flightFilters);

        //chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
        //    Chip chip = group.findViewById(checkedId);
       //     if (chip != null) {
       //         String selected = chip.getText().toString();
       //         // Do something with selected filter
       //     }
       // });

        recyclerView = view.findViewById(R.id.recyclerViewFlights);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Flight> mockFlights = Arrays.asList(
                new Flight("Belgrade → Paris", "Air Serbia", "12:30 PM"),
                new Flight("Zagreb → London", "British Airways", "3:00 PM"),
                new Flight("New York → Tokyo", "ANA", "9:00 AM")
        );

        adapter = new FlightAdapter(mockFlights);
        recyclerView.setAdapter(adapter);

        return view;
    }
}

