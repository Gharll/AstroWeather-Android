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
import android.widget.Toast;

import com.example.przemek.astroweather.CustomException.BadRangeException;
import com.example.przemek.astroweather.Fragment.SettingsStorage;

public class SettingsActivity extends AppCompatActivity {

    private SettingsStorage settingsStorage = SettingsStorage.getInstance();
    private EditText et_longitude;
    private EditText et_latitude;
    private Spinner frequencySpinner;
    private Spinner timeZoneSpinner;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
        saveAndExitHandle();


        try{
            SettingsStorage.restoreData();
        } catch (BadRangeException e){

        }

        showCurrentSettings();
    }

    ArrayAdapter<String> frequencyAdapter;
    ArrayAdapter<String> timeZoneAdapter;

    private void init(){
        SettingsStorage.mPrefs = getSharedPreferences("settings", 0);
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

                try{
                    settingsStorage.setLongitude(
                            Float.parseFloat(et_longitude.getText().toString()));
                    settingsStorage.setLatitude(
                            Float.parseFloat(et_latitude.getText().toString()));

                    settingsStorage.setDataFrequencyRefresh(
                            Integer.parseInt(frequencySpinner.getSelectedItem().toString()));

                    int inputTimeOffset = Integer.parseInt(timeZoneSpinner.getSelectedItem().toString());
                    settingsStorage.setTimeZone(inputTimeOffset);

                    startActivity(new Intent(SettingsActivity.this, MainActivity.class));

                    SettingsStorage.saveData();

                }catch(BadRangeException e){
                    Toast.makeText(SettingsActivity.this, "Bad range: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    et_longitude.setText(String.valueOf(SettingsStorage.getLongitude()));
                    et_latitude.setText(String.valueOf(SettingsStorage.getLatitude()));
                }


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



}
