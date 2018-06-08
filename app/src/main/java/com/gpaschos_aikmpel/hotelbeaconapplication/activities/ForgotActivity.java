package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.forgot_password.ForgotCallbacks;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.forgot_password.ForgotFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.forgot_password.ForgotNewPasswordFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.forgot_password.ForgotVerifyFragment;

public class ForgotActivity extends AppCompatActivity implements ForgotCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ForgotFragment forgotFragment = ForgotFragment.newInstance();
        transaction.setCustomAnimations(0, R.anim.exit_to_left, R.anim.enter_from_left, 0);
        transaction.replace(R.id.ForgotFragmentContainer, forgotFragment);
        transaction.commit();

    }

    @Override
    public void finishedForgot(String email) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        ForgotVerifyFragment forgotVerifyFragment = ForgotVerifyFragment.newInstance(email);
        transaction.addToBackStack(null);
        transaction.replace(R.id.ForgotFragmentContainer, forgotVerifyFragment);
        transaction.commit();
    }

    @Override
    public void finishedVerify(String email, String code) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        ForgotNewPasswordFragment forgotNewPasswordFragment = ForgotNewPasswordFragment.newInstance(email,code);
        transaction.addToBackStack(null);
        transaction.replace(R.id.ForgotFragmentContainer, forgotNewPasswordFragment);
        transaction.commit();
    }

    @Override
    public void finishedNewPassword() {
        finish();
    }
}
