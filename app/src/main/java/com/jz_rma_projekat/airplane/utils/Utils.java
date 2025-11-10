package com.jz_rma_projekat.airplane.utils;

import android.Manifest;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.jz_rma_projekat.airplane.database.AppDatabase;
import com.jz_rma_projekat.airplane.database.api_models.AirlineResponse;
import com.jz_rma_projekat.airplane.database.dao.AirlineDao;
import com.jz_rma_projekat.airplane.database.dto.AirlineDto;
import com.jz_rma_projekat.airplane.database.entities.AirlineEntity;
import com.jz_rma_projekat.airplane.database.entities.AirportEntity;
import com.jz_rma_projekat.airplane.database.entities.FlightEntity;
import com.jz_rma_projekat.airplane.network.AviationStackApi;
import com.jz_rma_projekat.airplane.network.RetrofitClient;
import com.jz_rma_projekat.airplane.utils.mappers.AirlineMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



// Based on API restrictions this Uclass tils is introduced
public class Utils {

    public static File getAirlinesCsvFile(Application app) {
        return new File(app.getExternalFilesDir(null), "airlines.csv");
    }

    public static File getAirportsCsvFile(Application app) {
        return new File(app.getExternalFilesDir(null), "airports.csv");
    }

    public static File getFlightsCsvFile(Application app) {
        return new File(app.getExternalFilesDir(null), "airports.csv");
    }

