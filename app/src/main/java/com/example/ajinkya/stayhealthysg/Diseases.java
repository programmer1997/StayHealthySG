package com.example.ajinkya.stayhealthysg;

import android.*;
import android.Manifest;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.service.voice.VoiceInteractionSession;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.data.kml.KmlLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ajinkya.stayhealthysg.R.id.tab2;

/**
 * Created by Ajinkya on 11/3/17.
 */

public class Diseases extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    protected double currentLatitude = 1.3521;
    protected double currentLongitude = 103.8198;
    protected GoogleApiClient mGoogleApiClient;

    private int east_psi;
    private int central_psi;
    private int south_psi;
    private int north_psi;
    private int west_psi;

    private int uv_now;
    private int uv_minus_one;
    private int uv_minus_two;
    private int uv_minus_three;
    private int uv_minus_four;

    SharedPreferences.OnSharedPreferenceChangeListener listener;

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
        spec.setContent(R.id.tab2);
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
                    currentLatitude = location.getLati tude();
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
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String font_pref = sharedPref.getString("font_list_value", "");
        Log.v(TAG, font_pref);

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {

            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("font_list_value")) {
                    Log.v(TAG,"Sudah dipanggil font_list_value");
                    setFontTextSize(sharedPref.getString(key, ""));
                }
            }
        };

        sharedPref.registerOnSharedPreferenceChangeListener(listener);

        setHaze();
        setUV();
    }

    public void setFontTextSize(String size) {
        Log.v(TAG, "Chang the size to " + size);
        if(size == "Large") {
            TextView tv1 = (TextView) findViewById(R.id.text1);
            tv1.setTextSize(20);

            TextView tv2 = (TextView) findViewById(R.id.text1_1);
            tv2.setTextSize(20);

            Button tv3 = (Button) findViewById(R.id.dengueButton);
            tv3.setTextSize(20);

            TextView tv4 = (TextView) findViewById(R.id.text2);
            tv4.setTextSize(20);

            TextView tv5 = (TextView) findViewById(R.id.text2_1);
            tv5.setTextSize(20);

            Button tv6 = (Button) findViewById(R.id.malariaButton);
            tv6.setTextSize(20);

            TextView tv7 = (TextView) findViewById(R.id.text3);
            tv7.setTextSize(20);

            TextView tv8 = (TextView) findViewById(R.id.text3_1);
            tv8.setTextSize(20);

            Button tv9 = (Button) findViewById(R.id.zikaButton);
            tv9.setTextSize(20);
        }
        else if(size == "Small") {
            TextView tv1 = (TextView) findViewById(R.id.text1);
            tv1.setTextSize(12);

            TextView tv2 = (TextView) findViewById(R.id.text1_1);
            tv2.setTextSize(12);

            Button tv3 = (Button) findViewById(R.id.dengueButton);
            tv3.setTextSize(12);

            TextView tv4 = (TextView) findViewById(R.id.text2);
            tv4.setTextSize(12);

            TextView tv5 = (TextView) findViewById(R.id.text2_1);
            tv5.setTextSize(12);

            Button tv6 = (Button) findViewById(R.id.malariaButton);
            tv6.setTextSize(12);

            TextView tv7 = (TextView) findViewById(R.id.text3);
            tv7.setTextSize(12);

            TextView tv8 = (TextView) findViewById(R.id.text3_1);
            tv8.setTextSize(12);

            Button tv9 = (Button) findViewById(R.id.zikaButton);
            tv9.setTextSize(12);
        }
        else if(size == "Medium"){
            TextView tv1 = (TextView) findViewById(R.id.text1);
            tv1.setTextSize(16);

            TextView tv2 = (TextView) findViewById(R.id.text1_1);
            tv2.setTextSize(16);

            Button tv3 = (Button) findViewById(R.id.dengueButton);
            tv3.setTextSize(16);

            TextView tv4 = (TextView) findViewById(R.id.text2);
            tv4.setTextSize(16);

            TextView tv5 = (TextView) findViewById(R.id.text2_1);
            tv5.setTextSize(16);

            Button tv6 = (Button) findViewById(R.id.malariaButton);
            tv6.setTextSize(16);

            TextView tv7 = (TextView) findViewById(R.id.text3);
            tv7.setTextSize(16);

            TextView tv8 = (TextView) findViewById(R.id.text3_1);
            tv8.setTextSize(16);

            Button tv9 = (Button) findViewById(R.id.zikaButton);
            tv9.setTextSize(16);
        }
    }

    public void setHaze(){
        final String url = "https://api.data.gov.sg/v1/environment/psi";

        Log.v(TAG, "getGovHazeApi called");
        Log.v(TAG, url);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                Log.v(TAG,"Inilah hasil JSON " + response.toString());
                try {
                    east_psi = response.getJSONArray("items").getJSONObject(0).getJSONObject("readings").getJSONObject("psi_twenty_four_hourly").getInt("east");
                    central_psi = response.getJSONArray("items").getJSONObject(0).getJSONObject("readings").getJSONObject("psi_twenty_four_hourly").getInt("central");
                    south_psi = response.getJSONArray("items").getJSONObject(0).getJSONObject("readings").getJSONObject("psi_twenty_four_hourly").getInt("south");
                    north_psi = response.getJSONArray("items").getJSONObject(0).getJSONObject("readings").getJSONObject("psi_twenty_four_hourly").getInt("north");
                    west_psi = response.getJSONArray("items").getJSONObject(0).getJSONObject("readings").getJSONObject("psi_twenty_four_hourly").getInt("west");
                } catch(JSONException e){
                    Log.v(TAG, "There is some error");
                }
        }
    }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error " + error.getMessage());
                Log.v(TAG, "onErrorResponse Site Info Error: " + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.v(TAG, "Headers called");
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("api-key","zGgdZ6YESy0gzJxa0kaaMCWF1NSTA2Tt");
                return headers;
            }
        };

        req.setShouldCache(false);

        MySingleton.getInstance(this).getRequestQueue().getCache().clear();

        MySingleton.getInstance(this).addToRequestQueue(req);
        Log.v(TAG, "Mantap");
    }

    public void setUV(){
        final String url = "https://api.data.gov.sg/v1/environment/uv-index";

        Log.v(TAG, "setUV called");
        Log.v(TAG, url);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                Log.v(TAG,"Inilah hasil JSON " + response.toString());
                try {
                    uv_now = response.getJSONArray("items").getJSONObject(0).getJSONArray("index").getJSONObject(0).getInt("value");
                    Log.v(TAG, "This is the value of east: " + uv_now);
                    uv_minus_one = response.getJSONArray("items").getJSONObject(0).getJSONArray("index").getJSONObject(1).getInt("value");
                    uv_minus_two = response.getJSONArray("items").getJSONObject(0).getJSONArray("index").getJSONObject(2).getInt("value");
                    uv_minus_three = response.getJSONArray("items").getJSONObject(0).getJSONArray("index").getJSONObject(3).getInt("value");
                    uv_minus_four = response.getJSONArray("items").getJSONObject(0).getJSONArray("index").getJSONObject(4).getInt("value");
                } catch(JSONException e){
                    Log.v(TAG, "There is some error");
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error " + error.getMessage());
                Log.v(TAG, "onErrorResponse Site Info Error: " + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.v(TAG, "Headers called");
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("api-key","zGgdZ6YESy0gzJxa0kaaMCWF1NSTA2Tt");
                return headers;
            }
        };

        req.setShouldCache(false);

        MySingleton.getInstance(this).getRequestQueue().getCache().clear();

        MySingleton.getInstance(this).addToRequestQueue(req);

        Log.v(TAG, "Mantap");
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
            LatLng currentLocation = new LatLng(currentLatitude, currentLongitude);
            GoogleDirection.withServerKey("AIzaSyDYLhi_jAkNofIR1UxCp5pczQDjKzE2F0s").from(currentLocation).to(latLng)
                    .execute(new DirectionCallback(){
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody){
                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);
                            ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                            PolylineOptions polylineOptions = DirectionConverter.createPolyline(getApplicationContext(), directionPositionList, 5, Color.RED);
                            mMap.addPolyline(polylineOptions);
                        }
                        @Override
                        public void onDirectionFailure(Throwable t){

                        }
                    });

            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,13.5f));
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

