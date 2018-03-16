package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class BookActivity extends AppCompatActivity {
    public static final String ROOMTITLE = "lala";
    public static final String ROOMPRICE = "leprice";
    public static final String ROOMIMAGE = "image";
    public static final String ARRIVAL = "arrival";
    public static final String DEPARTURE = "departure";
    public static final String PERSONS = "persons";
    //private TextView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String roomTitle =extras.getString(ROOMTITLE);
            int roomPrice = extras.getInt(ROOMPRICE);
            String arrival = extras.getString(ARRIVAL);
            String departure = extras.getString(DEPARTURE);
            int persons = extras.getInt(PERSONS);
            Toast.makeText(this, roomTitle+"\n"+roomPrice+"\n"+arrival+"\n"+
                    departure+"\n"+persons+"\n", Toast.LENGTH_SHORT).show();
        }
    }
}
