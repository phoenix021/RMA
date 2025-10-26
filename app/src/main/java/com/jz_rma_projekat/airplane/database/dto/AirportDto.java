package com.jz_rma_projekat.airplane.database.dto;

import com.google.gson.annotations.SerializedName;

public class AirportDto {
    @SerializedName("airport_name")
    private String name;

    @SerializedName("iata_code")
    private String iataCode;

    @SerializedName("country_name")
    private String country;

    public AirportDto(){
        super();
    }

    public AirportDto(String name, String iataCode, String country){
        super();
        this.name = name;
        this.iataCode = iataCode;
        this.country = country;
    }

    public String getDisplayName() {
        return name + " - " + iataCode + "(" + country + ")";
    }

    public String getIataCode() {
        return iataCode;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public void setName(String name) {
        this.name = name;
    }
}
