package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.review;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReviewWriteFragment extends Fragment implements JsonListener {

    private int reservationID;
    private static final String reservationID_KEY = "resID";

    private RatingBar ratingBar;
    private TextView tvRatingName;
    private EditText etRatingComment;

    private OnReviewInteraction listener;

    public ReviewWriteFragment() {
        // Required empty public constructor
    }

    public static ReviewWriteFragment newInstance(int reservationID) {
        ReviewWriteFragment fragment = new ReviewWriteFragment();
        Bundle args = new Bundle();
        args.putInt(reservationID_KEY, reservationID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reservationID = getArguments().getInt(reservationID_KEY);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnReviewInteraction) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnReviewInteraction");
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_write, container, false);

        ratingBar = view.findViewById(R.id.rbReview);
        tvRatingName = view.findViewById(R.id.tvReviewRatingName);
        etRatingComment = view.findViewById(R.id.etReviewComments);
        Button btnRatingSubmit = view.findViewById(R.id.btnReviewSubmit);

        btnRatingSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvRatingName.getText().toString().equals("")) {
                    Map<String, String> params = new HashMap<>();
                    params.put(POST.reviewRating, String.valueOf(ratingBar.getRating()));
                    params.put(POST.reviewRatingComments, etRatingComment.getText().toString());
                    params.put(POST.reviewReservationID, String.valueOf(reservationID));
                    VolleyQueue.getInstance(getContext()).jsonRequest(ReviewWriteFragment.this, URL.reviewURL, params);
                } else {
                    Toast.makeText(getActivity(), "Please choose a rating!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ratingBar.setStepSize(1.0f);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tvRatingName.setText(reviewString(rating));
            }
        });

        return view;
    }


    @Override
    public void getSuccessResult(String url, JSONObject json) {
        switch (url) {
            case URL.reviewURL:
                double rating = ratingBar.getRating();
                String comments = etRatingComment.getText().toString();
                listener.completeReview();
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(getContext(), url + ": " + error, Toast.LENGTH_SHORT).show();
    }

    public static String reviewString(float rating) {
        if (rating <= 1.0) {
            return "Poor";
        }
        if (rating <= 2.0) {
            return "Fair";
        }
        if (rating <= 3.0) {
            return "Average";
        }
        if (rating <= 4.0) {
            return "Good";
        }
        if (rating <= 5.0) {
            return "Excellent";
        }
        return "";
    }
}
