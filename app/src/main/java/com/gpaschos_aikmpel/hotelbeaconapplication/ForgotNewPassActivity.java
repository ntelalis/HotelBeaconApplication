package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ForgotNewPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_new_pass);
    }

    public void update(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
