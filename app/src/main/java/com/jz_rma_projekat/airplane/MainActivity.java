package com.jz_rma_projekat.airplane;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.jz_rma_projekat.airplane.utils.Utils;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import com.google.android.material.snackbar.Snackbar;
import com.jz_rma_projekat.airplane.database.AppDatabase;
import com.jz_rma_projekat.airplane.database.api_models.AirlineResponse;
import com.jz_rma_projekat.airplane.database.dao.AirlineDao;
import com.jz_rma_projekat.airplane.database.dto.AirlineDto;
import com.jz_rma_projekat.airplane.database.dto.AirportDto;
import com.jz_rma_projekat.airplane.database.entities.AirlineEntity;
import com.jz_rma_projekat.airplane.databinding.ActivityMainBinding;
import com.jz_rma_projekat.airplane.database.entities.AirportEntity;
import com.jz_rma_projekat.airplane.network.AviationStackApi;
import com.jz_rma_projekat.airplane.network.RetrofitClient;
import com.jz_rma_projekat.airplane.ui.adapters.AirportAutoCompleteAdapter;
import com.jz_rma_projekat.airplane.ui.adapters.AirportListAdapter;
import com.jz_rma_projekat.airplane.ui.adapters.FlightListAdapter;
import com.jz_rma_projekat.airplane.ui.viewmodel.AirlineViewModel;
import com.jz_rma_projekat.airplane.ui.viewmodel.AirportViewModel;
import com.jz_rma_projekat.airplane.utils.mappers.AirlineMapper;
import com.jz_rma_projekat.airplane.ui.viewmodel.FlightViewModel;

