package com.example.przemek.astroweather.CustomException;

/**
 * Created by student on 5/25/2018.
 */

public class BadRangeException extends Exception {

    private int min;
    private int max;

    public BadRangeException(String fieldName, int min, int max) {
        super(fieldName + "[Min: " + min + ", " + "Max: " + max + "]");
        this.min = min;
        this.max = max;
    }

    public int getMin(){
        return min;
    }

    public int getMax(){
        return max;
    }

}
