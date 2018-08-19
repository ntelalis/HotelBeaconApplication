package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.ReservationActivity;


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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_reservation_none, container, false);
        Button btnReserve = view.findViewById(R.id.btnUpcomingReservationsNone);
        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReservationActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }


}
