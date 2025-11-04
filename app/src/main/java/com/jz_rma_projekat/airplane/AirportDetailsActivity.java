package com.jz_rma_projekat.airplane;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.jz_rma_projekat.airplane.FlightMapFragment;

import com.jz_rma_projekat.airplane.database.entities.AirportEntity;

public class AirportDetailsActivity extends AppCompatActivity {
    FlightMapFragment map;
    private TextView tvAirportName, tvAirportIATA, tvCountry, tvCityCode,
            tvIcaoCode, tvTimezone, tvCoordinates, tvPhoneNumber;

    AirportEntity airport;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_details);

        tvAirportName = findViewById(R.id.tvAirportName);
        tvAirportIATA = findViewById(R.id.tvAirportIATA);
        tvCountry = findViewById(R.id.tvCountry);
        tvCityCode = findViewById(R.id.tvCityCode);
        tvIcaoCode = findViewById(R.id.tvIcaoCode);
        tvTimezone = findViewById(R.id.tvTimezone);
        tvCoordinates = findViewById(R.id.tvCoordinates);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);


        airport = (AirportEntity) getIntent().getSerializableExtra("airport");

        if (airport != null) {
            tvAirportName.setText(airport.getName());
            tvAirportIATA.setText("IATA: " + airport.getIataCode());
            tvCountry.setText("Country: " + airport.getCountry());
            tvCityCode.setText("City IATA: " + airport.getCityIataCode());
            tvIcaoCode.setText("ICAO Code: " + airport.getIcaoCode());
            tvTimezone.setText("Timezone: " + airport.getTimezone());
            tvCoordinates.setText("Coordinates: " + airport.getLatitude() + ", " + airport.getLongitude());
            tvPhoneNumber.setText("Phone: " + airport.getPhoneNumber());
        }

        // Directions button
        Button btnDirections = findViewById(R.id.btnDirections);
        btnDirections.setOnClickListener(v -> openGoogleMapsDirections());

        if (savedInstanceState == null) {
            FrameLayout container = findViewById(R.id.fragment_container_details);
            container.setVisibility(View.VISIBLE); // Make it visible first

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(container.getId(), FlightMapFragment.newInstance(
                            airport.getLatitude(),
                            airport.getLongitude(),
                            airport.getName()
                    ))
                    .commit();
        }
    }

    private void openGoogleMapsDirections() {
        if (airport == null) return;
            double lat = airport.getLatitude();
            double lng = airport.getLongitude();

        String uri = "geo:" + airport.getLatitude() + "," + airport.getLongitude() +
                "?q=" + airport.getLatitude() + "," + airport.getLongitude() +
                "(" + Uri.encode(airport.getName()) + ")";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "Google Maps not installed", Toast.LENGTH_SHORT).show();
            }
    }
}

