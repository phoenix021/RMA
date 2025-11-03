package com.jz_rma_projekat.airplane.database.dto;

import com.google.gson.annotations.SerializedName;

public class FlightInfoDto {

    @SerializedName("flight_date")
    public String flightDate;

    @SerializedName("flight_status")
    public String status;

    @SerializedName("departure")
    public DepartureInfo departure;

    @SerializedName("arrival")
    public ArrivalInfo arrival;

    @SerializedName("airline")
    public AirlineInfo airline;

    @SerializedName("flight")
    public FlightInfo flight;

    @SerializedName("aircraft")
    public Object aircraft; // can be expanded if needed

    @SerializedName("live")
    public Object live; // can be expanded if needed

    public static class DepartureInfo {
        @SerializedName("airport")
        public String airport;

        @SerializedName("timezone")
        public String timezone;

        @SerializedName("iata")
        public String iata;

        @SerializedName("icao")
        public String icao;

        @SerializedName("terminal")
        public String terminal;

        @SerializedName("gate")
        public String gate;

        @SerializedName("delay")
        public Integer delay;

        @SerializedName("scheduled")
        public String scheduled;

        @SerializedName("estimated")
        public String estimated;

        @SerializedName("actual")
        public String actual;

        @SerializedName("estimated_runway")
        public String estimatedRunway;

        @SerializedName("actual_runway")
        public String actualRunway;
    }

    public static class ArrivalInfo {
        @SerializedName("airport")
        public String airport;

        @SerializedName("timezone")
        public String timezone;

        @SerializedName("iata")
        public String iata;

        @SerializedName("icao")
        public String icao;

        @SerializedName("terminal")
        public String terminal;

        @SerializedName("gate")
        public String gate;

        @SerializedName("baggage")
        public String baggage;

        @SerializedName("delay")
        public Integer delay;

        @SerializedName("scheduled")
        public String scheduled;

        @SerializedName("estimated")
        public String estimated;

        @SerializedName("actual")
        public String actual;

        @SerializedName("estimated_runway")
        public String estimatedRunway;

        @SerializedName("actual_runway")
        public String actualRunway;
    }

    public static class AirlineInfo {
        @SerializedName("name")
        public String name;

        @SerializedName("iata")
        public String iata;

        @SerializedName("icao")
        public String icao;
    }

    public static class FlightInfo {
        @SerializedName("number")
        public String number;

        @SerializedName("iata")
        public String iata;

        @SerializedName("icao")
        public String icao;

        @SerializedName("codeshared")
        public Object codeshared; // can be expanded if needed
    }
}
