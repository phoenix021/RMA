package com.jz_rma_projekat.airplane;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import com.google.android.material.snackbar.Snackbar;
import com.jz_rma_projekat.airplane.database.AppDatabase;
import com.jz_rma_projekat.airplane.database.dao.AirportDao;
import com.jz_rma_projekat.airplane.database.dto.AirportDto;
import com.jz_rma_projekat.airplane.databinding.ActivityMainBinding;
import com.jz_rma_projekat.airplane.database.entities.AirportEntity;
import com.jz_rma_projekat.airplane.database.api_models.AirportsResponse;
import com.jz_rma_projekat.airplane.utils.mappers.AirportMapper;

//import androidx.core.app.ActivityCompat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.os.Looper;
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
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://api.aviationstack.com/v1/";
    private static final String API_KEY = "07d66aaa5c32f0546552c090cd95403f"; // Replace with your key
    private static final String DEBUG_TAG = "MainActivity";

    private RecyclerView rvFlights;
    private FlightsAdapter adapter;

    private AppDatabase db;

    AutoCompleteTextView etCountry ;
    AutoCompleteTextView etOrigin ;
    AutoCompleteTextView etDestination;

    ArrayList<AirportDto> airportList = new ArrayList<>();
    EditText etDate;
    Button btnSearchFlights;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private boolean mRequestingLocationUpdates = false;

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
        etCountry = findViewById(R.id.etCountry);
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

        db = AppDatabase.getInstance(getApplicationContext());
        new Thread(() -> {
            AirportDao dao = db.airportDao();

            int count = dao.getCount(); // number of records
            boolean exists = dao.hasAny(); // true if at least one row exists

            Log.d("MainActivity", "Number of airports: " + count);
            Log.d("MainActivity", "Are there any airports? " + exists);
            // Call AviationStack API and show list
            List<AirportDto> airports = new ArrayList<AirportDto>(0);
            if (!exists) {
                fetchAndSaveAllAirports();
            } else {
                count = dao.getCount();
                Log.d("MainActivity", "Number of airports: " + count);
                List<AirportEntity> airportEntities = dao.getAllAirports();
                List<AirportDto> dtos =  AirportMapper.toDtoList(airportEntities);
                runOnUiThread(() -> populateAirportDropdowns(dtos));
            }
        }).start();

        //getAirportsFromDatabase();


        //mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //createLocationRequest();

        // mLocationCallback = new LocationCallback() {
        //     @Override
        //     public void onLocationResult(@NonNull LocationResult locationResult) {
        //         if (locationResult == null) return;
//
        //              for (Location location : locationResult.getLocations()) {
        //               double lat = location.getLatitude();
        //             double lon = location.getLongitude();
        //           Log.d("LOCATION", "Lat: " + lat + ", Lon: " + lon);
        //     }
        //}
        //};
    }


    //ideja, kada avion bude na sat vremena udaljen izbaci notifikaciju da korisnik krene na aerodrom i ne razmislja o kasnjenju itd
    // kreiranje zahteva za lokaciju:

    //Google Play Location API pruža i druge servise kao sto su:
    //        ◦ Dobijanje adrese na osnovu lokacije – geocoding,
    //        ◦ Definisanje oblasti u odnosu na neku lokaciju - geofences, .

