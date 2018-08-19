package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class ContactFragment extends Fragment {


    public ContactFragment() {
        // Required empty public constructor
    }

    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        //TODO
        return view;
    }

}
