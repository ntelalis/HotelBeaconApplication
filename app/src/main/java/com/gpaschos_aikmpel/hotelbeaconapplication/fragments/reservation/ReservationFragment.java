package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.reservation;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.adapters.RoomAdapter;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Customer;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomType;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.RoomTypeCash;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.DatePickerFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.ImageViewFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.LocalVariables;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.Params;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;
import com.gpaschos_aikmpel.hotelbeaconapplication.utility.PickNumber;

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

public class ReservationFragment extends Fragment implements JsonListener, RoomAdapter.ClickCallbacks {

    private static final String TAG = ReservationFragment.class.getSimpleName();

    private EditText etArrivalDate;
    private EditText etDepartureDate;
    private SimpleDateFormat localizedFormat, mySQLFormat;
    private PickNumber pnAdults, pnChildren;
    private RecyclerView recyclerView;
    private ProgressBar pbLoading;
    private int reservationDays;
    private ReservationCallbacks listener;
    private Customer customer;

    public ReservationFragment() {
        // Required empty public constructor
    }

    public static ReservationFragment newInstance() {

        ReservationFragment fragment = new ReservationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localizedFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        mySQLFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        customer = RoomDB.getInstance(getContext()).customerDao().getCustomer();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reservation, container, false);

        etArrivalDate = v.findViewById(R.id.tietReservationArrival);
        etDepartureDate = v.findViewById(R.id.etReservationDeparture);
        recyclerView = v.findViewById(R.id.rvReservationRecycler);
        pbLoading = v.findViewById(R.id.pbAvailability);
        pnAdults = v.findViewById(R.id.pnReservationAdults);
        pnChildren = v.findViewById(R.id.pnReservationChildren);
        Button btnSearch = v.findViewById(R.id.btnReservationSearch);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

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

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });


        pnAdults.setOnValueChangedListener(new PickNumber.OnValueChangedListener() {
            @Override
            public void onValueChanged(int oldValue, int newValue) {
                recyclerView.setAdapter(null);
            }
        });

        pnChildren.setOnValueChangedListener(new PickNumber.OnValueChangedListener() {
            @Override
            public void onValueChanged(int oldValue, int newValue) {
                recyclerView.setAdapter(null);
            }
        });

        int maxAdults = RoomDB.getInstance(getContext()).roomTypeDao().getMaxAdults();
        pnAdults.setMaxValue(maxAdults);
        int maxChildren = RoomDB.getInstance(getContext()).roomTypeDao().getMaxChildren();
        pnChildren.setMaxValue(maxChildren);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ReservationCallbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ReservationCallbacks");
        }
    }

    private void search() {
        recyclerView.setAdapter(null);
        String arrivalDateLocal = etArrivalDate.getText().toString();
        String departureDateLocal = etDepartureDate.getText().toString();
        String adults = String.valueOf(pnAdults.getValue());
        String children = String.valueOf(pnChildren.getValue());

        if (!arrivalDateLocal.isEmpty() && !departureDateLocal.isEmpty() && !adults.isEmpty()) {

            Calendar calendarArrival = Calendar.getInstance();
            Calendar calendarDeparture = Calendar.getInstance();
            try {
                calendarArrival.setTime(localizedFormat.parse(arrivalDateLocal));
                String arrivalDateSQLString = mySQLFormat.format(calendarArrival.getTime());

                calendarDeparture.setTime(localizedFormat.parse(departureDateLocal));
                String departureDateSQLString = mySQLFormat.format(calendarDeparture.getTime());

                long reservationDaysInMillis = calendarDeparture.getTimeInMillis() - calendarArrival.getTimeInMillis();
                reservationDays = (int) (reservationDaysInMillis / 1000 / 60 / 60 / 24);

                Map<String, String> params = new HashMap<>();

                params.put(POST.availabilityArrivalDate, arrivalDateSQLString);
                params.put(POST.availabilityDepartureDate, departureDateSQLString);
                params.put(POST.availabilityAdults, adults);
                params.put(POST.availabilityChildren, children);

                VolleyQueue.getInstance(getContext()).jsonRequest(this, URL.availabilityUrl, params);

                pbLoading.setVisibility(View.VISIBLE);

            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Sorry There is a problem parsing the date", Toast.LENGTH_SHORT).show();
            }

        } else Toast.makeText(getContext(), "Please fill in fields", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        switch (url) {
            case URL.availabilityUrl:
                pbLoading.setVisibility(View.GONE);

                List<RoomAdapter.ModelRoomView> roomList = new ArrayList<>();

                JSONArray availabilityRoomTypeArray = json.getJSONArray(POST.availabilityRoomTypeArray);
                for (int i = 0; i < availabilityRoomTypeArray.length(); i++) {

                    int roomTypeID = availabilityRoomTypeArray.getInt(i);
                    int adults = pnAdults.getValue();
                    int children = pnChildren.getValue();
                    RoomDB roomDB = RoomDB.getInstance(getContext());


                    RoomType rt = roomDB.roomTypeDao().getRoomTypeById(roomTypeID);

                    RoomTypeCash rtc = roomDB.roomTypeCashDao().getRoomTypeCash(rt.getId(), adults,children);

                    String description = rt.getDescription();
                    String title = rt.getName();
                    int price = rtc.getCash();
                    Bitmap imageBitmap = LocalVariables.readImage(getContext(), rt.getImage());
                    String imageName = rt.getImage();


                    RoomAdapter.ModelRoomView roomType =
                            new RoomAdapter.ModelRoomView(roomTypeID, title, description, price, reservationDays, adults, imageBitmap, imageName);

                    roomList.add(roomType);
                }

                fillRecyclerView(roomList);
                recyclerView.getAdapter().notifyDataSetChanged();
                recyclerView.scheduleLayoutAnimation();
                break;
        }
    }

    @Override
    public void getErrorResult(String url, String error) {

        if (url.equals(URL.availabilityUrl)) {
            pbLoading.setVisibility(View.GONE);
        }
        Toast.makeText(getContext(), url + ": " + error, Toast.LENGTH_LONG).show();
    }

    public void fillRecyclerView(List<RoomAdapter.ModelRoomView> list) {
        RecyclerView.Adapter adapter = new RoomAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    public void pickedDate(int type, int day, int month, int year) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        String pickedDateLocal = localizedFormat.format(calendar.getTime());

        //Empty availability results after choosing new date either for arrival or departure
        if (recyclerView.getAdapter() != null) {
            fillRecyclerView(new ArrayList<RoomAdapter.ModelRoomView>());
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
                    Log.e(TAG, e.getLocalizedMessage());
                }
            }
        } else if (type == DatePickerFragment.etDeparture) {
            etDepartureDate.setText(pickedDateLocal);
        }

    }

    @Override
    public void imgClicked(String imgFileName) {
        ImageViewFragment fragment = ImageViewFragment.newInstance(imgFileName);
        if (getActivity() != null)
            fragment.show(getActivity().getSupportFragmentManager(), ImageViewFragment.TAG);
    }

    @Override
    public void bookRoom(RoomAdapter.ModelRoomView room) {

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
            Log.e(TAG, e.getLocalizedMessage());
            return;
        }

        int roomTypeID = room.roomTypeID;
        int adults = pnAdults.getValue();
        int children = pnChildren.getValue();
        listener.book(roomTypeID,arrivalDateSQL,departureDateSQL,adults,children);


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
                Toast.makeText(getContext(), "Please select Arrival Date", Toast.LENGTH_SHORT).show();
                return;

            } else {
                bundle.putInt(DatePickerFragment.EditTextType, DatePickerFragment.etDeparture);

                try {
                    calendar = Calendar.getInstance();
                    calendar.setTime(localizedFormat.parse(arrivalDateLocal));
                    //DEBUG CODE
                    //calendar.add(Calendar.DAY_OF_MONTH, 1);
                    bundle.putLong(DatePickerFragment.minimumDate_KEY, calendar.getTimeInMillis());
                    bundle.putLong(DatePickerFragment.maximumDate_KEY, maxTimeForReservationDeparture);

                } catch (ParseException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    return;
                }
            }
        }
        datePickerFragment.setArguments(bundle);
        if (getActivity() != null)
            datePickerFragment.show(getActivity().getSupportFragmentManager(), DatePickerFragment.TAG);
    }

}