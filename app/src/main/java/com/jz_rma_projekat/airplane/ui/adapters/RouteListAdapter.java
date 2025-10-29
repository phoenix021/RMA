package com.jz_rma_projekat.airplane.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_rma_projekat.airplane.R;
import com.jz_rma_projekat.airplane.database.entities.RouteEntity;

import java.util.ArrayList;
import java.util.List;

public class RouteListAdapter extends RecyclerView.Adapter<RouteViewHolder> {
    private List<RouteEntity> routes = new ArrayList<>();

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.route_item, parent, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        holder.bind(routes.get(position));
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public void setRoutes(List<RouteEntity> newRoutes) {
        this.routes = newRoutes;
        notifyDataSetChanged();
    }
}
