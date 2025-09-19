package com.jz_rma_projekat.airplane;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flight_map_fragment, new FlightMapFragment())
                .commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double latitude = 37.7749;
        double longitude = -122.4194;
        LatLng flightLocation = new LatLng(latitude, longitude);

        // Custom marker icon
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.airplane_icon);

        mMap.addMarker(new MarkerOptions()
                .position(flightLocation)
                .title("Flight ABC123")
                .icon(icon));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(flightLocation, 15));
    }
}
