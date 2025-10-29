package com.jz_rma_projekat.airplane.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "flights")
public class FlightEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id; // Flight ID (e.g., flight number or a unique identifier)

    private String flightNumber;
    private String departureTime;
    private String arrivalTime;
    private String status;
    private String departureAirportId;  // Foreign key to AirportEntity
    private String arrivalAirportId;    // Foreign key to AirportEntity

    public FlightEntity() {
    }

    @Ignore
    public FlightEntity(Long id, String flightNumber, String departureTime, String arrivalTime,
                        String status, String departureAirportId, String arrivalAirportId) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.status = status;
        this.departureAirportId = departureAirportId;
        this.arrivalAirportId = arrivalAirportId;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDepartureAirportId() { return departureAirportId; }
    public void setDepartureAirportId(String departureAirportId) { this.departureAirportId = departureAirportId; }

    public String getArrivalAirportId() { return arrivalAirportId; }
    public void setArrivalAirportId(String arrivalAirportId) { this.arrivalAirportId = arrivalAirportId; }
}
