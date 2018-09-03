package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class MyRoomInactiveFragment extends Fragment {

    public MyRoomInactiveFragment() {

    }

    public static MyRoomInactiveFragment newInstance() {
        MyRoomInactiveFragment fragment = new MyRoomInactiveFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_room_inactive, container, false);
        TextView msg =  v.findViewById(R.id.tvCustomerServicesNoReservation);
        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        animation.setDuration(1000);
        msg.setAnimation(animation);
        return v;
    }

}
