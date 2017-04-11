package com.example.ajinkya.stayhealthysg;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.R.string.no;
import static com.example.ajinkya.stayhealthysg.R.id.dengueMapActivity;
import static com.example.ajinkya.stayhealthysg.R.id.hazeMap;

/**
 * Created by BrigittaAngelina on 4/11/17.
 */

public class MarkerHaze extends AppCompatActivity implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback{
    private GoogleMap mMap;
    protected static final String TAG = MarkerHaze.class.getSimpleName();
    private Marker e; //east region(Bedok)
    private Marker n; //north region(Wooldlands)
    private Marker w; //west region(Jurong West)
    private Marker ne; //north east region(Hougang)
    private Marker c; //central region(Bukit Merah)

    static final LatLng east = new LatLng(1.323604,103.927341);
    static final LatLng north = new LatLng(1.438192,103.78896);
    static final LatLng west = new LatLng(1.34039,103.708988);
    static final LatLng northEast = new LatLng(1.361218,103.886253);
    static final LatLng central = new LatLng(1.281905,103.823918);

    private int east_psi;
    private int central_psi;
    private int south_psi;
    private int north_psi;
    private int west_psi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG,"kk");
        setContentView(R.layout.haze_map);

        setHaze();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.hazeMap);
        mapFragment.getMapAsync(this);



        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    }

    @Override
    public void onMapReady(GoogleMap map){
        Log.v(TAG,"bts");
        mMap = map;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3521, 103.8198 ), 11.0f));
        addMarkersToMap();

        mMap.setOnInfoWindowClickListener(this);


    }



    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();
    }

    public void addMarkersToMap(){
        Log.v(TAG,"adding marker to map");
        e= mMap.addMarker(new MarkerOptions()
                .position(east)
                .title("East Region")
                .snippet("PSI: "+east_psi));
        n= mMap.addMarker(new MarkerOptions()
                .position(north)
                .title("North Region")
                .snippet("PSI: "+north_psi));
        w= mMap.addMarker(new MarkerOptions()
                .position(west)
                .title("West Region")
                .snippet("PSI: "+west_psi));
        ne= mMap.addMarker(new MarkerOptions()
                .position(northEast)
                .title("North East Region")
                .snippet("PSI: "+south_psi));
        c= mMap.addMarker(new MarkerOptions()
                .position(central)
                .title("Central Region")
                .snippet("PSI: "+central_psi));

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
                Intent intent = new Intent(MarkerHaze.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
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
    
}
