package com.example.przemek.astroweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.przemek.astroweather.CustomException.BadRangeException;
import com.example.przemek.astroweather.Astro.AstroSettingsStorage;
import com.example.przemek.astroweather.Weather.TemperatureUnitEnum;
import com.example.przemek.astroweather.Weather.WeatherDataManager;
import com.example.przemek.astroweather.Weather.WeatherReader;
import com.example.przemek.astroweather.Weather.WeatherSettingsStorage;

import org.json.JSONException;

public class SettingsActivity extends AppCompatActivity {

    private EditText et_longitude;
    private EditText et_latitude;
    private Spinner frequencySpinner;
    private Spinner timeZoneSpinner;
    private Spinner temperatureSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
        saveAndExitHandle();


        try {
            AstroSettingsStorage.restoreData();
        } catch (BadRangeException e) {

        }

        showCurrentSettings();
    }

    ArrayAdapter<String> frequencyAdapter;
    ArrayAdapter<String> timeZoneAdapter;
    ArrayAdapter<String> temperatureAdapter;

    private void init() {
        AstroSettingsStorage.mPrefs = getSharedPreferences("settings", 0);
        et_longitude = (EditText) findViewById(R.id.et_longitude);
        et_latitude = (EditText) findViewById(R.id.et_latitude);
        initFrequencySpinner();
        initTimeZoneSpinner();
        initTemperatureSpinner();
        initCurrentSettingsCord();
    }

    private void initFrequencySpinner() {
        String[] frequencyArraySpinner = new String[]{
                "1", "5", "10", "30", "60"
        };
        frequencyAdapter = initAdapter(frequencyArraySpinner);
        frequencySpinner = initSpinner(R.id.spinner_frequency, frequencyArraySpinner);
    }

    private void initTimeZoneSpinner() {
        String[] timeZoneArraySpinner = initTimeZoneArraySpinner();
        timeZoneAdapter = initAdapter(timeZoneArraySpinner);
        timeZoneSpinner = initSpinner(R.id.spinner_timezone, timeZoneArraySpinner);
    }

    private String[] initTimeZoneArraySpinner() {
        int size = AstroSettingsStorage.MAX_TIME_OFFSET - AstroSettingsStorage.MIN_TIME_OFFSET + 1;
        String[] timeZoneArraySpinner = new String[size];
        int index = 0;
        for (int i = AstroSettingsStorage.MIN_TIME_OFFSET; i <= AstroSettingsStorage.MAX_TIME_OFFSET; i++) {
            timeZoneArraySpinner[index++] = String.valueOf(i);
        }

        return timeZoneArraySpinner;
    }

    private void initTemperatureSpinner() {
        String[] temperatureArraySpinner = new String[]{
                TemperatureUnitEnum.CELSIUS.name(), TemperatureUnitEnum.FAHRENHEIT.name()
        };

        temperatureAdapter = initAdapter(temperatureArraySpinner);
        temperatureSpinner = initSpinner(R.id.spinner_temperature, temperatureArraySpinner);
    }

    private ArrayAdapter<String> initAdapter(String[] arraySpinner) {
        return new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinner);
    }

    private Spinner initSpinner(int id, String[] arraySpinner) {
        ArrayAdapter<String> adapter = initAdapter(arraySpinner);
        Spinner spinner = (Spinner) findViewById(id);
        spinner.setAdapter(adapter);
        return spinner;
    }

    private void saveAndExitHandle() {
        Button button = (Button) findViewById(R.id.btn_save_and_exit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveAstroSettings();
                } catch (BadRangeException e) {
                    Toast.makeText(SettingsActivity.this, "Bad range: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    et_longitude.setText(String.valueOf(AstroSettingsStorage.getLongitude()));
                    et_latitude.setText(String.valueOf(AstroSettingsStorage.getLatitude()));
                }
                saveWeatherSettings();
            }
        });
    }

    private void saveAstroSettings() throws BadRangeException {
        AstroSettingsStorage.setLongitude(
                Float.parseFloat(et_longitude.getText().toString()));
        AstroSettingsStorage.setLatitude(
                Float.parseFloat(et_latitude.getText().toString()));

        AstroSettingsStorage.setDataFrequencyRefresh(
                Integer.parseInt(frequencySpinner.getSelectedItem().toString()));

        int inputTimeOffset = Integer.parseInt(timeZoneSpinner.getSelectedItem().toString());
        AstroSettingsStorage.setTimeZone(inputTimeOffset);

        startActivity(new Intent(SettingsActivity.this, MainActivity.class));

        AstroSettingsStorage.saveData();
    }

    private void saveWeatherSettings() {
        String temperatureStr = temperatureSpinner.getSelectedItem().toString();
        WeatherSettingsStorage.setTemperature(TemperatureUnitEnum.valueOf(temperatureStr));
    }

    private void showCurrentSettings() {
        et_longitude.setText(String.valueOf(AstroSettingsStorage.getLongitude()));
        et_latitude.setText(String.valueOf(AstroSettingsStorage.getLatitude()));
        frequencySpinner.setSelection(
                frequencyAdapter.getPosition(
                        String.valueOf(AstroSettingsStorage.getDataFrequencyRefresh())));
        timeZoneSpinner.setSelection(
                timeZoneAdapter.getPosition(
                        String.valueOf(AstroSettingsStorage.getTimeZone())));

        temperatureSpinner.setSelection(
                temperatureAdapter.getPosition(WeatherSettingsStorage.getTemperature().toString())
        );
    }

    private void initCurrentSettingsCord(){
        Button get_current_settings = findViewById(R.id.button_get_current_locaiton);
        get_current_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherDataManager weatherDataManager
                        = WeatherDataManager.getInstance(getApplicationContext());

                try{
                    WeatherReader weatherReader = new WeatherReader(weatherDataManager.getCurrentLocationJSON());
                    et_longitude.setText(weatherReader.getLongitude());
                    et_latitude.setText(weatherReader.getLatitude());
                } catch(NullPointerException e){

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
