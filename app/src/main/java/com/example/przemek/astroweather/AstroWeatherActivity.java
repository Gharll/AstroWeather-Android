package com.example.przemek.astroweather;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.przemek.astroweather.CustomException.BadRangeException;
import com.example.przemek.astroweather.Fragment.MoonFragment;
import com.example.przemek.astroweather.Fragment.SettingsStorage;
import com.example.przemek.astroweather.Fragment.SunFragment;
import com.example.przemek.astroweather.Fragment.WeatherAdditionalInfoFragment;
import com.example.przemek.astroweather.Fragment.WeatherBasicInfoFragment;
import com.example.przemek.astroweather.Fragment.WeatherForecastFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AstroWeatherActivity extends AppCompatActivity
        implements MoonFragment.OnFragmentInteractionListener,
        SunFragment.OnFragmentInteractionListener ,
        WeatherBasicInfoFragment.OnFragmentInteractionListener,
        WeatherAdditionalInfoFragment.OnFragmentInteractionListener,
        WeatherForecastFragment.OnFragmentInteractionListener{

    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(isPortrait()){
            setContentView(R.layout.astro_weather_portrait);
        }

        if(isLandscape()) {
            setContentView(R.layout.astro_weather_landscape);
        }

        if (!isTablet()) {
            pageInit();
        }

        SettingsStorage.mPrefs = getSharedPreferences("settings", 0);
        try {
            SettingsStorage.restoreData();
        } catch (BadRangeException e) {
            e.printStackTrace();
        }
        currentTimeAndDataInit();

    }

    boolean isPortrait(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    boolean isLandscape(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    boolean isTablet(){
        return (getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
        Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }
    private void pageInit() {
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new PageAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(vpPager);

    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    private void currentTimeAndDataInit() {
        new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateTime();
                                updateDate();
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                }
            }
        }.start();
    }

    private void updateTime() {
        TextView tvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        tvCurrentTime.setText(getStrDate("HH:mm:ss"));
    }

    private void updateDate(){
        TextView tvCurrentDate = (TextView) findViewById(R.id.tv_current_date);
        tvCurrentDate.setText(getStrDate("yyyy/MM/dd"));
    }

    private String getStrDate(String pattern){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdfDate = new SimpleDateFormat(pattern);
        Date now = new Date();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR, SettingsStorage.getTimeZone());

        return sdfDate.format(calendar.getTime());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
