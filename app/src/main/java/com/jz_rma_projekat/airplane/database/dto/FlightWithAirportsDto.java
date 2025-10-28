package com.jz_rma_projekat.airplane.database.dto;

import com.google.gson.annotations.SerializedName;

public class FlightWithAirportsDto {
    @SerializedName("flight_number")
    public String flightNumber;

    @SerializedName("departure_time")
    public String departureTime;

    @SerializedName("arrival_time")
    public String arrivalTime;

    @SerializedName("departure_airport_name")
    public String departureAirportName;

    @SerializedName("arrival_airport_name")
    public String arrivalAirportName;

    @SerializedName("status")
    public String status;

    @SerializedName("departure_airport_id")
    public String departureAirportId;

    @SerializedName("arrival_airport_id")
    public String arrivalAirportId;

    @SerializedName("flight_id")
    public String flightId;

    // Default constructor
    public FlightWithAirportsDto() {
    }

    // Constructor for easy initialization
    public FlightWithAirportsDto(String flightId, String flightNumber, String departureTime,
                                 String arrivalTime, String departureAirportName, String arrivalAirportName,
                                 String status, String departureAirportId, String arrivalAirportId) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAirportName = departureAirportName;
        this.arrivalAirportName = arrivalAirportName;
        this.status = status;
        this.departureAirportId = departureAirportId;
        this.arrivalAirportId = arrivalAirportId;
    }

    // Getters and setters for all fields
    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureAirportName() {
        return departureAirportName;
    }

    public void setDepartureAirportName(String departureAirportName) {
        this.departureAirportName = departureAirportName;
    }

    public String getArrivalAirportName() {
        return arrivalAirportName;
    }

    public void setArrivalAirportName(String arrivalAirportName) {
        this.arrivalAirportName = arrivalAirportName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartureAirportId() {
        return departureAirportId;
    }

    public void setDepartureAirportId(String departureAirportId) {
        this.departureAirportId = departureAirportId;
    }

    public String getArrivalAirportId() {
        return arrivalAirportId;
    }

    public void setArrivalAirportId(String arrivalAirportId) {
        this.arrivalAirportId = arrivalAirportId;
    }
}
