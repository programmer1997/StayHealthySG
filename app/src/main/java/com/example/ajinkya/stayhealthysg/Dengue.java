package com.example.ajinkya.stayhealthysg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import static com.example.ajinkya.stayhealthysg.R.id.dengueMapActivity;

/**
 * Created by Ajinkya on 24/3/17.
 */

public class Dengue extends AppCompatActivity implements OnMapReadyCallback{
    MapFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dengue);
        TabHost host=(TabHost) findViewById(R.id.dengueTabHost);
        host.setup();

        //Symptoms
        TabHost.TabSpec spec = host.newTabSpec("Symptoms");
        spec.setContent(R.id.dengueSymptoms);
        spec.setIndicator("Symptoms");
        host.addTab(spec);

        //Prevention
        spec = host.newTabSpec("Prevention");
        spec.setContent(R.id.denguePrevention);
        spec.setIndicator("Prevention");
        host.addTab(spec);

        //Clusters
        spec = host.newTabSpec("Clusters");
        spec.setContent(R.id.dengueClusters);
        spec.setIndicator("Clusters");
        host.addTab(spec);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(dengueMapActivity);
        mapFragment.getMapAsync(this);




    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3521, 103.8198 ), 11.0f));

    }
}
