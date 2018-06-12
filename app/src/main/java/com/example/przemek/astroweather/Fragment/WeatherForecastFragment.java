package com.example.przemek.astroweather.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.przemek.astroweather.R;
import com.example.przemek.astroweather.Weather.TemperatureUnitEnum;
import com.example.przemek.astroweather.Weather.WeatherUnit;
import com.example.przemek.astroweather.Weather.WeatherDataManager;
import com.example.przemek.astroweather.Weather.WeatherForecastReader;
import com.example.przemek.astroweather.Weather.WeatherSettingsStorage;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherForecastFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeatherForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherForecastFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "page";

    // TODO: Rename and change types of parameters
    private int page;
    private String title;

    public String getTitle() {
        return title;
    }

    private OnFragmentInteractionListener mListener;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WeatherForecastFragment newInstance(int page, String title) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, page);
        args.putString(ARG_PARAM2, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_PARAM1);
            title = getArguments().getString(ARG_PARAM2);
        }
    }

    private Map <String, String> dayMap = new HashMap<>();

    public void initDayMap(){
        dayMap.put("Mon", "Monday");
        dayMap.put("Tue", "Tuesday");
        dayMap.put("Wed", "Wednesday");
        dayMap.put("Thu", "Thursday");
        dayMap.put("Fri", "Friday");
        dayMap.put("Sat", "Saturday");
        dayMap.put("Sun", "Sunday");
    }

    View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather_forecast, container, false);
        mView = v;
        initDayMap();

        try {
            WeatherDataManager weatherDataManager = WeatherDataManager.getInstance(getContext());
            WeatherForecastReader weatherForecastReader = new WeatherForecastReader(weatherDataManager.getCurrentLocationJSON());

            TableLayout table = v.findViewById(R.id.table_forecast);


            for (int i = 0; i < 9; i++) {
                String day = dayMap.get(weatherForecastReader.getDay(i));
                createTitle(day, table);
                createContent("Description", weatherForecastReader.getText(i), table);
                createContent("Date", weatherForecastReader.getDate(i), table);
                createTemperatureContent(weatherForecastReader, i, table);
                createTitle(" ", table);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {

        }


        return v;
    }

    void createTemperatureContent(WeatherForecastReader weatherForecastReader, int i, TableLayout table)
            throws JSONException {

        int minTemperature = Integer.parseInt(weatherForecastReader.getLow(i));
        int maxTemperature = Integer.parseInt(weatherForecastReader.getHigh(i));
        String minTemperatureStr, maxTemperatureStr;

        if(WeatherSettingsStorage.getTemperature() == TemperatureUnitEnum.CELSIUS){
            minTemperature = Math.round(WeatherUnit.convertFahrenheitToCelsius(minTemperature));
            maxTemperature = Math.round(WeatherUnit.convertFahrenheitToCelsius(maxTemperature));

            minTemperatureStr = WeatherUnit.getFormattedCelsius(minTemperature);
            maxTemperatureStr = WeatherUnit.getFormattedCelsius(maxTemperature);

        } else {
            minTemperatureStr = WeatherUnit.getFormattedFahrenheit(minTemperature);
            maxTemperatureStr = WeatherUnit.getFormattedFahrenheit(maxTemperature);

        }
        createContent("Temperature min", minTemperatureStr , table);
        createContent("Temperature max", maxTemperatureStr, table);
    }

    void createTitle(String text, TableLayout table) {
        TableRow row = new TableRow(getContext());
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(32);




        row.addView(tv);
        table.addView(row);
    }

    void createContent(String titleText, String contentText, TableLayout table) {
        TableRow row = new TableRow(getContext());

        TextView title = new TextView(getContext());
        titleText += ": ";
        title.setText(titleText);

        EditText content = new EditText(getContext());
        content.setText(contentText);

        row.addView(title);
        row.addView(content);
        table.addView(row);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
