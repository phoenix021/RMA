package com.jz_rma_projekat.airplane.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "routes")
public class RouteEntity {

    @PrimaryKey
    @NonNull
    private String id;

    private String airlineIata;
    private String departureAirportIata;
    private String arrivalAirportIata;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAirlineIata() { return airlineIata; }
    public void setAirlineIata(String airlineIata) { this.airlineIata = airlineIata; }

    public String getDepartureAirportIata() { return departureAirportIata; }
    public void setDepartureAirportIata(String departureAirportIata) { this.departureAirportIata = departureAirportIata; }

    public String getArrivalAirportIata() { return arrivalAirportIata; }
    public void setArrivalAirportIata(String arrivalAirportIata) { this.arrivalAirportIata = arrivalAirportIata; }
}
