/*
Nazir Shuqair
Java 1 - 1409
Final Project
 */
package com.android.nazirshuqair.weatherjava2.parser;

import com.android.nazirshuqair.weatherjava2.model.Weather;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazirshuqair on 9/21/14.
 */
public class WeatherJSONParser {

    public static List<Weather> parseFeed(String content) {

        JSONObject apiData;
        try{

            List<Weather> weatherList = new ArrayList();

            apiData = new JSONObject(content);
            Weather weather = new Weather();

                weather.setTemperature(apiData.getJSONObject("current_observation").getInt("temp_f"));
                weather.setTempText(apiData.getJSONObject("current_observation").getString("weather"));

                weatherList.add(weather);
                //Change
                return weatherList;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<Weather> parseHourly(String content) {

        JSONObject apiData;
        try{

            List<Weather> weatherList = new ArrayList();

            apiData = new JSONObject(content);
            Weather weather = new Weather();

            weather.setHourlyJSON(apiData.getJSONArray("hourly_forecast"));

            weatherList.add(weather);
            //Change
            return weatherList;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<Weather> parseWeekly(String content) {

        JSONObject apiData;
        try{

            List<Weather> weatherList = new ArrayList();

            apiData = new JSONObject(content);
            Weather weather = new Weather();


            weather.setWeeklyJSON(apiData.getJSONObject("forecast").getJSONObject("txt_forecast").getJSONArray("forecastday"));


            weatherList.add(weather);
            //Change
            return weatherList;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}