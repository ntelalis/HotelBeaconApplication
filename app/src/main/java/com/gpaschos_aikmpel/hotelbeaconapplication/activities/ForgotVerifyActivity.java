package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotVerifyActivity extends AppCompatActivity implements JsonListener {

    public static final String email_KEY = "email";

    private EditText etVerify;

    private String email;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_verify);

        email = getIntent().getStringExtra(email_KEY);

        etVerify = findViewById(R.id.etVerifyCode);
    }

    public void verify(View view) {
        code = etVerify.getText().toString().trim();

        Map<String, String> params = new HashMap<>();

        params.put(POST.forgotVerifyEmail, email);
        params.put(POST.forgotVerifyVerification, code);

        VolleyQueue.getInstance(this).jsonRequest(this, URL.forgotVerifyUrl, params);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) {
        Intent intent = new Intent(this, ForgotNewPassActivity.class);
        intent.putExtra(ForgotNewPassActivity.email_KEY, email);
        intent.putExtra(ForgotNewPassActivity.code_KEY, code);
        startActivity(intent);
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_SHORT).show();
    }
}
