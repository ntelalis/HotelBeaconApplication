package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;


public class UpcomingReservationNoneFragment extends Fragment {

    private ReservationCallbacks listener;
    private Button btnReserve;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReservationCallbacks) {
            listener = (ReservationCallbacks) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement ReservationCallbacks");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_fragment_upcoming_reservation_none, container, false);
        btnReserve = view.findViewById(R.id.btnUpcomingReservationsNone);
        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.newReservation();
            }
        });
        return view;

    }




}
