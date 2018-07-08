package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class BookedFragment extends Fragment {

    private static final String RESERVATION_ID_KEY = "reservationID";
    public static final String TAG = BookedFragment.class.getSimpleName();
    private int reservationID;
    private Button btnShowReservations;
    private ReservationCallbacks listener;

    public BookedFragment() {
        // Required empty public constructor
    }

    public static BookedFragment newInstance(int reservationID) {
        BookedFragment fragment = new BookedFragment();
        Bundle args = new Bundle();
        args.putInt(RESERVATION_ID_KEY, reservationID);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reservationID = getArguments().getInt(RESERVATION_ID_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_booked, container, false);
        TextView tvReservationID = v.findViewById(R.id.tvBookConfirmReservationNumber);
        tvReservationID.setText(String.valueOf(reservationID));
        btnShowReservations = v.findViewById(R.id.btnBookConfirmReservation);
        btnShowReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.showReservations();
            }
        });
        return v;
    }



}