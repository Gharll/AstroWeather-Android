package com.example.przemek.astroweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureActivity(R.id.btn_astro_weather, AstroWeatherActivity.class);
        configureActivity(R.id.btn_settings, SettingsActivity.class);
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
