package com.example.przemek.astroweather.CustomException;

/**
 * Created by Przemek on 13.06.2018.
 */

public class InternetConnectionException extends Exception {

    public InternetConnectionException(){
        super("Cannot connect to internet");
    }
}
