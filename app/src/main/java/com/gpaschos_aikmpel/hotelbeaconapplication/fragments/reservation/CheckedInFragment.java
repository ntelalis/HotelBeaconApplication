package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.UpdateStoredVariables;

public class CheckedInFragment extends Fragment {

    private Callbacks listener;

    private static final String ID_KEY = "id_KEY";
    private Reservation r;

    public CheckedInFragment() {
        // Required empty public constructor
    }

    public static CheckedInFragment newInstance(int id) {
        CheckedInFragment fragment = new CheckedInFragment();
        Bundle args = new Bundle();
        args.putInt(ID_KEY, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Callbacks){
            listener = (Callbacks) context;
        }
        else{
            throw new ClassCastException(context.toString() + " must implement Callbacks");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int id = getArguments().getInt(ID_KEY);
            r = RoomDB.getInstance(getContext()).reservationDao().getReservationByID(id);
        }

        UpdateStoredVariables.checkedIn(getContext());


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_checked_in, container, false);

        Button btnMyRoom = v.findViewById(R.id.btnCheckedInMyRoom);
        btnMyRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.openMyRoom();
            }
        });
        TextView tvRoom = v.findViewById(R.id.tvCheckedInRoom);
        tvRoom.setText(String.valueOf(r.getRoomNumber()));

        return v;
    }

    public interface Callbacks{
        void openMyRoom();
    }

}