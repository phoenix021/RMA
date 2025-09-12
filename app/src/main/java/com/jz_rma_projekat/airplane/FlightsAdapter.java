package com.jz_rma_projekat.airplane;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.FlightViewHolder> {

    private List<FlightData> flights;

    public FlightsAdapter(List<FlightData> flights) {
        this.flights = flights;
    }

    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_flight, parent, false);
        return new FlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        FlightData flight = flights.get(position);
        holder.tvFlightNumber.setText("Flight: " + flight.flight.toString());
        holder.tvRoute.setText(flight.departure.airport + " → " + flight.arrival.airport);
        holder.tvTime.setText("Departure: " + flight.departure.scheduled +
                "\nArrival: " + flight.arrival.scheduled);
    }

    @Override
    public int getItemCount() {
        return flights.size();
    }

    public static class FlightViewHolder extends RecyclerView.ViewHolder {
        TextView tvFlightNumber, tvRoute, tvTime;

        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFlightNumber = itemView.findViewById(R.id.tvFlightNumber);
            tvRoute = itemView.findViewById(R.id.tvRoute);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }

    // Optionally add a method to update data:
    public void updateFlights(List<FlightData> newFlights) {
        flights.clear();
        flights.addAll(newFlights);
        notifyDataSetChanged();
    }
}
