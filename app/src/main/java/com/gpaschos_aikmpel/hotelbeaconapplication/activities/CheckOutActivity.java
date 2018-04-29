package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.adapters.MyCheckoutAdapter;
import com.gpaschos_aikmpel.hotelbeaconapplication.adapters.MyReservationsAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        int reservationID=getIntent().getIntExtra(RESERVATION_ID,0);

        totalPrice = findViewById(R.id.tvCheckoutTotalPrice);
        recyclerView = findViewById(R.id.rvCheckout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getCharges(reservationID);

    }

    public void getCharges(int reservationID){

        Map<String, String> params = new HashMap<>();
        params.put(POST.checkoutReservationID, String.valueOf(reservationID));
        VolleyQueue.getInstance(this).jsonRequest(this, URL.checkoutUrl, params);
    }

    public void fillRecyclerVandTextV(List<MyCheckoutAdapter.ChargeModel> list, double totalprice) {
        adapter = new MyCheckoutAdapter(list);
        recyclerView.setAdapter(adapter);
        totalPrice.setText(String.valueOf(totalprice));
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
                fillRecyclerVandTextV(charges,totalPrice);
                break;
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_LONG).show();
    }
}
