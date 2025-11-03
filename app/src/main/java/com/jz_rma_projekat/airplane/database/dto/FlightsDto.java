package com.jz_rma_projekat.airplane.database.dto;

import com.google.gson.annotations.SerializedName;

public class FlightsDto {

    @SerializedName("flight_date")
    public String flight_date;

    @SerializedName("flight_status")
    public String flight_status;

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
        public CodesharedInfo codeshared; // can be null
    }

    public static class CodesharedInfo {
        @SerializedName("airline_name")
        public String airline_name;

        @SerializedName("airline_iata")
        public String airline_iata;

        @SerializedName("airline_icao")
        public String airline_icao;

        @SerializedName("flight_number")
        public String flight_number;

        @SerializedName("flight_iata")
        public String flight_iata;

        @SerializedName("flight_icao")
        public String flight_icao;
    }
}



