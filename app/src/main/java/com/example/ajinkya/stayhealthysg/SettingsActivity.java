package com.example.ajinkya.stayhealthysg;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.Log;

/**
 * Created by Idea pad on 04/04/2017.
 */

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        if (key.equals("font_list_value")) {
            Log.v(this.getLocalClassName(),"Sudah dipanggil font_list_value");
            String fontSizePref = sharedPreferences.getString(key, "Mediums");
            int themeID = R.style.FontSizeMedium;
            if (fontSizePref == "Small") {
                themeID = R.style.FontSizeSmall;
            } else if (fontSizePref == "Large") {
                themeID = R.style.FontSizeLarge;
            }
            setTheme(themeID);
        }
    }
}
