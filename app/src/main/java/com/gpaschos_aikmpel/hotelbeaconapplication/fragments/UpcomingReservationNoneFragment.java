package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpcomingReservationNoneFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpcomingReservationNoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingReservationNoneFragment extends Fragment {

    public UpcomingReservationNoneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment UpcomingReservationNoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpcomingReservationNoneFragment newInstance() {
        UpcomingReservationNoneFragment fragment = new UpcomingReservationNoneFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_reservation_none, container, false);
        return view;

    }




}
