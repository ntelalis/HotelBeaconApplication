package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.review;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class ReviewConfirmationFragment extends Fragment {



    public ReviewConfirmationFragment() {
        // Required empty public constructor
    }

    public static ReviewConfirmationFragment newInstance() {
        ReviewConfirmationFragment fragment = new ReviewConfirmationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review_confirmation, container, false);
    }

}
