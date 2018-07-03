package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.BeaconApplication;
import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckInFragment extends Fragment {

    private Button btnCheckIn;
    private TextView tvArrivalDate;
    private TextView tvDepartureDate;
    private TextView tvReservationNo;
    private Reservation reservation;


    public CheckInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reservation = RoomDB.getInstance(getContext()).reservationDao().getCurrentReservation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_check_in, container, false);
        btnCheckIn = v.findViewById(R.id.btnCheckInCheckin);
        tvArrivalDate = v.findViewById(R.id.tvCheckInReservationArrival);
        tvDepartureDate = v.findViewById(R.id.tvCheckInReservationDeparture);
        tvReservationNo = v.findViewById(R.id.tvCheckInReservationNo);
        tvReservationNo.setText(String.valueOf(reservation.getId()));
        tvArrivalDate.setText(reservation.getStartDate());
        tvDepartureDate.setText(reservation.getEndDate());
        if(reservation.isCheckedIn()){
            btnCheckIn.setEnabled(false);
            btnCheckIn.setText(R.string.btnCheckInCheckedin);
        }

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BeaconApplication) Objects.requireNonNull(getActivity()).getApplication()).checkin(reservation.getId());
            }
        });

        return v;
    }

}