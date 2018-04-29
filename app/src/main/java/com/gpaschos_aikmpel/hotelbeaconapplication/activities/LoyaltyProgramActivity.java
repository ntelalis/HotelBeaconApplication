package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.toolbox.Volley;
import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoyaltyProgramActivity extends AppCompatActivity implements JsonListener {

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalty_program);

    }

    public void rewards(View view){

    }

    public void getLoyalty(){
        Map<String,String> params = new HashMap<>();
        sharedPref = getSharedPreferences(getString(R.string.spfile), Context.MODE_PRIVATE);
        int customerID = sharedPref.getInt(getString(R.string.saved_customerId), 0);
        params.put(POST.loyaltyPointsCustomerID,String.valueOf(customerID));
        VolleyQueue.getInstance(this).jsonRequest(this, URL.loyaltyPointsURL,params);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        switch (url){
            case URL.loyaltyPointsURL:
                break;
        }
    }

    @Override
    public void getErrorResult(String url, String error) {

    }
}
