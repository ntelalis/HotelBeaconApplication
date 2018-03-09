package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.gpaschos_aikmpel.hotelbeaconapplication.RequestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.RequestVolley.VolleyQueue;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

    }


}