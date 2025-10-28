package com.jz_rma_projekat.airplane.database.api_models;
import com.jz_rma_projekat.airplane.database.dto.AirlineDto;
import com.jz_rma_projekat.airplane.utils.Pagination;

import java.util.List;

public class AirlineResponse {

    private Pagination pagination;
    private List<AirlineDto> data;

    // Getters and setters
    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<AirlineDto> getData() {
        return data;
    }

    public void setData(List<AirlineDto> data) {
        this.data = data;
    }

    // Airline Model
    public static class Airline {
        private String id;
        private String fleet_average_age;
        private String airline_id;
        private String callsign;
        private String hub_code;
        private String iata_code;
        private String icao_code;
        private String country_iso2;
        private String date_founded;
        private String iata_prefix_accounting;
        private String airline_name;
        private String country_name;
        private String fleet_size;
        private String status;
        private String type;

        // Getters and setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFleet_average_age() {
            return fleet_average_age;
        }

        public void setFleet_average_age(String fleet_average_age) {
            this.fleet_average_age = fleet_average_age;
        }

        public String getAirline_id() {
            return airline_id;
        }

        public void setAirline_id(String airline_id) {
            this.airline_id = airline_id;
        }

        public String getCallsign() {
            return callsign;
        }

        public void setCallsign(String callsign) {
            this.callsign = callsign;
        }

        public String getHub_code() {
            return hub_code;
        }

        public void setHub_code(String hub_code) {
            this.hub_code = hub_code;
        }

        public String getIata_code() {
            return iata_code;
        }

        public void setIata_code(String iata_code) {
            this.iata_code = iata_code;
        }

        public String getIcao_code() {
            return icao_code;
        }

        public void setIcao_code(String icao_code) {
            this.icao_code = icao_code;
        }

        public String getCountry_iso2() {
            return country_iso2;
        }

        public void setCountry_iso2(String country_iso2) {
            this.country_iso2 = country_iso2;
        }

        public String getDate_founded() {
            return date_founded;
        }

        public void setDate_founded(String date_founded) {
            this.date_founded = date_founded;
        }

        public String getIata_prefix_accounting() {
            return iata_prefix_accounting;
        }

        public void setIata_prefix_accounting(String iata_prefix_accounting) {
            this.iata_prefix_accounting = iata_prefix_accounting;
        }

        public String getAirline_name() {
            return airline_name;
        }

        public void setAirline_name(String airline_name) {
            this.airline_name = airline_name;
        }

        public String getCountry_name() {
            return country_name;
        }

        public void setCountry_name(String country_name) {
            this.country_name = country_name;
        }

        public String getFleet_size() {
            return fleet_size;
        }

        public void setFleet_size(String fleet_size) {
            this.fleet_size = fleet_size;
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
    }
}