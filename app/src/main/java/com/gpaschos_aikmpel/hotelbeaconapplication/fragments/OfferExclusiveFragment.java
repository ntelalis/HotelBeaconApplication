package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class OfferExclusiveFragment extends Fragment {


    public OfferExclusiveFragment() {
        // Required empty public constructor
    }

    public static OfferExclusiveFragment newInstance() {
        OfferExclusiveFragment fragment = new OfferExclusiveFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_offer_exclusive, container, false);
        return v;
    }

}
