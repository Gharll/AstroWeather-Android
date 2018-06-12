package com.example.przemek.astroweather.Weather;

import android.content.SharedPreferences;

import com.example.przemek.astroweather.Astro.AstroSettingsStorage;

/**
 * Created by Przemek on 28.05.2018.
 */

public class WeatherSettingsStorage {

    public static SharedPreferences mPrefs;
    private static int expiredDateTimeSeconds = 10;

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

    public static int getExpiredDateTimeSeconds(){
        return expiredDateTimeSeconds;
    }


}
