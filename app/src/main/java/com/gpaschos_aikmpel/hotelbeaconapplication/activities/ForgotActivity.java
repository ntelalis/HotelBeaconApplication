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

public class ForgotActivity extends AppCompatActivity implements JsonListener {

    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        etEmail = findViewById(R.id.etForgotEmail);
    }

    public void reset(View view) {
        String email = etEmail.getText().toString().trim();

        Map<String, String> params = new HashMap<>();

        params.put(POST.forgotEmail, email);

        VolleyQueue.getInstance(this).jsonRequest(this, URL.forgotUrl, params);
    }


    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        String email = etEmail.getText().toString().trim();
        Intent intent = new Intent(this, ForgotVerifyActivity.class);
        intent.putExtra(ForgotVerifyActivity.email_KEY, email);
        startActivity(intent);
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_SHORT).show();
    }
}
