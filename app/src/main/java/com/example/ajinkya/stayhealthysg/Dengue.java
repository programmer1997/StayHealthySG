package com.example.ajinkya.stayhealthysg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

/**
 * Created by Ajinkya on 24/3/17.
 */

public class Dengue extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dengue);
        TabHost host=(TabHost) findViewById(R.id.dengueTabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Symptoms");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Symptoms");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Prevention");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Prevention");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Clusters");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Clusters");
        host.addTab(spec);


    }





}
