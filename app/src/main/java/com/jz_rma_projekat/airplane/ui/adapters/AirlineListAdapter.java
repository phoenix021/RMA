package com.jz_rma_projekat.airplane.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.jz_rma_projekat.airplane.R;
import com.jz_rma_projekat.airplane.database.entities.AirlineEntity;

public class AirlineListAdapter extends ListAdapter<AirlineEntity, AirlineViewHolder> {

    public AirlineListAdapter() {
        super(DIFF_CALLBACK);
    }

    public interface OnAirlineClickListener {
        void onAirlineClick(AirlineEntity airline);
    }

    private OnAirlineClickListener listener;


    public void setOnAirlineClickListener(OnAirlineClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AirlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.airline_item, parent, false);
        return new AirlineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AirlineViewHolder holder, int position) {
        AirlineEntity current = getItem(position);
        holder.bind(current);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAirlineClick(current);
            }
        });
    }

    public static final DiffUtil.ItemCallback<AirlineEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<AirlineEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull AirlineEntity oldItem, @NonNull AirlineEntity newItem) {
                    return oldItem.getIataCode().equals(newItem.getIataCode());
                }

                @Override
                public boolean areContentsTheSame(@NonNull AirlineEntity oldItem, @NonNull AirlineEntity newItem) {
                    return oldItem.getAirlineName().equals(newItem.getAirlineName()) &&
                            oldItem.getIataCode().equals(newItem.getIataCode());
                }
            };
}
