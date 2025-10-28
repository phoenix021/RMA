package com.jz_rma_projekat.airplane.database.dto;

import com.google.gson.annotations.SerializedName;

public class AirlineDto {
        @SerializedName("airline_id")
        private String airlineId;

        @SerializedName("airline_name")
        private String airlineName;

        @SerializedName("iata_code")
        private String iataCode;

        @SerializedName("icao_code")
        private String icaoCode;

        @SerializedName("callsign")
        private String callsign;

        @SerializedName("hub_code")
        private String hubCode;

        @SerializedName("country_iso2")
        private String countryIso2;

        @SerializedName("country_name")
        private String countryName;

        @SerializedName("date_founded")
        private String dateFounded;

        @SerializedName("fleet_size")
        private String fleetSize;

        @SerializedName("fleet_average_age")
        private String fleetAverageAge;

        @SerializedName("status")
        private String status;

        @SerializedName("type")
        private String type;

        @SerializedName("iata_prefix_accounting")
        private String iataPrefixAccounting;

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