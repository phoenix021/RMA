package com.jz_rma_projekat.airplane.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.aviationstack.com/v1/";
    private static Retrofit retrofit = null;

    public static String API_KEY = "07d66aaa5c32f0546552c090cd95403f";

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
