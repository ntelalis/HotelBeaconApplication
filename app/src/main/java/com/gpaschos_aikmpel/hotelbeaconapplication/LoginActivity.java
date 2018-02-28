package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

        StringRequest stringRequest = ServerFunctions.loginFunction(this,email,pass);
        RequestQueueVolley.getInstance(this).add(stringRequest);

    }

    public void forgot(View view){
        Intent intent = new Intent(this,ForgotActivity.class);
        startActivity(intent);
    }

}
