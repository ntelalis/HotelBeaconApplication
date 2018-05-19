package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UseLoyaltyPointsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UseLoyaltyPointsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UseLoyaltyPointsFragment extends Fragment {

    private static final String TOTAL_POINTS = "total_points";
    private static final String POINTS_FREE_NIGHT = "free_night_points";
    private static final String POINTS_PARTIAL_PAYMENT = "free_night_points";
    private static final String CASH_PARTIAL_PAYMENT = "free_night_points";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UseLoyaltyPointsFragment() {
        // Required empty public constructor
    }

    public static UseLoyaltyPointsFragment newInstance(String param1, String param2) {
        UseLoyaltyPointsFragment fragment = new UseLoyaltyPointsFragment();
        Bundle args = new Bundle();
        args.putString(TOTAL_POINTS, param1);
        args.putString(POINTS_FREE_NIGHT, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(TOTAL_POINTS);
            mParam2 = getArguments().getString(POINTS_FREE_NIGHT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_use_loyalty_points, container, false);
        return v;
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


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String s);
    }
}
