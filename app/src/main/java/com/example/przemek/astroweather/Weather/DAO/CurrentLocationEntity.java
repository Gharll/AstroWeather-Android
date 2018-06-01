package com.example.przemek.astroweather.Weather.DAO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Przemek on 01.06.2018.
 */

@Entity(tableName = "currentLocation",
foreignKeys = @ForeignKey(entity = WeatherDataEntity.class,
                            parentColumns = "id",
                            childColumns = "weatherDataID",
                            onDelete = CASCADE))
public class CurrentLocationEntity {

    @PrimaryKey(autoGenerate = true)
    long id;

    long weatherDataID;

    public long getWeatherDataID() {
        return weatherDataID;
    }

    public void setWeatherDataID(long weatherDataID) {
        this.weatherDataID = weatherDataID;
    }
}
