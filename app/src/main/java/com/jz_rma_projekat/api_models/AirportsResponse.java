package com.jz_rma_projekat.api_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AirportsResponse {
    @SerializedName("data")
    private List<Airport> data;

    public List<Airport> getData() {
        return data;
    }

    public void setData(List<Airport> data) {
        this.data = data;
    }
}
