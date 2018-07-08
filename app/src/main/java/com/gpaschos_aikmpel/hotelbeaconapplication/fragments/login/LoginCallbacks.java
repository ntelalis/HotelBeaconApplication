package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.login;

public interface LoginCallbacks {

    void finishedForgot(String email);

    void finishedVerify(String email, String code);

    void finishedNewPassword();

    void login();

    void register();

    void showForgot();

}
