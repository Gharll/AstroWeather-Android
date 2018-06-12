package com.example.przemek.astroweather.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.przemek.astroweather.R;
import com.example.przemek.astroweather.Weather.TemperatureUnitEnum;
import com.example.przemek.astroweather.Weather.WeatherUnit;
import com.example.przemek.astroweather.Weather.WeatherDataManager;
import com.example.przemek.astroweather.Weather.WeatherReader;
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


        TextView tv_place = (TextView) v.findViewById(R.id.tv_place);
        TextView tv_text = (TextView) v.findViewById(R.id.tv_text);

        EditText et_temperature = (EditText) v.findViewById(R.id.et_temperature);
        EditText et_longitude = (EditText) v.findViewById(R.id.et_longitude);
        EditText et_latitude = (EditText) v.findViewById(R.id.et_latitude);
        EditText et_time = (EditText) v.findViewById(R.id.et_time);
        EditText et_air_pressure = (EditText) v.findViewById(R.id.et_air_pressure);


        WeatherDataManager weatherDataManager = WeatherDataManager.getInstance(getContext());


       /* try {
            weatherReader.downloadCurrentData();
        } catch (LocationNotExistsException e) {
            e.printStackTrace();
        }*/

        try {
            WeatherReader weatherReader = new WeatherReader(weatherDataManager.getCurrentLocationJSON());
            tv_place.setText(weatherReader.getCity() + ", " + weatherReader.getCountry());
            tv_text.setText(weatherReader.getText());
            et_longitude.setText(weatherReader.getLongitude());
            et_latitude.setText(weatherReader.getLatitude());


            float fahrenheit =  Float.parseFloat(weatherReader.getFahrenheitTemperature());
            if(WeatherSettingsStorage.getTemperature() == TemperatureUnitEnum.CELSIUS){
                float celsius = WeatherUnit.convertFahrenheitToCelsius(fahrenheit);
                et_temperature.setText(WeatherUnit.getFormattedCelsius(celsius));
            }
            if(WeatherSettingsStorage.getTemperature() == TemperatureUnitEnum.FAHRENHEIT){
                et_temperature.setText(WeatherUnit.getFormattedFahrenheit(fahrenheit));
            }


            et_time.setText(weatherReader.getTime());
            et_air_pressure.setText(weatherReader.getAirPressure() + " " + WeatherUnit.PRESSURE);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e ){

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
