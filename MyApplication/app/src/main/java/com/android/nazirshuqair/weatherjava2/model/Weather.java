package com.android.nazirshuqair.weatherjava2.model;

import org.json.JSONArray;

/**
 * Created by nazirshuqair on 9/21/14.
 */
public class Weather {


    private int temperature;
    private String tempText;
    private JSONArray weeklyJSON;
    private JSONArray hourlyJSON;

    public JSONArray getHourlyJSON() {
        return hourlyJSON;
    }

    public void setHourlyJSON(JSONArray hourlyJSON) {
        this.hourlyJSON = hourlyJSON;
    }



    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getTempText() {
        return tempText;
    }

    public void setTempText(String tempText) {
        this.tempText = tempText;
    }

    public JSONArray getWeeklyJSON() {
        return weeklyJSON;
    }

    public void setWeeklyJSON(JSONArray weeklyJSON) {
        this.weeklyJSON = weeklyJSON;
    }

}
