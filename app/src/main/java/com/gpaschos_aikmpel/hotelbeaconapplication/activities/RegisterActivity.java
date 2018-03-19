package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.functions.Validation;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements JsonListener {

    private EditText etEmail;
    private EditText etPass;
    private EditText etPassConf;
    private EditText etFirstName;
    private EditText etLastName;
    private Spinner spTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.etRegisterEmail);
        etPass = findViewById(R.id.etRegisterPassword);
        etPassConf = findViewById(R.id.etRegisterPasswordConfirm);
        etFirstName = findViewById(R.id.etRegisterFirstName);
        etLastName = findViewById(R.id.etRegisterLastName);
        spTitle = findViewById(R.id.spRegisterTitle);

        loadSpinnerData();
    }

    //fill the spTitle with the Titles data.
    private void loadSpinnerData() {
        VolleyQueue.getInstance(this).jsonRequest(this, URL.titlesUrl, null);
    }

    public void register(View view) {

        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String passConf = etPassConf.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String title = spTitle.getSelectedItem().toString().trim();

        boolean flag = true;

        if (!Validation.checkEmail(email)) {
            etEmail.setError("Please enter a valid email address");
            flag = false;
        }

        //FIXME email changing doesn't fix error for pass contains email
        if (!Validation.checkPassword(pass, 6, Validation.PasswordType.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)) {
            etPass.setError("Invalid password");
            flag = false;
        } else {
            if (!Validation.checkNotContains(pass, email)) {
                etPass.setError("Password contains part of email");
                flag = false;
            }
        }

        if (!pass.equals(passConf)) {
            etPassConf.setError("Password confirmation doesn't match");
            flag = false;
        }

        if (!Validation.checkLength(firstName, 2, null)) {
            etFirstName.setError("Minimum Length is 2");
            flag = false;
        }

        if (!Validation.checkLength(lastName, 2, null)) {
            etLastName.setError("Minimum Length is 2");
            flag = false;
        }

        if (!flag) {
            return;
        }

        Map<String, String> params = new HashMap<>();

        params.put(POST.registerTitle, title);
        params.put(POST.registerFirstName, firstName);
        params.put(POST.registerLastName, lastName);
        params.put(POST.registerEmail, email);
        params.put(POST.registerPassword, pass);

        VolleyQueue.getInstance(this).jsonRequest(this, URL.registerUrl, params);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        switch (url) {
            case URL.registerUrl:
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case URL.titlesUrl:
                List<String> titleList = new ArrayList<>();
                JSONArray jsonArray = json.getJSONArray(POST.titlesTitleList);
                for (int i = 0; i < jsonArray.length(); i++) {
                    titleList.add(jsonArray.getString(i));
                }
                spTitle.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, titleList));
                break;
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_SHORT).show();
    }
}