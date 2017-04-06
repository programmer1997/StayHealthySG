package com.example.ajinkya.stayhealthysg;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;

import java.util.Date;

/**
 * Created by Ajinkya on 25/3/17.
 */

public class SplashScreen extends Activity {
    // The delay for the splash screen to stay
    private int DELAY=4000;
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);


        // TO create the Delay
       new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this, Diseases.class);
                startActivity(i);

            }
        },DELAY);



    }



    public void general_alert()
    {
        int uni_notif;

        uni_notif = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("You have a notification!")
                .setContentText("New alert!")
                .setAutoCancel(true)
                ;

        Intent launchIntent = new Intent();
        launchIntent.setClassName("com.example.ajinkya.stayhealthysg", "com.example.ajinkya.stayhealthysg.Diseases");
        PendingIntent launchPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        builder.setContentIntent(launchPendingIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(uni_notif, builder.build());

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
        PendingIntent launchPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        builder.setContentIntent(launchPendingIntent);


        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(uni_notif, builder.build());

    }

    private void malaria_alert()
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
        launchIntent.setClassName("com.example.ajinkya.stayhealthysg", ".Malaria");
        PendingIntent launchPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        builder.setContentIntent(launchPendingIntent);


        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(uni_notif, builder.build());

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



}




