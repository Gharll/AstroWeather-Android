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
    private long id;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "datetime")
    private String datetime;

    @ColumnInfo(name = "raw_json")
    private String rawJson;

    public WeatherDataEntity(String city, String datetime, String rawJson){
        this.city = city;
        this.datetime = datetime;
        this.rawJson = rawJson;
    }

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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRawJson() {
        return rawJson;
    }

    public void setRawJson(String rawJson) {
        this.rawJson = rawJson;
    }
}
