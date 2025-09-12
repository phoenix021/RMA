package com.jz_rma_projekat.airplane;

import lombok.Data;

@Data
public class Departure {
    public String airport;
    public String timezone;
    public String iata;
    public String icao;
    public String terminal;
    public String gate;
    public String delay;
    public String scheduled;
    public String estimated;
    public String actual;
    public String estimated_runway;
    public String actual_runway;
}
