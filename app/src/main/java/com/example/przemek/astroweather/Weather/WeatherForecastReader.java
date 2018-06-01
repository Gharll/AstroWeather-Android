package com.example.przemek.astroweather.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Przemek on 01.06.2018.
 */

public class WeatherForecastReader {

    private JSONArray forecast;

    public WeatherForecastReader(JSONObject weatherJSON){
        try {
            forecast = weatherJSON.getJSONObject( "results")
                    .getJSONObject("channel")
                    .getJSONObject("item")
                    .getJSONArray("forecast");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDate(int day) throws JSONException{
        return forecast.getJSONObject(day).getString("date");
    }

    public String getDay(int day) throws JSONException{
        return forecast.getJSONObject(day).getString("day");
    }

    public String getHigh(int day) throws JSONException{
        return forecast.getJSONObject(day).getString("high");
    }

    public String getLow(int day) throws JSONException{
        return forecast.getJSONObject(day).getString("low");
    }

    public String getText(int day) throws JSONException{
        return forecast.getJSONObject(day).getString("text");
    }
}
