package com.example.przemek.astroweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.przemek.astroweather.CustomException.LocationNotExistsException;
import com.example.przemek.astroweather.Weather.WeatherData;

import org.json.JSONException;
import org.w3c.dom.Text;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        final TableLayout table = (TableLayout) findViewById(R.id.table_stored_location);
        Button addLocationButton = (Button) findViewById(R.id.btn_add_location);
        final TextView error_message = (TextView) findViewById(R.id.tv_error_message);
        error_message.setVisibility(View.INVISIBLE);

        final EditText et_new_location = (EditText) findViewById(R.id.et_new_location);

        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                error_message.setVisibility(View.INVISIBLE);
                String userInput = et_new_location.getText().toString();
                WeatherData weatherData = new WeatherData(userInput);
                try {
                    weatherData.downloadCurrentData();
                    table.addView(createRowLocation(weatherData.getLocationName()));
                } catch (LocationNotExistsException e) {
                    error_message.setVisibility(View.VISIBLE);
                    error_message.setText(e.getMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                et_new_location.setText("");
            }
        });

    }

    private TableRow createRowLocation(String name) {
        TableRow row = new TableRow(this);

        TextView tv_location = new TextView(this);
        tv_location.setText("Location: ");

        EditText et_stored_location = new EditText(this);
        et_stored_location.setText(name);
        et_stored_location.setKeyListener(null);

        row.addView(tv_location);
        row.addView(et_stored_location);

        return row;
    }
}
