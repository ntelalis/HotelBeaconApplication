package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;


public class UpcomingReservationNoneFragment extends Fragment {

    public UpcomingReservationNoneFragment() {
        // Required empty public constructor
    }


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
