package com.example.przemek.astroweather;
import android.content.SharedPreferences;

/**
 * Created by Przemek on 18.04.2018.
 */

public class SettingsStorage {

    private static SettingsStorage settingsStorage = null;
    private static Float longitude;
    private static Float latitude;
    private static Integer dataFrequencyRefresh;
    //private TimeUnitEnum frequencyUnit = TimeUnitEnum.MINUTES;

    private SettingsStorage(){

    }

    public static SettingsStorage getInstance(){
        if(settingsStorage == null){
            initDefaultData();
            settingsStorage = new SettingsStorage();
        }
        /*if(longitude == null || latitude == null || dataFrequencyRefresh == null){
            initDefaultData();
        } else {
            restoreData();
        }*/
        return settingsStorage;
    }

    private static void initDefaultData(){
        longitude = 0f;
        latitude = 0f;
        dataFrequencyRefresh = 30;
    }

    /*private static void restoreData(){

    }*/

    /*private void saveData(){

    }*/

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
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
