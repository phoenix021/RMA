package com.jz_rma_projekat.airplane;

import lombok.Data;

@Data
public class FlightData {
    public Flight flight;
    public Departure departure;
    public Arrival arrival;
    public Airline airline;
    public Aircraft aircraft;
    public Live live;
}
