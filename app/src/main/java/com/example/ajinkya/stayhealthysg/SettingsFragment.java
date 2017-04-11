package com.example.ajinkya.stayhealthysg;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Idea pad on 04/04/2017.
 */

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}


/**
 * Created by Idea pad on 04/04/2017.
 */


