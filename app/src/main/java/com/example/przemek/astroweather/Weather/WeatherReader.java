package com.example.przemek.astroweather.Weather;

import com.example.przemek.astroweather.CustomException.LocationNotExistsException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Przemek on 28.05.2018.
 */

public class WeatherReader {

    private JSONObject weatherJSON;
    private JSONObject channel;


    public WeatherReader(){

    }

    public WeatherReader(JSONObject weatherJSON){
        setWeatherJSON(weatherJSON);
    }

    protected void setWeatherJSON(JSONObject weatherJSON){
        this.weatherJSON = weatherJSON;
        try {
            channel = weatherJSON.getJSONObject( "results").getJSONObject("channel");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getCity() throws JSONException {
        return channel.getJSONObject("location").getString("city");
    }

    public String getCountry() throws JSONException {
        return channel.getJSONObject("location").getString("country");
    }

    public String getFahrenheitTemperature() throws JSONException {
        return channel.getJSONObject("item").getJSONObject("condition").getString("temp");
    }

    public String getLatitude() throws JSONException {
        return channel.getJSONObject("item").getString("lat");
    }

    public String getLongitude() throws JSONException{
        return channel.getJSONObject("item").getString("long");
    }

    public String getTime() throws JSONException{
        return channel.getJSONObject("item").getString("pubDate");
    }

    public String getAirPressure() throws JSONException{
        return channel.getJSONObject("atmosphere").getString("pressure");
    }

    public String getWindStrength() throws JSONException{
        return channel.getJSONObject("wind").getString("speed");
    }

    public String getWindDirection() throws JSONException{
        return channel.getJSONObject("wind").getString("direction");
    }

    public String getHumidity() throws JSONException{
        return channel.getJSONObject("atmosphere").getString("humidity");
    }

    public String getVisibility() throws JSONException{
        return channel.getJSONObject("atmosphere").getString("visibility");
    }

    public String getText() throws JSONException{
        return channel.getJSONObject("item").getJSONObject("condition").getString("text");
    }
}
