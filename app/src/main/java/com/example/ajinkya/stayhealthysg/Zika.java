package com.example.ajinkya.stayhealthysg;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Date;

/**
 * Created by rach on 25-Mar-17.
 */

public class Zika extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private static final String TAG = Diseases.class.getSimpleName();
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

        //Clusters
        spec = host.newTabSpec("Clusters");
        spec.setContent(R.id.zikaClusters);
        spec.setIndicator("Clusters");
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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.zikaMapActivity);
        mapFragment.getMapAsync(this);

        zika_alert();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(Zika.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        KML.addKML(googleMap, R.raw.zika, getApplicationContext());
    }



    private void zika_alert()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("pref_zika", true)) {
            int uni_notif;

            uni_notif = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("You have a notification!")
                    .setContentText("New ZIKA alert!")
                    .setAutoCancel(true);


            Intent launchIntent = new Intent();
            launchIntent.setClassName("com.example.ajinkya.stayhealthysg", "com.example.ajinkya.stayhealthysg.Zika");
            PendingIntent launchPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(launchPendingIntent);


            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(uni_notif, builder.build());
        }
    }
}
