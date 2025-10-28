package com.jz_rma_projekat.airplane.database.dto;

import com.google.gson.annotations.SerializedName;

public class FlightDto {

    @SerializedName("flight_number")
    private String flightNumber;

    @SerializedName("departure_time")
    private String departureTime;

    @SerializedName("arrival_time")
    private String arrivalTime;

    @SerializedName("status")
    private String status;

    @SerializedName("departure_airport_name")
    private String departureAirportName;

    @SerializedName("arrival_airport_name")
    private String arrivalAirportName;

    // Constructor for DTO
    public FlightDto(String flightNumber, String departureTime, String arrivalTime, String status,
                     String departureAirportName, String arrivalAirportName) {
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.status = status;
        this.departureAirportName = departureAirportName;
        this.arrivalAirportName = arrivalAirportName;
    }

    // Getters and setters
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDepartureAirportName() { return departureAirportName; }
    public void setDepartureAirportName(String departureAirportName) { this.departureAirportName = departureAirportName; }

    public String getArrivalAirportName() { return arrivalAirportName; }
    public void setArrivalAirportName(String arrivalAirportName) { this.arrivalAirportName = arrivalAirportName; }
}

