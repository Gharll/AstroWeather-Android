package com.example.przemek.astroweather.Weather;

import android.content.Context;
import android.util.Log;

import com.example.przemek.astroweather.CustomException.InternetConnectionException;
import com.example.przemek.astroweather.CustomException.LocationAlreadyExists;
import com.example.przemek.astroweather.CustomException.LocationNotExistsException;
import com.example.przemek.astroweather.Weather.DAO.CurrentLocationEntity;
import com.example.przemek.astroweather.Weather.DAO.WeatherDataDAO;
import com.example.przemek.astroweather.Weather.DAO.WeatherDataEntity;
import com.example.przemek.astroweather.Weather.DAO.WeatherDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Przemek on 31.05.2018.
 */

public class WeatherDataManager {

    private static WeatherDataManager weatherDataManager;
    private static WeatherDatabase weatherDatabase;
    private static WeatherDataDAO weatherDataDAO;
    private static WeatherDownloader weatherDownloader;

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
        WeatherDataEntity weatherDataEntity = weatherDataDAO.getCurrentLocation();
        if(weatherDataEntity != null){
            weatherDataDAO.clearCurrentLocation();
        }

        weatherDataEntity = weatherDataDAO.findWeatherDataByCity(currentLocation);
        if(weatherDataEntity != null){
            CurrentLocationEntity currentLocationEntity = new CurrentLocationEntity();
            long id = weatherDataEntity.getId();
            currentLocationEntity.setWeatherDataID(id);
            weatherDataDAO.insertCurrentLocation(currentLocationEntity);
        }

    }

    public WeatherDataEntity getCurrentLocation(){
        return weatherDataDAO.getCurrentLocation();
    }

    public String storeCityAndGetFormatedName(String city)
            throws LocationNotExistsException, LocationAlreadyExists, InternetConnectionException {


        WeatherDataEntity weatherDataEntity = weatherDataDAO.findWeatherDataByCity(city);
        if(weatherDataEntity != null){
            throw new LocationAlreadyExists(city);
        }

        return downloadAndStoreCity(city);

    }

    public String downloadAndStoreCity(String city) throws LocationNotExistsException, InternetConnectionException {

        JSONObject weatherJSON = null;
        try {

            try{
                weatherJSON = new WeatherDownloader().execute(city).get(2, TimeUnit.SECONDS);

                if (weatherJSON.isNull("results")){
                    throw new LocationNotExistsException(city);
                }

            }catch (TimeoutException e) {
                throw new InternetConnectionException();
            } catch (NullPointerException e){
                throw new InternetConnectionException();
            }




            WeatherReader weatherReader = new WeatherReader(weatherJSON);

            String cityName = null;
            try {
                cityName = weatherReader.getCity();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Date date = new Date();
            WeatherDataEntity weatherDataEntity = weatherDataDAO.findWeatherDataByCity(city);
            if(weatherDataEntity != null){
                weatherDataEntity.setRawJson(weatherJSON.toString());
                weatherDataEntity.setDatetime(WeatherDate.format(date));
                weatherDataDAO.updateWeatherData(weatherDataEntity);
            } else {

                WeatherDataEntity weatherData = new WeatherDataEntity(cityName, WeatherDate.format(date), weatherJSON.toString());
                weatherDataDAO.insertWeatherData(weatherData);
            }

            return cityName;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

       return null;
    }

    public void deleteStoredLocation(String city){
        WeatherDataEntity weatherDataEntity = weatherDataDAO.findWeatherDataByCity(city);
        weatherDataDAO.deleteWeatherData(weatherDataEntity);
    }

    public JSONObject getCurrentLocationJSON() {

        JSONObject weatherJSON = null;

        if(weatherDataDAO.getCurrentLocation() == null){
            return null;
        }

        String currentCity = weatherDataDAO.getCurrentLocation().getCity();
        WeatherDataEntity weatherDataEntity =
                weatherDataDAO.findWeatherDataByCity(currentCity);

        if(WeatherDate.checkIsUpdate(WeatherDate.parse(weatherDataEntity.getDatetime()))){
            try {
                Log.v("MyDebug:", "Download new data");
                downloadAndStoreCity(currentCity);
            } catch (LocationNotExistsException e) {
                e.printStackTrace();
            } catch (InternetConnectionException e){

            }
        }

        weatherDataEntity =
                weatherDataDAO.findWeatherDataByCity(currentCity);

        try {
            weatherJSON = new JSONObject(weatherDataEntity.getRawJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weatherJSON;

    }

    public WeatherDataEntity findWeatherDataByCity(String city){
        return weatherDataDAO.findWeatherDataByCity(city);
    }

    public List<WeatherDataEntity> getAll(){
        return weatherDataDAO.getAllWeatherData();
    }

}
