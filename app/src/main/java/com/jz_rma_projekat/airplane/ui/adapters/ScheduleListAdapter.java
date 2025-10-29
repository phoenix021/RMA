package com.jz_rma_projekat.airplane.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.jz_rma_projekat.airplane.R;
import com.jz_rma_projekat.airplane.database.entities.ScheduleEntity;

public class ScheduleListAdapter extends ListAdapter<ScheduleEntity, ScheduleViewHolder> {

    public ScheduleListAdapter(@NonNull DiffUtil.ItemCallback<ScheduleEntity> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_item, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        ScheduleEntity schedule = getItem(position);

        holder.tvFlightNumber.setText(schedule.getFlightNumber());
        holder.tvAirline.setText(schedule.getId() + " schedule has no airline to show");
        holder.tvScheduleRoute.setText(schedule.getDepartureAirportId() + " → " + schedule.getArrivalAirportId());
        holder.tvDepartureTime.setText(schedule.getDepartureTime());
        holder.tvArrivalTime.setText(schedule.getArrivalTime());
    }

    public static class ScheduleDiff extends DiffUtil.ItemCallback<ScheduleEntity> {
        @Override
        public boolean areItemsTheSame(@NonNull ScheduleEntity oldItem, @NonNull ScheduleEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ScheduleEntity oldItem, @NonNull ScheduleEntity newItem) {
            return oldItem.equals(newItem); // Make sure ScheduleEntity implements equals() properly
        }
    }
}
