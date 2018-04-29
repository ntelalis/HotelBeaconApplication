package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.animation.Animator;
import com.gpaschos_aikmpel.hotelbeaconapplication.HoloCircularProgressBar;
import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LoyaltyProgramActivity extends AppCompatActivity implements JsonListener {

    private SharedPreferences sharedPref;

    private int customerID;
    private String firstName,lastName;
    private int points;
    private String tierName;
    private int tierPoints;
    private String nextTierName;
    private int nextTierPoints;
    private ArrayList<String> tierBenefits;

    private TextView tvCustomerID,tvPoints,tvRewardsMember,tvFirstName,tvLastName;
    private HoloCircularProgressBar hcpb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalty_program);

        tvCustomerID = findViewById(R.id.tvLoyaltyCustomerID);
        tvRewardsMember= findViewById(R.id.tvLoyaltyRewardsMember);
        tvPoints = findViewById(R.id.tvLoyaltyPoints);
        tvFirstName = findViewById(R.id.tvLoyaltyMemberFirstName);
        tvLastName = findViewById(R.id.tvLoyaltyMemberLastName);
        hcpb = findViewById(R.id.cpbLoyalty);
        getLoyalty();
    }

    public void rewards(View view){

    }

    public void getLoyalty(){
        Map<String,String> params = new HashMap<>();
        sharedPref = getSharedPreferences(getString(R.string.spfile), Context.MODE_PRIVATE);
        customerID = sharedPref.getInt(getString(R.string.saved_customerId), 0);
        params.put(POST.loyaltyPointsCustomerID,String.valueOf(customerID));
        VolleyQueue.getInstance(this).jsonRequest(this, URL.loyaltyPointsURL,params);
    }

    private void updateUI(){
        tvCustomerID.setText(String.valueOf(customerID));
        tvFirstName.setText(firstName);
        tvLastName.setText(lastName);
        tvRewardsMember.setText(tierName.toUpperCase());
        tvPoints.setText(String.valueOf(tierPoints));
        hcpb.setProgress(0.2f);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        switch (url){
            case URL.loyaltyPointsURL:
                firstName = json.getString(POST.loyaltyProgramFirstName);
                lastName = json.getString(POST.loyaltyProgramLastName);
                points = json.getInt(POST.loyaltyProgramPoints);
                tierName = json.getString(POST.loyaltyProgramTierName);
                tierPoints = json.getInt(POST.loyaltyProgramTierPoints);
                nextTierName = json.getString(POST.loyaltyProgramNextTierName);
                nextTierPoints = json.getInt(POST.loyaltyProgramNextTierPoints);
                JSONArray benefitsArray = json.getJSONArray(POST.loyaltyProgramBenefits);
                tierBenefits = new ArrayList<>();
                for(int i=0;i<benefitsArray.length();i++){
                    tierBenefits.add(benefitsArray.getString(i));
                }
                updateUI();
                break;
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url+": "+error, Toast.LENGTH_SHORT).show();
    }
}
