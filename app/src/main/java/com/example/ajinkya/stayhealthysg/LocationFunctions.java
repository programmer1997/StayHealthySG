package com.example.ajinkya.stayhealthysg;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ajinkya on 10/4/17.
 * This class contains all the functions to deal with the locations of the user
 */

public abstract class LocationFunctions extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    static double  currentLatitude = 1.3521;
     static double   currentLongitude = 103.8198;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private static final String TAG = Diseases.class.getSimpleName();
    final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    final String locationProvider = locationManager.GPS_PROVIDER;
    final LocationListener locationListener = new LocationListener() {

        // Called when location changed
        public void onLocationChanged(Location location) {
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
        }

        // Called when provider status changes. Either unable to fetch or available after long time.
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

        // When the provider is enabled

        public void onProviderEnabled(String provider) {
            try {
                Location location = locationManager.getLastKnownLocation(provider);
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();
            } catch(SecurityException e){
                e.printStackTrace();
            }
        }

        // When the provider is disabled
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


    public static LatLng getLocation(){
        return new LatLng(currentLatitude,currentLongitude);
    }



}
