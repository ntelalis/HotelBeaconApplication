package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.UpdateStoredVariables;

public class CheckedOutFragment extends Fragment {


    public CheckedOutFragment() {
        // Required empty public constructor
    }

    public static CheckedOutFragment newInstance() {
        CheckedOutFragment fragment = new CheckedOutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UpdateStoredVariables.checkedOut(getContext());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_checked_out, container, false);
    }

}
