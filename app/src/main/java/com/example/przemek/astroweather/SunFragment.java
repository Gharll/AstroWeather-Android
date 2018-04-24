package com.example.przemek.astroweather;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SunFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SunFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SunFragment extends Fragment {
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

    public SunFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(int page, String title) {
        SunFragment fragment = new SunFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sun, container, false);
        updateInfo(v);
        return v;
    }

    public void updateInfo(View v){
        SettingsStorage settingsStorage = SettingsStorage.getInstance();
        AstroCalculator.Location astroLocation = new AstroCalculator.Location(
                settingsStorage.getLatitude(),
                settingsStorage.getLongitude());

        AstroDateTime astroDateTime = AstroDateCalendarParser.getNow(settingsStorage.getTimeZone());
        AstroCalculator astroCalculator = new AstroCalculator(astroDateTime, astroLocation);
        AstroCalculator.SunInfo sunInfo = astroCalculator.getSunInfo();

        EditText et_sunrise = (EditText) v.findViewById(R.id.et_sunrise);
        et_sunrise.setText(AstroDateCalendarParser.getDateTime(sunInfo.getSunrise()) + " " + String.valueOf(sunInfo.getAzimuthRise()));

        EditText et_sunset = (EditText) v.findViewById(R.id.et_sunset);
        et_sunset.setText(AstroDateCalendarParser.getDateTime(sunInfo.getSunset()) + " " + String.valueOf(sunInfo.getAzimuthSet()));

        EditText et_dusk = (EditText) v.findViewById(R.id.et_dusk);
        et_dusk.setText(AstroDateCalendarParser.getDateTime(sunInfo.getTwilightEvening()));

        EditText et_civil_dawn = (EditText) v.findViewById(R.id.et_civil_dawn);
        et_civil_dawn.setText(AstroDateCalendarParser.getDateTime(sunInfo.getTwilightMorning()));
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
