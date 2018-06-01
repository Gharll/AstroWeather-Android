package com.example.przemek.astroweather.Weather.DAO;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Przemek on 31.05.2018.
 */

@Database(entities = {WeatherDataEntity.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {

    private static WeatherDatabase weatherDatabase;
    public abstract WeatherDataDAO weatherDataDAO();

    public static WeatherDatabase getInstance(Context context){
        if(weatherDatabase == null){
            weatherDatabase = Room.databaseBuilder(
                    context,
                    WeatherDatabase.class,
                    "weatherdata-db").allowMainThreadQueries().build();
        }

        return weatherDatabase;
    }

    public static void destroyInstance(){
        weatherDatabase = null;
    }
}
