package com.example.ajinkya.stayhealthysg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Ajinkya on 11/3/17.
 */

public class Diseases extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diseases);

    }

    public void displayMap(View view){
        Intent intent=new Intent(Diseases.this,MapsActivity.class);
        startActivity(intent);



    }
}
