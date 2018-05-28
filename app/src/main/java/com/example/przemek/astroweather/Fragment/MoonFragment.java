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

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import com.example.przemek.astroweather.Astro.AstroDateCalendarParser;
import com.example.przemek.astroweather.Astro.AstroSettingsStorage;
import com.example.przemek.astroweather.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoonFragment extends Fragment {

    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "page";

    private int page;
    private String title;

    private OnFragmentInteractionListener mListener;

    public MoonFragment() {
        // Required empty public constructor
    }

    public static MoonFragment newInstance(int page, String title) {
        MoonFragment fragment = new MoonFragment();
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

    protected View mView;

    final Handler refreshHandler = new Handler();
    int i = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_moon, container, false);
        mView = v;
        updateInfo(v);

        final int seconds = AstroSettingsStorage.getDataFrequencyRefresh();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateInfo(mView);
                refreshHandler.postDelayed(this, seconds * 1000);
            }
        };
        refreshHandler.postDelayed(runnable, seconds * 1000);


        return v;
    }

    public void updateInfo(View v){
        AstroSettingsStorage settingsStorage = AstroSettingsStorage.getInstance();
        AstroCalculator.Location astroLocation = new AstroCalculator.Location(
                settingsStorage.getLatitude(),
                settingsStorage.getLongitude()
        );

        NumberFormat formatter = new DecimalFormat("#0.00");


        AstroDateTime astroDateTime = AstroDateCalendarParser.getNow(settingsStorage.getTimeZone());
        AstroCalculator astroCalculator = new AstroCalculator(astroDateTime, astroLocation);
        AstroCalculator.MoonInfo moonInfo = astroCalculator.getMoonInfo();

        EditText et_moonrise = (EditText) v.findViewById(R.id.et_moonrise);
        et_moonrise.setText(AstroDateCalendarParser.getDateTime(moonInfo.getMoonrise()));

        EditText et_moonset = (EditText) v.findViewById(R.id.et_moonset);
        et_moonset.setText(AstroDateCalendarParser.getDateTime(moonInfo.getMoonset()));

        EditText et_fullmoon = (EditText) v.findViewById(R.id.et_full_moon);
        et_fullmoon.setText(AstroDateCalendarParser.getDateTime(moonInfo.getNextFullMoon()));

        EditText et_day_of_lunar_month = (EditText) v.findViewById(R.id.et_day_of_lunar_month);
        et_day_of_lunar_month.setText(String.valueOf(
                formatter.format(moonInfo.getAge() % 29.530588853 )));

        EditText et_lunar_phase = (EditText) v.findViewById(R.id.et_lunar_phase);


        String lunarPhasePercent =
                formatter.format(moonInfo.getIllumination() * 100);
        et_lunar_phase.setText(lunarPhasePercent + " %");
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

    public String getTitle() {
        return title;
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
