package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class BookConfirmationActivity extends AppCompatActivity {

    public static final String RESERVATION_NUMBER_KEY = "RES_NUM_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_confirmation);

        TextView tvReservationNumber = findViewById(R.id.tvBookConfirmReservationNumber);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            int reservationNumber = bundle.getInt(RESERVATION_NUMBER_KEY);
            tvReservationNumber.setText(String.valueOf(reservationNumber));
        }


    }

    public void reservations(View view) {
        Intent intent = new Intent(this, ReservationActivity.class);
        startActivity(intent);
    }

    public void home(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
