package com.example.przemek.astroweather.Weather.DAO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Przemek on 31.05.2018.
 */

@Entity(tableName = "weatherData")
public class WeatherDataEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "raw_json")
    public String rawJson;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRawJson() {
        return rawJson;
    }

    public void setRawJson(String rawJson) {
        this.rawJson = rawJson;
    }
}
