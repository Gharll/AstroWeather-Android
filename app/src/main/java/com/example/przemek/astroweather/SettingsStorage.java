package com.example.przemek.astroweather;
import android.content.SharedPreferences;

/**
 * Created by Przemek on 18.04.2018.
 */

public class SettingsStorage {

    private static SettingsStorage settingsStorage = null;
    private static double longitude;
    private static double latitude;
    private static Integer dataFrequencyRefresh;
    private static Integer timeZone;

    public final int MIN_TIME_OFFSET = -12;
    public final int MAX_TIME_OFFSET = 14;
    //private TimeUnitEnum frequencyUnit = TimeUnitEnum.MINUTES;

    private SettingsStorage(){

    }

    public static SettingsStorage getInstance(){
        if(settingsStorage == null){
            settingsStorage = new SettingsStorage();

            settingsStorage.setLatitude(51.7537150);
            settingsStorage.setLongitude(19.4517180);
            settingsStorage.setDataFrequencyRefresh(1);
            settingsStorage.setTimeZone(2);
        }
        return settingsStorage;
    }


    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        SettingsStorage.longitude = longitude;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        SettingsStorage.latitude = latitude;
    }

    public static int getDataFrequencyRefresh() {
        return dataFrequencyRefresh;
    }

    public static void setDataFrequencyRefresh(int dataFrequencyRefresh) {
        SettingsStorage.dataFrequencyRefresh = dataFrequencyRefresh;
    }

    public static Integer getTimeZone() {
        return timeZone;
    }

    public static void setTimeZone(Integer timeZone) {
        SettingsStorage.timeZone = timeZone;
    }
}
