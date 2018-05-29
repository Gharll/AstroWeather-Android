package com.example.przemek.astroweather.Weather;

/**
 * Created by Przemek on 28.05.2018.
 */

public class WeatherSettingsStorage {

    private WeatherSettingsStorage(){

    }

    private static TemperatureUnitEnum temperature = TemperatureUnitEnum.FAHRENHEIT;

    public static TemperatureUnitEnum getTemperature(){
        return temperature;
    }

    public static void setTemperature(TemperatureUnitEnum temperature){
        WeatherSettingsStorage.temperature = temperature;
    }
}
