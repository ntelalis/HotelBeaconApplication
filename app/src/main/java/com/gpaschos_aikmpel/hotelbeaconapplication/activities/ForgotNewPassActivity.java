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

public class ForgotNewPassActivity extends AppCompatActivity implements JsonListener {

    public static final String email_KEY = "email";
    public static final String code_KEY = "code";

    private EditText etPass;
    private EditText etPassVerify;

    private String code;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_new_pass);

        code = getIntent().getStringExtra(email_KEY);
        email = getIntent().getStringExtra(code_KEY);

        etPass = findViewById(R.id.etNewPassPassword);
        etPassVerify = findViewById(R.id.etNewPassConfirm);
    }

    public void update(View view) {

        String pass = etPass.getText().toString();
        String passVerify = etPassVerify.getText().toString();

        if (pass.equals(passVerify)) {
            Map<String, String> params = new HashMap<>();

            params.put(POST.forgotNewPassEmail, email);
            params.put(POST.forgotNewPassPassword, pass);
            params.put(POST.forgotNewPassVerification, code);

            VolleyQueue.getInstance(this).jsonRequest(this, URL.forgotNewPassUrl, params);
        } else {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_SHORT).show();
    }
}