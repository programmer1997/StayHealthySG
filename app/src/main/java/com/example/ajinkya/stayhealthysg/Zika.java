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

import static com.example.ajinkya.stayhealthysg.R.id.zikaMapActivity;

/**
 * Created by rach on 25-Mar-17.
 */

public class Zika extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    double currentLatitude = 1.3521;
    double currentLongitude = 103.8198;
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

        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        final String locationProvider = locationManager.GPS_PROVIDER;
        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                if(status != LocationProvider.AVAILABLE) {
                    try {
                        Location lastKnowLocation = locationManager.getLastKnownLocation(provider);
                        currentLatitude = lastKnowLocation.getLatitude();
                        currentLongitude = lastKnowLocation.getLongitude();
                    } catch (SecurityException e){
                        e.printStackTrace();
                    }
                }
            }

            public void onProviderEnabled(String provider) {
                try {
                    Location location = locationManager.getLastKnownLocation(provider);
                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();
                } catch(SecurityException e){
                    e.printStackTrace();
                }
            }

            public void onProviderDisabled(String provider) {
                try {
                    Location lastKnowLocation = locationManager.getLastKnownLocation(provider);
                    currentLatitude = lastKnowLocation.getLatitude();
                    currentLongitude = lastKnowLocation.getLongitude();
                } catch(SecurityException e){
                    e.printStackTrace();
                }
            }
        };
        try {
            locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        } catch (SecurityException e){
            e.printStackTrace();
        }
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(zikaMapActivity);
        mapFragment.getMapAsync(this);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void onConnected(Bundle connectionHint) {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                currentLatitude = mLastLocation.getLatitude();
                currentLatitude = mLastLocation.getLongitude();
            }
        } catch (SecurityException e){
            e.printStackTrace();
            Log.d(TAG, "Gobloks");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3521, 103.8198 ), 11.0f));
        LatLng sydney = new LatLng(currentLatitude, currentLongitude);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker is on"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        KmlLayer kmlLayer = null;
        try{
            kmlLayer = new KmlLayer(googleMap, R.raw.zika,getApplicationContext());
            kmlLayer.addLayerToMap();
        } catch(XmlPullParserException e){
            Log.d(TAG, "YO");
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