void populateAirportDropdowns(List<AirportDto> airports){
    List<String> airportNames = new ArrayList<>(0);
    Set<String> uniqueCountries = new HashSet<>();
    for(AirportDto airport :airports)
    {
        if (airport.getName() != null && airport.getIataCode() != null) {
            String formatted = airport.getName() + " (" + airport.getIataCode() + ") Country:" + airport.getCountry();
            Log.e("MainActivity", "Populating airport: " + formatted);

            airportNames.add(formatted);
        }

        if (airport.getCountry() != null && !airport.getCountry().isEmpty()) {
            uniqueCountries.add(airport.getCountry());
        }
    }

    List<String> airportCountries = new ArrayList<>(uniqueCountries);
    Collections.sort(airportCountries);

    ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(
            MainActivity.this,
            android.R.layout.simple_dropdown_item_1line,
            airportCountries
    );

    ArrayAdapter<String> adapter = new ArrayAdapter<>(
            MainActivity.this,
            android.R.layout.simple_dropdown_item_1line,
            airportNames
    );

    etCountry = findViewById(R.id.etCountry);
    etOrigin = findViewById(R.id.etOrigin);
    etDestination = findViewById(R.id.etDestination);

    etOrigin.setAdapter(adapter);
    etDestination.setAdapter(adapter);
    etCountry.setAdapter(countryAdapter);
}


    private void loadAirportsForCountry(String country) {
        new Thread(() -> {
            AirportDao dao = db.airportDao();
            List<AirportEntity> airportEntities = dao.getAirportsByCountry(country);
            List<AirportDto> airports = AirportMapper.toDtoList(airportEntities);

            runOnUiThread(() -> {
                populateAirportDropdowns(airports);
                Toast.makeText(MainActivity.this,
                        "Loaded " + airports.size() + " airports in " + country,
                        Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
    protected void createLocationRequest() {
        // ✅ Correct usage of LocationRequest.Builder (new API)
        mLocationRequest = new LocationRequest.Builder(
                10000 // interval in ms
        )
                .setMinUpdateIntervalMillis(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private void startLocationUpdates() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);
        // ✅ Make sure you’ve checked runtime permissions before this call!
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Request permission if missing
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE
                );

            return;
        }
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                Looper.getMainLooper()
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // User granted permission — start updates
                startLocationUpdates();
            } else {
                // User denied permission
                Log.e("PERMISSION", "Location permission denied by user");
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
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

    public void getAirportsFromDatabase() {
        new Thread(() -> {
            List<AirportEntity> airportEntities = db.airportDao().getAllAirports();

            if (airportEntities == null || airportEntities.isEmpty()) {
                Log.d("Room", "No airports found in DB");
            } else {
                for (AirportEntity airport : airportEntities) {
                    Log.d("Room", "Airport: " + airport.name + " (" + airport.iataCode + ")");
                }
            }
            List<AirportDto> airportDtos = airportEntities.stream()
                    .map(entity -> new AirportDto(
                            entity.iataCode,
                            entity.name,
                            entity.country
                    ))
                    .collect(Collectors.toList());

            runOnUiThread(() -> {
                populateAirportDropdowns(airportDtos); // safely update AutoCompleteTextView
            });
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

    //provera konektcije
    //sa sajlajdova
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo1 = connMgr.getNetworkInfo(network);
            if (networkInfo1.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo1.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }
        Log.d(DEBUG_TAG, "Wifi connected: " + isWifiConn);
        Log.d(DEBUG_TAG, "Mobile connected: " + isMobileConn);


        return (networkInfo != null && networkInfo.isConnected());
    }

    private ArrayList<AirportDto> fetchAirports() {
        Log.d("Airport API", "Starting fetchFlights()");

        AviationStackApi api =RetrofitClient.getApi();

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

                    //saveAirportsToDatabase(airports);


                    runOnUiThread(() -> {
                        populateAirportDropdowns(airports); // safely update AutoCompleteTextView
                    });

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

    private void fetchAndSaveAllAirports() {
        AviationStackApi api = RetrofitClient.getApi();
        Log.d("Airport API", "Starting airport fetch from offset 0");
        fetchAirportsPage(api, 0); // start from offset 0
    }

    // Recursive async function to fetch pages
    private void fetchAirportsPage(AviationStackApi api, int offset) {
        Call<AirportsResponse> call = api.getAirports(API_KEY, 100, offset);

        call.enqueue(new Callback<AirportsResponse>() {
            @Override
            public void onResponse(Call<AirportsResponse> call, Response<AirportsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AirportsResponse data = response.body();
                    List<AirportDto> airports = data.getData();
                    int total = data.getPagination().getTotal();

                    if (airports != null && !airports.isEmpty()) {
                        saveAirportsToDatabase(airports);
                        Log.d("Airport API", "Saved " + airports.size() +
                                " airports (offset=" + offset + ")");
                    }

                    // Continue fetching the next page
                    int nextOffset = offset + 100;
                    if (nextOffset < total) {
                        fetchAirportsPage(api, nextOffset); // recursive async call
                    } else {
                        Log.d("Airport API", "✅ All airports fetched!");
                        // Once done, update your dropdowns safely on UI thread
                        AirportDao dao = db.airportDao();
                        List<AirportEntity> entities = dao.getAllAirports();
                        List<AirportDto> allAirports = AirportMapper.toDtoList(entities);
                        runOnUiThread(() -> {
                            populateAirportDropdowns(allAirports);
                        });
                    }

                } else {
                    Log.e("Airport API", "❌ Response unsuccessful. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AirportsResponse> call, Throwable t) {
                Log.e("Airport API", "💥 API call failed", t);
            }
        });
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