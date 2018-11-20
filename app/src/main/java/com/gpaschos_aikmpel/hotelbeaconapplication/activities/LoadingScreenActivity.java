package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.alert.NoInternetDialog;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.JSONHelper;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.SyncServerData;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoadingScreenActivity extends AppCompatActivity implements JsonListener, SyncServerData.SyncCallbacks, NoInternetDialog.NoInternetDialogListener {

    private static final String TAG = LoadingScreenActivity.class.getSimpleName();
    private Customer customer;

    private boolean finishedSyncing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        ProgressBar progressBar = findViewById(R.id.pbLoadingScreen);
        TextView tvHotelName = findViewById(R.id.tvLoadingScreenHotelName);
        tvHotelName.setText(Params.HotelName);
        int mAnimationDuration = 3000;
        //tvHotelName.setVisibility(View.GONE);
        //progressBar.setVisibility(View.GONE);

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

        initApp();
    }



    public void initApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork;
                if (cm != null) {
                    activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null &&
                            activeNetwork.isConnectedOrConnecting();
                    if (isConnected) {
                        SyncServerData.getInstance(LoadingScreenActivity.this).getDataFromServer();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!finishedSyncing) {
                                    NoInternetDialog dialog = NoInternetDialog.newInstance();
                                    getSupportFragmentManager().beginTransaction().add(dialog,NoInternetDialog.TAG).commitAllowingStateLoss();
                                }
                            }
                        }, 20000);
                    } else {
                        NoInternetDialog dialog = NoInternetDialog.newInstance();
                        getSupportFragmentManager().beginTransaction().add(dialog,NoInternetDialog.TAG).commitAllowingStateLoss();
                        //dialog.show(getSupportFragmentManager(), "NoInternetDialog");
                    }
                }
            }
        }, 3000);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) {
        if (url.equals(URL.loginUrl)) {

            finishedSyncing = true;

            RoomDB roomDB = RoomDB.getInstance(this);
            Customer c = roomDB.customerDao().getCustomer();

            try {
                int customerId = json.getInt(POST.loginCustomerID);
                String title = JSONHelper.getString(json,POST.loginTitle);
                String firstName = JSONHelper.getString(json,POST.loginFirstName);
                String lastName = JSONHelper.getString(json,POST.loginLastName);
                String birthDate = JSONHelper.getString(json,POST.loginBirthDate);
                String country = JSONHelper.getString(json,POST.loginCountry);
                String phone = JSONHelper.getString(json,POST.loginPhone);
                String address1 = JSONHelper.getString(json,POST.loginAddress1);
                String address2 = JSONHelper.getString(json,POST.loginAddress2);
                String city = JSONHelper.getString(json,POST.loginCity);
                String postalCode = JSONHelper.getString(json,POST.loginPostalCode);
                boolean oldCustomer = json.getBoolean(POST.loginOldCustomer);
                String modified = JSONHelper.getString(json,POST.loginModified);

                c.update(customerId,title,firstName,lastName,birthDate,country,phone,address1,address2,city,postalCode,oldCustomer,modified);
                roomDB.customerDao().insert(c);

            } catch (JSONException e) {
                Log.d(TAG, e.toString());
            }

            SyncServerData.getInstance(LoadingScreenActivity.this).getCustomerDataFromServer(c);

        }

    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void dataSynced() {
        customer = RoomDB.getInstance(this).customerDao().getCustomer();

        if (customer != null) {
            Map<String, String> params = new HashMap<>();

            params.put(POST.loginEmail, customer.getEmail());
            params.put(POST.loginPassword, customer.getPassword());
            params.put(POST.loginModified, customer.getModified());

            VolleyQueue.getInstance(this).jsonRequest(this, URL.loginUrl, params);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void customerDataSynced() {

        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        initApp();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        finishAffinity();
    }
}
