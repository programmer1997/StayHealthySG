package com.example.ajinkya.stayhealthysg;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.kml.KmlContainer;
import com.google.maps.android.kml.KmlLayer;
import com.google.maps.android.kml.KmlPlacemark;
import com.google.maps.android.kml.KmlPolygon;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import static com.example.ajinkya.stayhealthysg.R.id.map_activity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map_activity);
        mapFragment.getMapAsync(this);










    }




    public void onMapReady(GoogleMap googleMap) {

        KmlLayer kmlLayer= null;
        try {
            kmlLayer = new KmlLayer(googleMap, R.xml.denguekml,getApplicationContext());
            kmlLayer.addLayerToMap();
            KmlContainer container=kmlLayer.getContainers().iterator().next();
            KmlPlacemark placemark=container.getPlacemarks().iterator().next();
            KmlPolygon polygon=(KmlPolygon)placemark.getGeometry();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng latLng : polygon.getOuterBoundaryCoordinates()) {
                builder.include(latLng);
            }

            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), width, height, 1));

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3521, 103.8198 ), 11.0f));








        }
    }


