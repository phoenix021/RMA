package com.jz_rma_projekat.airplane;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AviationStackApi {
    @GET("flights")
    Call<ApiResponse> getFlights(@Query("access_key") String accessKey);

    @GET("flights")
    Call<ApiResponse> getFlightsByDate(
            @Query("access_key") String accessKey,
            @Query("flight_date") String flightDate
    );
}
