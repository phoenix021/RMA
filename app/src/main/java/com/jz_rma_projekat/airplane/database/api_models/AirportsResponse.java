package com.jz_rma_projekat.airplane.database.api_models;

import com.google.gson.annotations.SerializedName;
import com.jz_rma_projekat.airplane.database.dto.AirportDto;
import com.jz_rma_projekat.airplane.database.entities.AirportEntity;

import java.util.ArrayList;
import java.util.List;

public class AirportsResponse {
    @SerializedName("data")
    private List<AirportDto> data;

    public List<AirportDto> getData() {
        return data;
    }

    public void setData(List<AirportDto> data) {
        this.data = data;
    }

    private List<AirportEntity> mapToEntities(AirportsResponse response) {
        List<AirportEntity> entities = new ArrayList<>();
        for (AirportDto dto : response.data) {
            entities.add(new AirportEntity(dto.getIataCode(), dto.getName(), dto.getCountry()));
        }
        return entities;
    }
}
