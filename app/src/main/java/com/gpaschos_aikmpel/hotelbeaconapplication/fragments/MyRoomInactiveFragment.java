package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class MyRoomInactiveFragment extends Fragment {

    public MyRoomInactiveFragment() {

    }

    public static MyRoomInactiveFragment newInstance() {
        MyRoomInactiveFragment fragment = new MyRoomInactiveFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_room_inactive, container, false);
    }

}
