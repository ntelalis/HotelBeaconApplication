package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.gpaschos_aikmpel.hotelbeaconapplication.RequestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.RequestVolley.VolleyQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReservationActivity extends AppCompatActivity implements DatePickerFragment.DateSelected,
        JsonListener,MyRoomsAdapter.ClickCallbacks {

    private EditText etArrivalDate;
    private EditText etDepartureDate;
    private SimpleDateFormat simpleDateFormat;
    private Spinner spinner;
    private String persons;
    private ListView lvRooms;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        recyclerView = findViewById(R.id.rvReservationRecycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //adapter = new MyRoomsAdapter(list);
        //recyclerView.setAdapter(adapter);
        simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();

        progressBar = findViewById(R.id.progressBar);

        etArrivalDate = findViewById(R.id.etReservationArrival);
        etArrivalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pick(etArrivalDate);
            }
        });
        etDepartureDate = findViewById(R.id.etReservationDeparture);
        etDepartureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pick(etDepartureDate);
            }
        });
        spinner = findViewById(R.id.spReservationPeople);
        loadSpinnerData(GlobalVars.personsUrl);
        setSpinnerListener();
    }

    //unnecessary prolly
    private void setSpinnerListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                persons = spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void loadSpinnerData(String url) {
        StringRequest stringRequest = ServerFunctions.getPersonsSpinnerDataRequest(this, url, spinner);
        RequestQueueVolley.getInstance(this).add(stringRequest);
    }

    public void availability(View view) throws ParseException {
        String arrivalDate = etArrivalDate.getText().toString();
        String departureDate = etDepartureDate.getText().toString();
        persons = spinner.getSelectedItem().toString();

        // TODO FIX CALENDAR DATE

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(simpleDateFormat.parse(arrivalDate));
        String aDateString = dateFormat.format(calendar.getTime());
        calendar.setTime(simpleDateFormat.parse(departureDate));
        String dDateString = dateFormat.format(calendar.getTime());

        Map<String, String> params = new HashMap<>();
        params.put("arrivalDate", aDateString);
        params.put("departureDate", dDateString);
        params.put("persons", persons);

        VolleyQueue.getInstance(this).jsonRequest(this, GlobalVars.availabilityUrl, params);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void book(View view) {
        String arrivalDate = etArrivalDate.getText().toString();
        String departureDate = etDepartureDate.getText().toString();
        int personZ = Integer.parseInt(spinner.getSelectedItem().toString());
    }

    public void pick(View view) {
        DialogFragment dialogFragment = new DatePickerFragment();

        Bundle bundle = new Bundle();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        long plusOneYear = calendar.getTimeInMillis();
        bundle.putLong("maxDate", plusOneYear);

        if (view == etArrivalDate) {
            bundle.putLong("minDate", Calendar.getInstance().getTimeInMillis());
            bundle.putLong("maxDate", plusOneYear);
            bundle.putInt(DatePickerFragment.ET_TYPE, DatePickerFragment.etArrival);
        } else {
            String arrivalDate = etArrivalDate.getText().toString();
            if (arrivalDate.isEmpty()) {
                Toast.makeText(this, "Please select Arrival Date", Toast.LENGTH_SHORT).show();
                return;
            } else {
                bundle.putInt(DatePickerFragment.ET_TYPE, DatePickerFragment.etDeparture);
                calendar = Calendar.getInstance();
                try {
                    calendar.setTime(simpleDateFormat.parse(arrivalDate));
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    bundle.putLong("minDate", calendar.getTimeInMillis());
                    bundle.putLong("maxDate", plusOneYear);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void pickedDate(int type, int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        String date = simpleDateFormat.format(calendar.getTime());

        if (type == DatePickerFragment.etArrival) {
            etArrivalDate.setText(date);
            try {
                calendar.setTime(simpleDateFormat.parse(etDepartureDate.getText().toString()));
                long departureTime = calendar.getTimeInMillis();
                calendar.setTime(simpleDateFormat.parse(etArrivalDate.getText().toString()));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                long arrivalTime = calendar.getTimeInMillis();
                if (arrivalTime > departureTime) {
                    etDepartureDate.setText("");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else
            etDepartureDate.setText(date);
    }

    public void fillRecyclerView(List<MyRoomsAdapter.ModelRoomView> list) {
        adapter = new MyRoomsAdapter(this,list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        switch (url) {
            case GlobalVars.availabilityUrl:
                progressBar.setVisibility(View.GONE);
                List<MyRoomsAdapter.ModelRoomView> roomList = new ArrayList<>();
                JSONArray availabilityResults = json.getJSONArray("results");
                for (int i = 0; i < availabilityResults.length(); i++) {
                    JSONObject room = availabilityResults.getJSONObject(i);
                    String description = room.getString("description");
                    String title = room.getString("title");
                    int price = room.getInt("price");

                    String base64Img = room.getString("img");
                    //decode the base64 string to convert it to byte array
                    byte[] decodedString = Base64.decode(base64Img, Base64.DEFAULT);
                    //use this byte array to convert Bitmap decodedByte
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    MyRoomsAdapter.ModelRoomView roomType = new MyRoomsAdapter.ModelRoomView(description
                            , title, price, decodedByte);
                    roomList.add(roomType);
                    fillRecyclerView(roomList);
                }
                break;
        }
    }

    @Override
    public void getErrorResult(String url, String error) {
        switch (url){
            case GlobalVars.availabilityUrl:
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, url + ":" + error, Toast.LENGTH_LONG).show();
                break;
        }
    }

    //TODO Change this to more efficient design by not passing the bitmap around
    @Override
    public void imgClick(Bitmap bitmap) {
        ImageViewFragment fragment = ImageViewFragment.newInstance(bitmap);
        fragment.show(getFragmentManager(),"Image");
    }
}
