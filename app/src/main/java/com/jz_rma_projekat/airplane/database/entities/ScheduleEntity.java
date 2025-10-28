package com.jz_rma_projekat.airplane.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "schedules")
public class ScheduleEntity {

    @PrimaryKey
    @NonNull
    private String id;

    private String flightNumber;
    private String departureTime;
    private String arrivalTime;
    private String departureAirportId;
    private String arrivalAirportId;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public String getDepartureAirportId() { return departureAirportId; }
    public void setDepartureAirportId(String departureAirportId) { this.departureAirportId = departureAirportId; }

    public String getArrivalAirportId() { return arrivalAirportId; }
    public void setArrivalAirportId(String arrivalAirportId) { this.arrivalAirportId = arrivalAirportId; }
}

