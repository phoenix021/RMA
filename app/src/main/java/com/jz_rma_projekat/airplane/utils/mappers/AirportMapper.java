package com.jz_rma_projekat.airplane.utils.mappers;

import com.jz_rma_projekat.airplane.database.entities.AirportEntity;
import com.jz_rma_projekat.airplane.database.dto.AirportDto;

import java.util.List;
import java.util.stream.Collectors;

public class AirportMapper {

    public static AirportDto toDto(AirportEntity entity) {
        return new AirportDto(
                entity.getId(),
                entity.getGmt(),
                entity.getAirportId(),
                entity.getIataCode(),
                entity.getCityIataCode(),
                entity.getIcaoCode(),
                entity.getCountryIso2(),
                entity.getGeonameId(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getName(),
                entity.getCountry(),
                entity.getPhoneNumber(),
                entity.getTimezone()
        );
    }

    public static List<AirportDto> toDtoList(List<AirportEntity> entities) {
        return entities.stream()
                .map(AirportMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<AirportEntity> toEntityList(List<AirportDto> dtos) {
        return dtos.stream()
                .map(AirportMapper::toEntity)
                .collect(Collectors.toList());
    }

    public static AirportEntity toEntity(AirportDto airportDto) {
        AirportEntity entity = new AirportEntity();
        entity.setId(airportDto.getId());
        entity.setGmt(airportDto.getGmt());
        entity.setAirportId(airportDto.getAirportId());
        entity.setIataCode(airportDto.getIataCode());
        entity.setCityIataCode(airportDto.getCityIataCode());
        entity.setIcaoCode(airportDto.getIcaoCode());
        entity.setCountryIso2(airportDto.getCountryIso2());
        entity.setGeonameId(airportDto.getGeonameId());
        entity.setLatitude(airportDto.getLatitude());
        entity.setLongitude(airportDto.getLongitude());
        entity.setName(airportDto.getName());
        entity.setCountry(airportDto.getCountry());
        entity.setPhoneNumber(airportDto.getPhoneNumber());
        entity.setTimezone(airportDto.getTimezone());
        return entity;
    }

}