package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.login.LoginCallbacks;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.login.ForgotFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.login.ForgotNewPasswordFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.login.ForgotVerifyFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.login.LoginFragment;

public class LoginActivity extends AppCompatActivity implements LoginCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        showLogin();
    }

    @Override
    public void finishedForgot(String email) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in,android.R.anim.fade_out);
        ForgotVerifyFragment forgotVerifyFragment = ForgotVerifyFragment.newInstance(email);
        transaction.addToBackStack(null);
        transaction.replace(R.id.ForgotFragmentContainer, forgotVerifyFragment);
        transaction.commit();
    }

    @Override
    public void finishedVerify(String email, String code) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in,android.R.anim.fade_out);
        ForgotNewPasswordFragment forgotNewPasswordFragment = ForgotNewPasswordFragment.newInstance(email, code);
        transaction.addToBackStack(null);
        transaction.replace(R.id.ForgotFragmentContainer, forgotNewPasswordFragment);
        transaction.commit();
    }

    @Override
    public void finishedNewPassword() {
        showLogin();
    }

    private void showLogin() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        LoginFragment loginFragment = LoginFragment.newInstance();
        transaction.replace(R.id.ForgotFragmentContainer, loginFragment);
        transaction.commit();
    }

    @Override
    public void login() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }

    @Override
    public void showForgot() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ForgotFragment forgotFragment = ForgotFragment.newInstance();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in,android.R.anim.fade_out);
        transaction.addToBackStack(null);
        transaction.replace(R.id.ForgotFragmentContainer, forgotFragment);
        transaction.commit();
    }
}
