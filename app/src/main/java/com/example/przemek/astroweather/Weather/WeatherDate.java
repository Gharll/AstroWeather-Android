package com.example.przemek.astroweather.Weather;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Przemek on 05.06.2018.
 */

public class WeatherDate {

    private static SimpleDateFormat dateFormatter
            = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static int expiredTimeSeconds = 10;

    public static Date parse(String rawDateStr){
        Date date = null;
        try {
            date = dateFormatter.parse(rawDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String format(Date date){
        return dateFormatter.format(date);
    }

    public static boolean checkIsUpdate(Date date){
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());

        Calendar checkingDate = Calendar.getInstance();
        checkingDate.setTime(date);
        checkingDate.add(Calendar.SECOND, WeatherSettingsStorage.getExpiredDateTimeSeconds());

        Log.v("NOW:", now.toString());
        Log.v("CHECK", checkingDate.toString());

        return !checkingDate.after(now);
    }

    public static SimpleDateFormat getDateFormatter(){
        return dateFormatter;
    }
}
