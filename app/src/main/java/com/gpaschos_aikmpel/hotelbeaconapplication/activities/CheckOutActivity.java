package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.adapters.MyCheckoutAdapter;
import com.gpaschos_aikmpel.hotelbeaconapplication.adapters.MyReservationsAdapter;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckOutActivity extends AppCompatActivity implements JsonListener{

    public static final String RESERVATION_ID = "reservationID";

    private RecyclerView recyclerView;
    private MyCheckoutAdapter adapter;
    private TextView totalPrice;
    private Button confirmCheckout;
    private int reservationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        //reservationID=getIntent().getIntExtra(RESERVATION_ID,0);
        reservationID = RoomDB.getInstance(this).reservationDao().getCurrentReservation().getId();

        confirmCheckout = findViewById(R.id.btnCheckoutConfirm);
        totalPrice = findViewById(R.id.tvCheckoutTotalPrice);
        recyclerView = findViewById(R.id.rvCheckout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getCharges(reservationID);

    }

    private void getCharges(int reservationID){

        Map<String, String> params = new HashMap<>();
        params.put(POST.checkoutReservationID, String.valueOf(reservationID));
        VolleyQueue.getInstance(this).jsonRequest(this, URL.checkoutUrl, params);
    }

    private void fillRecyclerViewAndTextView(List<MyCheckoutAdapter.ChargeModel> list, double totalprice) {
        adapter = new MyCheckoutAdapter(list);
        recyclerView.setAdapter(adapter);
        totalPrice.setText(String.valueOf(totalprice));
    }

    public void confirmCheckout (View view){
        Map<String, String> params = new HashMap<>();
        params.put(POST.checkoutConfirmReservationID, String.valueOf(reservationID));
        VolleyQueue.getInstance(this).jsonRequest(this, URL.checkoutConfirmationUrl, params);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        JSONArray response;
        switch (url){
            case URL.checkoutUrl:
                double totalPrice = json.getDouble(POST.checkoutTotalPrice);
                response = json.getJSONArray(POST.checkoutChargeDetails);

                List<MyCheckoutAdapter.ChargeModel> charges = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    String service = response.getJSONObject(i).getString(POST.checkoutService);
                    double price = response.getJSONObject(i).getDouble(POST.checkoutServicePrice);

                    charges.add(new MyCheckoutAdapter.ChargeModel(service, price));
                }
                fillRecyclerViewAndTextView(charges,totalPrice);
                break;
            case URL.checkoutConfirmationUrl:
                String checkoutDate = json.getString(POST.checkoutConfirmDate);

                //update Room with the checked-out information
                Reservation r = RoomDB.getInstance(this).reservationDao().getCurrentReservation();
                r.setCheckOut(checkoutDate);
                RoomDB.getInstance(this).reservationDao().update(r);

                Intent intent = new Intent(this, CheckedOutActivity.class);

                startActivity(intent);
                finish();
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_LONG).show();
    }
}
