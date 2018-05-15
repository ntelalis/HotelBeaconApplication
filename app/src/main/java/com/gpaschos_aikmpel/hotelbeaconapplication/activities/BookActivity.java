package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.BeaconApplication;
import com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions.NotificationCreation;
import com.gpaschos_aikmpel.hotelbeaconapplication.NotificationsFunctions.ScheduleNotifications;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.Validation;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BookActivity extends AppCompatActivity implements JsonListener {

    //KEYS
    public static final String ROOM_TYPE_ID_KEY = "room_type_id_KEY";
    public static final String ROOM_TITLE_KEY = "room_KEY";
    public static final String ROOM_PRICE_KEY = "price_KEY";
    public static final String ROOM_IMAGE_KEY = "image_KEY";
    public static final String ARRIVAL_KEY = "arrival_KEY";
    public static final String DEPARTURE_KEY = "departure_KEY";
    public static final String PERSONS_KEY = "persons_KEY";

    private Button btnBook;

    private int roomTypeID;
    private String roomTitle;

    private String arrivalSQLString;
    private String departureSQLString;
    private Date arrivalDate, departureDate;
    private int persons;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        sharedPreferences = getSharedPreferences(getString(R.string.spfile), Context.MODE_PRIVATE);

        SimpleDateFormat localizedFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        TextView tvCheckIn = findViewById(R.id.tvBookCheckIn);
        TextView tvCheckOut = findViewById(R.id.tvBookCheckOut);
        TextView tvRoomTitle = findViewById(R.id.tvBookRoomTitle);
        TextView tvPersons = findViewById(R.id.tvBookPersons);
        TextView tvPrice = findViewById(R.id.tvBookPrice);
        ImageView ivRoomImage = findViewById(R.id.ivBookRoomImage);

        btnBook = findViewById(R.id.btnBookConfirm);

        CheckBox cbTerms = findViewById(R.id.cbBookTerms);
        cbTerms.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnBook.setEnabled(true);
                } else {
                    btnBook.setEnabled(false);
                }
            }
        });

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            roomTypeID = extras.getInt(ROOM_TYPE_ID_KEY);
            roomTitle = extras.getString(ROOM_TITLE_KEY);
            tvRoomTitle.setText(roomTitle);
            tvPrice.setText(String.valueOf(extras.getInt(ROOM_PRICE_KEY)));
            arrivalSQLString = extras.getString(ARRIVAL_KEY);
            departureSQLString = extras.getString(DEPARTURE_KEY);


            try {
                Calendar c = Calendar.getInstance();
                c.setTime(sqlFormat.parse(arrivalSQLString));
                arrivalDate = c.getTime();
                String arrivalLocalString = localizedFormat.format(arrivalDate);

                c.setTime(sqlFormat.parse(departureSQLString));
                departureDate = c.getTime();
                String departureLocalString = localizedFormat.format(departureDate);
                tvCheckIn.setText(arrivalLocalString);
                tvCheckOut.setText(departureLocalString);
            } catch (ParseException e) {
                Log.e(getLocalClassName(), e.getLocalizedMessage());
            }

            persons = extras.getInt(PERSONS_KEY);
            tvPersons.setText(String.valueOf(persons));

            String imageFileName = extras.getString(ROOM_IMAGE_KEY);

            Bitmap roomImage = LocalVariables.readImage(this, imageFileName);
            ivRoomImage.setImageBitmap(roomImage);
        }


    }
    //TODO 1:include option to use "Use my Rewards Points" checkbox, then "Check Availability" btn
    //TODO 2:that opens a new Activity that displays the points needed per night. maybe use cash+
    // TODO and points if available


    // TODO Reservation Pending Idea (Change DB to include status of reservation PENDING/CONFIRMED
    // TODO in order to not let 2 users make a reservation for one last room)
    public void confirmAndBook(View view) {

        View fragmentView = getSupportFragmentManager().findFragmentById(R.id.fmBookCreditCard).getView();

        EditText etCreditCard = fragmentView.findViewById(R.id.etCrediCardCard);
        EditText etHoldersName = fragmentView.findViewById(R.id.etCreditCardHoldersName);
        EditText etCVV = fragmentView.findViewById(R.id.etCreditCardCVV);

        Spinner spMonth = fragmentView.findViewById(R.id.spCreditCardExpMonth);
        Spinner spYear = fragmentView.findViewById(R.id.spCreditCardExpYear);

        etCreditCard.setError(null);
        etHoldersName.setError(null);
        boolean flag = false;

        String creditCard = etCreditCard.getText().toString().replaceAll("\\s","");
        String holdersName = etHoldersName.getText().toString();
        if(!Validation.checkCreditCard(creditCard)){
            etCreditCard.setError("Please enter a valid card");
            flag=true;
        }

        if(!Validation.checkLength(holdersName,4,null)){
            etHoldersName.setError("Please enter a valid name");
            flag=true;
        }
        if(flag) return;

        String month = String.valueOf(spMonth.getSelectedItem());
        String year = String.valueOf(spYear.getSelectedItem());
        String cvv = etCVV.getText().toString();

        Map<String, String> values = new HashMap<>();

        values.put(POST.bookRoomTypeID, String.valueOf(roomTypeID));
        values.put(POST.bookRoomArrival, arrivalSQLString);
        values.put(POST.bookRoomDeparture, departureSQLString);
        values.put(POST.bookRoomPeople, (String.valueOf(persons)));

        values.put(POST.bookRoomCreditCard,creditCard);
        values.put(POST.bookRoomHoldersName,holdersName);
        values.put(POST.bookRoomExpMonth,month);
        values.put(POST.bookRoomExpYear,year);
        values.put(POST.bookRoomCVV,cvv);

        int customerID = RoomDB.getInstance(this).customerDao().getCustomer().getCustomerId();

        values.put(POST.bookRoomCustomerID, String.valueOf(customerID));

        VolleyQueue.getInstance(this).jsonRequest(this, URL.bookUrl, values);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        int resID = json.getInt(POST.bookRoomReservationID);
        String bookedDate = json.getString(POST.bookRoomBookedDate);
        /*new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/

        RoomDB.getInstance(this).reservationDao().insert(new Reservation(resID, roomTypeID, persons, bookedDate, arrivalSQLString, departureSQLString));
        Reservation r1 = RoomDB.getInstance(this).reservationDao().getCurrentReservation();


        ScheduleNotifications.checkinNotification(this, arrivalSQLString);
        ScheduleNotifications.checkoutNotification(this, departureSQLString);

        Intent intent = new Intent(this, BookConfirmationActivity.class);
        intent.putExtra(BookConfirmationActivity.RESERVATION_NUMBER_KEY, resID);
        startActivity(intent);

        finish();

    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_SHORT).show();
    }
}