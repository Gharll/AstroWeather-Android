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
    //private TimeUnitEnum frequencyUnit = TimeUnitEnum.MINUTES;

    private SettingsStorage(){

    }

    public static SettingsStorage getInstance(){
        if(settingsStorage == null){
            settingsStorage = new SettingsStorage();
            settingsStorage.setLatitude(51.7537150);
            settingsStorage.setLongitude(19.4517180);
        }
        /*if(longitude == null || latitude == null || dataFrequencyRefresh == null){
            initDefaultData();
        } else {
            restoreData();
        }*/
        return settingsStorage;
    }

    /*private static void restoreData(){

    }*/

    /*private void saveData(){

    }*/

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getDataFrequencyRefresh() {
        return dataFrequencyRefresh;
    }

    public void setDataFrequencyRefresh(int dataFrequencyRefresh) {
        this.dataFrequencyRefresh = dataFrequencyRefresh;
    }

    /*public TimeUnitEnum getFrequencyUnit() {
        return frequencyUnit;
    }

    public void setFrequencyUnit(TimeUnitEnum frequencyUnit) {
        this.frequencyUnit = frequencyUnit;
    }*/
}
