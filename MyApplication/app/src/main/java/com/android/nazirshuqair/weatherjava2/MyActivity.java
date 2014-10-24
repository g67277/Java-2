package com.android.nazirshuqair.weatherjava2;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nazirshuqair.weatherjava2.model.Weather;
import com.android.nazirshuqair.weatherjava2.parser.WeatherJSONParser;


public class MyActivity extends Activity implements ActionBar.TabListener, NowFragment.mMasterClickListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */

    //ProgressBar pb;
    List<Weather> weatherList;

    SectionsPagerAdapter mSectionsPagerAdapter;

    int tabPosition = 0;

    String userInput = null;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
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
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void captureInput(String input) {

        userInput = input;

        String apiURI = "http://api.wunderground.com/api/601c7f5a284ed9f5/conditions/q/" + userInput + ".json";

        if (isOnline()){
            requestData(apiURI);
        }else {
            Toast.makeText(this, "You're Offline", Toast.LENGTH_SHORT).show();
        }

    }

    private void requestData(String uri){

        MyTask myTask = new MyTask();
        myTask.execute(uri);

    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute(){
            //updateDisplay
        }

        @Override
        protected String doInBackground(String... params){

            String content = HttpManager.getData(params[0]);

            return content;
        }

        @Override
        protected void onPostExecute(String result){

            //pb.setVisibility(View.INVISIBLE);

            Log.i("TESTING: ", result);

            if (result == null){
                Toast.makeText(MyActivity.this, "Can't Connect", Toast.LENGTH_LONG).show();
                return;
            }

            switch (tabPosition){
                case 0:
                    weatherList = WeatherJSONParser.parseFeed(result);
                    break;
                case 1:
                    weatherList = WeatherJSONParser.parseHourly(result);
                    break;
                case 2:
                    weatherList = WeatherJSONParser.parseWeekly(result);
            }

            if (weatherList == null){
                Toast.makeText(MyActivity.this, "City is invalid", Toast.LENGTH_LONG).show();
            }

            updateDisplay();
        }

        @Override
        protected void onProgressUpdate(String... values){

        }

    }

    public void updateDisplay(){
        Log.i("TESTING: ", "THIS WORKS!");
    }

    protected boolean isOnline(){

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    tabPosition = 0;
                    return NowFragment.newInstance(position);
                case 1:
                    tabPosition = 1;
                    if (userInput != null){
                        String apiURI = "http://api.wunderground.com/api/601c7f5a284ed9f5/hourly/q/" + userInput + ".json";
                        if (isOnline()){
                            requestData(apiURI);
                        }else {
                            Toast.makeText(MyActivity.this, "You're Offline", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return PlaceholderFragment.newInstance(position);
                case 2:
                    tabPosition = 2;
                    if (userInput != null){
                        String apiURI = "http://api.wunderground.com/api/601c7f5a284ed9f5/forecast/q/" + userInput + ".json";
                        if (isOnline()){
                            requestData(apiURI);
                        }else {
                            Toast.makeText(MyActivity.this, "You're Offline", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return PlaceholderFragment.newInstance(position);
                default:
                    return NowFragment.newInstance(position);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "Now".toUpperCase(l);
                case 1:
                    return "Hourly".toUpperCase(l);
                case 2:
                    return "Weekly".toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_hourly, container, false);
            return rootView;
        }
    }

}