//import androidx.core.app.ActivityCompat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
     private static final String DEBUG_TAG = "MainActivity";

    private static final int REQUEST_WRITE_STORAGE_PERMISSION = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1002;

    AirportViewModel airportViewModel;
    AirlineViewModel airlineViewModel;
    List<AirlineEntity> airlines;

    ArrayAdapter<String> airplanesAdapter;
    private AirportAutoCompleteAdapter airportautocompleteAdapter;

    private RecyclerView rvFlights;
    private FlightsAdapter adapter;
    private FlightViewModel flightViewModel;

    private AppDatabase db;

    Button showAirlinesBtn;
    Button downloadBtn;
    Button shareAirlinesBtn;
    Button downloadAllBtn;

    AutoCompleteTextView etCountry ;
    AutoCompleteTextView etOrigin ;
    AutoCompleteTextView etDestination;
    AutoCompleteTextView etAirplanes;

    ArrayList<AirportDto> airportList = new ArrayList<>();
    EditText etDate;
    Button btnSearchFlights;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private boolean mRequestingLocationUpdates = false;
    DrawerLayout drawerLayout;

    private AirportListAdapter airportAdapter;

    private FlightListAdapter flightAdapter;

    AirportEntity searchOrigin;
    AirportEntity searchDestination;
    Date searchDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createNotificationChannel();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

        } else {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE_PERMISSION);
        }

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

        shareAirlinesBtn = findViewById(R.id.btnShareAirlines);
        shareAirlinesBtn.setOnClickListener(v -> Utils.shareAirlinesCsv(getApplication()));

        downloadBtn = findViewById(R.id.btnDownloadAirlines);
        downloadBtn.setOnClickListener(v -> Utils.saveAirlinesCsvToDownloads(getApplication()));

        downloadAllBtn = findViewById(R.id.btnDownloadAll);
        downloadAllBtn.setOnClickListener(v -> Utils.downloadAllCsvs(getApplication()));


        showAirlinesBtn =  findViewById(R.id.btnShowAirlines);
        showAirlinesBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AirlinesListActivity.class);
            startActivity(intent);
        });

        //airplanesJsonBtn = findViewById(R.id.btnAirplanesJson);
        //airplanesJsonBtn.setOnClickListener(v -> downloadAllCsvs());


        // Only add the fragment if this is the first creation
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new FlightMapFragment())
                    .commit();
            // findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
        }


        etDate = findViewById(R.id.etDate);
        etAirplanes = findViewById(R.id.airplanes);
        btnSearchFlights = findViewById(R.id.btnSearchFlights);

        // Date picker
        etDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        searchDate = new Date(year, month, dayOfMonth);
                        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                        etDate.setText(selectedDate);
                        showFlightNotification("Selection date: ", selectedDate);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dialog.show();
        });

        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        // Navigation drawer menu click
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_airlines) {
                startActivity(new Intent(this, AirlinesListActivity.class));
            } else if (id == R.id.nav_airports) {
                startActivity(new Intent(this, AirportListActivity.class));
            } else if (id == R.id.nav_flights) {
                startActivity(new Intent(this, FlightsActivity.class));
            }

            drawerLayout.closeDrawers();
            return true;
        });

        Button btnViewAirports = findViewById(R.id.btnViewAirports);
        btnViewAirports.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AirportListActivity.class);
            startActivity(intent);
        });

        flightAdapter = new FlightListAdapter();
        // Initialize ViewModel
        flightViewModel = new ViewModelProvider(this).get(FlightViewModel.class);
        flightViewModel.getAllFlights().observe(this, flights -> {
            if (flights != null && !flights.isEmpty()) {
                flightAdapter.submitList(flights);
            }
        });

        airportAdapter = new AirportListAdapter();
        // Initialize ViewModel
        airportViewModel = new ViewModelProvider(this).get(AirportViewModel.class);

        // Observe the LiveData for airport data
        airportViewModel.getAllAirports().observe(this, airports -> {
            if (airports != null && !airports.isEmpty()) {
                // Update the RecyclerView with the airport list
                airportAdapter.submitList(airports);

                if (airportautocompleteAdapter == null) {
                    populateAirportsDropdown(airports);
                } else {
                    // LiveData emitted a new list — refresh it
                    airportautocompleteAdapter.updateAllAirports(airports);
                }
            }
        });

        Button btnViewFlights = findViewById(R.id.btnViewFlights);
        btnViewFlights.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FlightsActivity.class);
            startActivity(intent);
        });

        etAirplanes = findViewById(R.id.airplanes);
        airplanesAdapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>()
        );
        etAirplanes.setAdapter(airplanesAdapter);

        // Initialize ViewModel
        airlineViewModel = new ViewModelProvider(this).get(AirlineViewModel.class);
        airlineViewModel.getAllAirlines().observe(this, airlines -> {
            if (airlines != null && !airlines.isEmpty()) {
                this.airlines = airlines;


                // Convert the list of AirlineDto to a list of Strings
                List<String> airlineStrings = airlines.stream()
                        .map(airline -> String.format("%s (%s) - %s - %s",
                                airline.getAirlineName(),
                                airline.getIataCode(),
                                airline.getCountryName(),
                                airline.getFleetSize()))
                        .collect(Collectors.toList());
                airplanesAdapter.clear();
                airplanesAdapter.addAll(airlineStrings);
                airplanesAdapter.notifyDataSetChanged();
            }
        });

        // Search button logic
        btnSearchFlights.setOnClickListener(v -> {
            String searchDate = etDate.getText().toString().trim();

            if (searchOrigin == null || searchDestination == null || searchDate.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            } else {
                flightViewModel.searchFlights(searchOrigin, searchDestination, searchDate)
                        .observe(this, flights -> {
                            if (flights == null || flights.isEmpty()) {
                                Toast.makeText(this, "No flights found", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("JELENA", "Found flights for the search parameters");
                            }
                        });
            }
        });


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

    private void populateAirportsDropdown(List<AirportEntity> airports) {
        // Create the custom adapter to display airport names with IATA codes
        airportautocompleteAdapter = new AirportAutoCompleteAdapter(MainActivity.this, airports);
        //AirportAutoCompleteAdapter countryAdapter = new AirportAutoCompleteAdapter(MainActivity.this, airports);

        etCountry = findViewById(R.id.etCountry);
        etOrigin = findViewById(R.id.etOrigin);
        etDestination = findViewById(R.id.etDestination);

        // Set the custom adapter for both Origin and Destination fields
        //etOrigin.setThreshold(2);
        etOrigin.setAdapter(airportautocompleteAdapter);
        etOrigin.setThreshold(2);
        etOrigin.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                airportautocompleteAdapter.getFilter().filter(editable.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                airportautocompleteAdapter.getFilter().filter(s);
            }
        });
        //etDestination.setThreshold(2);
        etDestination.setAdapter(airportautocompleteAdapter);
        //etCountry.setAdapter(countryAdapter);

        // Handling item clicks for origin
        etOrigin.setOnItemClickListener((parent, view, position, id) -> {
            AirportEntity selectedAirport = (AirportEntity) parent.getAdapter().getItem(position);
            Log.e("Selected Airport", "Name: " + selectedAirport.getName() + ", IATA: " + selectedAirport.getIataCode());
            searchOrigin = selectedAirport;
        });

        // Handling item clicks for destination
        etDestination.setOnItemClickListener((parent, view, position, id) -> {
            AirportEntity selectedAirport = (AirportEntity) parent.getAdapter().getItem(position);
            Log.e("Selected Destination", "Name: " + selectedAirport.getName() + ", IATA: " + selectedAirport.getIataCode());
            searchDestination = selectedAirport;
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "flight_alerts";
            String channelName = "Flight Alerts";
            String description = "Notifications for upcoming flights";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void showFlightNotification(String title, String message) {
        // Check if permission is granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {

            // Request permission if not granted (Android 13+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
               // ActivityCompat.requestPermissions(
               //         this,
               //         new String[]{Manifest.permission.POST_NOTIFICATIONS},
               //         NOTIFICATION_PERMISSION_REQUEST_CODE
                //);

                if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_REQUEST_CODE);
                }
            }
            return;
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "flight_alerts")
                .setSmallIcon(R.drawable.airplane_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
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

        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // User granted permission → you can safely send the notification now
                showFlightNotification("Flight Updates Enabled", "You will now receive flight alerts ✈️");
            } else {
                Toast.makeText(this, "Notification permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        //stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
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


    private File csvFile;

    private void initCsvFile() {
        csvFile = new File(getExternalFilesDir(null), "airports.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            // Write header once
            writer.append("iata_code,name,city,country,latitude,longitude\n");
            writer.flush();
            Log.d("Airport CSV", "CSV file initialized at: " + csvFile.getAbsolutePath());
        } catch (IOException e) {
            Log.e("Airport CSV", "Error initializing CSV", e);
        }
    }

    private void appendAirportsToCsv(List<AirportDto> airports) {
        try (FileWriter writer = new FileWriter(csvFile, true)) { // true = append mode
            for (AirportDto airport : airports) {
                // IATA Code
                writer.append(airport.getIataCode() != null ? airport.getIataCode() : "N/A").append(",");

                // Name (replacing commas with spaces)
                writer.append(airport.getName() != null ? airport.getName().replace(",", " ") : "N/A").append(",");

                // City (currently a placeholder, to be updated)
                //String city = airport.getCity() != null ? airport.getCity().replace(",", " ") : "N/A";
                //writer.append(city).append(",");

                // Country (replacing commas with spaces)
                writer.append(airport.getCountry() != null ? airport.getCountry().replace(",", " ") : "N/A").append(",");

                // Latitude (to be updated, currently empty string replaced with N/A)
                //String latitude = airport.getLatitude() != null ? String.valueOf(airport.getLatitude()) : "N/A";
                //writer.append(latitude).append(",");

                // Longitude (to be updated, currently empty string replaced with N/A)
                //String longitude = airport.getLongitude() != null ? String.valueOf(airport.getLongitude()) : "N/A";
                //writer.append(longitude).append("\n");
            }
            writer.flush();
            Log.d("Airport CSV", "Appended " + airports.size() + " airports to CSV.");
        } catch (IOException e) {
            Log.e("Airport CSV", "Error appending to CSV", e);
        }
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