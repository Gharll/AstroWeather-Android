package com.example.przemek.astroweather.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import com.example.przemek.astroweather.AstroDateCalendarParser;
import com.example.przemek.astroweather.R;

import java.text.DecimalFormat;


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

    View mView;
    final Handler refreshHandler = new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sun, container, false);
        mView = v;

        final int seconds = SettingsStorage.getDataFrequencyRefresh();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateInfo(mView);
                refreshHandler.postDelayed(this, seconds * 1000);
            }
};
        refreshHandler.postDelayed(runnable, seconds * 1000);

                updateInfo(v);
                return v;
    }

    public void updateInfo(View v){
        AstroCalculator.Location astroLocation = new AstroCalculator.Location(
                SettingsStorage.getLatitude(),
                SettingsStorage.getLongitude());

        AstroDateTime astroDateTime = AstroDateCalendarParser.getNow(SettingsStorage.getTimeZone());
        AstroCalculator astroCalculator = new AstroCalculator(astroDateTime, astroLocation);
        AstroCalculator.SunInfo sunInfo = astroCalculator.getSunInfo();

        DecimalFormat azimuthFormatter = new DecimalFormat("#.##");
        final String DEGREE  = "\u00b0";

        EditText et_sunrise = (EditText) v.findViewById(R.id.et_sunrise);
        String sunriseText = "(" + AstroDateCalendarParser.getDateTime(sunInfo.getSunrise()) + ") "
                + azimuthFormatter.format(sunInfo.getAzimuthRise()) + DEGREE;
        et_sunrise.setText(sunriseText);

        EditText et_sunset = (EditText) v.findViewById(R.id.et_sunset);
        String sunsetText = "(" + AstroDateCalendarParser.getDateTime(sunInfo.getSunset()) + ") "
                + azimuthFormatter.format(sunInfo.getAzimuthSet()) + DEGREE;
        et_sunset.setText(sunsetText);

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
