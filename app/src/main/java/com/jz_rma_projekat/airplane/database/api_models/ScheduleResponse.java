package com.jz_rma_projekat.airplane.database.api_models;

import com.jz_rma_projekat.airplane.database.dto.ScheduleDto;
import com.jz_rma_projekat.airplane.utils.Pagination;

import java.util.List;

public class ScheduleResponse {
    private List<ScheduleDto> data;
    private Pagination pagination;

    // Getters and Setters
    public List<ScheduleDto> getData() {
        return data;
    }

    public void setData(List<ScheduleDto> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}

