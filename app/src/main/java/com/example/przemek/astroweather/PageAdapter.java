package com.example.przemek.astroweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Przemek on 18.04.2018.
 */

public class PageAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 2;
    private String [] titles = {"Moon", "Sun"};

    public PageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        fragmentInit();

    }

    public void fragmentInit(){
            FragmentStorage.addFragment("moon", MoonFragment.newInstance(1, titles[0]));
            FragmentStorage.addFragment("sun", SunFragment.newInstance(2, titles[1]));
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragmentStorage.getFragment("moon");
            case 1:
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
