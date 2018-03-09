package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.toolbox.StringRequest;

public class ForgotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

    }

    public void reset(View view){
        EditText etEmail = findViewById(R.id.etForgotEmail);
        String email = etEmail.getText().toString().trim();

        StringRequest forgotRequest = ServerFunctions.getForgotRequest(this,email);
        Response.getInstance(this).add(forgotRequest);
    }



}
