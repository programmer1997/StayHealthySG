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

public class SettingsActivity extends PreferenceActivity{

    //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    /*ListPreference myPref = (ListPreference) findPreference("font_list_value");
    SwitchPreference infectiousPref = (SwitchPreference) findPreference("pref_infectious");
    SwitchPreference weatherPref = (SwitchPreference) findPreference("pref_weather");
    SwitchPreference hazePref = (SwitchPreference) findPreference("pref_haze");
    SwitchPreference uvPref =(SwitchPreference) findPreference("pref_uv");*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
        /*
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ListPreference lp = (ListPreference) findPreference("font_list_value");
                String currentValue = lp.getValue();
                SharedPreferences.Editor editor = sharedPref.edit();

                if (currentValue.equals("Small")) {
                    editor.putString("FONT_SIZE", "Small");
                    editor.commit();
                    return true;
                } else if (currentValue.equals("Large")) {
                    editor.putString("FONT_SIZE", "Large");
                    editor.commit();
                    return true;
                } else if (currentValue.equals("Medium")) {
                    editor.putString("FONT_SIZE", "Medium");
                    editor.commit();
                    return true;
                }
                return false;
            }
        });
        weatherPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SwitchPreference infectious = (SwitchPreference) findPreference("pref_infectious");

                if (infectious.isChecked()) {
                    infectious.setChecked(false);
                    return true;
                } else if (!infectious.isChecked()) {
                    infectious.setChecked(true);
                    return true;
                }
                return false;
            }
        });
        weatherPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SwitchPreference weather = (SwitchPreference) findPreference("pref_weather");

                if (weather.isChecked()) {
                    weather.setChecked(false);
                    return true;
                } else if (!weather.isChecked()) {
                    weather.setChecked(true);
                    return true;
                }
                return false;
            }
        });
        uvPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SwitchPreference uv = (SwitchPreference) findPreference("pref_uv");

                if (uv.isChecked()) {
                    uv.setChecked(false);
                    return true;
                } else if (!uv.isChecked()) {
                    uv.setChecked(true);
                    return true;
                }
                return false;
            }
        });
        hazePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SwitchPreference haze = (SwitchPreference) findPreference("pref_haze");

                if (haze.isChecked()) {
                    haze.setChecked(false);
                    return true;
                } else if (!haze.isChecked()) {
                    haze.setChecked(true);
                    return true;
                }
                return false;
            }
        });
        infectiousPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SwitchPreference infectious = (SwitchPreference) findPreference("pref_infectious");

                if (infectious.isChecked()) {
                    infectious.setChecked(false);
                    return true;
                } else if (!infectious.isChecked()) {
                    infectious.setChecked(true);
                    return true;
                }
                return false;
            }
        });
         */
    }
}
