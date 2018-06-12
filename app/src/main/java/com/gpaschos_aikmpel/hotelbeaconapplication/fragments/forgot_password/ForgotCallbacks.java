package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.forgot_password;

public interface ForgotCallbacks {

    void finishedForgot(String email);

    void finishedVerify(String email, String code);

    void finishedNewPassword();

    void login();

    void register();

    void showForgot();

}
