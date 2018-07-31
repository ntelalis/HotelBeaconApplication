package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.NotificationCallbacks;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.UpdateStoredVariables;

public class CheckedInFragment extends Fragment {


    private static final String ID_KEY = "id_KEY";
    private Reservation r;
    private TextView tvroom;

    public CheckedInFragment() {
        // Required empty public constructor
    }

    public static CheckedInFragment newInstance(int id) {
        CheckedInFragment fragment = new CheckedInFragment();
        Bundle args = new Bundle();
        args.putInt(ID_KEY,id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            int id = getArguments().getInt(ID_KEY);
            r = RoomDB.getInstance(getContext()).reservationDao().getReservationByID(id);
        }

        UpdateStoredVariables.checkedIn(getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_checked_in, container, false);

        tvroom = v.findViewById(R.id.tvcheckedinRoom);
        tvroom.setText(String.valueOf(r.getRoomNumber()));

        return v;
    }

}