package com.example.ajinkya.stayhealthysg;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlPlacemark;
import com.google.maps.android.data.kml.KmlPolygon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ajinkya on 11/3/17.
 * this is the homepage of the applicaition
 * It has two tabs
 * One of the tab gives the options for selecting one of the diseases
 * The other tab is used for viewing the disease clusters.
 */

public class Diseases extends AppCompatActivity implements OnMapReadyCallback{



    ClusterNotification clusterNotification = new ClusterNotification();
    double currentLatitude = 1.3521;
    double currentLongitude = 103.8198;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private static final String TAG = Diseases.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        spec = host.newTabSpec("Disease Clusters");
        spec.setContent(R.id.safestRoute);
        spec.setIndicator("Disease Clusters");
        host.addTab(spec);




        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.safestRoute);
        mapFragment.getMapAsync(this);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        dengue_alert();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
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
                      }Address address = addressList.get(0);
                       LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                       //mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                      // mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                   }
           }




    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Add kml layers for all the diseases

        KML.addKML(googleMap,R.raw.dengue,getApplicationContext());
        KML.addKML(googleMap,R.raw.malaria,getApplicationContext());
        KML.addKML(googleMap,R.raw.zika,getApplicationContext());

        // Check whether location falls in clusters for all the diseases

        KML.checkLocationInCluster(googleMap,R.raw.dengue,getApplicationContext());
        KML.checkLocationInCluster(googleMap,R.raw.malaria,getApplicationContext());
        KML.checkLocationInCluster(googleMap,R.raw.zika,getApplicationContext());



    }












    //To get all the containers

    private ArrayList getPolygons(Iterable<KmlContainer> containers) {
        ArrayList<KmlPolygon> polygons = new ArrayList<>();

        if (containers == null) {
            return polygons;
        }

        for (KmlContainer container : containers) {
            polygons.addAll(getPlacemarks(container));
        }

        return polygons;
    }

    //to get all the placemarks

    private ArrayList<KmlPolygon> getPlacemarks(KmlContainer container) {
        ArrayList<KmlPolygon> polygons = new ArrayList<>();

        if (container == null) {
            return polygons;
        }

        Iterable<KmlPlacemark> placemarks = container.getPlacemarks();
        if (placemarks != null) {
            for (KmlPlacemark placemark : placemarks) {
                if (placemark.getGeometry() instanceof KmlPolygon) {
                    polygons.add((KmlPolygon) placemark.getGeometry());
                }
            }
        }

        if (container.hasContainers()) {
            polygons.addAll(getPolygons(container.getContainers()));
        }

        return polygons;
    }


    //

    private boolean liesOnPolygon(ArrayList<KmlPolygon> polygons, LatLng test) {
        boolean lies = false;

        if (polygons == null || test == null) {
            return lies;
        }

        for (KmlPolygon polygon : polygons) {
            if (boundaries(polygon, test)) {
                lies = true;
                break;
            }
        }

        return lies;
    }

    //

    private boolean boundaries(KmlPolygon polygon, LatLng test) {
        boolean lies = false;

        if (polygon == null || test == null) {
            return lies;
        }


        List<LatLng> outerBoundary = polygon.getOuterBoundaryCoordinates();
        lies = PolyUtil.containsLocation(test, outerBoundary,true);

        if (lies) {
            ArrayList<ArrayList<LatLng>> innerBoundaries = (ArrayList) polygon.getInnerBoundaryCoordinates();
            if (innerBoundaries != null) {
                for (ArrayList<LatLng> innerBoundary : innerBoundaries) {

                    if (PolyUtil.containsLocation(test, innerBoundary,true)) {
                        lies =false;
                        break;
                    }
                }
            }
        }

        return lies;
    }




    public void dengue_alert()
    {
        int uni_notif;

        uni_notif = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("You have a notification!")
                .setContentText("New DENGUE alert!")
                .setAutoCancel(true)
                ;

        Intent launchIntent = new Intent();
        launchIntent.setClassName("com.example.ajinkya.stayhealthysg", "com.example.ajinkya.stayhealthysg.Dengue");
        PendingIntent launchPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent,PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(launchPendingIntent);


        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(uni_notif, builder.build());

    }

}



