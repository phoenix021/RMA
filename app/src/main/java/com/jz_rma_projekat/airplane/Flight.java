package com.jz_rma_projekat.airplane;

public class Flight {
    private String route;
    private String airline;
    private String time;

    private String flight_number;

    public Flight(String route, String airline, String time) {
        this.route = route;
        this.airline = airline;
        this.time = time;
    }

    public Flight(String route, String airline, String time, String flight_number) {
        this.route = route;
        this.airline = airline;
        this.time = time;
        this.flight_number = flight_number;
    }

    public String getRoute() {
        return route;
    }

    public String getAirline() {
        return airline;
    }

    public String getTime() {
        return time;
    }

    public String getFlightNumber(){
        return flight_number;
    }
}
