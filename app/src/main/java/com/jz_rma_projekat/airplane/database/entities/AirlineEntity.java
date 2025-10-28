package com.jz_rma_projekat.airplane.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "airlines")
public class AirlineEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String airlineId;
    private String airlineName;
    private String iataCode;
    private String icaoCode;
    private String callsign;
    private String hubCode;
    private String countryIso2;
    private String countryName;
    private String dateFounded;
    private String fleetSize;
    private String fleetAverageAge;
    private String status;
    private String type;
    private String iataPrefixAccounting;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getHubCode() {
        return hubCode;
    }

    public void setHubCode(String hubCode) {
        this.hubCode = hubCode;
    }

    public String getCountryIso2() {
        return countryIso2;
    }

    public void setCountryIso2(String countryIso2) {
        this.countryIso2 = countryIso2;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getDateFounded() {
        return dateFounded;
    }

    public void setDateFounded(String dateFounded) {
        this.dateFounded = dateFounded;
    }

    public String getFleetSize() {
        return fleetSize;
    }

    public void setFleetSize(String fleetSize) {
        this.fleetSize = fleetSize;
    }

    public String getFleetAverageAge() {
        return fleetAverageAge;
    }

    public void setFleetAverageAge(String fleetAverageAge) {
        this.fleetAverageAge = fleetAverageAge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIataPrefixAccounting() {
        return iataPrefixAccounting;
    }

    public void setIataPrefixAccounting(String iataPrefixAccounting) {
        this.iataPrefixAccounting = iataPrefixAccounting;
    }

}
