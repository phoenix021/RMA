package com.jz_rma_projekat.airplane.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jz_rma_projekat.airplane.database.entities.AirportEntity;

import java.util.ArrayList;
import java.util.List;

public class AirportAutoCompleteAdapter extends ArrayAdapter<AirportEntity> {

    private Context context;
    private List<AirportEntity> airports;
    private List<AirportEntity> visibleAirports;

    public AirportAutoCompleteAdapter(@NonNull Context context, List<AirportEntity> airports) {
        super(context, android.R.layout.simple_dropdown_item_1line, airports);
        this.context = context;
        this.airports = airports;
        this.visibleAirports = new ArrayList<>(airports);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<AirportEntity> filtered = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filtered.addAll(airports);
                } else {
                    String pattern = constraint.toString().toLowerCase().trim();
                    for (AirportEntity airport : airports) {
                        if (airport.getName().toLowerCase().contains(pattern)
                                || airport.getIataCode().toLowerCase().contains(pattern)) {
                            filtered.add(airport);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filtered;
                results.count = filtered.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                visibleAirports.clear();
                if (results.values != null) {
                    visibleAirports.addAll((List<AirportEntity>) results.values);
                }
                notifyDataSetChanged();
            }
        };
    }

    public void updateAllAirports(List<AirportEntity> newAirports) {
        airports.clear();
        airports.addAll(newAirports);
        visibleAirports.clear();
        visibleAirports.addAll(newAirports);
        notifyDataSetChanged();
    }


    public void setAirports(List<AirportEntity> airports) {
        this.airports = airports;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d("JELENA AutoCompleteAdapter", "getView called for position: " + position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);

        // Defensive: make sure list is valid
        if (position < 0 || position >= visibleAirports.size()) {
            Log.e("JELENA", "Invalid position: " + position + ", visibleAirports size: " + visibleAirports.size());
            textView.setText("Invalid airport");
            return convertView;
        }

        AirportEntity airport = visibleAirports.get(position);

        if (airport == null) {
            Log.e("JELENA", "No airport found at position " + position);
            textView.setText("Unknown airport");
            return convertView;
        }

        // Safe string construction
        String name = airport.getName() != null ? airport.getName() : "Unknown Name";
        String iataCode = airport.getIataCode() != null ? airport.getIataCode() : "Unknown Code";
        String country = airport.getCountry() != null ? airport.getCountry() : "Unknown Country";

        String formatted = name + " (" + iataCode + ") - " + country;
        textView.setText(formatted);

        return convertView;
    }

    @Override
    public int getCount() {
        return visibleAirports.size(); // only show filtered items
    }

    @Nullable
    @Override
    public AirportEntity getItem(int position) {
        return visibleAirports.get(position);
    }
}
