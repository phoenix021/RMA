package com.jz_rma_projekat.airplane;

import com.jz_rma_projekat.airplane.database.api_models.AirlineResponse;
import com.jz_rma_projekat.airplane.database.api_models.AirportsResponse;

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
    Call<ApiResponse> getFlights(@Query("access_key") String accessKey);

    @GET("flights")
    Call<ApiResponse> getFlightsByDate(
            @Query("access_key") String accessKey,
            @Query("flight_date") String flightDate
    );

    @GET("airports")
    Call<AirportsResponse> getAirports(@Query("access_key") String accessKey);

    @GET("airports")
    Call<AirportsResponse> getAirports(
            @Query("access_key") String apiKey,
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}
