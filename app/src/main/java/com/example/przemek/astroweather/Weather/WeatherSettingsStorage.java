package com.example.przemek.astroweather.Weather;

import android.content.SharedPreferences;

import com.example.przemek.astroweather.Astro.AstroSettingsStorage;

/**
 * Created by Przemek on 28.05.2018.
 */

public class WeatherSettingsStorage {

    public static SharedPreferences mPrefs;
    public static final int expiredDataTimeSeconds = 10;
    public static final int maxTimeoutConnection = 3;

    private WeatherSettingsStorage(){

    }

    private static TemperatureUnitEnum temperature = TemperatureUnitEnum.FAHRENHEIT;

    public static TemperatureUnitEnum getTemperature(){
        return temperature;
    }

    public static void setTemperature(TemperatureUnitEnum temperature){
        WeatherSettingsStorage.temperature = temperature;
    }

    public static void restoreData(){
        String tempStr = mPrefs.getString("Temperature", TemperatureUnitEnum.FAHRENHEIT.name());
        temperature = TemperatureUnitEnum.valueOf(tempStr);
    }

    public static void saveData(){
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("Temperature", temperature.name()).commit();
    }


}
