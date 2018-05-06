package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions.NotificationCreation;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements JsonListener {

    private EditText etEmail;
    private EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //create a notification channel
        NotificationCreation.channel(this, "basic_channel","default channel" );

        String storedEmail = LocalVariables.readString(this, R.string.saved_email);
        String storedPass = LocalVariables.readString(this, R.string.saved_password);

        if (storedEmail != null && storedPass != null) {
            loginRequest(storedEmail,storedPass);
        }

        etEmail = findViewById(R.id.etLoginEmail);
        etPass = findViewById(R.id.etLoginPassword);

    }

    public void login(View view) {
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();

        loginRequest(email,pass);
    }

    private void loginRequest(String email, String pass) {
        Map<String, String> params = new HashMap<>();

        params.put(POST.loginEmail, email);
        params.put(POST.loginPassword, pass);

        VolleyQueue.getInstance(this).jsonRequest(this, URL.loginUrl, params);
    }

    public void forgotBtn(View view) {
        Intent intent = new Intent(this, ForgotActivity.class);
        startActivity(intent);
    }

    public void registerBtn(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        if (url.equals(URL.loginUrl)) {
            int customerId = json.getInt(POST.loginCustomerID);
            String firstName = json.getString(POST.loginFirstName);
            String title = json.getString(POST.loginTitle);
            String lastName = json.getString(POST.loginLastName);


            //TODO Implement OAUTH2 Token
            LocalVariables.storeInt(this, R.string.saved_customerId,customerId);
            LocalVariables.storeString(this, R.string.saved_email,etEmail.getText().toString().trim());
            LocalVariables.storeString(this, R.string.saved_password,etPass.getText().toString().trim());
            LocalVariables.storeString(this, R.string.saved_firstName,firstName);
            LocalVariables.storeString(this, R.string.saved_lastName,lastName);
            LocalVariables.storeString(this, R.string.saved_title,title);

            Toast.makeText(this, "Login Successful! CustomerID: " + customerId
                    +"firstName: "+ title+ firstName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_SHORT).show();
    }

}
