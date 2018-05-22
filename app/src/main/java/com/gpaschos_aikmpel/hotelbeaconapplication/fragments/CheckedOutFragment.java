package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class CheckedOutFragment extends Fragment {


    public CheckedOutFragment() {
        // Required empty public constructor
    }

    public static CheckedOutFragment newInstance() {
        CheckedOutFragment fragment = new CheckedOutFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_checked_out, container, false);
    }

}
