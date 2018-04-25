package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;


public class ReviewAlreadyFragment extends Fragment {

    private static final String KEY_RATING = "RATING";
    private static final String KEY_COMMENTS = "COMMENTS";

    private double rating;
    private String comments;

    private RatingBar rbRating;
    private TextView tvRating;
    private EditText etComments;

    public ReviewAlreadyFragment() {
        // Required empty public constructor
    }

    public static ReviewAlreadyFragment newInstance(double rating, String comments) {
        ReviewAlreadyFragment fragment = new ReviewAlreadyFragment();
        Bundle args = new Bundle();
        args.putDouble(KEY_RATING, rating);
        args.putString(KEY_COMMENTS, comments);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rating = getArguments().getDouble(KEY_RATING);
        comments = getArguments().getString(KEY_COMMENTS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_already, container, false);
        rbRating = view.findViewById(R.id.rbReviewAlreadyRating);
        rbRating.setRating((float) rating);
        tvRating = view.findViewById(R.id.tvReviewAlreadyRating);
        tvRating.setText(ReviewWriteFragment.reviewString((float) rating));
        etComments = view.findViewById(R.id.etReviewAlreadyComments);
        etComments.setText(comments);
        etComments.setKeyListener(null);
        return view;
    }

}
