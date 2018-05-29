package com.example.przemek.astroweather.CustomException;

/**
 * Created by Przemek on 29.05.2018.
 */

public class LocationNotExistsException extends Exception {

    private String locationName;
    public LocationNotExistsException(String locationName){
            super(locationName + " not exists");
    }

    public String getLocationName(){
        return locationName;
    }
}
