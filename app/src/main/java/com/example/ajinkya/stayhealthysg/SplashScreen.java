package com.example.ajinkya.stayhealthysg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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
    }




