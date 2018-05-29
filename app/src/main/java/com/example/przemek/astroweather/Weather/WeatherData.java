package com.example.przemek.astroweather.Weather;

import com.example.przemek.astroweather.CustomException.LocationNotExistsException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Przemek on 28.05.2018.
 */

public class WeatherData {

    private JSONObject json;
    private JSONObject channel;
    private String locationName;
    private WeatherDownloader weatherDownloader;

    public WeatherData(String locationName){
        this.locationName = locationName;
        this.weatherDownloader = new WeatherDownloader();
        //downloadCurrentData();
    }

    public void downloadCurrentData() throws LocationNotExistsException {
        try {
            json = weatherDownloader.execute(locationName).get();

            if (json.isNull("results")){
                throw new LocationNotExistsException(locationName);
            }

            channel = json.getJSONObject( "results").getJSONObject("channel");
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }

    public String getCity() throws JSONException {
        return channel.getJSONObject("location").getString("city");
    }

    public String getCountry() throws JSONException {
        return channel.getJSONObject("location").getString("country");
    }

    public String getLocationName() throws JSONException{
        return getCity() + ", " + getCountry();
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

}
