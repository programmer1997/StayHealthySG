package com.example.ajinkya.stayhealthysg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Ajinkya on 25/3/17.
 */

public class StayHealthySG extends Activity {
    // The delay for the splash screen to stay
    private int DELAY=4000;
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.stayhealthysg);


        // TO create the Delay
       new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(StayHealthySG.this, Diseases.class);
                startActivity(i);

            }
        },DELAY);


    }
    }




