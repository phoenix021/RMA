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
    private String departureAirportId;
    private String arrivalAirportId;

    // Flight general info
    public String flightDate;

    // Departure info
    public String departureAirport;
    public String departureIata;
    public String departureIcao;
    public String departureTerminal;
    public String departureGate;
    public String departureScheduled;

    // Arrival info
    public String arrivalAirport;
    public String arrivalIata;
    public String arrivalIcao;
    public String arrivalTerminal;
    public String arrivalGate;
    public String arrivalScheduled;

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getDepartureIata() {
        return departureIata;
    }

    public void setDepartureIata(String departureIata) {
        this.departureIata = departureIata;
    }

    public String getDepartureIcao() {
        return departureIcao;
    }

    public void setDepartureIcao(String departureIcao) {
        this.departureIcao = departureIcao;
    }

    public String getDepartureTerminal() {
        return departureTerminal;
    }

    public void setDepartureTerminal(String departureTerminal) {
        this.departureTerminal = departureTerminal;
    }

    public String getDepartureGate() {
        return departureGate;
    }

    public void setDepartureGate(String departureGate) {
        this.departureGate = departureGate;
    }

    public String getDepartureScheduled() {
        return departureScheduled;
    }

    public void setDepartureScheduled(String departureScheduled) {
        this.departureScheduled = departureScheduled;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getArrivalIata() {
        return arrivalIata;
    }

    public void setArrivalIata(String arrivalIata) {
        this.arrivalIata = arrivalIata;
    }

    public String getArrivalIcao() {
        return arrivalIcao;
    }

    public void setArrivalIcao(String arrivalIcao) {
        this.arrivalIcao = arrivalIcao;
    }

    public String getArrivalTerminal() {
        return arrivalTerminal;
    }

    public void setArrivalTerminal(String arrivalTerminal) {
        this.arrivalTerminal = arrivalTerminal;
    }

    public String getArrivalGate() {
        return arrivalGate;
    }

    public void setArrivalGate(String arrivalGate) {
        this.arrivalGate = arrivalGate;
    }

    public String getArrivalScheduled() {
        return arrivalScheduled;
    }

    public void setArrivalScheduled(String arrivalScheduled) {
        this.arrivalScheduled = arrivalScheduled;
    }

    public String getAirlineIata() {
        return airlineIata;
    }

    public void setAirlineIata(String airlineIata) {
        this.airlineIata = airlineIata;
    }

    public String getAirlineIcao() {
        return airlineIcao;
    }

    public void setAirlineIcao(String airlineIcao) {
        this.airlineIcao = airlineIcao;
    }

    public String getFlightIata() {
        return flightIata;
    }

    public void setFlightIata(String flightIata) {
        this.flightIata = flightIata;
    }

    public String getFlightIcao() {
        return flightIcao;
    }

    public void setFlightIcao(String flightIcao) {
        this.flightIcao = flightIcao;
    }

    // Airline info
    public String airlineName;
    public String airlineIata;
    public String airlineIcao;

    public String flightIata;

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String flightIcao;

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

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    @Override
    public String toString() {
        return "FlightEntity{" +
                "id=" + id +
                ", flightNumber='" + flightNumber + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", status='" + status + '\'' +
                ", departureAirportId='" + departureAirportId + '\'' +
                ", arrivalAirportId='" + arrivalAirportId + '\'' +
                ", flightDate='" + flightDate + '\'' +
                ", departureAirport='" + departureAirport + '\'' +
                ", departureIata='" + departureIata + '\'' +
                ", departureGate='" + departureGate + '\'' +
                ", airlineIata='" + airlineIata + '\'' +
                ", flightIata='" + flightIata + '\'' +
                '}';
    }
}
