package com.jz_rma_projekat.airplane.ui.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_rma_projekat.airplane.R;
import com.jz_rma_projekat.airplane.database.entities.AirportEntity;

public class AirportViewHolder extends RecyclerView.ViewHolder {

    public final TextView tvAirportName;
    public final TextView tvAirportIata;

    public AirportViewHolder(@NonNull View itemView) {
        super(itemView);
        tvAirportName = itemView.findViewById(R.id.tvAirportName);
        tvAirportIata = itemView.findViewById(R.id.tvAirportIata);
    }

    public void bind(AirportEntity airport) {
        tvAirportName.setText(airport.getName());
        tvAirportIata.setText(airport.getIataCode());
    }
}
