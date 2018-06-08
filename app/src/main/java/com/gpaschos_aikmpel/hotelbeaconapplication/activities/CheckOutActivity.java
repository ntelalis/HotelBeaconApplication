package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.CheckOutFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.CheckedOutFragment;

public class CheckOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

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
