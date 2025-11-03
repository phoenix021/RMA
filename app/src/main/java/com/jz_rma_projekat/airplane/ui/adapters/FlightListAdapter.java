package com.jz_rma_projekat.airplane.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.jz_rma_projekat.airplane.R;
import com.jz_rma_projekat.airplane.database.entities.FlightEntity;

public class FlightListAdapter extends ListAdapter<FlightEntity, FlightViewHolder> {

    private OnFlightClickListener listener;

    // Interface for click events
    public interface OnFlightClickListener {
        void onFlightClick(FlightEntity flight);
    }

    // Set listener
    public void setOnFlightClickListener(OnFlightClickListener listener) {
        this.listener = listener;
    }

    public FlightListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flight_item, parent, false);
        return new FlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        FlightEntity current = getItem(position);
        holder.bind(current);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFlightClick(current);
            }
        });
    }

    public static final DiffUtil.ItemCallback<FlightEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<FlightEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull FlightEntity oldItem, @NonNull FlightEntity newItem) {
                    return oldItem.getFlightNumber().equals(newItem.getFlightNumber());
                }

                @Override
                public boolean areContentsTheSame(@NonNull FlightEntity oldItem, @NonNull FlightEntity newItem) {
                    return oldItem.getFlightNumber().equals(newItem.getFlightNumber()) &&
                            oldItem.getDepartureAirportId().equals(newItem.getDepartureAirportId()) &&
                            oldItem.getArrivalAirportId().equals(newItem.getArrivalAirportId()) &&
                            oldItem.getDepartureTime().equals(newItem.getDepartureTime()) &&
                            oldItem.getArrivalTime().equals(newItem.getArrivalTime());
                }

            };
}
