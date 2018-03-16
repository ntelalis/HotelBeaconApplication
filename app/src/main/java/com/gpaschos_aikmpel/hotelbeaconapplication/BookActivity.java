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

import org.json.JSONException;
import org.json.JSONObject;

public class BookActivity extends AppCompatActivity implements JsonListener{
    public static final String ROOMTITLE = "lala";
    public static final String ROOMPRICE = "leprice";
    public static final String ROOMIMAGE = "image";
    public static final String ARRIVAL = "arrival";
    public static final String DEPARTURE = "departure";
    public static final String PERSONS = "persons";
    private TextView tvCheckIn;
    private TextView tvCheckOut;
    private TextView tvRoomTitle;
    private TextView tvPrice;
    private TextView tvPersons;
    private ImageView ivRoomImage;



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
            tvRoomTitle.setText(extras.getString(ROOMTITLE));
            tvPrice.setText(String.valueOf(extras.getInt(ROOMPRICE)));
            tvCheckIn.setText(extras.getString(ARRIVAL));
            tvCheckOut.setText(extras.getString(DEPARTURE));
            tvPersons.setText(String.valueOf(extras.getInt(PERSONS)));
            byte[] imgBytes = extras.getByteArray(ROOMIMAGE);
            Bitmap roomImage = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
            ivRoomImage.setImageBitmap(roomImage);

            //Toast.makeText(this, roomTitle+"\n"+roomPrice+"\n"+arrival+"\n"+
              //      departure+"\n"+persons+"\n", Toast.LENGTH_SHORT).show();
        }

    }

    public void confirmAndBook(View view){

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

    }
}
