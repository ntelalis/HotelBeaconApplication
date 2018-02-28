package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.toolbox.StringRequest;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void login(View view) {

        EditText etEmail = (EditText) findViewById(R.id.etEmailLogin);
        EditText etPass = (EditText) findViewById(R.id.etPassLogin);
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();

        StringRequest stringRequest = ServerFunctions.getLoginRequest(this,email,pass);
        RequestQueueVolley.getInstance(this).add(stringRequest);

    }

    public void forgot(View view){
        Intent intent = new Intent(this,ForgotActivity.class);
        startActivity(intent);
    }

    public void registerA(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }
}
