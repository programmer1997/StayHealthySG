package com.example.ajinkya.stayhealthysg;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Date;

/**
 * Created by YY on 6/4/2017.
 */

public class ClusterNotification extends AppCompatActivity
{


   public void general_alert(View view)
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
      PendingIntent launchPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent,PendingIntent.FLAG_ONE_SHOT);
      builder.setContentIntent(launchPendingIntent);

      // Add as notification
      NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      manager.notify(uni_notif, builder.build());


   }

   public void dengue_alert(View view)
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

   public void malaria_alert(View view)
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

   public void zika_alert(View view)
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
      PendingIntent launchPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent,PendingIntent.FLAG_ONE_SHOT);
      builder.setContentIntent(launchPendingIntent);


      // Add as notification
      NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      manager.notify(uni_notif, builder.build());

   }







}
