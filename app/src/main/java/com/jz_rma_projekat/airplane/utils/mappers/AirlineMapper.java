package com.jz_rma_projekat.airplane.utils.mappers;

import com.jz_rma_projekat.airplane.database.dto.AirlineDto;
import com.jz_rma_projekat.airplane.database.entities.AirlineEntity;

import java.util.ArrayList;
import java.util.List;

public class AirlineMapper {
    public static AirlineEntity toEntity(AirlineDto dto) {
        AirlineEntity e = new AirlineEntity();
        e.setAirlineId(dto.getAirlineId());
        e.setAirlineName(dto.getAirlineName());
        e.setIataCode(dto.getIataCode());
        e.setIcaoCode(dto.getIcaoCode());
        e.setCallsign(dto.getCallsign());
        e.setHubCode(dto.getHubCode());
        e.setCountryIso2(dto.getCountryIso2());
        e.setCountryName(dto.getCountryName());
        e.setDateFounded(dto.getDateFounded());
        e.setFleetSize(dto.getFleetSize());
        e.setFleetAverageAge(dto.getFleetAverageAge());
        e.setStatus(dto.getStatus());
        e.setType(dto.getType());
        e.setIataPrefixAccounting(dto.getIataPrefixAccounting());
        return e;
    }

    // Convert a list of AirlineEntity objects to a list of AirlineDto objects
    public static List<AirlineDto> toDtoList(List<AirlineEntity> entities) {
        List<AirlineDto> dtoList = new ArrayList<>();
        for (AirlineEntity entity : entities) {
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }

    public static List<AirlineEntity> toEntityList(List<AirlineDto> list) {
        List<AirlineEntity> entities = new ArrayList<>();
        for (AirlineDto dto : list) {
            entities.add(toEntity(dto));
        }
        return entities;
    }

    // Convert a single AirlineEntity to AirlineDto
    public static AirlineDto toDto(AirlineEntity entity) {
        AirlineDto dto = new AirlineDto();
        dto.setAirlineId(entity.getAirlineId());
        dto.setAirlineName(entity.getAirlineName());
        dto.setIataCode(entity.getIataCode());
        dto.setIcaoCode(entity.getIcaoCode());
        dto.setCallsign(entity.getCallsign());
        dto.setHubCode(entity.getHubCode());
        dto.setCountryIso2(entity.getCountryIso2());
        dto.setCountryName(entity.getCountryName());
        dto.setDateFounded(entity.getDateFounded());
        dto.setFleetSize(entity.getFleetSize());
        dto.setFleetAverageAge(entity.getFleetAverageAge());
        dto.setStatus(entity.getStatus());
        dto.setType(entity.getType());
        dto.setIataPrefixAccounting(entity.getIataPrefixAccounting());
        return dto;
    }
}
