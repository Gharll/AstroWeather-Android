package com.example.przemek.astroweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.przemek.astroweather.Astro.AstroSettingsStorage;
import com.example.przemek.astroweather.CustomException.BadRangeException;
import com.example.przemek.astroweather.Weather.WeatherSettingsStorage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureActivity(R.id.btn_astro_weather, AstroWeatherActivity.class);
        configureActivity(R.id.btn_settings, SettingsActivity.class);
        configureActivity(R.id.btn_location, LocationActivity.class);

        restoreData();

        handleExit();
    }

    private void configureActivity(int resource, final Class activityClass){
        Button button = (Button) findViewById(resource);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, activityClass));
            }
        });
    }

    private void restoreData(){
        try {
            AstroSettingsStorage.mPrefs = getSharedPreferences("settings", 0);
            AstroSettingsStorage.restoreData();

        } catch (BadRangeException e) {
            e.printStackTrace();
        }
        WeatherSettingsStorage.mPrefs = getSharedPreferences("settings", 0);
        WeatherSettingsStorage.restoreData();
    }

    private void handleExit(){
        Button exitButton = (Button) findViewById(R.id.btn_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }
}
