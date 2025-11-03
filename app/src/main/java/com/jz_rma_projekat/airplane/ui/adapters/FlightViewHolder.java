package com.jz_rma_projekat.airplane.ui.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_rma_projekat.airplane.R;
import com.jz_rma_projekat.airplane.database.entities.FlightEntity;
import com.jz_rma_projekat.airplane.database.entities.ScheduleEntity;

public class FlightViewHolder extends RecyclerView.ViewHolder {

    private TextView tvFlightNumber;
    private TextView tvDeparture;
    private TextView tvArrival;
    private TextView tvStatus;

    public FlightViewHolder(@NonNull View itemView) {
        super(itemView);
        tvFlightNumber = itemView.findViewById(R.id.tvFlightNumber);
        tvDeparture = itemView.findViewById(R.id.tvDeparture);
        tvArrival = itemView.findViewById(R.id.tvArrival);
        tvStatus = itemView.findViewById(R.id.tvStatus);
    }

    public void bind(FlightEntity flight) {
        if (flight != null) {
            tvFlightNumber.setText(flight.getFlightNumber());
            tvDeparture.setText(flight.getDepartureAirportId());
            tvArrival.setText(flight.getArrivalAirportId());
            tvStatus.setText(flight.getStatus() != null ? flight.getStatus() : "Unknown");
        }
    }
}

