package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gpaschos_aikmpel.hotelbeaconapplication.RequestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.RequestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements JsonListener {

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

        //StringRequest loginRequest = ServerFunctions.getLoginRequest(this, email, pass);
        //Response.getInstance(this).add(loginRequest);

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("pass", pass);
        VolleyQueue.getInstance(this).jsonRequest(this, GlobalVars.loginUrl, params);

    }

    public void forgot(View view) {
        Intent intent = new Intent(this, ForgotActivity.class);
        startActivity(intent);
    }

    public void registerA(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        if (url.equals(GlobalVars.loginUrl)) {
            int customerId = json.getInt("customerID");
            Toast.makeText(this, "Login Successful! CustomerID: " + customerId, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeScreenActivity.class);
            intent.putExtra("customerid", customerId);
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        if (url.equals(GlobalVars.loginUrl)) {
            Toast.makeText(this,error, Toast.LENGTH_SHORT).show();
        }
    }
}
