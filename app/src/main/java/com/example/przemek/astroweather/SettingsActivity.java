package com.example.przemek.astroweather;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private Spinner frequencySpinner;
    private Spinner timeZoneSpinner;


    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
        saveAndExitHandle();

        mPrefs = getSharedPreferences("settings", 0);
        restoreData();
        showCurrentSettings();
    }

    ArrayAdapter<String> frequencyAdapter;
    ArrayAdapter<String> timeZoneAdapter;

    private void init(){
        et_longitude = (EditText) findViewById(R.id.et_longitude);
        et_latitude = (EditText) findViewById(R.id.et_latitude);
        initSpinner();
    }

    private void initSpinner(){
        String[] frequencyArraySpinner = new String[]{
                "1", "5", "10", "30", "60"
        };

        String[] timeZoneArraySpinner = initTimeZoneArraySpinner();

        frequencyAdapter = initAdapter(frequencyArraySpinner);
        frequencySpinner = initSpinner(R.id.spinner_frequency, frequencyArraySpinner);

        timeZoneAdapter = initAdapter(timeZoneArraySpinner);
        timeZoneSpinner = initSpinner(R.id.spinner_timezone, timeZoneArraySpinner);
    }

    private String[] initTimeZoneArraySpinner(){
        int size = settingsStorage.MAX_TIME_OFFSET - settingsStorage.MIN_TIME_OFFSET + 1;
        String[] timeZoneArraySpinner = new String[size];
        int index = 0;
        for(int i = settingsStorage.MIN_TIME_OFFSET; i <= settingsStorage.MAX_TIME_OFFSET; i++){
            timeZoneArraySpinner[index++] = String.valueOf(i);
        }

        return timeZoneArraySpinner;
    }

    private ArrayAdapter<String> initAdapter(String[] arraySpinner){
        return new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinner);
    }

    private Spinner initSpinner(int id, String[] arraySpinner){
        ArrayAdapter<String> adapter = initAdapter(arraySpinner);
        Spinner spinner = (Spinner) findViewById(id);
        spinner.setAdapter(adapter);
        return spinner;
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
                settingsStorage.setTimeZone(inputTimeOffset);

                startActivity(new Intent(SettingsActivity.this, MainActivity.class));

                saveData();
            }
        });
    }

    private void showCurrentSettings(){
        et_longitude.setText(String.valueOf(settingsStorage.getLongitude()));
        et_latitude.setText(String.valueOf(settingsStorage.getLatitude()));
        frequencySpinner.setSelection(
                frequencyAdapter.getPosition(
                        String.valueOf(SettingsStorage.getDataFrequencyRefresh())));
        timeZoneSpinner.setSelection(
                timeZoneAdapter.getPosition(
                        String.valueOf(SettingsStorage.getTimeZone())));
    }


    private void restoreData(){
        SettingsStorage.setLongitude(Double.parseDouble(mPrefs.getString("longitude", "404")));
        SettingsStorage.setLatitude(Double.parseDouble(mPrefs.getString("latitude", "404")));

        SettingsStorage.setTimeZone(mPrefs.getInt("time_zone", 4));
        SettingsStorage.setDataFrequencyRefresh(mPrefs.getInt("refresh", 4));
    }

    private void saveData(){
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("longitude", String.valueOf(SettingsStorage.getLongitude())).commit();
        mEditor.putString("latitude", String.valueOf(SettingsStorage.getLatitude())).commit();
        mEditor.putInt("time_zone", SettingsStorage.getTimeZone()).commit();
        mEditor.putInt("refresh", SettingsStorage.getDataFrequencyRefresh()).commit();
    }
}
