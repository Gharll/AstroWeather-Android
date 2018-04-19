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
    private Fragment currentFragment;
    private String [] titles = {"Moon", "Sun"};

    public PageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MoonFragment.newInstance(1, titles[0]);
            case 1:
                return SunFragment.newInstance(2,  titles[1]);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
