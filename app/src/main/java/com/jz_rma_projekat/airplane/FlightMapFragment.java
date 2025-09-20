package com.jz_rma_projekat.airplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;


import org.maplibre.android.MapLibre;
import org.maplibre.android.WellKnownTileServer;
import org.maplibre.android.camera.CameraPosition;
import org.maplibre.android.camera.CameraUpdateFactory;
import org.maplibre.android.geometry.LatLng;
import org.maplibre.android.maps.MapView;
import org.maplibre.android.maps.MapLibreMap;
//import org.maplibre.android.maps.MapboxMap;
import org.maplibre.android.maps.OnMapReadyCallback;
import org.maplibre.android.maps.Style;
import org.maplibre.android.style.layers.PropertyFactory;
import org.maplibre.android.style.layers.SymbolLayer;
import org.maplibre.android.style.sources.GeoJsonSource;
import org.maplibre.geojson.Feature;


import org.maplibre.geojson.FeatureCollection;
import org.maplibre.geojson.Point;

public class FlightMapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private MapLibreMap mapLibreMap;
    private MapLibre mapLibreInstance;

    private static final String MARKER_ID = "marker-id";
    private static final String SOURCE_ID = "source-id";

    private static final double LATITUDE = 37.7749;
    private static final double LONGITUDE = -122.4194;

    ImageButton btnZoomIn;
    ImageButton btnZoomOut;
    ImageButton recenterBtn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.e("FlightMapFragment", "Before MapLibre.getInstance()");
        mapLibreInstance = MapLibre.getInstance(requireContext().getApplicationContext(), null, WellKnownTileServer.MapTiler);
        Log.e("FlightMapFragment", "After MapLibre.getInstance()");
        View view = inflater.inflate(R.layout.flight_map_fragment, container, false);
        mapView = view.findViewById(R.id.mapView);
        Log.e("FlightMapFragment", "Calling onCreate fro mapView");
        mapView.onCreate(savedInstanceState);
        Log.e("FlightMapFragment", "Calling getMapAsync");
        mapView.getMapAsync(this);


        btnZoomIn = view.findViewById(R.id.btnZoomIn);
        btnZoomOut = view.findViewById(R.id.btnZoomOut);
        recenterBtn = view.findViewById(R.id.btnRecenter);
        btnZoomIn.setOnClickListener(v -> {
            if (mapLibreMap != null) {
                mapLibreMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        btnZoomOut.setOnClickListener(v -> {
            if (mapLibreMap != null) {
                mapLibreMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });

        recenterBtn.setOnClickListener(v -> recenterOnFlight());
        return view;
    }

    private void recenterOnFlight() {
        if (mapLibreMap != null) {
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(LATITUDE, LONGITUDE))
                    .build();
            mapLibreMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 500, null);
        }
    }

    @Override
    public void onMapReady(@NonNull MapLibreMap mapLibreMap) {
        this.mapLibreMap = mapLibreMap;

       // String styleUrl = "https://demotiles.maplibre.org/style.json";
       // mapLibreMap.getUiSettings().setZoomControlsEnabled(true);
        mapLibreMap.getUiSettings().setZoomGesturesEnabled(true);
        mapLibreMap.setStyle(new Style.Builder().fromUri("https://demotiles.maplibre.org/style.json"), style -> {
            Log.e("MapLibre in FlightMapFragment", "Style loaded, moving camera...");
            // Move camera to location
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(LATITUDE, LONGITUDE))  // Note longitude first!
                    .zoom(1f)
                    .build();
            this.mapLibreMap.setCameraPosition(position);

            Bitmap airplaneBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.airplane_icon);
            style.addImage("airplane_icon", getBitmapFromVectorDrawable(getContext(), R.drawable.airplane_icon));

            // Create GeoJsonSource with one feature (your flight location)
            Feature feature = Feature.fromGeometry(Point.fromLngLat(LONGITUDE, LATITUDE));
            GeoJsonSource source = new GeoJsonSource(SOURCE_ID, FeatureCollection.fromFeature(feature));
            style.addSource(source);


            // Add SymbolLayer using the source and the custom icon
            SymbolLayer symbolLayer = new SymbolLayer(MARKER_ID, SOURCE_ID);
            symbolLayer.withProperties(
                    PropertyFactory.iconImage("airplane_icon"),
                    PropertyFactory.iconAllowOverlap(true),
                    PropertyFactory.iconIgnorePlacement(true),
                    PropertyFactory.iconSize(0.1f)
            );
            style.addLayer(symbolLayer);
        });
    }

    private Bitmap getBitmapFromVectorDrawable(Context context, @DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


    // Forward lifecycle events to the MapView
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
