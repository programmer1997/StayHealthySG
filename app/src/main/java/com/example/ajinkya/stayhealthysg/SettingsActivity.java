package com.example.ajinkya.stayhealthysg;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

/**
 * Created by Idea pad on 04/04/2017.
 */

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {

        if (key.equals("font_key")) {
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
