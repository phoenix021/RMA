package com.jz_rma_projekat.airplane;

import com.jz_rma_projekat.airplane.network.AviationStackApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.aviationstack.com/v1/";
    private static Retrofit retrofit = null;

    private RetrofitClient() {} // prevent instantiation

    public static AviationStackApi getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(AviationStackApi.class);
    }
}
