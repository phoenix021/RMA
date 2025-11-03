package com.jz_rma_projekat.airplane.network;

import com.jz_rma_projekat.airplane.database.api_models.AirlineResponse;
import com.jz_rma_projekat.airplane.database.api_models.AirportsResponse;
import com.jz_rma_projekat.airplane.database.api_models.ApiResponse;
import com.jz_rma_projekat.airplane.database.api_models.FlightResponse;
import com.jz_rma_projekat.airplane.database.api_models.RouteResponse;
import com.jz_rma_projekat.airplane.database.api_models.ScheduleResponse;
import com.jz_rma_projekat.airplane.database.dto.FlightInfoDto;
import com.jz_rma_projekat.airplane.database.dto.FlightsDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AviationStackApi {

    // Fetching airlines with pagination
    @GET("airlines")
    Call<AirlineResponse> getAirlines(
            @Query("access_key") String apiKey,
            @Query("limit") int limit,
            @Query("offset") int offset
    );
    @GET("flights")
    Call<FlightResponse> getFlights(@Query("access_key") String accessKey);

    @GET("flights")
    Call<FlightResponse> getFlightsByDate(
            @Query("access_key") String accessKey,
            @Query("flight_date") String flightDate
    );

    @GET("flights")
    Call<FlightResponse> getFlightsByRouteAndDate(
            @Query("access_key") String accessKey,
            @Query("dep_iata") String originIata,
            @Query("arr_iata") String destinationIata,
            @Query("flight_date") String flightDate
    );

    @GET("flights")
    Call<ApiResponse<FlightInfoDto>> getFlightsByRoute(
            @Query("access_key") String accessKey,
            @Query("dep_iata") String originIata,
            @Query("arr_iata") String destinationIata
    );

    @GET("flights")
    Call<ApiResponse<FlightsDto>> getFlightsByRoute(
            @Query("access_key") String accessKey,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("routes")
    Call<RouteResponse> getRoutesByDeparture(
            @Query("access_key") String accessKey,
            @Query("dep_iata") String departureIata
    );

    //@GET("airports")
    //Call<AirportsResponse> getAirports(@Query("access_key") String accessKey);

    @GET("airports")
    Call<AirportsResponse> getAirports(
            @Query("access_key") String apiKey,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    // Fetching airports
    @GET("airports")
    Call<AirportsResponse> getAirports(
            @Query("access_key") String accessKey
    );

    // Fetching routes information
    @GET("routes")
    Call<RouteResponse> getRoutes(
            @Query("access_key") String accessKey
    );

    // Fetching schedules information
    @GET("schedules")
    Call<ScheduleResponse> getSchedules(
            @Query("access_key") String accessKey
    );

    // Fetching routes by source and destination airport (optional parameters)
    @GET("routes")
    Call<RouteResponse> getRoutesByAirports(
            @Query("access_key") String accessKey,
            @Query("dep_iata") String departureIATA,
            @Query("arr_iata") String arrivalIATA
    );

    // Fetching schedules by date (optional)
    @GET("schedules")
    Call<ScheduleResponse> getSchedulesByDate(
            @Query("access_key") String accessKey,
            @Query("date") String date
    );
}
