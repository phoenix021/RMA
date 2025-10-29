package com.jz_rma_projekat.airplane.ui.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_rma_projekat.airplane.R;
import com.jz_rma_projekat.airplane.database.entities.FlightEntity;
import com.jz_rma_projekat.airplane.database.entities.ScheduleEntity;

public class FlightViewHolder extends RecyclerView.ViewHolder {

    public final TextView tvRoute;
    //public final TextView tvTime;
    public final TextView tvAirline;

    public FlightViewHolder(@NonNull View itemView) {
        super(itemView);
        tvRoute = itemView.findViewById(R.id.tvScheduleRoute);
        //tvTime = itemView.findViewById(R.id.tvScheduleTime);
        tvAirline = itemView.findViewById(R.id.tvAirline);
    }

    public void bind(FlightEntity flight) {
        tvRoute.setText(flight.getDepartureAirportId() + " → " + flight.getArrivalAirportId());
        //tvTime.setText(flight.getDepartureTime() + " - " + flight.getArrivalTime());
        tvAirline.setText("Flight doesnt have a field airline");
    }
}

