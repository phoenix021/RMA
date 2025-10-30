package com.jz_rma_projekat.airplane.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.jz_rma_projekat.airplane.R;
import com.jz_rma_projekat.airplane.database.entities.AirportEntity;

import java.util.ArrayList;
import java.util.List;

public class AirportListAdapter extends ListAdapter<AirportEntity, AirportViewHolder> {

    public AirportListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public AirportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.airport_item, parent, false);
        return new AirportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AirportViewHolder holder, int position) {
        AirportEntity current = getItem(position);
        holder.bind(current);
    }

    public static final DiffUtil.ItemCallback<AirportEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<AirportEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull AirportEntity oldItem, @NonNull AirportEntity newItem) {
                    return oldItem.getIataCode().equals(newItem.getIataCode());
                }

                @Override
                public boolean areContentsTheSame(@NonNull AirportEntity oldItem, @NonNull AirportEntity newItem) {
                    return oldItem.getName().equals(newItem.getName()) &&
                            oldItem.getIataCode().equals(newItem.getIataCode());
                }
            };
}
