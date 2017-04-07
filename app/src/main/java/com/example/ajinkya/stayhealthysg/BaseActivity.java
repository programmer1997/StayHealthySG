package com.example.ajinkya.stayhealthysg;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Idea pad on 04/04/2017.
 */

public class BaseActivity extends Activity{
    @Override
    public void onStart(){
        super.onStart();

        try {
            SharedPreferences settings =
                    getSharedPreferences("com.example.ajinkya.stayhealthysg",Context.MODE_PRIVATE);
            String fontSizePref = settings.getString("FONT_SIZE", "Medium");
            int themeID = R.style.FontSizeMedium;
            if(fontSizePref == "Small"){
                themeID = R.style.FontSizeSmall;
            }
            else if (fontSizePref == "Large") {
                themeID = R.style.FontSizeLarge;
            }
            setTheme(themeID);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
