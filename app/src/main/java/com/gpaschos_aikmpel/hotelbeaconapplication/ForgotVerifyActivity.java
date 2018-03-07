package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.toolbox.StringRequest;

public class ForgotVerifyActivity extends AppCompatActivity {

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_verify);
        email = getIntent().getStringExtra("email");
    }

    public void verify(View view){
        EditText etVerify = (EditText)findViewById(R.id.etConfirmVerify);
        String verification = etVerify.getText().toString().trim();

        StringRequest forgotverifyRequest = ServerFunctions.getForgotVerifyRequest(this,email,verification);
        RequestQueueVolley.getInstance(this).add(forgotverifyRequest);

    }
}
