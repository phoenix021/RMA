package com.jz_rma_projekat.airplane;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import com.jz_rma_projekat.airplane.database.dto.AirportDto;
import com.jz_rma_projekat.airplane.ui.viewmodel.AirlineViewModel;
import com.jz_rma_projekat.airplane.ui.viewmodel.AirportViewModel;
import com.jz_rma_projekat.airplane.ui.viewmodel.FlightViewModel;
import com.jz_rma_projekat.airplane.utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class DatasetActivity extends AppCompatActivity {

    Button downloadAirlinesBtn;

    Button downloadAirportsBtn;
    Button downloadFlightsBtn;


    Button shareAirlinesBtn;
    Button downloadAllBtn;

    AirportViewModel airportViewModel;
    AirlineViewModel airlineViewModel;

    private FlightViewModel flightViewModel;

    private File csvFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataset);

        shareAirlinesBtn = findViewById(R.id.btnShareAirlines);
        shareAirlinesBtn.setOnClickListener(v -> {
            Utils.shareAirlinesCsv(getApplication());
            showFlightNotification("FlightTracker", "Download complete");
        });

        downloadAirlinesBtn = findViewById(R.id.btnDownloadAirlines);
        downloadAirlinesBtn.setOnClickListener(v -> {
            showAirlinesCsvPreview();
            showFlightNotification("FlightTracker", "Download complete");
        });
        downloadAirportsBtn = findViewById(R.id.btnDownloadAirports);
        downloadAirportsBtn.setOnClickListener(v -> {
            showAirportsCsvPreview();});

        downloadFlightsBtn = findViewById(R.id.btnDownloadFlights);
        downloadFlightsBtn.setOnClickListener(v -> {
            showFlightsCsvPreview();
            showFlightNotification("FlightTracker", "Download complete");
        });


        downloadAllBtn = findViewById(R.id.btnDownloadAllCsvs);
        downloadAllBtn.setOnClickListener(v -> {Utils.downloadAllCsvs(getApplication());
            showFlightNotification("FlightTracker", "Downloads complete");
        });

        airportViewModel = new ViewModelProvider(this).get(AirportViewModel.class);

        // Observe the LiveData for airport data
        airportViewModel.getAllAirports().observe(this, airports -> {
            if (airports != null && !airports.isEmpty()) {
                Utils.appendAirportsToCsvFile(getApplication(), airports);
                //showAirportsCsvPreview();
            }
        });

        flightViewModel = new ViewModelProvider(this).get(FlightViewModel.class);
        flightViewModel.getAllFlights().observe(this, flights -> {
            if (flights != null && !flights.isEmpty()) {
                Utils.appendFlightsToCsvFile(getApplication(), flights);
                //showFlightsCsvPreview();
            }
        });

        airlineViewModel = new ViewModelProvider(this).get(AirlineViewModel.class);
        airlineViewModel.getAllAirlines().observe(this, airlines -> {
            if (airlines != null && !airlines.isEmpty()) {
                Utils.appendAirlinesToCsvFile(getApplication(), airlines);
                //showAirlinesCsvPreview();
            }
        });
    }

    private void showAirlinesCsvPreview() {
       File csvFile = Utils.getAirlinesCsvFile(getApplication());
        if (!csvFile.exists()) {
            Toast.makeText(this, "No file to preview", Toast.LENGTH_SHORT).show();
            return;
        }

        createAlertDialogFromFile(csvFile);
    }

    private void showAirportsCsvPreview() {
        File csvFile = Utils.getAirportsCsvFile(getApplication());
        if (!csvFile.exists()) {
            Toast.makeText(this, "No file to preview", Toast.LENGTH_SHORT).show();
            return;
        }

        createAlertDialogFromFile(csvFile);
    }


    private void showFlightsCsvPreview() {
        File csvFile = Utils.getFlightsCsvFile(getApplication());
        if (!csvFile.exists()) {
            Toast.makeText(this, "No file to preview", Toast.LENGTH_SHORT).show();
            return;
        }

        createAlertDialogFromFile(csvFile);
    }

    private void createAlertDialogFromFile(File csvFile){
        try {
            List<String> lines = Files.readAllLines(csvFile.toPath());
            int previewCount = Math.min(10, lines.size());
            String preview = TextUtils.join("\n", lines.subList(0, previewCount));

            new AlertDialog.Builder(this)
                    .setTitle("CSV Preview")
                    .setMessage(preview)
                    //.setPositiveButton("Download", (d, w) -> Utils.saveAirlinesCsvToDownloads(getApplication()))
                    .setNegativeButton("Close", null)
                    .show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showFlightNotification(String title, String message) {
        // Check if permission is granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Notifications not enabled",Toast.LENGTH_LONG).show();
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
}
