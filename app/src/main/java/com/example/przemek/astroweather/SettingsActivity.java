package com.example.przemek.astroweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private SettingsStorage settingsStorage = SettingsStorage.getInstance();
    private EditText et_longitude;
    private EditText et_latitude;
    /*private EditText et_frequency;
    private EditText et_time_zone_offset;*/
    private Spinner frequencySpinner;
    private Spinner timeZoneSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
        saveAndExitHandle();
        showCurrentSettings();
    }

    ArrayAdapter<String> frequencyAdapter;
    ArrayAdapter<String> timeZoneAdapter;

    private void init(){
        et_longitude = (EditText) findViewById(R.id.et_longitude);
        et_latitude = (EditText) findViewById(R.id.et_latitude);
       //et_frequency = (EditText) findViewById(R.id.et_frequency);
       // et_time_zone_offset = (EditText) findViewById(R.id.et_time_zone_offset);


        String[] frequencyArraySpinner = new String[]{
                "1", "5", "10", "30", "60"
        };

        int size = settingsStorage.MAX_TIME_OFFSET - settingsStorage.MIN_TIME_OFFSET + 1;
        String[] timeZoneArraySpinner = new String[size];
        int index = 0;
        for(int i = settingsStorage.MIN_TIME_OFFSET; i <= settingsStorage.MAX_TIME_OFFSET; i++){
            timeZoneArraySpinner[index++] = String.valueOf(i);
        }

        frequencySpinner = (Spinner) findViewById(R.id.spinner_frequency);
        frequencyAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, frequencyArraySpinner);
        frequencySpinner.setAdapter(frequencyAdapter);

        timeZoneSpinner = (Spinner) findViewById(R.id.spinner_timezone);
        timeZoneAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, timeZoneArraySpinner);
        timeZoneSpinner.setAdapter(timeZoneAdapter);

    }

    private void saveAndExitHandle(){
        Button button = (Button) findViewById(R.id.btn_save_and_exit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsStorage.setLongitude(
                        Float.parseFloat(et_longitude.getText().toString()));
                settingsStorage.setLatitude(
                        Float.parseFloat(et_latitude.getText().toString()));
                settingsStorage.setDataFrequencyRefresh(
                        Integer.parseInt(frequencySpinner.getSelectedItem().toString()));

                int inputTimeOffset = Integer.parseInt(timeZoneSpinner.getSelectedItem().toString());
                if(inputTimeOffset > settingsStorage.MAX_TIME_OFFSET){
                    inputTimeOffset = settingsStorage.MAX_TIME_OFFSET;
                }
                if(inputTimeOffset < settingsStorage.MIN_TIME_OFFSET){
                    inputTimeOffset = settingsStorage.MIN_TIME_OFFSET;
                }
                settingsStorage.setTimeZone(inputTimeOffset);

                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            }
        });
    }

    private void showCurrentSettings(){
        et_longitude.setText(String.valueOf(settingsStorage.getLongitude()));
        et_latitude.setText(String.valueOf(settingsStorage.getLatitude()));
        frequencySpinner.setSelection(frequencyAdapter.getPosition(String.valueOf(SettingsStorage.getDataFrequencyRefresh())));
        timeZoneSpinner.setSelection(timeZoneAdapter.getPosition(String.valueOf(SettingsStorage.getTimeZone())));

        //et_frequency.setText(String.valueOf(settingsStorage.getDataFrequencyRefresh()));
//        et_time_zone_offset.setText(String.valueOf(settingsStorage.getTimeZone()));
    }
}
