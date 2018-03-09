package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        EditText etEmail = (EditText) findViewById(R.id.etLoginEmail);
        EditText etPass = (EditText) findViewById(R.id.etLoginPassword);
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();

        StringRequest loginRequest = ServerFunctions.getLoginRequest(this, email, pass);
        RequestQueueVolley.getInstance(this).add(loginRequest);

    }

    public void forgot(View view) {
        Intent intent = new Intent(this, ForgotActivity.class);
        startActivity(intent);
    }

    public void registerA(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
