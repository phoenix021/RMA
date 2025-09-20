package com.jz_rma_projekat.api_models;

import com.google.gson.annotations.SerializedName;

public class Airport {
    @SerializedName("airport_name")
    private String name;

    @SerializedName("iata_code")
    private String iataCode;

    @SerializedName("country_name")
    private String country;

    public String getDisplayName() {
        return name + " - " + iataCode;
    }

    public String getIataCode() {
        return iataCode;
    }

    public String getName() {
        return name;
    }
}
