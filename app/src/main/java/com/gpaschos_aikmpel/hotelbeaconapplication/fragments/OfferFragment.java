package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfferFragment extends Fragment {


    public OfferFragment() {
        // Required empty public constructor
    }

    public static OfferFragment newInstance() {
        OfferFragment fragment = new OfferFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_offer, container, false);
        return v;
    }

}
