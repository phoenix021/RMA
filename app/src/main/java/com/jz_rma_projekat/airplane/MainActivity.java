package com.jz_rma_projekat.airplane;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.jz_rma_projekat.airplane.database.AppDatabase;
import com.jz_rma_projekat.airplane.database.dto.AirportDto;
import com.jz_rma_projekat.airplane.databinding.ActivityMainBinding;
import com.jz_rma_projekat.airplane.database.entities.AirportEntity;
import com.jz_rma_projekat.airplane.database.dto.AirportDto;
import com.jz_rma_projekat.airplane.database.api_models.AirportsResponse;

import androidx.room.Room;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.Calendar;
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

    AutoCompleteTextView etOrigin, etDestination;
    ArrayList<AirportDto> airportList = new ArrayList<>();
    EditText etDate;
    Button btnSearchFlights;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setSupportActionBar(binding.toolbar);

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });

        // Only add the fragment if this is the first creation
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new FlightMapFragment())
                    .commit();
           // findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
        }

        etOrigin = findViewById(R.id.etOrigin);
        etDestination = findViewById(R.id.etDestination);
        etDate = findViewById(R.id.etDate);
        btnSearchFlights = findViewById(R.id.btnSearchFlights);

        // Date picker
        etDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                        etDate.setText(selectedDate);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dialog.show();
        });

        // Search button logic
        btnSearchFlights.setOnClickListener(v -> {
            String origin = etOrigin.getText().toString().trim();
            String destination = etDestination.getText().toString().trim();
            String date = etDate.getText().toString().trim();

            if (origin.isEmpty() || destination.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

        });

        // Call AviationStack API and show list
        ArrayList<AirportDto> airports = fetchAirports();

        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "aviation_db"
        ).build();

        saveAirportsToDatabase(airports);

    }

    //private void saveToDatabase(List<AirportEntity> airports) {
    //    AppDatabase db = Room.databaseBuilder(
    //            getApplicationContext(),
    //            AppDatabase.class,
    //            "app_database"
    //    ).build();

    //    Executors.newSingleThreadExecutor().execute(() -> {
    //        db.airportDao().insertAll(airports);
    //    });
    //}

    public void saveAirportsToDatabase(List<AirportDto> airports){
        new Thread(() -> {
            List<AirportEntity> airportEntities = new ArrayList<>();
            for (AirportDto airport : airports) {
                airportEntities.add(new AirportEntity(
                        airport.getIataCode(),
                        airport.getName(),
                        airport.getCountry()
                ));
            }

            db.airportDao().insertAll(airportEntities);
            Log.d("Room", "Airports saved to DB");
        }).start();
    }
    public void preloadAutoComplete(){
        new Thread(() -> {
            List<AirportEntity> airportEntities = db.airportDao().getAllAirports();
            List<String> names = new ArrayList<>();

            for (AirportEntity airport : airportEntities) {
                names.add(airport.name + " (" + airport.iataCode + ")");
            }

            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        MainActivity.this,
                        android.R.layout.simple_dropdown_item_1line,
                        names
                );
                etOrigin.setAdapter(adapter);
                etDestination.setAdapter(adapter);
            });
        }).start();
    }

    private ArrayList<AirportDto> fetchAirports() {
        Log.d("Airport API", "Starting fetchFlights()");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.aviationstack.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AviationStackApi api = retrofit.create(AviationStackApi.class);

        Log.e("Airport API", "Making API call to getAirports");
        Call<AirportsResponse> call = api.getAirports(API_KEY);


        call.enqueue(new Callback<AirportsResponse>() {
            @Override
            public void onResponse(Call<AirportsResponse> call, Response<AirportsResponse> response) {
                Log.e("Airport API", "onResponse() triggered");

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Airport API", "API call successful");

                    List<AirportDto> airports = response.body().getData();
                    Log.d("Airport API", "Fetched " + (airports != null ? airports.size() : 0) + " airports");

                    List<String> airportNames = new ArrayList<>();
                    for (AirportDto airport : airports) {
                        if (airport.getName() != null && airport.getIataCode() != null) {
                            String formatted = airport.getName() + " (" + airport.getIataCode() + ")";
                            Log.e("Airport API", "Adding airport: " + formatted);
                            airportNames.add(formatted);
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            MainActivity.this,
                            android.R.layout.simple_dropdown_item_1line,
                            airportNames
                    );

                    AutoCompleteTextView etOrigin = findViewById(R.id.etOrigin);
                    AutoCompleteTextView etDestination = findViewById(R.id.etDestination);

                    etOrigin.setAdapter(adapter);
                    etDestination.setAdapter(adapter);

                    // Optional: show dropdown immediately for testing
                    // etOrigin.showDropDown();
                    // etDestination.showDropDown();
                } else {
                    Log.e("Airport API", "Response unsuccessful. Code: " + response.code());

                    if (response.errorBody() != null) {
                        try {
                            String error = response.errorBody().string();
                            Log.e("Airport API", "Error body: " + error);
                        } catch (IOException e) {
                            Log.e("Airport API", "Failed to read error body", e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AirportsResponse> call, Throwable t) {
                Log.e("Airport API", "API call failed", t);
            }

        });
        return null;
    }


    public void doAviationAPIfunctions(){
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
    public void sendIntentToGoogleMaps(){
        double latitude = 37.7749;
        double longitude = -122.4194;

        String flightName = "Flight ABC123";
        String uri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(" + Uri.encode(flightName) + ")";

        //String uri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(Flight+Location)";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.e("MainActivity", "Can not resolve activity google maps");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}