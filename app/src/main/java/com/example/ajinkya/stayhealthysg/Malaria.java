package com.example.ajinkya.stayhealthysg;
//<<<<<<< HEAD

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.Date;

//=======
//>>>>>>> 4bc3b5cffb47ff528d5e152ce936a7b7845d0c3f
/**
 * Created by Ajinkya on 24/3/17.
 */
public class Malaria extends AppCompatActivity implements OnMapReadyCallback{
    double currentLatitude = 1.3521;
    double currentLongitude = 103.8198;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private static final String TAG = Diseases.class.getSimpleName();
    MapFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.malaria);
        TabHost host=(TabHost) findViewById(R.id.malariaTabHost);
        host.setup();

        //About
        TabHost.TabSpec spec = host.newTabSpec("About");
        spec.setContent(R.id.malariaAbout);
        spec.setIndicator("About");
        host.addTab(spec);

        //Clusters
        spec = host.newTabSpec("Clusters");
        spec.setContent(R.id.malariaClusters);
        spec.setIndicator("Clusters");
        host.addTab(spec);

        //Symptoms
        spec = host.newTabSpec("Symptoms");
        spec.setContent(R.id.malariaSymptoms);
        spec.setIndicator("Symptoms");
        host.addTab(spec);

        //Prevention
        spec = host.newTabSpec("Prevention");
        spec.setContent(R.id.malariaPrevention);
        spec.setIndicator("Prevention");
        host.addTab(spec);

        //Treatment
        spec = host.newTabSpec("Treatment");
        spec.setContent(R.id.malariaTreatment);
        spec.setIndicator("Treatment");
        host.addTab(spec);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.malariaMapActivity);
        mapFragment.getMapAsync(this);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        malaria_alert();

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
                Intent intent = new Intent(Malaria.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        KML.addKML(googleMap,R.raw.malaria,getApplicationContext());

    }

    public void malaria_alert()
    {
        int uni_notif;

        uni_notif = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("You have a notification!")
                .setContentText("New MALARIA alert!")
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
