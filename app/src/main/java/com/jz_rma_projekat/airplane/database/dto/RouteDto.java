package com.jz_rma_projekat.airplane.database.dto;

import com.google.gson.annotations.SerializedName;

public class RouteDto {

    @SerializedName("airline_iata")
    private String airlineIata;

    @SerializedName("departure_airport_iata")
    private String departureAirportIata;

    @SerializedName("arrival_airport_iata")
    private String arrivalAirportIata;

    // Constructor for DTO
    public RouteDto(String airlineIata, String departureAirportIata, String arrivalAirportIata) {
        this.airlineIata = airlineIata;
        this.departureAirportIata = departureAirportIata;
        this.arrivalAirportIata = arrivalAirportIata;
    }

    // Getters and setters
    public String getAirlineIata() { return airlineIata; }
    public void setAirlineIata(String airlineIata) { this.airlineIata = airlineIata; }

    public String getDepartureAirportIata() { return departureAirportIata; }
    public void setDepartureAirportIata(String departureAirportIata) { this.departureAirportIata = departureAirportIata; }

    public String getArrivalAirportIata() { return arrivalAirportIata; }
    public void setArrivalAirportIata(String arrivalAirportIata) { this.arrivalAirportIata = arrivalAirportIata; }
}
