package com.example.android.newsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Log.i(LOG_TAG, "TEST:settings_main called.");
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);


            //we can see the section choice on the screen
            Preference sectionName = findPreference(getString(R.string.settings_section_key));
            bindPreferenceSummaryToValue(sectionName);
            Log.i(LOG_TAG, "TEST:settings_section_key called.");

            // we can see the oldest-newest selection
            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);
            Log.i(LOG_TAG, "TEST:settings_order_by_key called.");
        }

        //we can see the choice on the screen
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            // The code in this method takes care of updating the displayed preference summary after it has been changed
            String stringValue = value.toString();

            //for oldest-newest choice
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                    Log.i(LOG_TAG, "TEST:onPreferenceChange called.");
                }
            } else {

                preference.setSummary(stringValue);
                Log.i(LOG_TAG, "TEST:onPreferenceChange2 called.");
            }

            return true;

        }

        //we can see the choice on the screen
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
            Log.i(LOG_TAG, "TEST:bindPreferenceSummaryToValue called.");
        }
    }

}
