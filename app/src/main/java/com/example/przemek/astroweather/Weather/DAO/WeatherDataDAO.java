package com.example.przemek.astroweather.Weather.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Przemek on 31.05.2018.
 */

@Dao
public interface WeatherDataDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeatherData(WeatherDataEntity weatherDataEntity);

    @Update
    void updateWeatherData(WeatherDataEntity weatherDataEntity);

    @Delete
    void deleteWeatherData(WeatherDataEntity weatherDataEntity);


    @Query("SELECT * FROM weatherData WHERE City LIKE :searchedCity")
    WeatherDataEntity findWeatherDataByCity(String searchedCity);

    @Query("SELECT * FROM weatherData")
    List<WeatherDataEntity> getAll();
}
