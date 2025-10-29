package com.jz_rma_projekat.airplane.ui.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jz_rma_projekat.airplane.database.entities.RouteEntity;

import com.jz_rma_projekat.airplane.R;

public class RouteViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvDepartureCode;
    private final TextView tvArrivalCode;
    private final TextView tvRouteInfo;
    private final TextView tvAirline;
    private final TextView tvCountryInfo;

    public RouteViewHolder(@NonNull View itemView) {
        super(itemView);
        tvDepartureCode = itemView.findViewById(R.id.tvDepartureCode);
        tvArrivalCode = itemView.findViewById(R.id.tvArrivalCode);
        tvRouteInfo = itemView.findViewById(R.id.tvRouteInfo);
        tvAirline = itemView.findViewById(R.id.tvAirline);
        tvCountryInfo = itemView.findViewById(R.id.tvCountryInfo);
    }

    public void bind(RouteEntity route) {
        tvDepartureCode.setText(route.getDepartureAirportIata());
        tvArrivalCode.setText(route.getArrivalAirportIata());

        String routeInfo = route.getDepartureAirportIata() + " → " + route.getArrivalAirportIata();
        tvRouteInfo.setText(routeInfo);

        if (route.getAirlineIata() != null) {
            tvAirline.setText("Operated by: " + route.getAirlineIata());
        } else {
            tvAirline.setText("Operated by: N/A");
        }

        tvCountryInfo.setText("Departure country not contained in route?" + " → " + "nor the arrival?");
    }
}
