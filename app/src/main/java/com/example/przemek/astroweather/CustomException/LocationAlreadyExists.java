package com.example.przemek.astroweather.CustomException;

/**
 * Created by Przemek on 31.05.2018.
 */

public class LocationAlreadyExists extends Exception {

    String location;
    public LocationAlreadyExists(String location){
        super("Location '" + location + "' already exists");
        this.location = location;
    }

    public String getLocation(){
        return location;
    }
}
