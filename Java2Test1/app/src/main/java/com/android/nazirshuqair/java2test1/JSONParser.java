/*
Nazir Shuqair
Java 1 - 1409
Final Project
 */
package com.android.nazirshuqair.java2test1;


import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazirshuqair on 9/21/14.
 */
public class JSONParser {

    public static List<Movies> parseFeed(String content) {

        JSONObject apiData;

        try{

            List<Movies> moviesList = new ArrayList();

            apiData = new JSONObject(content);
            Movies movies = new Movies();

            int numOfResult = apiData.getInt("total");

            if (numOfResult < 1){
                return null;
            }else {
                JSONArray incomingMovies = apiData.getJSONArray("movies");
                Log.i("Project Log", String.valueOf(incomingMovies));
                movies.setTitle(incomingMovies.getJSONObject(0).getString("title"));
                movies.setYear(incomingMovies.getJSONObject(0).getInt("year"));
                movies.setRating(incomingMovies.getJSONObject(0).getJSONObject("ratings").getInt("audience_score"));
                movies.setDescription(incomingMovies.getJSONObject(0).getString("synopsis"));
                movies.setStar(incomingMovies.getJSONObject(0).getJSONArray("abridged_cast").getJSONObject(0).getString("name"));

                moviesList.add(movies);


                return moviesList;
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}