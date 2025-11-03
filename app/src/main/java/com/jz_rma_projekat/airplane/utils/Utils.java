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

import com.jz_rma_projekat.airplane.database.AppDatabase;
import com.jz_rma_projekat.airplane.database.api_models.AirlineResponse;
import com.jz_rma_projekat.airplane.database.dao.AirlineDao;
import com.jz_rma_projekat.airplane.database.dto.AirlineDto;
import com.jz_rma_projekat.airplane.database.entities.AirlineEntity;
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

    public static void fetchAndSaveAllAirlines(Application application) {
        AviationStackApi api = RetrofitClient.getApi();
        Log.d("Airline API", "Starting airline fetch from offset 0");
        fetchAirlinesPage(application, api, 0); // start from offset 0
    }

    // Recursive async function to fetch pages
       public static void fetchAirlinesPage(Application application, AviationStackApi api, int offset) {
        Call<AirlineResponse> call = api.getAirlines("07d66aaa5c32f0546552c090cd95403f", 100, offset);

        call.enqueue(new Callback<AirlineResponse>() {
            @Override
            public void onResponse(Call<AirlineResponse> call, Response<AirlineResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AirlineResponse data = response.body();
                    List<AirlineDto> airlines = data.getData();
                    int total = data.getPagination().getTotal();

                    if (airlines != null && !airlines.isEmpty()) {
                        saveAirlinesToDatabase(application, airlines);  // Save to Room Database
                        Log.d("Airline API", "Saved " + airlines.size() +
                                " airlines (offset=" + offset + ")");
                        appendAirlinesToCsv(application, airlines);
                        appendJsonToExternalStorage(application, response.body().toString());
                    }

                    // Continue fetching the next page
                    //int nextOffset = offset + 100;
                    int nextOffset = offset + 1000; // just fot development purposes because of the api limits are invalved, return to 100 for prod
                    if (nextOffset < total) {
                        fetchAirlinesPage(application, api, nextOffset); // recursive async call
                    } else {
                        Log.d("Airline API", "✅ All airlines fetched!");
                        closeJsonArray();
                        //AirlineDao dao = db.airlineDao();
                        //List<AirlineEntity> entities = dao.getAllAirlines();
                        //List<AirlineDto> allAirlines = AirlineMapper.toDtoList(entities);
                    }

                } else {
                    Log.e("Airline API", "❌ Response unsuccessful. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AirlineResponse> call, Throwable t) {
                Log.e("Airline API", "❌ Request failed: " + t.getMessage());
                // TODO: make some tost or something
            }
        });
    }

    public static void saveAirlinesToDatabase(Application application, List<AirlineDto> airlineDtos) {
        // Convert List<AirlineDto> to List<AirlineEntity> using the AirlineMapper
        List<AirlineEntity> airlineEntities = AirlineMapper.toEntityList(airlineDtos);

        // Insert into the database asynchronously
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(application.getApplicationContext());
            AirlineDao dao = db.airlineDao();
            dao.insertAirlines(airlineEntities);
        }).start();
    }

    public static void appendAirlinesToCsv(Application application, List<AirlineDto> airlines) {
        File csvFile = new File(application.getExternalFilesDir(null), "airlines.csv");

        try (FileWriter fw = new FileWriter(csvFile, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            for (AirlineDto a : airlines) {
                String line = String.join(",",
                        a.getAirlineId(),
                        a.getAirlineName(),
                        a.getIataCode(),
                        a.getIcaoCode(),
                        a.getCallsign(),
                        a.getHubCode(),
                        a.getCountryIso2(),
                        a.getCountryName(),
                        a.getDateFounded(),
                        a.getFleetSize(),
                        a.getFleetAverageAge(),
                        a.getStatus(),
                        a.getType(),
                        a.getIataPrefixAccounting()
                );
                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void shareAirlinesCsv(Application application) {
        File csvFile = new File(application.getExternalFilesDir(null), "airlines.csv");

        if (!csvFile.exists()) {
            Toast.makeText(application.getApplicationContext(), "CSV file not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri fileUri = FileProvider.getUriForFile(
                application.getApplicationContext(),
                application.getPackageName() + ".provider",
                csvFile
        );

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        application.startActivity(Intent.createChooser(intent, "Download or share CSV"));
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

        copyCsvToDownloads(application, airportsCsv, "airports.csv");
        copyCsvToDownloads(application, airlinesCsv, "airlines.csv");
    }


    private static final int REQUEST_WRITE_STORAGE_PERMISSION = 1;
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
