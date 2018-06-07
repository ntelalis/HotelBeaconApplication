package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.SyncServerData;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoadingScreenActivity extends AppCompatActivity  implements JsonListener{

    private ProgressBar progressBar;
    private Customer customer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        progressBar = findViewById(R.id.pbLoadingScreen);

        //TODO DataSyncing on Login. Is this a good choice?
        SyncServerData.getInstance(this).getDataFromServer();

        customer = RoomDB.getInstance(this).customerDao().getCustomer();

        if (customer != null) {
            Map<String, String> params = new HashMap<>();

            params.put(POST.loginEmail, customer.getEmail());
            params.put(POST.loginPassword, customer.getPassword());

            VolleyQueue.getInstance(this).jsonRequest(this, URL.loginUrl, params);
        }
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        if (url.equals(URL.loginUrl)) {

            int customerId = json.getInt(POST.loginCustomerID);
            Toast.makeText(this, "Login Successful! CustomerID: " + customerId, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url+": "+error, Toast.LENGTH_SHORT).show();
    }
}
