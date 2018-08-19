package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
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

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypePoints;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeCashPoints;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation.UseLoyaltyPointsFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.JSONHelper;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.Validation;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.notifications.ScheduleNotifications;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookActivity extends AppCompatActivity implements JsonListener/*, UseLoyaltyPointsFragment.OnPickedLoyaltyReward*/ {

    //KEYS
    public static final String ROOM_TYPE_ID_KEY = "room_type_id_KEY";
    public static final String ROOM_TITLE_KEY = "room_KEY";
    public static final String ROOM_IMAGE_KEY = "image_KEY";
    public static final String ROOM_ARRIVAL_KEY = "arrival_KEY";
    public static final String ROOM_DEPARTURE_KEY = "departure_KEY";
    public static final String ROOM_ADULTS_KEY = "adults_KEY";
    public static final String ROOM_CHILDREN_KEY = "Children_KEY";
    public static final String ROOM_PRICE_KEY = "roomPrice_KEY";
    public static final String ROOM_DAYS_KEY = "roomDays_KEY";

    private Button btnBook;

    private int roomTypeID;

    private String arrivalSQLString;
    private String departureSQLString;
    private int adults, children, days, roomPrice, totalPrice;
    private int cashPrice;
    private int freeNights = 0,cashNights = 0;
    private TextView tvTotalPrice, tvPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        SimpleDateFormat localizedFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        TextView tvCheckIn = findViewById(R.id.tvBookCheckIn);
        TextView tvCheckOut = findViewById(R.id.tvBookCheckOut);
        TextView tvRoomTitle = findViewById(R.id.tvBookRoomTitle);
        TextView tvAdults = findViewById(R.id.tvBookAdults);
        TextView tvChildren = findViewById(R.id.tvBookChildren);
        TextView tvRoomPrice = findViewById(R.id.tvBookRoomPrice);
        TextView tvDays = findViewById(R.id.tvBookDays);
        tvPoints = findViewById(R.id.tvBookPoints);
        tvTotalPrice = findViewById(R.id.tvBookTotalPrice);

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
            String roomTitle = extras.getString(ROOM_TITLE_KEY);
            days = extras.getInt(ROOM_DAYS_KEY);
            roomPrice = extras.getInt(ROOM_PRICE_KEY);
            arrivalSQLString = extras.getString(ROOM_ARRIVAL_KEY);
            departureSQLString = extras.getString(ROOM_DEPARTURE_KEY);
            adults = extras.getInt(ROOM_ADULTS_KEY);
            children = extras.getInt(ROOM_CHILDREN_KEY);

            tvRoomTitle.setText(roomTitle);
            totalPrice = roomPrice * days;
            tvTotalPrice.setText(String.valueOf(totalPrice));
            tvDays.setText(String.valueOf(days));
            tvRoomPrice.setText(String.valueOf(roomPrice));
            tvAdults.setText(String.valueOf(adults));
            tvChildren.setText(String.valueOf(children));

            try {
                Calendar c = Calendar.getInstance();
                c.setTime(sqlFormat.parse(arrivalSQLString));
                Date arrivalDate = c.getTime();
                String arrivalLocalString = localizedFormat.format(arrivalDate);

                c.setTime(sqlFormat.parse(departureSQLString));
                Date departureDate = c.getTime();
                String departureLocalString = localizedFormat.format(departureDate);
                tvCheckIn.setText(arrivalLocalString);
                tvCheckOut.setText(departureLocalString);
            } catch (ParseException e) {
                Log.e(getLocalClassName(), e.getLocalizedMessage());
            }

            String imageFileName = extras.getString(ROOM_IMAGE_KEY);

            Bitmap roomImage = LocalVariables.readImage(this, imageFileName);
            ivRoomImage.setImageBitmap(roomImage);
        }


    }


    /* TODO Reservation Pending Idea (Change DB to include status of reservation PENDING/CONFIRMED
     in order to not let 2 users make a reservation for one last room)*/

    public void confirmAndBook(View view) {

        View fragmentView = getSupportFragmentManager().findFragmentById(R.id.fmBookCreditCard).getView();

        if(fragmentView==null){
            throw new RuntimeException("Fragment is not found");
        }

        EditText etCreditCard = fragmentView.findViewById(R.id.etCrediCardCard);
        EditText etHoldersName = fragmentView.findViewById(R.id.etCreditCardHoldersName);
        EditText etCVV = fragmentView.findViewById(R.id.etCreditCardCVV);

        Spinner spMonth = fragmentView.findViewById(R.id.spCreditCardExpMonth);
        Spinner spYear = fragmentView.findViewById(R.id.spCreditCardExpYear);

        etCreditCard.setError(null);
        etHoldersName.setError(null);
        boolean flag = false;

        String creditCard = etCreditCard.getText().toString().replaceAll("\\s", "");
        String holdersName = etHoldersName.getText().toString();
        if (!Validation.checkCreditCard(creditCard)) {
            etCreditCard.setError("Please enter a valid card");
            flag = true;
        }

        if (!Validation.checkLength(holdersName, 4, null)) {
            etHoldersName.setError("Please enter a valid name");
            flag = true;
        }
        if (flag) return;

        String month = String.valueOf(spMonth.getSelectedItem());
        String year = String.valueOf(spYear.getSelectedItem());
        String cvv = etCVV.getText().toString();

        Map<String, String> values = new HashMap<>();

        values.put(POST.bookRoomTypeID, String.valueOf(roomTypeID));
        values.put(POST.bookRoomArrival, arrivalSQLString);
        values.put(POST.bookRoomDeparture, departureSQLString);
        values.put(POST.bookRoomAdults, (String.valueOf(adults)));
        values.put(POST.bookRoomChildren, (String.valueOf(children)));
        values.put(POST.bookRoomFreeNights,String.valueOf(freeNights));
        values.put(POST.bookRoomCashNights,String.valueOf(cashNights));

        values.put(POST.bookRoomCreditCard, creditCard);
        values.put(POST.bookRoomHoldersName, holdersName);
        values.put(POST.bookRoomExpMonth, month);
        values.put(POST.bookRoomExpYear, year);
        values.put(POST.bookRoomCVV, cvv);

        int customerID = RoomDB.getInstance(this).customerDao().getCustomer().getCustomerId();

        values.put(POST.bookRoomCustomerID, String.valueOf(customerID));

        VolleyQueue.getInstance(this).jsonRequest(this, URL.bookUrl, values);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {

        switch (url) {
            case URL.bookUrl:

                int resID = json.getInt(POST.bookRoomReservationID);
                String bookedDate = JSONHelper.getString(json,POST.bookRoomBookedDate);
                String modified = JSONHelper.getString(json,POST.bookRoomModified);

                RoomDB.getInstance(this).reservationDao().insert(new Reservation(resID, roomTypeID, adults, children, bookedDate, arrivalSQLString, departureSQLString,modified));

                ScheduleNotifications.checkinNotification(this, resID);
                ScheduleNotifications.checkoutNotification(this, departureSQLString);

                Intent intent = new Intent(this, BookConfirmationActivity.class);
                intent.putExtra(BookConfirmationActivity.RESERVATION_NUMBER_KEY, resID);
                startActivity(intent);
                finish();
                break;
            case URL.totalPointsUrl:

                int totalPoints = json.getInt(POST.totalPoints);

                RoomDB roomDB = RoomDB.getInstance(this);
                RoomTypePoints roomTypeFreeNightsPoints = roomDB.roomTypePointsDao().getRoomTypePoints(roomTypeID, adults);
                int freeNightPoints = roomTypeFreeNightsPoints.getSpendingPoints();

                RoomTypeCashPoints roomTypePointsAndCash = roomDB.roomTypeCashPointsDao().getRoomTypeCashPoints(roomTypeID, adults, 1);
                int cashPoints = roomTypePointsAndCash.getPoints();
                cashPrice = roomTypePointsAndCash.getCash();

                DialogFragment dialogFragment = UseLoyaltyPointsFragment.newInstance(totalPoints, freeNightPoints, cashPoints, cashPrice, this.days);
                dialogFragment.show(getSupportFragmentManager(), UseLoyaltyPointsFragment.TAG);
                break;
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_SHORT).show();
    }

    public void test4(View v) {

        int customerID = RoomDB.getInstance(this).customerDao().getCustomer().getCustomerId();

        Map<String, String> params = new HashMap<>();
        params.put(POST.loyaltyProgramCustomerID, String.valueOf(customerID));
        VolleyQueue.getInstance(this).jsonRequest(this, URL.totalPointsUrl, params);
    }

    /*@Override*/
    public void onLoyaltyPicked(int freeNights, int cashNights, int totalPoints) {
        this.freeNights = freeNights;
        this.cashNights = cashNights;
        //int price = totalPrice - roomPrice * freeNights - cashNights * roomPrice + cashPrice * cashNights;
        int price = totalPrice - roomPrice * freeNights + cashNights*(cashPrice-roomPrice);
        tvTotalPrice.setText(String.valueOf(price));
        tvPoints.setText(String.valueOf(totalPoints));
        if (totalPoints > 0) {
            findViewById(R.id.groupBookPoints).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.groupBookPoints).setVisibility(View.GONE);
        }
    }
}