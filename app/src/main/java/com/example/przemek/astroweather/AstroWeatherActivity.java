package com.example.przemek.astroweather;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AstroWeatherActivity extends AppCompatActivity
        implements SunFragment.OnFragmentInteractionListener, MoonFragment.OnFragmentInteractionListener {

    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.astro_weather);
        if (!isTablet()) {
            pageInit();
        }
        currentTimeInit();
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

    private void currentTimeInit() {
        new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateTime();
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
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        String strTime = sdfTime.format(now);
        tvCurrentTime.setText(strTime);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
