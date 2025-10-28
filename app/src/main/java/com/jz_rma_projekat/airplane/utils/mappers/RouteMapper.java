package com.jz_rma_projekat.airplane.utils.mappers;

import com.jz_rma_projekat.airplane.database.dto.RouteDto;
import com.jz_rma_projekat.airplane.database.entities.RouteEntity;

import java.util.ArrayList;
import java.util.List;

public class RouteMapper {

    // Convert Entity to DTO
    public static RouteDto toDto(RouteEntity entity) {
        return new RouteDto(
                entity.getAirlineIata(),
                entity.getDepartureAirportIata(),
                entity.getArrivalAirportIata()
        );
    }

    // Convert DTO to Entity
    public static RouteEntity toEntity(RouteDto dto) {
        RouteEntity entity = new RouteEntity();
        entity.setAirlineIata(dto.getAirlineIata());
        entity.setDepartureAirportIata(dto.getDepartureAirportIata());
        entity.setArrivalAirportIata(dto.getArrivalAirportIata());
        return entity;
    }

    // Convert List of Entities to List of DTOs
    public static List<RouteDto> toDtoList(List<RouteEntity> entities) {
        List<RouteDto> dtoList = new ArrayList<>();
        for (RouteEntity entity : entities) {
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }

    public static List<RouteEntity> toEntityList(List<RouteDto> dtos) {
        List<RouteEntity> entityList = new ArrayList<>();
        for (RouteDto dto : dtos) {
            entityList.add(toEntity(dto));
        }
        return entityList;
    }
}

