package com.example.przemek.astroweather;

import android.net.Uri;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AstroWeatherActivity extends AppCompatActivity
        implements SunFragment.OnFragmentInteractionListener, MoonFragment.OnFragmentInteractionListener {

    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.astro_weather_standard);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new PageAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
