package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.alert.NoInternetDialog;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.SyncServerData;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoadingScreenActivity extends AppCompatActivity implements JsonListener, SyncServerData.SyncCallbacks, NoInternetDialog.NoInternetDialogListener {

    private static final String TAG = LoadingScreenActivity.class.getSimpleName();
    private ProgressBar progressBar;
    private TextView tvHotelName;
    private Customer customer;
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

        initApp();
    }

    public void initApp(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO DataSyncing on Login. Is this a good choice?
                ConnectivityManager cm =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (isConnected) {
                    SyncServerData.getInstance(LoadingScreenActivity.this).getDataFromServer();
                } else {
                    NoInternetDialog dialog = NoInternetDialog.newInstance();
                    dialog.show(getSupportFragmentManager(),"NoInternetDialog");
                }
            }
        }, 3000);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        if (url.equals(URL.loginUrl)) {

            try {
                int customerId = json.getInt(POST.loginCustomerID);
                String firstName = json.getString(POST.loginFirstName);
                int titleID = json.getInt(POST.loginTitleID);
                String lastName = json.getString(POST.loginLastName);
                String birthDate = json.getString(POST.loginBirthDate);
                int countryID = json.getInt(POST.loginCountryID);
                String modified = json.getString(POST.loginModified);

                RoomDB roomDB = RoomDB.getInstance(this);
                Customer c = roomDB.customerDao().getCustomer();
                c.update(customerId, titleID, firstName, lastName, birthDate, countryID, modified);
                roomDB.customerDao().insert(c);

            } catch (JSONException e) {
                Log.d(TAG, "No data modified");
            }


            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }

    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dataSynced() {
        customer = RoomDB.getInstance(this).customerDao().getCustomer();

        if (customer != null) {
            SyncServerData.getInstance(LoadingScreenActivity.this).getCustomerDataFromServer(customer);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void customerDataSynced() {
        Map<String, String> params = new HashMap<>();

        params.put(POST.loginEmail, customer.getEmail());
        params.put(POST.loginPassword, customer.getPassword());
        params.put(POST.loginModified, customer.getModified());

        VolleyQueue.getInstance(this).jsonRequest(this, URL.loginUrl, params);
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
