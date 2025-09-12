package com.jz_rma_projekat.airplane;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.jz_rma_projekat.airplane.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://api.aviationstack.com/v1/";
    private static final String API_KEY = "07d66aaa5c32f0546552c090cd95403f"; // Replace with your key

    private RecyclerView rvFlights;
    private FlightsAdapter adapter;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });

        rvFlights = findViewById(R.id.rvFlights);
        rvFlights.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FlightsAdapter(new ArrayList<>());
        rvFlights.setAdapter(adapter);

        // Set up Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AviationStackApi api = retrofit.create(AviationStackApi.class);

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD", Locale.getDefault());
        String dateString = sdf.format(now);
        Call<ApiResponse> call = api.getFlightsByDate(API_KEY, dateString);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FlightData> flights = response.body().data;
                    for (FlightData flight : flights) {
                        Log.d("Flight", "Flight: " + flight.flight.toString() + " from " + flight.departure.airport + " to " + flight.arrival.airport);
                    }
                    adapter.updateFlights(flights);
                } else {
                    Log.e("API Error", "Response failed or empty");
                    Log.e("API Error", "HTTP Code: " + response.code());

                    if (response.errorBody() != null) {
                        try {
                            String errorBodyStr = response.errorBody().string();
                            Log.e("API Error", "Error Body: " + errorBodyStr);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("API Error", "Error body is null");
                    }
                }
            }


            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("API Error", "JELENA: " + call.toString());
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}