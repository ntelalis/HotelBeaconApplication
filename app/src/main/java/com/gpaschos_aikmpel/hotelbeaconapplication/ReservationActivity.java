package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ReservationActivity extends AppCompatActivity implements DatePickerFragment.DateSelected {

    private EditText etArrivalDate;
    private EditText etDepartureDate;
    private SimpleDateFormat simpleDateFormat;
    private Spinner spinner;
    private int persons;
    private ListView lvRooms;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        recyclerView = findViewById(R.id.rvReservationRecycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<MyRoomsAdapter.ModelRoomView> list = new ArrayList<>();
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_launcher_background);
        MyRoomsAdapter.ModelRoomView ha = new MyRoomsAdapter.ModelRoomView("Exei polla (kai kala)","Laxative Room",1000, icon);
        list.add(ha);
        Bitmap icon2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_launcher_background);
        ha = new MyRoomsAdapter.ModelRoomView("Exei liga (kai kaka)","Non Laxative Room",10,icon2);
        list.add(ha);
        Bitmap icon3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_launcher_background);
        ha = new MyRoomsAdapter.ModelRoomView("xaliaaa makriaaa","Dungeon Room",2, icon3);
        list.add(ha);
        adapter = new MyRoomsAdapter(list);
        recyclerView.setAdapter(adapter);
        simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();

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


    private void setSpinnerListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                persons = Integer.parseInt(spinner.getItemAtPosition(i).toString());
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
            bundle.putInt (DatePickerFragment.ET_TYPE,DatePickerFragment.etArrival);
        } else{
            String arrivalDate = etArrivalDate.getText().toString();
            if (arrivalDate.isEmpty()) {
                Toast.makeText(this, "Please select Arrival Date", Toast.LENGTH_SHORT).show();
                return;
            } else {
                bundle.putInt (DatePickerFragment.ET_TYPE,DatePickerFragment.etDeparture);
                calendar = Calendar.getInstance();
                try {
                    calendar.setTime(simpleDateFormat.parse(arrivalDate));
                    calendar.add(Calendar.DAY_OF_MONTH,1);
                    bundle.putLong("minDate",calendar.getTimeInMillis());
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
        calendar.set(year,month,day);
        String date = simpleDateFormat.format(calendar.getTime());

        if(type==DatePickerFragment.etArrival){
            etArrivalDate.setText(date);
            try {
                calendar.setTime(simpleDateFormat.parse(etDepartureDate.getText().toString()));
                long departureTime = calendar.getTimeInMillis();
                calendar.setTime(simpleDateFormat.parse(etArrivalDate.getText().toString()));
                calendar.add(Calendar.DAY_OF_MONTH,1);
                long arrivalTime = calendar.getTimeInMillis();
                if(arrivalTime>departureTime){
                    etDepartureDate.setText("");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else
            etDepartureDate.setText(date);
    }
}
