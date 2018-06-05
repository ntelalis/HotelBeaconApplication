package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomType;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeCash;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.DatePickerFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.ImageViewFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.adapters.MyRoomsAdapter;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;
import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class ReservationActivity extends AppCompatActivity implements DatePickerFragment.DateSelected,
        JsonListener, MyRoomsAdapter.ClickCallbacks {

    private EditText etArrivalDate;
    private EditText etDepartureDate;
    private SimpleDateFormat localizedFormat, mySQLFormat;
    private Spinner spPeople;
    private RecyclerView recyclerView;
    private ProgressBar pbLoading;

    private int reservationDays;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        etArrivalDate = findViewById(R.id.etReservationArrival);
        etDepartureDate = findViewById(R.id.etReservationDeparture);
        recyclerView = findViewById(R.id.rvReservationRecycler);
        pbLoading = findViewById(R.id.pbAvailability);
        spPeople = findViewById(R.id.spReservationPeople);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        localizedFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        mySQLFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        etArrivalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate(etArrivalDate);
            }
        });

        etDepartureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate(etDepartureDate);
            }
        });

        spPeople.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recyclerView.setAdapter(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadSpinnerData();
        //GetData.getInstance(this).getDataFromServer();
    }


    private void loadSpinnerData() {
        VolleyQueue.getInstance(this).jsonRequest(this, URL.personsUrl, null);
    }

    public void roomAvailability(View view) throws ParseException {
        recyclerView.setAdapter(null);
        String arrivalDateLocal = etArrivalDate.getText().toString();
        String departureDateLocal = etDepartureDate.getText().toString();
        String persons = spPeople.getSelectedItem().toString();

        if (!arrivalDateLocal.isEmpty() && !departureDateLocal.isEmpty() && !persons.isEmpty()) {

            Calendar calendarArrival = Calendar.getInstance();
            calendarArrival.setTime(localizedFormat.parse(arrivalDateLocal));
            String arrivalDateSQLString = mySQLFormat.format(calendarArrival.getTime());

            Calendar calendarDeparture = Calendar.getInstance();
            calendarDeparture.setTime(localizedFormat.parse(departureDateLocal));
            String departureDateSQLString = mySQLFormat.format(calendarDeparture.getTime());

            long reservationDaysInMillis = calendarDeparture.getTimeInMillis() - calendarArrival.getTimeInMillis();
            reservationDays = (int) (reservationDaysInMillis / 1000 / 60 / 60 / 24);

            Map<String, String> params = new HashMap<>();

            params.put(POST.availabilityArrivalDate, arrivalDateSQLString);
            params.put(POST.availabilityDepartureDate, departureDateSQLString);
            params.put(POST.availabilityPeople, persons);

            VolleyQueue.getInstance(this).jsonRequest(this, URL.availabilityUrl, params);

            pbLoading.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Please fill in fields", Toast.LENGTH_SHORT).show();
        }
    }


    public void pickDate(View view) {

        DialogFragment datePickerFragment = new DatePickerFragment();

        Bundle bundle = new Bundle();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, Params.maxReservationDateInYears);
        long maxTimeForReservationArrival = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        long maxTimeForReservationDeparture = calendar.getTimeInMillis();


        if (view == etArrivalDate) {
            bundle.putLong(DatePickerFragment.minimumDate_KEY, Calendar.getInstance().getTimeInMillis());
            bundle.putLong(DatePickerFragment.maximumDate_KEY, maxTimeForReservationArrival);
            bundle.putInt(DatePickerFragment.EditTextType, DatePickerFragment.etArrival);

        } else if (view == etDepartureDate) {
            String arrivalDateLocal = etArrivalDate.getText().toString();

            if (arrivalDateLocal.isEmpty()) {
                Toast.makeText(this, "Please select Arrival Date", Toast.LENGTH_SHORT).show();
                return;

            } else {
                bundle.putInt(DatePickerFragment.EditTextType, DatePickerFragment.etDeparture);

                try {
                    calendar = Calendar.getInstance();
                    calendar.setTime(localizedFormat.parse(arrivalDateLocal));
                    //FIXME DEBUG MODE FOR CHECKOUT
                    //calendar.add(Calendar.DAY_OF_MONTH, 1);
                    bundle.putLong(DatePickerFragment.minimumDate_KEY, calendar.getTimeInMillis());
                    bundle.putLong(DatePickerFragment.maximumDate_KEY, maxTimeForReservationDeparture);

                } catch (ParseException e) {
                    Log.e(getLocalClassName(), e.getLocalizedMessage());
                    return;
                }
            }
        }
        datePickerFragment.setArguments(bundle);
        datePickerFragment.show(getSupportFragmentManager(), DatePickerFragment.TAG);
    }

    @Override
    public void pickedDate(int type, int day, int month, int year) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        String pickedDateLocal = localizedFormat.format(calendar.getTime());

        //Empty availability results after choosing new date either for arrival or departure
        if (recyclerView.getAdapter() != null) {
            fillRecyclerView(new ArrayList<MyRoomsAdapter.ModelRoomView>());
        }


        if (type == DatePickerFragment.etArrival) {

            etArrivalDate.setText(pickedDateLocal);

            //Clear departure string if picked arrival time is after departure time
            if (!etDepartureDate.getText().toString().isEmpty()) {

                try {
                    calendar.setTime(localizedFormat.parse(etDepartureDate.getText().toString()));
                    long departureTime = calendar.getTimeInMillis();

                    calendar.setTime(localizedFormat.parse(etArrivalDate.getText().toString()));
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    long arrivalTime = calendar.getTimeInMillis();

                    if (arrivalTime > departureTime) {
                        etDepartureDate.setText("");
                    }

                } catch (ParseException e) {
                    Log.e(getLocalClassName(), e.getLocalizedMessage());
                }
            }
        } else if (type == DatePickerFragment.etDeparture) {
            etDepartureDate.setText(pickedDateLocal);
        }

    }

    public void fillRecyclerView(List<MyRoomsAdapter.ModelRoomView> list) {
        RecyclerView.Adapter adapter = new MyRoomsAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }



    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        switch (url) {
            case URL.availabilityUrl:
                pbLoading.setVisibility(View.GONE);

                List<MyRoomsAdapter.ModelRoomView> roomList = new ArrayList<>();

                JSONArray availabilityResults = json.getJSONArray(POST.availabilityResultsArray);
                for (int i = 0; i < availabilityResults.length(); i++) {
                    JSONObject room = availabilityResults.getJSONObject(i);

                    int roomTypeID = room.getInt(POST.availabilityRoomTypeID);
                    int people = Integer.parseInt(spPeople.getSelectedItem().toString());

                    RoomDB roomDB = RoomDB.getInstance(this);

                    Log.d("ASDFASDF",roomTypeID+"");
                    RoomType rt = roomDB.roomTypeDao().getRoomTypeById(roomTypeID);
                    Log.d("ASDFASDF",rt.getId()+"");
                    RoomTypeCash rtc = roomDB.roomTypeCashDao().getRoomTypeCash(rt.getId(), people, 1);

                    String description = rt.getDescription();
                    String title = rt.getName();
                    //int price = rt.getPrice();
                    int price = rtc.getPrice();
                    Bitmap imageBitmap = LocalVariables.readImage(this, rt.getImage());
                    String imageName = rt.getImage();
                    int persons = Integer.parseInt(spPeople.getSelectedItem().toString());

                    MyRoomsAdapter.ModelRoomView roomType =
                            new MyRoomsAdapter.ModelRoomView(roomTypeID, title, description, price, reservationDays, persons, imageBitmap, imageName);

                    roomList.add(roomType);
                }

                fillRecyclerView(roomList);
                recyclerView.getAdapter().notifyDataSetChanged();
                recyclerView.scheduleLayoutAnimation();
                break;
            case URL.personsUrl:

                List<Integer> personsList = new ArrayList<>();

                int maxCapacity = json.getInt(POST.personsMaxCapacity);
                for (int i = 1; i <= maxCapacity; i++) {
                    personsList.add(i);
                }

                spPeople.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, personsList));
                break;
        }
    }

    @Override
    public void getErrorResult(String url, String error) {

        if (url.equals(URL.availabilityUrl)) {
            pbLoading.setVisibility(View.GONE);
        }
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_LONG).show();
    }

    //TODO Change this to more efficient design by not passing the bitmap around
    @Override
    public void imgClicked(String imgFileName) {
        ImageViewFragment fragment = ImageViewFragment.newInstance(imgFileName);
        fragment.show(getFragmentManager(), ImageViewFragment.TAG);
    }

    @Override
    public void bookRoom(MyRoomsAdapter.ModelRoomView room) {

        String arrivalDateSQL, departureDateSQL;

        String arrivalDateLocal = etArrivalDate.getText().toString();
        String departureDateLocal = etDepartureDate.getText().toString();

        try {
            Calendar c = Calendar.getInstance();
            c.setTime(localizedFormat.parse(arrivalDateLocal));
            arrivalDateSQL = mySQLFormat.format(c.getTime());

            c.setTime(localizedFormat.parse(departureDateLocal));
            departureDateSQL = mySQLFormat.format(c.getTime());

        } catch (ParseException e) {
            Log.e(getLocalClassName(), e.getLocalizedMessage());
            return;
        }

        int roomTypeID = room.roomTypeID;
        String roomTitle = room.title;

        String roomImage = room.imgFileName;

        int people = Integer.parseInt(spPeople.getSelectedItem().toString());

        Intent intent = new Intent(this, BookActivity.class);
        intent.putExtra(BookActivity.ROOM_TYPE_ID_KEY, roomTypeID);
        intent.putExtra(BookActivity.ROOM_TITLE_KEY, roomTitle);
        intent.putExtra(BookActivity.ROOM_IMAGE_KEY, roomImage);
        intent.putExtra(BookActivity.ROOM_PRICE_KEY, room.price);
        intent.putExtra(BookActivity.ROOM_DAYS_KEY, room.days);
        intent.putExtra(BookActivity.ROOM_ARRIVAL_KEY, arrivalDateSQL);
        intent.putExtra(BookActivity.ROOM_DEPARTURE_KEY, departureDateSQL);
        intent.putExtra(BookActivity.ROOM_PERSONS_KEY, people);
        startActivity(intent);


    }
}
