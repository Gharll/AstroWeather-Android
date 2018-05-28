package com.example.przemek.astroweather.Astro;

import android.content.SharedPreferences;

import com.example.przemek.astroweather.CustomException.BadRangeException;


/**
 * Created by Przemek on 18.04.2018.
 */

public class AstroSettingsStorage {

    private static AstroSettingsStorage settingsStorage = null;
    private static double longitude;
    private static double latitude;
    private static Integer dataFrequencyRefresh;
    private static Integer timeZone;

    public final int MIN_TIME_OFFSET = -12;
    public final int MAX_TIME_OFFSET = 14;
    public final static int MAX_LONGITUDE = 80;
    public final static int MAX_LATITUDE = 80;
    public final static int MIN_LONGITUDE = -80;
    public final static int MIN_LATITUDE = -80;

    private AstroSettingsStorage(){

    }

    public static AstroSettingsStorage getInstance(){
        if(settingsStorage == null){
            settingsStorage = new AstroSettingsStorage();

            latitude = 51.7537150;
            longitude = 19.4517180;
            dataFrequencyRefresh = 1;
            timeZone = 2;
        }
        return settingsStorage;
    }


    public static SharedPreferences mPrefs;

    public static void restoreData() throws BadRangeException{
        AstroSettingsStorage.setLongitude(Double.parseDouble(mPrefs.getString("longitude", "50")));
        AstroSettingsStorage.setLatitude(Double.parseDouble(mPrefs.getString("latitude", "50")));
        AstroSettingsStorage.setTimeZone(mPrefs.getInt("time_zone", 4));
        AstroSettingsStorage.setDataFrequencyRefresh(mPrefs.getInt("refresh", 4));
    }

    public static void saveData(){
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("longitude", String.valueOf(AstroSettingsStorage.getLongitude())).commit();
        mEditor.putString("latitude", String.valueOf(AstroSettingsStorage.getLatitude())).commit();
        mEditor.putInt("time_zone", AstroSettingsStorage.getTimeZone()).commit();
        mEditor.putInt("refresh", AstroSettingsStorage.getDataFrequencyRefresh()).commit();
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) throws BadRangeException {
        if(longitude > MAX_LONGITUDE){
            AstroSettingsStorage.longitude = MAX_LONGITUDE;
            throw new BadRangeException("Longitude", MIN_LONGITUDE, MAX_LONGITUDE);
        }
        if(longitude < MIN_LONGITUDE){
            AstroSettingsStorage.longitude = MIN_LONGITUDE;
            throw new BadRangeException("Longitude", MIN_LONGITUDE, MAX_LONGITUDE);
        }

        AstroSettingsStorage.longitude = longitude;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) throws BadRangeException {
        if(latitude > MAX_LATITUDE){
            AstroSettingsStorage.latitude = MAX_LATITUDE;
            throw new BadRangeException("Latitude", MIN_LATITUDE, MAX_LATITUDE);
        }
        if(latitude < MIN_LATITUDE){
            AstroSettingsStorage.latitude = MIN_LATITUDE;
            throw new BadRangeException("Latitude", MIN_LATITUDE, MAX_LATITUDE);
        }

        AstroSettingsStorage.latitude = latitude;
    }

    public static int getDataFrequencyRefresh() {
        return dataFrequencyRefresh;
    }

    public static void setDataFrequencyRefresh(int dataFrequencyRefresh) {
        AstroSettingsStorage.dataFrequencyRefresh = dataFrequencyRefresh;
    }

    public static Integer getTimeZone() {
        return timeZone;
    }

    public static void setTimeZone(Integer timeZone) {
        AstroSettingsStorage.timeZone = timeZone;
    }
}
