package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.OnReviewInteraction;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.ReviewAlreadyFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.ReviewCheckFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.ReviewConfirmationFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.ReviewWriteFragment;

public class ReviewActivity extends AppCompatActivity implements OnReviewInteraction {

    //TODO change it to get it correctly
    private int reservationID = 95;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ReviewCheckFragment reviewCheckFragment = ReviewCheckFragment.newInstance(reservationID);
        transaction.replace(R.id.ReviewfragmentContainer, reviewCheckFragment);
        transaction.commit();
    }

    @Override
    public void writeReview() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ReviewWriteFragment reviewWriteFragment = ReviewWriteFragment.newInstance(reservationID);
        transaction.replace(R.id.ReviewfragmentContainer, reviewWriteFragment);
        transaction.commit();
    }

    @Override
    public void checkReview(double rating, String comments) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ReviewAlreadyFragment reviewAlreadyFragment = ReviewAlreadyFragment.newInstance(rating,comments);
        transaction.replace(R.id.ReviewfragmentContainer, reviewAlreadyFragment);
        transaction.commit();
    }

    @Override
    public void completeReview() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ReviewConfirmationFragment reviewConfirmationFragment = ReviewConfirmationFragment.newInstance();
        transaction.replace(R.id.ReviewfragmentContainer, reviewConfirmationFragment);
        transaction.commit();
    }
}
