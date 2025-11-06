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

import com.jz_rma_projekat.airplane.database.entities.AirlineEntity;

public class AirlineDetailsActivity extends AppCompatActivity {
    private TextView tvAirlineName, tvAirlineIATA, tvCountry, tvCountryIso,
            tvIcaoCode, tvDateFounded, tvFleetSize, tvFleetAverageAge;

    private Button btnMoreDetails;

    AirlineEntity airline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airline_details);

        btnMoreDetails = findViewById(R.id.btnMoreDetails);

        tvAirlineName = findViewById(R.id.tvAirlineName);
        tvAirlineIATA = findViewById(R.id.tvAirlineIATA);
        tvCountry = findViewById(R.id.tvCountry);
        tvCountryIso = findViewById(R.id.tvCountryIso);
        tvIcaoCode = findViewById(R.id.tvIcaoCode);
        tvDateFounded = findViewById(R.id.tvDateFounded);
        tvFleetSize = findViewById(R.id.tvFleetSize);
        tvFleetAverageAge = findViewById(R.id.tvFleetAverageAge);


        airline = (AirlineEntity) getIntent().getSerializableExtra("airline");

        if (airline != null) {
            tvAirlineName.setText(airline.getAirlineName());
            tvAirlineIATA.setText("IATA: " + airline.getIataCode());
            tvCountry.setText("Country: " + airline.getCountryName());
            tvCountryIso.setText("Country iso: " + airline.getCountryIso2());
            tvIcaoCode.setText("ICAO Code: " + airline.getIcaoCode());
            tvDateFounded.setText("Date founded: " + airline.getDateFounded());
            tvFleetSize.setText("Fleet size: " + airline.getFleetSize());
            tvFleetAverageAge.setText("Fleet average age: " + airline.getFleetAverageAge());
        }

        btnMoreDetails.setOnClickListener(v -> {
            String query = "https://www.google.com/search?q=" + Uri.encode(airline.getAirlineName());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(query));
            startActivity(intent);
        });

    }

}

