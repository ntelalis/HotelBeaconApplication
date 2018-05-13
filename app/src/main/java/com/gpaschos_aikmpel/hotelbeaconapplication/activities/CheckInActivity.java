package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.BeaconApplication;
import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;

public class CheckInActivity extends AppCompatActivity {

    private Button btnCheckIn;
    private TextView tvArrivalDate;
    private TextView tvDepartureDate;
    private TextView tvReservationNo;
    int reservationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        btnCheckIn = findViewById(R.id.btnCheckInCheckin);
        tvArrivalDate = findViewById(R.id.tvCheckInReservationArrival);
        tvDepartureDate = findViewById(R.id.tvCheckInReservationDeparture);
        tvReservationNo = findViewById(R.id.tvCheckInReservationNo);

        Reservation r = RoomDB.getInstance(this).reservationDao().getCurrentReservation();
        String reservationStartDate = r.getStartDate();
        String reservationEndDate = r.getEndDate();
        reservationID = r.getId();

        tvReservationNo.setText(String.valueOf(reservationID));
        tvArrivalDate.setText(reservationStartDate);
        tvDepartureDate.setText(reservationEndDate);
    }

    public void checkIn(View view){
        ((BeaconApplication)getApplication()).checkin(reservationID);
    }
}