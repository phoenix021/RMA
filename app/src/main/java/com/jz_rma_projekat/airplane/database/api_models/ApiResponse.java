package com.jz_rma_projekat.airplane.database.api_models;

import com.jz_rma_projekat.airplane.database.dto.FlightWithAirportsDto;
import com.jz_rma_projekat.airplane.utils.Pagination;

import java.util.List;
public class ApiResponse<T> {
    private Pagination pagination;
    private List<T> data;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}

