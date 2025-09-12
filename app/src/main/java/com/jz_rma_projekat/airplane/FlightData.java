package com.jz_rma_projekat.airplane;

public class FlightData {
    public String flight_date;
    public String flight_status;
    public Airline airline;
    public Flight flight;
    public Departure departure;
    public Arrival arrival;

    public class Airline {
        public String name;
    }

    public class Flight {
        public String number;
    }

    public class Departure {
        public String airport;
        public String scheduled;
    }

    public class Arrival {
        public String airport;
        public String scheduled;
    }
}

