package com.android.nazirshuqair.news;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, SettingsFragment.PrefClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private boolean overMobile = true;
    SharedPreferences defaultPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        defaultPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();

        switch (position) {
            case 0:
                mTitle = "Featured";
                fragmentManager.beginTransaction()
                        .replace(R.id.container, FeaturedFragment.newInstance())
                        .commit();
                if (!overMobile){
                    Toast.makeText(this, "Offline Mode", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                mTitle = "Recent";
                fragmentManager.beginTransaction()
                        .replace(R.id.container, RecentFragment.newInstance())
                        .commit();
                if (!overMobile){
                    Toast.makeText(this, "Offline Mode", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                mTitle = "Breaking News";
                fragmentManager.beginTransaction()
                        .replace(R.id.container, BreakingNewsFragment.newInstance())
                        .commit();
                if (!overMobile){
                    Toast.makeText(this, "Offline Mode", Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                mTitle = "Settings";
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new SettingsFragment())
                        .commit();
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.my, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void syncNetworkPref(boolean b) {

        overMobile = b;
    }
}
