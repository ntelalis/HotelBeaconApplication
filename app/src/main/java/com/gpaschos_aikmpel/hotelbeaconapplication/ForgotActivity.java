package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

public class ForgotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
    }

    public void reset(View view){
        EditText etEmail = (EditText)findViewById(R.id.etEmailForgot);
        String email = etEmail.getText().toString().trim();

        Intent intent = new Intent(this,ForgotVerifyActivity.class);
        startActivity(intent);
    }
}
