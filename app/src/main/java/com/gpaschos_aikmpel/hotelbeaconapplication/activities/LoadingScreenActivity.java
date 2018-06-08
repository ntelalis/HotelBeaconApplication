package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.SyncServerData;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoadingScreenActivity extends AppCompatActivity implements JsonListener, SyncServerData.SyncCallbacks {

    private ProgressBar progressBar;
    private TextView tvHotelName;
    private Customer customer = null;
    private int mAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        progressBar = findViewById(R.id.pbLoadingScreen);
        tvHotelName = findViewById(R.id.tvLoadingScreenHotelName);
        mAnimationDuration = 3000;
        tvHotelName.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        tvHotelName.setAlpha(0f);
        tvHotelName.setVisibility(View.VISIBLE);

        tvHotelName.animate()
                .alpha(1f)
                .setDuration(mAnimationDuration)
                .setListener(null);

        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        progressBar.setAlpha(0f);
        progressBar.setVisibility(View.VISIBLE);

        progressBar.animate()
                .alpha(1f)
                .setDuration(4000)
                .setListener(null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO DataSyncing on Login. Is this a good choice?
                    Log.d("meow", "gav");
                    SyncServerData.getInstance(LoadingScreenActivity.this).getDataFromServer();
            }
        }, 5000);

    }

    public void login() {
        Log.d("meow", "inside login()");
        customer = RoomDB.getInstance(this).customerDao().getCustomer();

        if (customer != null) {
            Map<String, String> params = new HashMap<>();

            params.put(POST.loginEmail, customer.getEmail());
            params.put(POST.loginPassword, customer.getPassword());

            VolleyQueue.getInstance(this).jsonRequest(this, URL.loginUrl, params);
            Log.d("meow", "Requested login to server");
        } else {
            Log.d("meow", "redirecting to login Activity...");

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
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
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void synced() {
        login();
    }
}
