package com.jz_rma_projekat.airplane.database.api_models;

import com.jz_rma_projekat.airplane.database.dto.RouteDto;
import com.jz_rma_projekat.airplane.utils.Pagination;

import java.util.List;

public class RouteResponse {
    private List<RouteDto> data;
    private Pagination pagination;

    // Getters and Setters
    public List<RouteDto> getData() {
        return data;
    }

    public void setData(List<RouteDto> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}

