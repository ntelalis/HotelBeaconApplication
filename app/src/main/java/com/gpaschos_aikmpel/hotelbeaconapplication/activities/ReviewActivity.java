package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.review.OnReviewInteraction;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.review.ReviewAlreadyFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.review.ReviewCheckFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.review.ReviewConfirmationFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.review.ReviewWriteFragment;

public class ReviewActivity extends AppCompatActivity implements OnReviewInteraction {

    private int reservationID;
    private String checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Reservation reservation = RoomDB.getInstance(this).reservationDao().getCurrentReservation();

        reservationID = reservation.getId();
        checkout = reservation.getCheckOut();

        if (checkout != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ReviewCheckFragment reviewCheckFragment = ReviewCheckFragment.newInstance(reservationID);
            transaction.replace(R.id.ReviewfragmentContainer, reviewCheckFragment);
            transaction.commit();
        }
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
        ReviewAlreadyFragment reviewAlreadyFragment = ReviewAlreadyFragment.newInstance(rating, comments);
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
