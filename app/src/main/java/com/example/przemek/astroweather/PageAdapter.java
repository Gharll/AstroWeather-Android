package com.example.przemek.astroweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.przemek.astroweather.Fragment.FragmentStorage;
import com.example.przemek.astroweather.Fragment.MoonFragment;
import com.example.przemek.astroweather.Fragment.SunFragment;
import com.example.przemek.astroweather.Fragment.WeatherAdditionalInfoFragment;
import com.example.przemek.astroweather.Fragment.WeatherBasicInfoFragment;
import com.example.przemek.astroweather.Fragment.WeatherForecastFragment;

/**
 * Created by Przemek on 18.04.2018.
 */

public class PageAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS;
    private String [] titles = {"Weather Basic", "Weather Additional", "Weather Forecast" ,"Moon", "Sun"};

    public PageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        fragmentInit();
        NUM_ITEMS = titles.length;

    }

    public void fragmentInit(){
            FragmentStorage.addFragment("weatherBasic", WeatherBasicInfoFragment.newInstance(1, titles[0]));
            FragmentStorage.addFragment("weatherAdditional", WeatherAdditionalInfoFragment.newInstance(2, titles[1]));
            FragmentStorage.addFragment("weatherForecast", WeatherForecastFragment.newInstance(3, titles[2]));
            FragmentStorage.addFragment("moon", MoonFragment.newInstance(4, titles[3]));
            FragmentStorage.addFragment("sun", SunFragment.newInstance(5, titles[4]));
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragmentStorage.getFragment("weatherBasic");
            case 1:
                return FragmentStorage.getFragment("weatherAdditional");
            case 2:
                return FragmentStorage.getFragment("weatherForecast");
            case 3:
                return FragmentStorage.getFragment("moon");
            case 4:
                return FragmentStorage.getFragment("sun");
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
