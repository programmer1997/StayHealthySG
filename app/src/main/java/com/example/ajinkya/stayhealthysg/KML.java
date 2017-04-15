package com.example.ajinkya.stayhealthysg;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.kml.KmlPlacemark;
import com.google.maps.android.data.kml.KmlPolygon;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.ajinkya.stayhealthysg.Diseases.currentLatitude;
import static com.example.ajinkya.stayhealthysg.Diseases.currentLongitude;

/**
 * Created by Ajinkya on 10/4/17.
 * This is a class with all the function related to Adding KML layer and checking whether your location lies in the KML polygon.
 */

public abstract  class KML extends AppCompatActivity {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("You have a notification!")
            .setContentText("New alert!")
            .setAutoCancel(true)
            ;




    /*

    This is the function used to add a KML layer on the map passed as argument.
    The second argument is the integer reference to the kml file for the respective disease eg. R.raw.dengue
    The code also narrows down the focus to Singapore.
    */

    public static void addKML(GoogleMap googleMap, int ref, Context context){
        try{
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3521, 103.8198 ), 11.0f));
            LatLng singapore = new LatLng(currentLatitude, currentLongitude);
            googleMap.addMarker(new MarkerOptions().position(singapore).title("Marker is on"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(singapore));

            KmlLayer kml = new KmlLayer(googleMap,ref,context);
            kml.addLayerToMap();

        } catch(XmlPullParserException e){

            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    /*
    This a method which checks whether your present location is in any of the clusters
    For presentation, the mock location layLngTest has been included
     */
    public static void checkLocationInCluster(GoogleMap googleMap,int ref,Context context){
        LatLng latLngTest=new LatLng(103.9416328865264,1.349915568981681);

        try {
            KmlLayer kmlLayer = new KmlLayer(googleMap,ref,context);
            ArrayList<KmlPolygon> polygonsInLayer = getPolygons(kmlLayer.getContainers());
            boolean isInside = liesOnPolygon(polygonsInLayer, latLngTest);
            if(isInside==false) ;
            //NotificationManager notificationManager = (NotificationManager)
            //getSystemService(NOTIFICATION_SERVICE);
            // notificationManager.notify(0,builder.build());

        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }







    // a function To get all the containers in the KML Document

    private  static ArrayList getPolygons(Iterable<KmlContainer> containers) {
        ArrayList<KmlPolygon> polygons = new ArrayList<>();

        if (containers == null) {
            return polygons;
        }

        for (KmlContainer container : containers) {
            polygons.addAll(getPlacemarks(container));
        }

        return polygons;
    }

    // a function to get all the placemarks in the container

    private  static ArrayList<KmlPolygon> getPlacemarks(KmlContainer container) {
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


    // the function To check for each polygon

    private static boolean liesOnPolygon(ArrayList<KmlPolygon> polygons, LatLng test) {
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

    // the function to check  If a point lies in the given polygon

    private static boolean boundaries(KmlPolygon polygon, LatLng test) {
        boolean lies = false;

        if (polygon == null || test == null) {
            return lies;
        }


        ArrayList<LatLng> outerBoundary = (ArrayList) polygon.getOuterBoundaryCoordinates();
        lies = PolyUtil.containsLocation(test, outerBoundary, true);

        if (lies) {
            ArrayList<ArrayList<LatLng>> innerBoundaries = (ArrayList) polygon.getInnerBoundaryCoordinates();
            if (innerBoundaries != null) {
                for (ArrayList<LatLng> innerBoundary : innerBoundaries) {

                    if (PolyUtil.containsLocation(test, innerBoundary, true)) {
                        lies = false;
                        break;
                    }
                }
            }
        }

        return lies;
    }



}


