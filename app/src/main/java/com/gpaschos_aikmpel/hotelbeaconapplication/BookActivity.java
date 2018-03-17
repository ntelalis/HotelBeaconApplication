package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.RequestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.RequestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookActivity extends AppCompatActivity implements JsonListener{
    public static final String ROOMTITLE_KEY = "roomTitle";
    public static final String ROOMPRICE_KEY = "price";
    public static final String ROOMIMAGE_KEY = "image";
    public static final String ARRIVAL_KEY = "arrival";
    public static final String DEPARTURE_KEY = "departure";
    public static final String PERSONS_KEY = "persons";
    private TextView tvCheckIn;
    private TextView tvCheckOut;
    private TextView tvRoomTitle;
    private TextView tvPrice;
    private TextView tvPersons;
    private ImageView ivRoomImage;
    private String roomTitle;
    private String arrival;
    private String departure;
    private int persons;

    //TODO Change reservation to hold available room types instead of specific room in order to assign a room at check in process

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        tvCheckIn = findViewById(R.id.tvBookCheckIn);
        tvCheckOut = findViewById(R.id.tvBookCheckOut);
        tvRoomTitle = findViewById(R.id.tvBookRoomTitle);
        tvPersons = findViewById(R.id.tvBookPersons);
        tvPrice = findViewById(R.id.tvBookCurrency);
        ivRoomImage = findViewById(R.id.ivBookRoomImage);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            roomTitle = extras.getString(ROOMTITLE_KEY);
            tvRoomTitle.setText(roomTitle);
            tvPrice.setText(String.valueOf(extras.getInt(ROOMPRICE_KEY)));
            arrival = extras.getString(ARRIVAL_KEY);
            tvCheckIn.setText(arrival);
            departure = extras.getString(DEPARTURE_KEY);
            tvCheckOut.setText(departure);
            persons = extras.getInt(PERSONS_KEY);
            tvPersons.setText(String.valueOf(persons));
            byte[] imgBytes = extras.getByteArray(ROOMIMAGE_KEY);
            Bitmap roomImage = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
            ivRoomImage.setImageBitmap(roomImage);

            //Toast.makeText(this, roomTitle+"\n"+roomPrice+"\n"+arrival+"\n"+
              //      departure+"\n"+persons+"\n", Toast.LENGTH_SHORT).show();
        }

    }

    // TODO Reservation Pending Idea (Change DB to include status of reservation PENDING/CONFIRMED
    // in order to not let 2 users make a reservation for one last room)
    public void confirmAndBook(View view){
        Map<String,String> values = new HashMap<>();
        values.put(ROOMTITLE_KEY,roomTitle);
        values.put(ARRIVAL_KEY,arrival);
        values.put(DEPARTURE_KEY,departure);
        values.put(PERSONS_KEY,(String.valueOf(persons)));
        values.put("CustomerID","25");

        VolleyQueue.getInstance(this).jsonRequest(this,GlobalVars.bookUrl,values);


    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        int resID = json.getInt("ReservationID");
        Intent intent = new Intent(this,BookConfirmation.class);
        intent.putExtra(BookConfirmation.RESERVATION_NUMBER_KEY,resID);
        startActivity(intent);
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
