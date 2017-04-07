package com.example.ajinkya.stayhealthysg;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

import static com.example.ajinkya.stayhealthysg.R.id.zikaMapActivity;

/**
 * Created by rach on 25-Mar-17.
 */

public class Zika extends AppCompatActivity implements OnMapReadyCallback{
    MapFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zika);
        TabHost host=(TabHost) findViewById(R.id.zikaTabHost);
        host.setup();

        //About
        TabHost.TabSpec spec = host.newTabSpec("About");
        spec.setContent(R.id.zikaAbout);
        spec.setIndicator("About");
        host.addTab(spec);

        //Symptoms
        spec = host.newTabSpec("Symptoms");
        spec.setContent(R.id.zikaSymptoms);
        spec.setIndicator("Symptoms");
        host.addTab(spec);

        //Prevention
        spec = host.newTabSpec("Prevention");
        spec.setContent(R.id.zikaPrevention);
        spec.setIndicator("Prevention");
        host.addTab(spec);

        //Treatment
        spec = host.newTabSpec("Treatment");
        spec.setContent(R.id.zikaTreatment);
        spec.setIndicator("Treatment");
        host.addTab(spec);

        //Clusters
        spec = host.newTabSpec("Clusters");
        spec.setContent(R.id.zikaClusters);
        spec.setIndicator("Clusters");
        host.addTab(spec);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
             .findFragmentById(zikaMapActivity);
        mapFragment.getMapAsync(this);




    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3521, 103.8198 ), 11.0f));

    }



    private void zika_alert()
    {

        int uni_notif;

        uni_notif = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("You have a notification!")
                .setContentText("New ZIKA alert!")
                .setAutoCancel(true)
                ;


        Intent launchIntent = new Intent();
        launchIntent.setClassName("com.example.ajinkya.stayhealthysg", "com.example.ajinkya.stayhealthysg.Zika");
        PendingIntent launchPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        builder.setContentIntent(launchPendingIntent);


        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(uni_notif, builder.build());

    }


}
