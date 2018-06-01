package com.example.przemek.astroweather.Weather;

import android.content.Context;

import com.example.przemek.astroweather.CustomException.LocationAlreadyExists;
import com.example.przemek.astroweather.CustomException.LocationNotExistsException;
import com.example.przemek.astroweather.Weather.DAO.WeatherDataDAO;
import com.example.przemek.astroweather.Weather.DAO.WeatherDataEntity;
import com.example.przemek.astroweather.Weather.DAO.WeatherDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Przemek on 31.05.2018.
 */

public class WeatherDataManager {

    private static WeatherDataManager weatherDataManager;
    private static WeatherDatabase weatherDatabase;
    private static WeatherDataDAO weatherDataDAO;
    private static WeatherDownloader weatherDownloader;
    private String currentWeatherCity;

    private WeatherDataManager(){

    }

    public static WeatherDataManager getInstance(Context context){
        if(weatherDataManager == null){
            weatherDataManager = new WeatherDataManager();
            initialize(context);
        }
        return weatherDataManager;
    }

    private static void initialize(Context context){
        weatherDatabase = WeatherDatabase.getInstance(context);
        weatherDataDAO = weatherDatabase.weatherDataDAO();
        weatherDownloader = new WeatherDownloader();

    }

    public void setCurrentLocation(String currentLocation){
        this.currentWeatherCity = currentLocation;

    }

    public void storeCity(String city)
            throws LocationNotExistsException, LocationAlreadyExists {


        WeatherDataEntity weatherDataEntity = weatherDataDAO.findWeatherDataByCity(city);
        if(weatherDataEntity != null){
            throw new LocationAlreadyExists(city);
        }

        JSONObject weatherJSON = null;
        try {
            //weatherJSON = weatherDownloader.execute(location).get();
            weatherJSON = new WeatherDownloader().execute(city).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (weatherJSON.isNull("results")){
            throw new LocationNotExistsException(city);
        }

        WeatherDataEntity weatherData = new WeatherDataEntity();
        //weatherData.setId(0);
        weatherData.setCity(city);
        weatherData.setRawJson(weatherJSON.toString());
        weatherDataDAO.insertWeatherData(weatherData);

    }

    public void deleteStoredLocation(String city){
        WeatherDataEntity weatherDataEntity = weatherDataDAO.findWeatherDataByCity(city);
        weatherDataDAO.deleteWeatherData(weatherDataEntity);
    }

    public JSONObject getCurrentLocationJSON() {

        JSONObject weatherJSON = null;
        WeatherDataEntity weatherDataEntity =
                weatherDataDAO.findWeatherDataByCity(currentWeatherCity);

        //check is update
        //if no download from downloader

        try {
            weatherJSON = new JSONObject(weatherDataEntity.getRawJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weatherJSON;

    }

    public List<WeatherDataEntity> getAll(){
        return weatherDataDAO.getAll();
    }
}
