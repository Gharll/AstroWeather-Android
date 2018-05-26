package com.example.przemek.astroweather.Fragment;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Przemek on 05.05.2018.
 */

public class FragmentStorage {

    private static HashMap<String, Fragment> fragments = new HashMap<>();

    FragmentStorage(){

    }

    public static Fragment getFragment(String key){
        return fragments.get(key);
    }

    public static void addFragment(String name, Fragment fragment){
        fragments.put(name, fragment);
    }

}
