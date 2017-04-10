package com.example.ajinkya.stayhealthysg;

import android.*;
import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Ajinkya on 11/3/17.
 */

public class Diseases extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    protected double currentLatitude = 1.3521;
    protected double currentLongitude = 103.8198;
    protected GoogleApiClient mGoogleApiClient;

    protected Location mLastKnownLocation;
    protected GoogleMap mMap;
    protected static final String TAG = Diseases.class.getSimpleName();
    private boolean mLocationPermissionGranted;
    private static final int PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Call onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diseases);
        TabHost host=(TabHost) findViewById(R.id.mainTabHost);
        host.setup();

        //Diseases
        TabHost.TabSpec spec = host.newTabSpec("Diseases");
        spec.setContent(R.id.Diseases);
        spec.setIndicator("Diseases");
        host.addTab(spec);

        //SafestRoute
        spec = host.newTabSpec("SafestRoute");
        spec.setContent(R.id.safestRoute);
        spec.setIndicator("SafestRoute");
        host.addTab(spec);

        /*final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
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
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        */
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        mGoogleApiClient.connect();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        Log.v(TAG, "Call onConnected");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.safestRoute);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void onMapSearch(View view){
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if(location != null || location.equals("")){
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch(IOException e){
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(Diseases.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buttonClick(View view)
    {
        switch(view.getId())
        {
            case R.id.dengueButton:
                Intent intent1 = new Intent(Diseases.this,Dengue.class);
                startActivity(intent1);

                break;

            case R.id.malariaButton:
                Intent intent2 = new Intent(Diseases.this,Malaria.class);
                startActivity(intent2);

                break;

            case R.id.zikaButton:
                Intent intent3 = new Intent(Diseases.this,Zika.class);
                startActivity(intent3);
                break;
        }
    }

    public void dengue(View view){
        Intent intent = new Intent(Diseases.this, Dengue.class);
        startActivity(intent);

    }

    // evoked when you click the malaria button
    public void malaria(View view){
        Intent intent = new Intent(Diseases.this, Malaria.class);
        startActivity(intent);
    }

    // evoked when you click the zika button
    public void zika(View view){
        Intent intent = new Intent(Diseases.this, Zika.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.v(TAG, "Call onMapReady");

        mMap = googleMap;

        updateLocationUI();

        getDeviceLocation();

        KmlLayer kmlLayer = null;
        KmlLayer kmlLayerZika=null;
        KmlLayer kmlLayerMalaria=null;

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3521, 103.8198 ), 11.0f));
        Log.v(TAG, "Lang " + currentLatitude + "long " + currentLongitude);
        LatLng sydney = new LatLng(currentLatitude, currentLongitude);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker is on"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        try{
            kmlLayer = new KmlLayer(googleMap, R.raw.dengue,getApplicationContext());
            kmlLayer.addLayerToMap();
            kmlLayerZika = new KmlLayer(googleMap,R.raw.zika,getApplicationContext());
            kmlLayerZika.addLayerToMap();
            kmlLayerMalaria = new KmlLayer(googleMap,R.raw.malaria,getApplicationContext());
            kmlLayerMalaria.addLayerToMap();
        } catch(XmlPullParserException e){
            Log.d(TAG, "GG");
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void updateLocationUI() {
        Log.v(TAG, "Call updateLocationUI");
        if(mMap == null){
            return;
        }
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
        }

        if(mLocationPermissionGranted){
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        else{
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastKnownLocation = null;
        }
    }

    private void getDeviceLocation() {
        Log.v(TAG, "Call getDeviceLocation");
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mLocationPermissionGranted = true;
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
        }

        if(mLocationPermissionGranted){
            Log.v(TAG, "mLocationPermissionGranted");
            mLastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            currentLatitude = mLastKnownLocation.getLatitude();
            currentLongitude = mLastKnownLocation.getLongitude();
        }
    }

    public void general_alert()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("pref_infectious", true)) {
            int uni_notif;

            uni_notif = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("You have a notification!")
                    .setContentText("New alert!")
                    .setAutoCancel(true);

            Intent launchIntent = new Intent();
            launchIntent.setClassName("com.example.ajinkya.stayhealthysg", "com.example.ajinkya.stayhealthysg.Diseases");
            PendingIntent launchPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
            builder.setContentIntent(launchPendingIntent);

            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(uni_notif, builder.build());
        }
    }

    public void dengue_alert()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("pref_weather", true)) {
            int uni_notif;

            uni_notif = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("You have a notification!")
                    .setContentText("New DENGUE alert!")
                    .setAutoCancel(true);

            Intent launchIntent = new Intent();
            launchIntent.setClassName("com.example.ajinkya.stayhealthysg", "com.example.ajinkya.stayhealthysg.Dengue");
            PendingIntent launchPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
            builder.setContentIntent(launchPendingIntent);


            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(uni_notif, builder.build());
        }
    }

    private void malaria_alert()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("pref_haze", true)) {
            int uni_notif;

            uni_notif = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("You have a notification!")
                    .setContentText("New MALARIA alert!")
                    .setAutoCancel(true);


            Intent launchIntent = new Intent();
            launchIntent.setClassName("com.example.ajinkya.stayhealthysg", ".Malaria");
            PendingIntent launchPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
            builder.setContentIntent(launchPendingIntent);


            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(uni_notif, builder.build());
        }
    }

    private void zika_alert()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("pref_uv", true)) {
            int uni_notif;

            uni_notif = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("You have a notification!")
                    .setContentText("New ZIKA alert!")
                    .setAutoCancel(true);


            Intent launchIntent = new Intent();
            launchIntent.setClassName("com.example.ajinkya.stayhealthysg", "com.example.ajinkya.stayhealthysg.Zika");
            PendingIntent launchPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
            builder.setContentIntent(launchPendingIntent);


            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(uni_notif, builder.build());
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

