package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class UpcomingReservationActivity extends AppCompatActivity implements JsonListener,
        MyReservationsAdapter.ClickCallbacks {

    private RecyclerView recyclerView;
    MyReservationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_reservation);

        recyclerView = findViewById(R.id.rvUpcomingReservations);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //myReservations();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myReservations();
    }

    //query to the server about my reservation list
    public void myReservations() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.spfile), Context.MODE_PRIVATE);
        int customerID = sharedPreferences.getInt(getString(R.string.saved_customerId), 0);

        Map<String, String> params = new HashMap<>();
        params.put(POST.upcomingreservationsCustomerID, String.valueOf(customerID));
        VolleyQueue.getInstance(this).jsonRequest(this, URL.upcomingreservationsUrl, params);
    }

    public void fillRecyclerView(List<MyReservationsAdapter.ReservationModel> list) {
        adapter = new MyReservationsAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    //sends the reservationID to the server in order to check in
    @Override
    public void checkIn(MyReservationsAdapter.ReservationModel obj) {
        int reservationID = obj.reservationID;
        Map<String, String> params = new HashMap<>();
        params.put(POST.checkinReservationID, String.valueOf(reservationID));
        VolleyQueue.getInstance(this).jsonRequest(this,URL.checkinUrl, params);

    }

    @Override
    public void checkOut(MyReservationsAdapter.ReservationModel obj) {
        Intent intent = new Intent(this, CheckOutActivity.class);
        intent.putExtra(CheckOutActivity.RESERVATION_ID, obj.reservationID);
        startActivity(intent);

    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        switch (url) {
            case URL.upcomingreservationsUrl:
                JSONArray response = json.getJSONArray(POST.upcomingreservationsResponseArray);

                List<MyReservationsAdapter.ReservationModel> reservations = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    int adults = response.getJSONObject(i).getInt(POST.upcomingreservationsAdults);
                    int reservationID = response.getJSONObject(i).getInt(POST.upcomingreservationsReservationID);
                    String arrival = response.getJSONObject(i).getString(POST.upcomingreservationsArrival);
                    String departure = response.getJSONObject(i).getString(POST.upcomingreservationsDeparture);
                    String roomTitle = response.getJSONObject(i).getString(POST.upcomingreservationsRoomTitle);
                    String room = response.getJSONObject(i).getString(POST.upcomingreservationsCheckedinRoom);
                    int statusCode = response.getJSONObject(i).getInt(POST.upcomingreservationsEligibleForCheckinCheckout);

                    reservations.add(new MyReservationsAdapter.ReservationModel(adults, roomTitle, reservationID,
                            arrival, departure, statusCode, room));
                }
                fillRecyclerView(reservations);
                break;
            case URL.checkinUrl:
                String room = json.getString(POST.checkinRoom);
                Intent intent = new Intent(this, CheckedInActivity.class);
                intent.putExtra(CheckedInActivity.ROOM, room);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_SHORT).show();
    }
}