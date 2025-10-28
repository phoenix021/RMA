package com.jz_rma_projekat.airplane.database.api_models;

import com.jz_rma_projekat.airplane.database.dto.FlightDto;
import com.jz_rma_projekat.airplane.database.dto.FlightWithAirportsDto;
import com.jz_rma_projekat.airplane.utils.Pagination;

import java.util.List;

public class FlightResponse {
    private List<FlightWithAirportsDto> data; // or FlightWithAirportsDto
    private Pagination pagination;

    // Getters and Setters
    public List<FlightWithAirportsDto> getData() {
        return data;
    }

    public void setData(List<FlightWithAirportsDto> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}