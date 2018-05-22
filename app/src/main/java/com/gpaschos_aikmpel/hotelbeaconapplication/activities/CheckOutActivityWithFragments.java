package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.CheckOutFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.CheckedOutFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.UpcomingReservationNoneFragment;

public class CheckOutActivityWithFragments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_with_fragments);

        if(savedInstanceState==null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CheckOutFragment fragment = CheckOutFragment.newInstance();
            transaction.replace(R.id.flCheckOutContainer, fragment);
            transaction.commit();
        }
    }

    public void checkedOutConfirmation(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CheckedOutFragment fragment = CheckedOutFragment.newInstance();
        transaction.replace(R.id.flCheckOutContainer, fragment);
        transaction.commit();
    }

}
