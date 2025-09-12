package com.jz_rma_projekat.airplane;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_rma_projekat.airplane.Flight;

import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {

    private List<Flight> flightList;

    // Konstruktor adaptera
    public FlightAdapter(List<Flight> flightList) {
        this.flightList = flightList;
    }

    // ViewHolder klasa
    public static class FlightViewHolder extends RecyclerView.ViewHolder {
        TextView routeText, airlineText, timeText;

        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);
            routeText = itemView.findViewById(R.id.routeText);
            routeText = itemView.findViewById(R.id.routeText);
            airlineText = itemView.findViewById(R.id.airlineText);
            timeText = itemView.findViewById(R.id.timeText);
        }
    }

    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the item layout (item_flight.xml)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_flight, parent, false);
        return new FlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        Flight flight = flightList.get(position);
        holder.routeText.setText(flight.getRoute());
        holder.airlineText.setText(flight.getAirline());
        holder.timeText.setText(flight.getTime());
    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }
}
