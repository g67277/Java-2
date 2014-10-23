package com.android.nazirshuqair.news;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * Created by nazirshuqair on 10/22/14.
 */
public class SettingsFragment extends PreferenceFragment {

    public static final String TAG = "SettingsFragment.TAG";


    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
    }

    public interface PrefClickListener{

        public void syncNetworkPref(boolean b);

    }

    private PrefClickListener mListener;

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);

        if(_activity instanceof PrefClickListener) {
            mListener = (PrefClickListener)_activity;
        } else {
            throw new IllegalArgumentException("Containing activity must implement OnButtonClickListener interface");
        }
    }
    @Override
    public void onActivityCreated(Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        // Find preference by key

        Preference netPref = findPreference("online_options");
        netPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){

            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {

                boolean nO = (Boolean) o;
                mListener.syncNetworkPref(nO);

                return true;
            }
        });
    }
}
