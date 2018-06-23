package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;
import com.gpaschos_aikmpel.hotelbeaconapplication.utility.HoloCircularProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoyaltyFragment extends Fragment implements JsonListener {

    private int customerID;
    private String firstName, lastName;
    private int points;
    private String tierName;
    private int tierPoints;
    private String nextTierName;
    private int nextTierPoints;

    private TextView tvCustomerID, tvPoints, tvRewardsMember, tvFirstName, tvLastName, tvNextTierPoints, tvNextTier, tvNextTierUnlockLabel, tvNextTierAtLabel, tvNextTierPointsLabel;
    private HoloCircularProgressBar hcpb;


    public LoyaltyFragment() {
        // Required empty public constructor
    }

    public static LoyaltyFragment newInstance() {
        LoyaltyFragment fragment = new LoyaltyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loyalty, container, false);

        tvCustomerID = v.findViewById(R.id.tvLoyaltyCustomerID);
        tvRewardsMember = v.findViewById(R.id.tvLoyaltyRewardsMember);
        tvPoints = v.findViewById(R.id.tvLoyaltyPoints);
        tvFirstName = v.findViewById(R.id.tvLoyaltyMemberFirstName);
        tvLastName = v.findViewById(R.id.tvLoyaltyMemberLastName);
        tvNextTierPoints = v.findViewById(R.id.tvLoyaltyPointsNextTierPoints);
        tvNextTier = v.findViewById(R.id.tvLoyaltyPointsNextTier);
        tvNextTierUnlockLabel = v.findViewById(R.id.tvLoyaltyPointsNextTierUnlockLabel);
        tvNextTierAtLabel = v.findViewById(R.id.tvLoyaltyPointsNextTierAtLabel);
        tvNextTierPointsLabel = v.findViewById(R.id.tvLoyaltyPointsNextTierPointsLabel);
        hcpb = v.findViewById(R.id.cpbLoyalty);
        getLoyalty();

        return v;
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        switch (url) {
            case URL.loyaltyPointsURL:
                Customer customer = RoomDB.getInstance(getContext()).customerDao().getCustomer();

                firstName = customer.getFirstName();
                lastName = customer.getLastName();
                points = json.getInt(POST.loyaltyProgramPoints);
                tierName = json.getString(POST.loyaltyProgramTierName);
                tierPoints = json.getInt(POST.loyaltyProgramTierPoints);
                nextTierName = json.getString(POST.loyaltyProgramNextTierName);
                nextTierPoints = json.getInt(POST.loyaltyProgramNextTierPoints);
                JSONArray benefitsArray = json.getJSONArray(POST.loyaltyProgramBenefits);
                ArrayList<String> tierBenefits = new ArrayList<>();
                for (int i = 0; i < benefitsArray.length(); i++) {
                    tierBenefits.add(benefitsArray.getString(i));
                }
                updateUI();

                break;
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(getContext(), url + ": " + error, Toast.LENGTH_SHORT).show();
    }

    private void updateUI() {
        if(getContext()==null){
            return;
        }
        tvCustomerID.setText(String.valueOf(customerID));
        tvFirstName.setText(firstName);
        tvLastName.setText(lastName);
        tvRewardsMember.setText(tierName.toUpperCase());
        tvPoints.setText(String.valueOf(points));
        hcpb.setProgressColor(ContextCompat.getColor(getContext(), R.color.green));
        hcpb.setProgressBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));

        if (nextTierPoints != 0) {
            tvNextTier.setText(nextTierName);
            tvNextTierPoints.setText(String.valueOf(nextTierPoints));
            float progress = (float) (points - tierPoints) / (float) (nextTierPoints - tierPoints);
            animate(hcpb, null, progress, 1500);
        } else {
            tvNextTier.setVisibility(View.INVISIBLE);
            tvNextTierPoints.setVisibility(View.INVISIBLE);
            tvNextTierUnlockLabel.setVisibility(View.INVISIBLE);
            tvNextTierAtLabel.setVisibility(View.INVISIBLE);
            tvNextTierPointsLabel.setVisibility(View.INVISIBLE);
            animate(hcpb, null, 1f, 1500);
        }

        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, points);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tvPoints.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        valueAnimator.start();

    }

    public void getLoyalty() {
        if (getContext() == null)
            return;
        Map<String, String> params = new HashMap<>();
        customerID = RoomDB.getInstance(getContext()).customerDao().getCustomer().getCustomerId();
        params.put(POST.loyaltyPointsCustomerID, String.valueOf(customerID));
        VolleyQueue.getInstance(getContext()).jsonRequest(this, URL.loyaltyPointsURL, params);
    }

    private void animate(final HoloCircularProgressBar progressBar, final Animator.AnimatorListener listener,
                         final float progress, final int duration) {

        ObjectAnimator hcpbAnimator = ObjectAnimator.ofFloat(progressBar, "progress", progress);
        hcpbAnimator.setDuration(duration);

        hcpbAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationCancel(final Animator animation) {
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                progressBar.setProgress(progress);
            }

            @Override
            public void onAnimationRepeat(final Animator animation) {
            }

            @Override
            public void onAnimationStart(final Animator animation) {
            }
        });
        if (listener != null) {
            hcpbAnimator.addListener(listener);
        }
        hcpbAnimator.reverse();
        hcpbAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                progressBar.setProgress((Float) animation.getAnimatedValue());
            }
        });
        progressBar.setMarkerProgress(progress);
        hcpbAnimator.start();
    }
}