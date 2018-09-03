package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.AnimationUtilsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.activities.ReservationActivity;


public class UpcomingReservationNoneFragment extends Fragment {

    public UpcomingReservationNoneFragment() {
        // Required empty public constructor
    }


    public static UpcomingReservationNoneFragment newInstance() {
        UpcomingReservationNoneFragment fragment = new UpcomingReservationNoneFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_reservation_none, container, false);
        TextView tvNoRes = view.findViewById(R.id.tvUpcomingReservationNoneFragment);
        Animation a = AnimationUtils.loadAnimation(getContext(),R.anim.item_animation_fall_down);
        final Animation fadeIn = AnimationUtils.loadAnimation(getContext(),android.R.anim.fade_in);
        fadeIn.setDuration(1000);
        tvNoRes.clearAnimation();
        tvNoRes.startAnimation(a);
        final Button btnReserve = view.findViewById(R.id.btnUpcomingReservationsNone);
        btnReserve.setVisibility(View.INVISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnReserve.startAnimation(fadeIn);
                btnReserve.setVisibility(View.VISIBLE);
            }
        },800);

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReservationActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }


}
