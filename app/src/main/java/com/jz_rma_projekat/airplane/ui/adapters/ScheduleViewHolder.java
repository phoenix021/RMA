package com.jz_rma_projekat.airplane.ui.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_rma_projekat.airplane.R;

public class ScheduleViewHolder extends RecyclerView.ViewHolder {

    public final TextView tvFlightNumber;
    public final TextView tvAirline;
    public final TextView tvScheduleRoute;
    public final TextView tvDepartureTime;
    public final TextView tvArrivalTime;

    public ScheduleViewHolder(@NonNull View itemView) {
        super(itemView);
        tvFlightNumber = itemView.findViewById(R.id.tvFlightNumber);
        tvAirline = itemView.findViewById(R.id.tvAirline);
        tvScheduleRoute = itemView.findViewById(R.id.tvScheduleRoute);
        tvDepartureTime = itemView.findViewById(R.id.tvDepartureTime);
        tvArrivalTime = itemView.findViewById(R.id.tvArrivalTime);
    }
}

