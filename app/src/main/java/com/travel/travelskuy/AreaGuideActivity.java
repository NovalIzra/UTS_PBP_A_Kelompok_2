package com.travel.travelskuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.travel.travelskuy.databinding.ActivityAreaGuideBinding;

public class AreaGuideActivity extends AppCompatActivity {

    ActivityAreaGuideBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        binding = DataBindingUtil.setContentView(this,R.layout.activity_area_guide);
        binding.getLifecycleOwner();

        binding.mapview.onCreate(savedInstanceState);
        binding.mapview.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        LatLng terminalsurabaya = new LatLng(-7.339965, 112.729585);
                        LatLng stasiunjogja = new LatLng(-7.831624, 110.383239);
                        LatLng bandarajakarta = new LatLng(-6.127129663106069, 106.65357751503457);
                        LatLng terminalbandung = new LatLng(-6.9158156780198015, 107.60252926901288);
                        LatLng stasiunsemarang = new LatLng(-6.96473618664681, 110.42796086038601);
                        CameraPosition position = new CameraPosition.Builder()
                                .target(terminalsurabaya)
                                .zoom(5)
                                .tilt(20)
                                .build();                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(terminalsurabaya)
                                .title("Terminal Surabaya"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(stasiunjogja)
                                .title("Stasuin Jogja"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(bandarajakarta)
                                .title("Bandara Jakarta"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(terminalbandung)
                                .title("Terminal Bandung"));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position(stasiunsemarang)
                                .title("Stasiun Semarang"));


                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 3000);


                    }
                });

            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mapview.onDestroy();

        binding = null;

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.mapview.onStart();
    }

    @Override



    public void onResume() {
        super.onResume();
        binding.mapview.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapview.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.mapview.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.mapview.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapview.onLowMemory();
    }

}