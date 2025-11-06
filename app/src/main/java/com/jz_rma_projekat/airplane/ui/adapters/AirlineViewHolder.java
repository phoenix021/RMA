package com.jz_rma_projekat.airplane.ui.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_rma_projekat.airplane.R;
import com.jz_rma_projekat.airplane.database.entities.AirlineEntity;

public class AirlineViewHolder extends RecyclerView.ViewHolder {

    public final TextView tvAirlineName;
    public final TextView tvAirlineIata;

    public AirlineViewHolder(@NonNull View itemView) {
        super(itemView);
        tvAirlineName = itemView.findViewById(R.id.tvAirlineName);
        tvAirlineIata = itemView.findViewById(R.id.tvAirlineIata);
    }

    public void bind(AirlineEntity airport) {
        tvAirlineName.setText(airport.getAirlineName());
        tvAirlineIata.setText(airport.getIataCode());
    }
}
