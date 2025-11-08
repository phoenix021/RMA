package com.jz_rma_projekat.airplane;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jz_rma_projekat.airplane.database.dto.AirportDto;
import com.jz_rma_projekat.airplane.utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DatasetActivity extends AppCompatActivity {

    Button downloadBtn;
    Button shareAirlinesBtn;
    Button downloadAllBtn;

    private File csvFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataset);

        shareAirlinesBtn = findViewById(R.id.btnShareAirlines);
        shareAirlinesBtn.setOnClickListener(v -> Utils.shareAirlinesCsv(getApplication()));

        downloadBtn = findViewById(R.id.btnDownloadAirlines);
        downloadBtn.setOnClickListener(v -> Utils.saveAirlinesCsvToDownloads(getApplication()));

        downloadAllBtn = findViewById(R.id.btnDownloadAllCsvs);
        downloadAllBtn.setOnClickListener(v -> Utils.downloadAllCsvs(getApplication()));

        //airplanesJsonBtn = findViewById(R.id.btnAirplanesJson);
        //airplanesJsonBtn.setOnClickListener(v -> downloadAllCsvs());
    }


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
}
