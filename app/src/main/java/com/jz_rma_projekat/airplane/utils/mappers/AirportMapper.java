package com.jz_rma_projekat.airplane.utils.mappers;

import com.jz_rma_projekat.airplane.database.entities.AirportEntity;
import com.jz_rma_projekat.airplane.database.dto.AirportDto;

import java.util.List;
import java.util.stream.Collectors;

public class AirportMapper {

    public static AirportDto toDto(AirportEntity entity) {
        return new AirportDto(
                entity.iataCode,
                entity.name,
                entity.country
        );
    }

    public static List<AirportDto> toDtoList(List<AirportEntity> entities) {
        return entities.stream()
                .map(AirportMapper::toDto)
                .collect(Collectors.toList());
    }

    public static AirportEntity toEntity(AirportDto dto) {
        return new AirportEntity(
                dto.getIataCode(),
                dto.getName(),
                dto.getCountry()
        );
    }

    public static List<AirportEntity> toEntityList(List<AirportDto> dtos) {
        return dtos.stream()
                .map(AirportMapper::toEntity)
                .collect(Collectors.toList());
    }
}

