package com.example.przemek.astroweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.przemek.astroweather.CustomException.LocationAlreadyExists;
import com.example.przemek.astroweather.CustomException.LocationNotExistsException;
import com.example.przemek.astroweather.Weather.DAO.WeatherDataEntity;
import com.example.przemek.astroweather.Weather.WeatherDataManager;
import com.example.przemek.astroweather.Weather.WeatherReader;

import org.json.JSONException;

import java.util.List;

public class LocationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        final TableLayout table = (TableLayout) findViewById(R.id.table_stored_location);
        final Button addLocationButton = (Button) findViewById(R.id.btn_add_location);
        final TextView error_message = (TextView) findViewById(R.id.tv_error_message);
        error_message.setVisibility(View.INVISIBLE);
        final EditText et_current_location = (EditText) findViewById(R.id.et_current_location);

        final EditText et_new_location = (EditText) findViewById(R.id.et_new_location);


        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                error_message.setVisibility(View.INVISIBLE);
                String userInput = et_new_location.getText().toString();

                WeatherDataManager weatherDataManager = WeatherDataManager.getInstance(getApplicationContext());
                weatherDataManager.setCurrentLocation(userInput);

                try {
                    weatherDataManager.storeCity(userInput);
                    weatherDataManager.setCurrentLocation(userInput);
                    WeatherReader weatherReader = new WeatherReader(weatherDataManager.getCurrentLocationJSON());
                    createRowLocation(weatherReader.getCity(), table, et_current_location);
                } catch (LocationNotExistsException e) {
                    error_message.setVisibility(View.VISIBLE);
                    error_message.setText(e.getMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (LocationAlreadyExists e) {
                    error_message.setVisibility(View.VISIBLE);
                    error_message.setText(e.getMessage());
                }

                et_new_location.setText("");
            }
        });

        showStoredData(table, et_current_location);
    }

    public void showStoredData(TableLayout tableLayout, EditText currentLocation ){
        WeatherDataManager weatherDataManager = WeatherDataManager.getInstance(getApplicationContext());
        List <WeatherDataEntity> storedData = weatherDataManager.getAll();
        for(WeatherDataEntity data : storedData){
            createRowLocation(data.getCity(), tableLayout, currentLocation);
        }
    }

    private void createRowLocation(final String name, TableLayout table, EditText et_current_location) {
        final TableLayout tl = table;
        final TableRow row = new TableRow(this);
        final EditText current_location = et_current_location;
        final String mName = name;

        TextView tv_location = new TextView(this);
        tv_location.setText("Location: ");

        EditText et_stored_location = new EditText(this);
        et_stored_location.setText(name);
        et_stored_location.setKeyListener(null);

        Button delete_button = new Button(this);
        delete_button.setText("delete");
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherDataManager weatherDataManager = WeatherDataManager.getInstance(getApplicationContext());
                tl.removeView(row);
                weatherDataManager.deleteStoredLocation(name);
            }
        });

        final Button current_button = new Button(this);
        current_button.setText("current");
        current_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_location.setText(mName);
            }
        });

        row.addView(tv_location);
        row.addView(et_stored_location);
        row.addView(delete_button);
        row.addView(current_button);
        table.addView(row);
    }
}
