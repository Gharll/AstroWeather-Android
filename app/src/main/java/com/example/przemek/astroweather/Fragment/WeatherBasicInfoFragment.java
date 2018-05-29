package com.example.przemek.astroweather.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.przemek.astroweather.CustomException.LocationNotExistsException;
import com.example.przemek.astroweather.R;
import com.example.przemek.astroweather.Weather.TemperatureUnitEnum;
import com.example.przemek.astroweather.Weather.UnitFormatter;
import com.example.przemek.astroweather.Weather.WeatherData;
import com.example.przemek.astroweather.Weather.WeatherSettingsStorage;

import org.json.JSONException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherBasicInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeatherBasicInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherBasicInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "page";

    // TODO: Rename and change types of parameters
    private int page;
    private String title;

    public String getTitle(){
        return title;
    }
    private OnFragmentInteractionListener mListener;

    public WeatherBasicInfoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WeatherBasicInfoFragment newInstance(int page, String title) {
        WeatherBasicInfoFragment fragment = new WeatherBasicInfoFragment();
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


    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_weather_basic_info, container, false);
        mView = v;


        EditText et_place = (EditText) v.findViewById(R.id.et_place);
        EditText et_temperature = (EditText) v.findViewById(R.id.et_temperature);
        EditText et_longitude = (EditText) v.findViewById(R.id.et_longitude);
        EditText et_latitude = (EditText) v.findViewById(R.id.et_latitude);
        EditText et_time = (EditText) v.findViewById(R.id.et_time);
        EditText et_air_pressure = (EditText) v.findViewById(R.id.et_air_pressure);

        WeatherData weatherData = new WeatherData("lodz");
        try {
            weatherData.downloadCurrentData();
        } catch (LocationNotExistsException e) {
            e.printStackTrace();
        }
        try {
            et_place.setText(weatherData.getCity() + ", " + weatherData.getCountry());
            et_longitude.setText(weatherData.getLongitude());
            et_latitude.setText(weatherData.getLatitude());

            if(WeatherSettingsStorage.getTemperature() == TemperatureUnitEnum.CELSIUS){
                float celsius = UnitFormatter.convertFahrenheitToCelsius(
                        Float.parseFloat(weatherData.getFahrenheitTemperature()));
                et_temperature.setText(UnitFormatter.getFormattedCelsius(celsius));
            }
            if(WeatherSettingsStorage.getTemperature() == TemperatureUnitEnum.FAHRENHEIT){
                float fahrenheit =  Float.parseFloat(weatherData.getFahrenheitTemperature());
                et_temperature.setText(UnitFormatter.getFormattedCelsius(fahrenheit));
            }


            et_time.setText(weatherData.getTime());
            et_air_pressure.setText(weatherData.getAirPressure());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
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
