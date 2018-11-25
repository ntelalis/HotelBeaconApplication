package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.review;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReviewCheckFragment extends Fragment {

    private ProgressBar pbReviewCheck;
    private TextView tvReviewCheckMessage;
    private OnReviewInteraction listener;

    private int reservationID;

    public static final String reservationID_KEY = "resID";

    public ReviewCheckFragment() {
        // Required empty public constructor
    }

    public static ReviewCheckFragment newInstance(int reservationID) {
        ReviewCheckFragment fragment = new ReviewCheckFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_check, container, false);

        pbReviewCheck = view.findViewById(R.id.pbReviewCheck);
        tvReviewCheckMessage = view.findViewById(R.id.tvReviewCheckMessage);

        Reservation r = RoomDB.getInstance(getContext()).reservationDao().getCurrentReservation();
        if(r.isReviewed()){
            pbReviewCheck.setVisibility(View.INVISIBLE);
            tvReviewCheckMessage.setVisibility(View.VISIBLE);

            double rating = r.getRating();
            String comments = r.getRatingComments();

            listener.checkReview(rating, comments);
            pbReviewCheck.setVisibility(View.VISIBLE);
            tvReviewCheckMessage.setVisibility(View.INVISIBLE);
        }
        else{
            listener.writeReview();
        }
        return view;
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

}
