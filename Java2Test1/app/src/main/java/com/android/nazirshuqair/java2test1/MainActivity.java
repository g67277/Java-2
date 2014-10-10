package com.android.nazirshuqair.java2test1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;


public class MainActivity extends Activity implements MasterFragment.MasterClickListener, SettingsFragment.PrefClickListener {

    static final String LOGTAG = "Project Log:";

    List<Movies> moviesList;

    SharedPreferences defaultPrefs;
    ConnectivityManager cm;

    boolean cellBoolean;
    boolean offlineMode;

    String fileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffff0912")));

        defaultPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        if(savedInstanceState == null) {
            MasterFragment frag = MasterFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.container1, frag, MasterFragment.TAG).commit();

        }

        if (!isOnline()){
            //Pull data from cache
            Toast.makeText(this, "You're Offline", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void retriveData(String _text){

        fileName = _text;

        //mobile
        NetworkInfo.State mobile = cm.getNetworkInfo(0).getState();
        cellBoolean = defaultPrefs.getBoolean("networkOption", true);
        Log.i(LOGTAG, String.valueOf(cellBoolean));
        offlineMode = defaultPrefs.getBoolean("offlineMode", false);

        if ((mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) && !cellBoolean) {
            Toast.makeText(this, "Please connect to a WiFi network, or change your settings", Toast.LENGTH_SHORT).show();
        } else{

            if (isOnline() && !offlineMode){
                String userEntry = _text.replaceAll("\\s","+");
                String apiURL1 = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=sa9xnrpreedwhbcktqst2hks&q=";
                String apiURL2 = "&page_limit=1";
                String apiRequestURL = apiURL1 + userEntry + apiURL2;
                Log.i(LOGTAG, apiRequestURL);
                requestData(apiRequestURL);
            }else {
                String result = null;

                try {
                    result = readCache(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (result == null){
                    if (offlineMode){
                        Toast.makeText(this, "You're in Offline Mode and the Movie is not Cached", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "You're Offline and the Movie is not Cached", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    moviesList = JSONParser.parseFeed(result);
                    updateDisplay();

                }
            }
        }
    }

    private void requestData(String uri){

        MyTask myTask = new MyTask();
        myTask.execute(uri);

    }

    protected boolean isOnline(){

        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()){

            return true;
        }else {
            return false;
        }


    }

    @Override
    public void syncNetworkPref(boolean b) {

        // Get a new editor
        SharedPreferences.Editor edit = defaultPrefs.edit();

        edit.putBoolean("networkOption", b);

        edit.apply();

    }

    @Override
    public void syncOfflinePref(boolean b) {

        // Get a new editor
        SharedPreferences.Editor edit = defaultPrefs.edit();

        edit.putBoolean("offlineMode", b);

        edit.apply();
    }

    @Override
    public void clearCache() {

        File dir = getBaseContext().getFilesDir();

        if (dir.isDirectory()) {
            String[] children = dir.list();

            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }

            Toast.makeText(this, "Cache Clear", Toast.LENGTH_SHORT).show();
        }

    }


    private class MyTask extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(String... params) {

            String content = HttpManager.getData(params[0]);

            return content;
        }

        @Override
        protected void onPostExecute(String result){

            if (result == null){
                Toast.makeText(MainActivity.this, "Can't Connect", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                cacheData(result);
            } catch (IOException e) {
                e.printStackTrace();
            }

            moviesList = JSONParser.parseFeed(result);

            if (moviesList == null){
                Toast.makeText(MainActivity.this, "Can't find movie", Toast.LENGTH_LONG).show();
            }

            updateDisplay();

        }
    }

    public void cacheData (String _data) throws IOException {

        FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
        fos.write(_data.getBytes());
        fos.close();
    }



    public String readCache (String _fileName) throws IOException {

        File file = getBaseContext().getFileStreamPath(_fileName);
        if (file.exists()){
            FileInputStream fis = openFileInput(_fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);
            StringBuffer b = new StringBuffer();
            while (bis.available() != 0){
                char c = (char) bis.read();
                b.append(c);
            }
            bis.close();
            fis.close();

            return String.valueOf(b);
        }else {
            return null;
        }

    }

    private void updateDisplay() {

        String title = null;
        String description = null;
        String star = null;
        int rating = 0;
        int year = 0;

        // This definitely needs to be cleaned up
        if (moviesList != null){

            for (Movies movies : moviesList){
                title = movies.getTitle();
                if (movies.getDescription().length() < 1){
                    description = "Sorry, Summery is not available";
                }else {
                    description = movies.getDescription();
                }
                star = movies.getStar();
                rating = movies.getRating();
                year =  movies.getYear();
            }

            DetailFragment frag = (DetailFragment)getFragmentManager().findFragmentByTag(DetailFragment.TAG);

            if(frag == null) {
                frag = DetailFragment.newInstance(title, description, star, rating, year);
                getFragmentManager().beginTransaction().replace(R.id.container2, frag, DetailFragment.TAG).commit();
            } else {
                frag.setDisplayText(title, description, star, rating, year);
            }
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container2, new SettingsFragment());
            ft.commit();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
