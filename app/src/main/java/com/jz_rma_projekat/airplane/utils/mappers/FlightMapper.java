package com.jz_rma_projekat.airplane.utils.mappers;

import com.jz_rma_projekat.airplane.database.dto.FlightDto;
import com.jz_rma_projekat.airplane.database.dto.FlightInfoDto;
import com.jz_rma_projekat.airplane.database.dto.FlightWithAirportsDto;
import com.jz_rma_projekat.airplane.database.dto.FlightsDto;
import com.jz_rma_projekat.airplane.database.entities.AirlineEntity;
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

    public static FlightEntity allInfoToEntity(FlightInfoDto dto) {
        if (dto == null) return null;

        FlightEntity entity = new FlightEntity();

        entity.setFlightDate(dto.flightDate);
        entity.setStatus(dto.status);


        // Airline
        if (dto.airline != null) {
            entity.setAirlineName(dto.airline.name);
            entity.setAirlineIata(dto.airline.iata);
            entity.setAirlineIcao(dto.airline.icao);
        }

        // Flight info
        if (dto.flight != null) {
            entity.setFlightNumber(dto.flight.number);
            entity.setFlightIata(dto.flight.iata);
            entity.setFlightIcao(dto.flight.icao);
        }

        // Departure info
        if (dto.departure != null) {
            entity.setDepartureAirport(dto.departure.airport);
            entity.setDepartureIata(dto.departure.iata);
            entity.setDepartureIcao(dto.departure.icao);
            entity.setDepartureTerminal(dto.departure.terminal);
            entity.setDepartureGate(dto.departure.gate);
            entity.setDepartureScheduled(dto.departure.scheduled);
            entity.setDepartureTime(dto.departure.actual != null ? dto.departure.actual : dto.departure.estimated);
            entity.setDepartureAirportId(dto.departure.iata);
        }

        // Arrival info
        if (dto.arrival != null) {
            entity.setArrivalAirport(dto.arrival.airport);
            entity.setArrivalIata(dto.arrival.iata);
            entity.setArrivalIcao(dto.arrival.icao);
            entity.setArrivalTerminal(dto.arrival.terminal);
            entity.setArrivalGate(dto.arrival.gate);
            entity.setArrivalScheduled(dto.arrival.scheduled);
            entity.setArrivalTime(dto.arrival.actual != null ? dto.arrival.actual : dto.arrival.estimated);
            entity.setArrivalAirportId(dto.arrival.iata);
        }
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

    public static FlightWithAirportsDto flightWithAirportstoDto(FlightEntity flightEntity, String departureAirportName, String arrivalAirportName) {
        return new FlightWithAirportsDto(
                flightEntity.getId().toString(),  // Flight ID
                flightEntity.getFlightNumber(),  // Flight Number
                flightEntity.getDepartureTime(),  // Departure Time
                flightEntity.getArrivalTime(),  // Arrival Time
                departureAirportName,  // Departure Airport Name
                arrivalAirportName,  // Arrival Airport Name
                flightEntity.getStatus(),  // Flight Status
                flightEntity.getDepartureAirportId(),  // Departure Airport ID
                flightEntity.getArrivalAirportId()  // Arrival Airport ID
        );
    }

    // Converts FlightWithAirportsDto to FlightEntity
    public static FlightEntity flightWithAirportsDtoToEntity(FlightWithAirportsDto dto) {
        // Assuming the FlightEntity constructor accepts all relevant fields
        return new FlightEntity(
                null,               // Room will automatically create the id //to check
                dto.getFlightNumber(),           // Map the flightNumber
                dto.getDepartureTime(),         // Map the departureTime
                dto.getArrivalTime(),           // Map the arrivalTime
                dto.getStatus(),                // Map the status
                dto.getDepartureAirportId(),    // Map departureAirportId
                dto.getArrivalAirportId()       // Map arrivalAirportId
        );
    }

    // You could also implement a method to convert a list of DTOs to a list of entities:
    public static List<FlightEntity> flightWithAirportsDtoToEntityList(List<FlightWithAirportsDto> dtoList) {
        List<FlightEntity> entities = new ArrayList<>();
        for (FlightWithAirportsDto dto : dtoList) {
            entities.add(flightWithAirportsDtoToEntity(dto));  // Add each converted entity to the list
        }
        return entities;
    }

    public static FlightEntity flightsInfoDtoToEntity(FlightInfoDto dto) {
        FlightEntity entity = new FlightEntity();
        entity.setFlightNumber(dto.flight.number);
        entity.setDepartureTime(dto.departure.scheduled);
        entity.setArrivalTime(dto.arrival.scheduled);
        entity.setStatus(dto.status);
        entity.setDepartureAirportId(dto.departure.iata);
        entity.setArrivalAirportId(dto.arrival.iata);
        return entity;
    }

    public static List<FlightEntity> flightsInfoDtoToEntityList(List<FlightInfoDto> dtoList) {
        List<FlightEntity> entities = new ArrayList<>();
        for (FlightInfoDto dto : dtoList) {
            entities.add(flightsInfoDtoToEntity(dto));
        }
        return entities;
    }

    public static List<FlightEntity> allInfoDtoToEntityList(List<FlightInfoDto> dtoList) {
        List<FlightEntity> entities = new ArrayList<>();
        for (FlightInfoDto dto : dtoList) {
            entities.add(allInfoToEntity(dto));
        }
        return entities;
    }



    public static FlightEntity fullFlightsDtoToEntity(FlightsDto dto) {
        FlightEntity entity = new FlightEntity();

        entity.setFlightDate(dto.flight_date);
        entity.setStatus(dto.flight_status);
        entity.setDepartureAirportId(dto.departure.iata);
        entity.setArrivalAirportId(dto.arrival.iata);

        if (dto.departure != null) {
            entity.setDepartureAirport(dto.departure.airport);
            entity.setDepartureIata(dto.departure.iata);
            entity.setDepartureIcao(dto.departure.icao);
            entity.setDepartureTerminal(dto.departure.terminal);
            entity.setDepartureGate(dto.departure.gate);
            entity.setDepartureScheduled(dto.departure.scheduled);
        }

        if (dto.arrival != null) {
            entity.setArrivalAirport(dto.arrival.airport);
            entity.setArrivalIata(dto.arrival.iata);
            entity.setArrivalIcao(dto.arrival.icao);
            entity.setArrivalTerminal(dto.arrival.terminal);
            entity.setArrivalGate(dto.arrival.gate);
            entity.setArrivalScheduled(dto.arrival.scheduled);
        }

        if (dto.airline != null) {
            entity.setAirlineName(dto.airline.name);
            entity.setAirlineIata(dto.airline.iata);
            entity.setAirlineIcao(dto.airline.icao);
        }

        if (dto.flight != null) {
            entity.setFlightNumber(dto.flight.number);
            entity.setFlightIata(dto.flight.iata);
            entity.setFlightIcao(dto.flight.icao);
        }

        return entity;
    }

    public static List<FlightEntity> fullFlightsDtoToEntityList(List<FlightsDto> apiFlights) {
        List<FlightEntity> entities = new ArrayList<>();
        for (FlightsDto apiFlight : apiFlights) {
            entities.add(fullFlightsDtoToEntity(apiFlight));
        }
        return entities;
    }

}