    public static void appendAirlinesToCsvFile(Application app, List<AirlineEntity> airlines) {
        File csvFile = getAirlinesCsvFile(app);
        boolean exists = csvFile.exists();

        try (FileWriter fw = new FileWriter(csvFile, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            // write header only once
            if (!exists) {
                bw.write("AirlineId,AirlineName,IATACode,ICAOCode,Callsign,HubCode,CountryISO2,CountryName,DateFounded,FleetSize,FleetAverageAge,Status,Type,IATAPrefixAccounting");
                bw.newLine();
            }

            for (AirlineEntity a : airlines) {
                String line = String.join(",",
                        safe(a.getAirlineId()), safe(a.getAirlineName()), safe(a.getIataCode()),
                        safe(a.getIcaoCode()), safe(a.getCallsign()), safe(a.getHubCode()),
                        safe(a.getCountryIso2()), safe(a.getCountryName()), safe(a.getDateFounded()),
                        safe(a.getFleetSize()), safe(a.getFleetAverageAge()), safe(a.getStatus()),
                        safe(a.getType()), safe(a.getIataPrefixAccounting())
                );
                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            Log.e("Utils", "Error writing CSV", e);
        }
    }

    private static String safe(String s) {
        return s == null ? "N/A" : s.replace(",", " "); // prevent CSV break
    }

    public static void appendAirportsToCsvFile(Application app, List<AirportEntity> airports) {
        File csvFile = getAirportsCsvFile(app);
        boolean exists = csvFile.exists();

        try (FileWriter fw = new FileWriter(csvFile, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            if (!exists) {
                bw.write("AirportId,AirportGmt,AirportId2, " +
                        "AirportIataCode,AirportCityIataCode," +
                        "AirportIcaoCode,CountryIso2,GeonameId,Latitude," +
                        "Longitude,Name,Country," +
                        "PhoneNumber,Timezone");
                bw.newLine();
            }

            for (AirportEntity entity : airports) {
                String line = String.join(",",
                        safe(entity.getId()),
                        safe(entity.getGmt()),
                        safe(entity.getAirportId() + ""),
                        safe(entity.getIataCode()),
                        safe(entity.getCityIataCode()),
                        safe(entity.getIcaoCode()),
                        safe(entity.getCountryIso2()),
                        safe(entity.getGeonameId() + ""),
                        safe(entity.getLatitude() + ""),
                        safe(entity.getLongitude() + ""),
                        safe(entity.getName()),
                        safe( entity.getCountry()),
                        safe( entity.getPhoneNumber()),
                        safe(entity.getTimezone())
                );
                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            Log.e("Utils", "Error writing CSV", e);
        }
    }

    public static void appendFlightsToCsvFile(Application app, List<FlightEntity> flights) {
        File csvFile = getFlightsCsvFile(app);
        boolean exists = csvFile.exists();

        try (FileWriter fw = new FileWriter(csvFile, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            if (!exists) {
                bw.write("FlightId,ArrivalAirportId,ArrivalStatus,ArrivalAirport, " +
                        "AirlineName,AirlineAiata," +
                        "AirlineIcao,ArrivalGate,ArrivalIata,ArrivalTerminal," +
                        "ArrivalIcao,ArrivalScheduled,ArrivalTime," +
                        "DepartureAirport,DepartureAirportId," +
                        "DeartureGate, DepartureIcao, DepartureIata," +
                        "De[artireScheduled, DepartureTerminal, DepartureTime");
                bw.newLine();
            }

            for (FlightEntity entity : flights) {
                String line = String.join(",",
                        safe(entity.getId()+""),
                        safe(entity.getArrivalAirportId()),
                        safe(entity.getStatus()),
                        safe(entity.getArrivalAirport()),
                        safe(entity.getAirlineName()),
                        safe(entity.getAirlineIata()),
                        safe(entity.getAirlineIcao()),
                        safe(entity.getArrivalGate()),
                        safe(entity.getArrivalIata()),
                        safe(entity.getArrivalTerminal()),
                        safe(entity.getArrivalIcao()),
                        safe(entity.getArrivalScheduled()),
                        safe(entity.getArrivalTime()),
                        safe(entity.getDepartureAirport()),
                        safe(entity.getDepartureAirportId()),
                        safe(entity.getDepartureGate()),
                        safe(entity.getDepartureIcao()),
                        safe(entity.getDepartureIata()),
                        safe(entity.getDepartureIcao()),
                        safe(entity.getDepartureScheduled()),
                        safe(entity.getDepartureTerminal()),
                        safe(entity.getDepartureTime())
                );
                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            Log.e("Utils", "Error writing CSV", e);
        }
    }



    public static void appendAirlinesToJson(Application app, List<AirlineEntity> airlines) {
        File jsonFile = new File(app.getExternalFilesDir(null), "airlines.json");
        boolean exists = jsonFile.exists();

        try (FileWriter fw = new FileWriter(jsonFile, true);
             JsonWriter writer = new JsonWriter(fw)) {

            if (!exists) {
                fw.write("[");
            } else {
                fw.write(",");
            }

            Gson gson = new Gson();
            for (AirlineEntity airline : airlines) {
                gson.toJson(airline, AirlineEntity.class, writer);
            }

        } catch (IOException e) {
            Log.e("Utils", "Error writing JSON", e);
        }
    }

    public static void finalizeJson(Application app) {
        File jsonFile = new File(app.getExternalFilesDir(null), "airlines.json");
        try (FileWriter fw = new FileWriter(jsonFile, true)) {
            fw.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void shareAirlinesCsv(Application application) {
        File file = new File(application.getExternalFilesDir(null), "airlines.csv");

        Uri uri = FileProvider.getUriForFile(
                application.getApplicationContext(),
                application.getPackageName() + ".provider",
                file
        );

        Log.d("ShareURI", uri.toString());
        Log.d("ShareFile", file.getAbsolutePath());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/csv"); // MIME type (use "application/json" or others as needed)
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(application.getPackageManager()) != null) {
            application.startActivity(intent);
        } else {
            Log.e("Flights Tracker", "Share option is currently unavailable");
        }

    }
    public static void saveAirlinesCsvToDownloads(Application application) {
        File csvFile = new File(application.getExternalFilesDir(null), "airlines.csv");

        if (!csvFile.exists()) {
            Toast.makeText(application.getApplicationContext(), "CSV file not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = "airlines.csv";

        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
        values.put(MediaStore.Downloads.MIME_TYPE, "text/csv");
        values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        ContentResolver resolver = application.getContentResolver();
        Uri uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);

        if (uri != null) {
            try (OutputStream out = resolver.openOutputStream(uri);
                 FileInputStream in = new FileInputStream(csvFile)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }

                Toast.makeText(application.getApplicationContext(), "CSV saved to Downloads", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(application.getApplicationContext(), "Failed to save CSV: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(application.getApplicationContext(), "Failed to create file in Downloads", Toast.LENGTH_SHORT).show();
        }
    }

    private static void copyCsvToDownloads(Application application, File csvFile, String fileName) {
        if (!csvFile.exists()) {
            Toast.makeText(application.getApplicationContext(), fileName + " not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
        values.put(MediaStore.Downloads.MIME_TYPE, "text/csv");
        values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        ContentResolver resolver = application.getApplicationContext().getContentResolver();
        Uri uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);

        if (uri != null) {
            try (OutputStream out = resolver.openOutputStream(uri);
                 FileInputStream in = new FileInputStream(csvFile)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }

                Toast.makeText(application.getApplicationContext(), fileName + " saved to Downloads", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(application.getApplicationContext(), "Failed to save " + fileName + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(application.getApplicationContext(), "Failed to create " + fileName + " in Downloads", Toast.LENGTH_SHORT).show();
        }
    }

    public static void downloadAllCsvs(Application application) {
        File airportsCsv = new File(application.getExternalFilesDir(null), "airports.csv");
        File airlinesCsv = new File(application.getExternalFilesDir(null), "airlines.csv");
        File flightsCsv = new File(application.getExternalFilesDir(null), "flights.csv");

        copyCsvToDownloads(application, airportsCsv, "airports.csv");
        copyCsvToDownloads(application, airlinesCsv, "airlines.csv");
        copyCsvToDownloads(application, flightsCsv, "flights.csv");
    }

    private static void saveAirlinesJsonToExternalStorage(Application application, String jsonResponse) {
        // Check if the permission is granted
        if (ContextCompat.checkSelfPermission(application.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, proceed with saving the file
            writeJsonToFile(jsonResponse);
        } else {
            Log.e("Utils", "No permission to write to external storage");
        }
    }

    private static void writeJsonToFile(String jsonResponse) {
        File directory = new File(Environment.getExternalStorageDirectory(), "AviationApp");
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }

        File file = new File(directory, "airline.json");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(jsonResponse.getBytes());
            fos.close();
            Log.d("Airline API", "JSON saved to external storage: " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.e("Airline API", "Error saving JSON to external storage", e);
        }
    }

    private static void appendJsonToExternalStorage(Application application, String jsonResponse) {
        // Check if the permission is granted
        if (ContextCompat.checkSelfPermission(application.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

            File directory = new File(Environment.getExternalStorageDirectory(), "AviationApp");
            if (!directory.exists()) {
                directory.mkdirs();  // Create the directory if it doesn't exist
            }

            File file = new File(directory, "airline.json");

            try {
                // Open the file in append mode
                FileOutputStream fos = new FileOutputStream(file, true);  // 'true' means append
                fos.write(jsonResponse.getBytes());
                fos.write("\n".getBytes());  // Add a newline after each page's JSON data
                fos.close();
                Log.d("Airline API", "JSON appended to external storage: " + file.getAbsolutePath());
            } catch (IOException e) {
                Log.e("Airline API", "Error appending JSON to external storage", e);
            }
        } else {
           Log.e("Utils", "Permisssion to write to external storage not granted");
        }
    }

    private static void closeJsonArray() {
        File directory = new File(Environment.getExternalStorageDirectory(), "AviationApp");
        File file = new File(directory, "airline.json");

        try {
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write("\n]".getBytes());  // Close the array
            fos.close();
            Log.d("Airline API", "JSON array closed.");
        } catch (IOException e) {
            Log.e("Airline API", "Error closing JSON array", e);
        }
    }
}
