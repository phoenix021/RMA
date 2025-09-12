package com.jz_rma_projekat.airplane;

public class Flight {
    private String route;
    private String airline;
    private String time;

    public Flight(String route, String airline, String time) {
        this.route = route;
        this.airline = airline;
        this.time = time;
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
}
