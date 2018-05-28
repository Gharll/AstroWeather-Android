package com.example.przemek.astroweather.Astro;

import com.astrocalculator.AstroDateTime;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Przemek on 19.04.2018.
 */

public class AstroDateCalendarParser {

    private AstroDateCalendarParser(){

    }

    public static AstroDateTime getNow (int timeZone){

        AstroDateTime astroDateTime = new AstroDateTime();

        Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.HOUR, timeZone);

        astroDateTime.setTimezoneOffset(timeZone);
        astroDateTime.setYear(calendar.get(Calendar.YEAR));
        astroDateTime.setMonth(calendar.get(Calendar.MONTH)+1);
        astroDateTime.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        astroDateTime.setHour(calendar.get(Calendar.HOUR));
        astroDateTime.setMinute(calendar.get(Calendar.MINUTE));
        astroDateTime.setSecond(calendar.get(Calendar.SECOND));

        return astroDateTime;
    }

    public static String getDate(AstroDateTime dt){
        return dt.getYear() + "/" + format(dt.getMonth()) + "/" + format(dt.getDay());
    }

    public static String getTime(AstroDateTime dt){
       return  format(dt.getHour()) + ":" + format(dt.getMinute()) + ":" + format(dt.getSecond());
    }

    public static String format(int number){
        String sNumber = String.valueOf(number);
        if(number < 10){
            sNumber = "0" + sNumber;
        }
        return sNumber;
    }

    public static String getDateTime(AstroDateTime dt){
        return getDate(dt) + " " + getTime(dt);
    }
}
