package com.jz_rma_projekat.airplane.utils.mappers;

import com.jz_rma_projekat.airplane.database.dto.ScheduleDto;
import com.jz_rma_projekat.airplane.database.entities.ScheduleEntity;

import java.util.ArrayList;
import java.util.List;

public class ScheduleMapper {

    // Convert Entity to DTO
    public static ScheduleDto toDto(ScheduleEntity entity, String departureAirportName, String arrivalAirportName) {
        return new ScheduleDto(
                entity.getFlightNumber(),
                entity.getDepartureTime(),
                entity.getArrivalTime(),
                departureAirportName,
                arrivalAirportName
        );
    }

    // Convert DTO to Entity
    public static ScheduleEntity toEntity(ScheduleDto dto) {
        ScheduleEntity entity = new ScheduleEntity();
        entity.setFlightNumber(dto.getFlightNumber());
        entity.setDepartureTime(dto.getDepartureTime());
        entity.setArrivalTime(dto.getArrivalTime());
        // Handle foreign keys (airport ids) in a real case
        return entity;
    }

    // Convert List of Entities to List of DTOs
    public static List<ScheduleDto> toDtoList(List<ScheduleEntity> entities, String departureAirportName, String arrivalAirportName) {
        List<ScheduleDto> dtoList = new ArrayList<>();
        for (ScheduleEntity entity : entities) {
            dtoList.add(toDto(entity, departureAirportName, arrivalAirportName));
        }
        return dtoList;
    }

    public static List<ScheduleEntity> toEntityList(List<ScheduleDto> dtos) {
        List<ScheduleEntity> entityList = new ArrayList<>();
        for (ScheduleDto dto : dtos) {
            entityList.add(toEntity(dto));
        }
        return entityList;
    }
}

