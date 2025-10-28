package com.jz_rma_projekat.airplane.utils.mappers;

import com.jz_rma_projekat.airplane.database.dto.FlightDto;
import com.jz_rma_projekat.airplane.database.entities.FlightEntity;

import java.util.ArrayList;
import java.util.List;

public class FlightMapper {

    public static FlightDto toDto(FlightEntity entity, String departureAirportName, String arrivalAirportName) {
        return new FlightDto(
                entity.getFlightNumber(),
                entity.getDepartureTime(),
                entity.getArrivalTime(),
                entity.getStatus(),
                departureAirportName,
                arrivalAirportName
        );
    }

    public static FlightEntity toEntity(FlightDto dto) {
        FlightEntity entity = new FlightEntity();
        entity.setFlightNumber(dto.getFlightNumber());
        entity.setDepartureTime(dto.getDepartureTime());
        entity.setArrivalTime(dto.getArrivalTime());
        entity.setStatus(dto.getStatus());
        // You will need to handle foreign keys (airport ids) in a real case
        return entity;
    }

    public static List<FlightDto> toDtoList(List<FlightEntity> entities, String departureAirportName, String arrivalAirportName) {
        List<FlightDto> dtoList = new ArrayList<>();
        for (FlightEntity entity : entities) {
            dtoList.add(toDto(entity, departureAirportName, arrivalAirportName));
        }
        return dtoList;
    }

    public static List<FlightEntity> toEntityList(List<FlightDto> dtos) {
        List<FlightEntity> entityList = new ArrayList<>();
        for (FlightDto dto : dtos) {
            entityList.add(toEntity(dto));
        }
        return entityList;
    }
}

