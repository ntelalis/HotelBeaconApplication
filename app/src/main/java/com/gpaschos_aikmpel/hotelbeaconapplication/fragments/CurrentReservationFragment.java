package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class CurrentReservationFragment extends Fragment {

    private int pos;

    public CurrentReservationFragment() {

    }

    public static CurrentReservationFragment newInstance(int position) {
        CurrentReservationFragment currentReservationFragment = new CurrentReservationFragment();
        Bundle args = new Bundle();
        args.putInt("huh",position);
        currentReservationFragment.setArguments(args);
        return currentReservationFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pos = getArguments().getInt("huh");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_reservation, container, false);
        TextView tv =view.findViewById(R.id.tvViewRes);
        tv.setText(String.valueOf(pos));
        return view;
    }

}
