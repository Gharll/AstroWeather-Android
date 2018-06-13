package com.example.przemek.astroweather.Astro;

import android.content.SharedPreferences;

import com.example.przemek.astroweather.CustomException.BadRangeException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;


/**
 * Created by Przemek on 18.04.2018.
 */

public class AstroSettingsStorage {

    //For test location - Lodz
    private static final double defaultLongitude = 51.7537150;
    private static final double defaultLatitude = 19.4517180;

    private static double longitude = defaultLongitude;
    private static double latitude = defaultLatitude;
    private static Integer dataFrequencyRefresh = 1;
    private static Integer timeZone = 2;

    public final static int MIN_TIME_OFFSET = -12;
    public final static int MAX_TIME_OFFSET = 14;
    public final static int MAX_LONGITUDE = 70;
    public final static int MAX_LATITUDE = 70;
    public final static int MIN_LONGITUDE = -70;
    public final static int MIN_LATITUDE = -70;


    private static DecimalFormat cordFormat = new DecimalFormat("#.######");

    private AstroSettingsStorage(){
    }

    public static SharedPreferences mPrefs;

    public static void restoreData() throws BadRangeException{
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator('.');
        cordFormat = new DecimalFormat("#.######", otherSymbols);

        AstroSettingsStorage.setLongitude(
                Double.parseDouble(mPrefs.getString("longitude", String.valueOf(defaultLongitude))));
        AstroSettingsStorage.setLatitude(
                Double.parseDouble(mPrefs.getString("latitude", String.valueOf(defaultLatitude))));
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

    public static String getFormattedLatitude() throws ParseException {
        return cordFormat.format(latitude);
    }

    public static String getFormattedLongitude() throws ParseException {
        return cordFormat.format(longitude);
    }
}
